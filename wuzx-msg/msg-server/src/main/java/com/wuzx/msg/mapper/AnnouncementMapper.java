package com.wuzx.msg.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.pagehelper.PageInfo;
import com.wuzx.msg.constant.AnnouncementStatusEnum;
import com.wuzx.msg.entity.Announcement;
import com.wuzx.msg.qo.AnnouncementQO;
import com.wuzx.msg.vo.AnnouncementPageVO;
import com.yh.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.yh.mybatis.plus.domain.PageParam;
import com.yh.mybatis.plus.mapper.ExtendMapper;
import com.yh.mybatis.plus.toolkit.WrappersX;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 09:50
 **/
public interface AnnouncementMapper extends ExtendMapper<Announcement> {

    /**
     * 分页查询
     * @param pageParam 分页参数
     * @param qo 查询对象
     * @return 分页结果数据 PageResult
     */
    default PageInfo<AnnouncementPageVO> queryPage(PageParam pageParam, AnnouncementQO qo) {
        IPage<Announcement> page = this.prodPage(pageParam);
        LambdaQueryWrapperX<Announcement> wrapperX = WrappersX.lambdaAliasQueryX(Announcement.class)
                .likeIfPresent(Announcement::getTitle, qo.getTitle())
                .inIfPresent(Announcement::getStatus, (Object[]) qo.getStatus())
                .eqIfPresent(Announcement::getRecipientFilterType, qo.getRecipientFilterType())
                .orderByDesc(Announcement::getId);
        IPage<AnnouncementPageVO> voPage = this.selectByPage(page, wrapperX);
        final PageInfo<AnnouncementPageVO> pageInfo = PageInfo.of(voPage.getRecords());
        pageInfo.setTotal(voPage.getTotal());
        return pageInfo;
    }

    /**
     * 分页查询通知
     * @param page 分页封装对象
     * @param wrapper 条件构造器
     * @return 分页封装对象
     */
    IPage<AnnouncementPageVO> selectByPage(IPage<Announcement> page,
                                           @Param(Constants.WRAPPER) Wrapper<Announcement> wrapper);


    List<Announcement> listUserAnnouncements(@Param("userId") Integer userId, @Param("pulled") boolean pulled);


    /**
     * 更新公共（限制只能更新未发布的公共）
     * @param announcement 公共信息
     * @return 更新是否成功
     */
    default boolean updateIfUnpublished(Announcement announcement) {
        int flag = this.update(announcement,
                Wrappers.<Announcement>lambdaUpdate().eq(Announcement::getId, announcement.getId())
                        .eq(Announcement::getStatus, AnnouncementStatusEnum.UNPUBLISHED.getValue()));
        return SqlHelper.retBool(flag);
    }
}
