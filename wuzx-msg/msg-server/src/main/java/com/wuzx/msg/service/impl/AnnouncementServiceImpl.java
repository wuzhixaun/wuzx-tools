package com.wuzx.msg.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.pagehelper.PageInfo;
import com.yh.common.exception.ServiceException;
import com.wuzx.msg.constant.AnnouncementStatusEnum;
import com.wuzx.msg.constant.BooleanEnum;
import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import com.wuzx.msg.converter.AnnouncementConverter;
import com.wuzx.msg.converter.NotifyInfoConverter;
import com.wuzx.msg.dto.AnnouncementDTO;
import com.wuzx.msg.entity.Announcement;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.event.AnnouncementCloseEvent;
import com.wuzx.msg.event.NotifyPublishEvent;
import com.wuzx.msg.mapper.AnnouncementMapper;
import com.wuzx.msg.pojo.NotifyInfo;
import com.wuzx.msg.qo.AnnouncementQO;
import com.wuzx.msg.qo.UserQO;
import com.wuzx.msg.service.AnnouncementService;
import com.wuzx.msg.service.UserService;
import com.wuzx.msg.vo.AnnouncementPageVO;
import com.yh.mybatis.plus.domain.PageParam;
import com.yh.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公告信息
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 10:25
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl extends ExtendServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {


    private final ApplicationEventPublisher publisher;

    private final UserService userService;

    /**
     * 根据QueryObject查询分页数据
     * @param pageParam 分页参数
     * @param qo 查询参数对象
     * @return PageResult<AnnouncementVO> 分页数据
     */
    @Override
    public PageInfo<AnnouncementPageVO> queryPage(PageParam pageParam, AnnouncementQO qo) {
        return baseMapper.queryPage(pageParam, qo);
    }
    /**
     * 创建公告
     * @param announcementDTO 公告信息
     * @return boolean
     */
    @Override
    public boolean addAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = AnnouncementConverter.INSTANCE.dtoToPo(announcementDTO);
        announcement.setId(null);
        int flag = baseMapper.insert(announcement);
        boolean inserted = SqlHelper.retBool(flag);
        // 公告发布事件
        boolean isPublishStatus = announcement.getStatus() == AnnouncementStatusEnum.ENABLED.getValue();
        if (inserted && isPublishStatus) {
            this.onAnnouncementPublish(announcement);
        }
        return inserted;
    }

    /**
     * 更新公告信息
     * @param announcementDTO announcementDTO
     * @return boolean
     */
    @Override
    public boolean updateAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement oldAnnouncement = baseMapper.selectById(announcementDTO.getId());
        if (oldAnnouncement.getStatus() != AnnouncementStatusEnum.UNPUBLISHED.getValue()) {
            throw new ServiceException("不允许修改已经发布过的公告！");
        }

        Announcement announcement = AnnouncementConverter.INSTANCE.dtoToPo(announcementDTO);
        // 不允许修改为《发布中》以外的状态
        boolean isPublishStatus = announcement.getStatus() == AnnouncementStatusEnum.ENABLED.getValue();
        if (!isPublishStatus) {
            announcement.setStatus(null);
        }
        // 保证当前状态未被修改过
        boolean isUpdated = baseMapper.updateIfUnpublished(announcement);
        // 公告发布事件
        if (isUpdated && isPublishStatus) {
            this.onAnnouncementPublish(announcement);
        }
        return isUpdated;
    }

    /**
     * 发布公告信息
     * @param announcementId 公告ID
     * @return boolean
     */
    @Override
    public boolean publish(Long announcementId) {
        Announcement announcement = baseMapper.selectById(announcementId);
        if (announcement.getStatus() != AnnouncementStatusEnum.UNPUBLISHED.getValue()) {
            throw new ServiceException( "不允许修改已经发布过的公告！");
        }
        if (BooleanEnum.TRUE.getValue() != announcement.getImmortal()
                && LocalDateTime.now().isAfter(announcement.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
            throw new ServiceException( "公告失效时间必须迟于当前时间！");
        }

        // 更新公共至发布状态
        Announcement entity = new Announcement();
        entity.setId(announcementId);
        entity.setStatus(AnnouncementStatusEnum.ENABLED.getValue());
        boolean isUpdated = baseMapper.updateIfUnpublished(entity);
        if (isUpdated) {
            announcement.setStatus(AnnouncementStatusEnum.ENABLED.getValue());
            this.onAnnouncementPublish(announcement);
        }
        return isUpdated;
    }

    /**
     * 关闭公告信息
     * @param announcementId 公告ID
     * @return boolean
     */
    @Override
    public boolean close(Long announcementId) {
        Announcement announcement = new Announcement();
        announcement.setId(announcementId);
        announcement.setStatus(AnnouncementStatusEnum.DISABLED.getValue());
        int flag = baseMapper.updateById(announcement);
        boolean isUpdated = SqlHelper.retBool(flag);
        if (isUpdated) {
            publisher.publishEvent(new AnnouncementCloseEvent(announcementId));
        }
        return isUpdated;
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> files) {
        return null;
    }

    /**
     * 当前用户未拉取过的发布中，且满足失效时间的公告信息
     * @return List<Announcement>
     */
    @Override
    public List<Announcement> listUnPulled(Integer userId) {
        return baseMapper.listUserAnnouncements(userId, false);
    }

    /**
     * 获取用户拉取过的发布中，且满足失效时间的公告信息
     * @param userId 用户id
     * @return List<Announcement>
     */
    @Override
    public List<Announcement> listActiveAnnouncements(Integer userId) {
        return baseMapper.listUserAnnouncements(userId, true);
    }

    /**
     * 公告发布事件
     * @param announcement 公告信息
     */
    private void onAnnouncementPublish(Announcement announcement) {
        NotifyInfo notifyInfo = NotifyInfoConverter.INSTANCE.fromAnnouncement(announcement);
        publisher.publishEvent(new NotifyPublishEvent(notifyInfo));
    }


    @Override
    public AnnouncementPageVO detail(Long id) {
        final Announcement announcement = baseMapper.selectById(id);
        final AnnouncementPageVO announcementPageVO = AnnouncementConverter.INSTANCE.poToPageVo(announcement);
        if (NotifyRecipientFilterTypeEnum.SPECIFY_USER.getValue() == announcement.getRecipientFilterType()) {
            List<Integer> userIds = announcement.getRecipientFilterCondition().stream().map(Integer.class::cast).collect(Collectors.toList());
            // 根据ids查询用户信息
            announcementPageVO.setUserNameList(userService.getUserList(UserQO.builder().userIds(userIds).build()).stream().map(User::getUsername).collect(Collectors.toList()));
        }

        return announcementPageVO;
    }
}
