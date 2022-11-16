package com.wuzx.msg.service;

import com.github.pagehelper.PageInfo;
import com.wuzx.msg.dto.AnnouncementDTO;
import com.wuzx.msg.entity.Announcement;
import com.wuzx.msg.qo.AnnouncementQO;
import com.wuzx.msg.vo.AnnouncementPageVO;
import com.yh.mybatis.plus.domain.PageParam;
import com.yh.mybatis.plus.service.ExtendService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 公告信息接口
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/11 10:21
 **/
public interface AnnouncementService extends ExtendService<Announcement> {

    /**
     * 根据QueryObject查询分页数据
     * @param announcementQO 查询参数对象
     * @return PageResult<AnnouncementVO> 分页数据
     */
    PageInfo<AnnouncementPageVO> queryPage(PageParam page, AnnouncementQO qo);


    /**
     * 创建公告
     * @param announcementDTO 公告信息
     * @return boolean
     */
    boolean addAnnouncement(AnnouncementDTO announcementDTO);

    /**
     * 更新公告信息
     * @param announcementDTO announcementDTO
     * @return boolean
     */
    boolean updateAnnouncement(AnnouncementDTO announcementDTO);

    /**
     * 发布公告信息
     * @param announcementId 公告ID
     * @return boolean
     */
    boolean publish(Long announcementId);

    /**
     * 关闭公告信息
     * @param announcementId 公告ID
     * @return boolean
     */
    boolean close(Long announcementId);

    /**
     * 批量上传公告图片
     * @param files 图片文件
     * @return 上传后的图片相对路径集合
     */
    List<String> uploadImages(List<MultipartFile> files);

    /**
     * 当前用户未拉取过的发布中，且满足失效时间的公告信息
     * @param userId 用户id
     * @return List<Announcement>
     */
    List<Announcement> listUnPulled(Integer userId);

    /**
     * 获取用户拉取过的发布中，且满足失效时间的公告信息
     * @param userId 用户id
     * @return List<Announcement>
     */
    List<Announcement> listActiveAnnouncements(Integer userId);

    /**
     * 公告详情信息
     * @param id
     * @return
     */
    AnnouncementPageVO detail(Long id);
}
