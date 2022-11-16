package com.wuzx.msg.controller;


import com.github.pagehelper.PageInfo;
import com.wuzx.msg.dto.AnnouncementDTO;
import com.wuzx.msg.entity.Announcement;
import com.wuzx.msg.qo.AnnouncementQO;
import com.wuzx.msg.service.AnnouncementService;
import com.wuzx.msg.vo.AnnouncementPageVO;
import com.yh.mybatis.plus.domain.PageParam;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 公告信息
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/announcement")
@Api(value = "公告信息管理", tags = "公告信息管理")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /**
     * 分页查询
     *
     * @param announcementQO 公告信息查询对象
     * @return R 通用返回体
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageInfo<AnnouncementPageVO> getAnnouncementPage(@Validated PageParam pageParam, AnnouncementQO announcementQO) {
        return announcementService.queryPage(pageParam, announcementQO);
    }


    /**
     * 新增公告信息
     *
     * @param announcementDTO 公告信息
     * @return R 通用返回体
     */
    @PostMapping
    @Operation(summary = "新增公告信息", description = "新增公告信息")
    public boolean save(@Valid @RequestBody AnnouncementDTO announcementDTO) {
        return announcementService.addAnnouncement(announcementDTO);
    }


    /**
     * 修改公告信息
     *
     * @param announcementDTO 公告信息
     * @return R 通用返回体
     */
    @PutMapping
    @Operation(summary = "修改公告信息", description = "修改公告信息")
    public boolean updateById(@Valid @RequestBody AnnouncementDTO announcementDTO) {
        return announcementService.updateAnnouncement(announcementDTO);
    }

    /**
     * 通过id删除公告信息
     *
     * @param id id
     * @return R 通用返回体
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "通过id删除公告信息", description = "通过id删除公告信息")
    public boolean removeById(@PathVariable("id") Long id) {
        return announcementService.removeById(id);
    }


    /**
     * 发布公告信息
     *
     * @return R 通用返回体
     */
    @PatchMapping("/publish/{announcementId}")
    @Operation(summary = "发布公告信息", description = "发布公告信息")
    public boolean enableAnnouncement(@PathVariable("announcementId") Long announcementId) {
        return announcementService.publish(announcementId);
    }

    /**
     * 关闭公告信息
     *
     * @return R 通用返回体
     */
    @PatchMapping("/close/{announcementId}")
    @Operation(summary = "关闭公告信息", description = "关闭公告信息")
    public boolean disableAnnouncement(@PathVariable("announcementId") Long announcementId) {
        return announcementService.close(announcementId);
    }


    /**
     * 公告详情
     */
    @GetMapping("/detail/{id}")
    @Operation(summary = "公告详情", description = "公告详情")
    public AnnouncementPageVO detail(@PathVariable("id") Long id) {
        return announcementService.detail(id);
    }
}