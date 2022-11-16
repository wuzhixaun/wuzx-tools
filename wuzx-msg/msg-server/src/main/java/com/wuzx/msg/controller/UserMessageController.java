package com.wuzx.msg.controller;

import com.github.pagehelper.PageInfo;
import com.wuzx.msg.entity.UserMessage;
import com.wuzx.msg.pojo.MessageNotifyInfo;
import com.wuzx.msg.qo.UserMessageQO;
import com.wuzx.msg.service.UserMessageService;
import com.wuzx.msg.vo.UserMessagePageVO;
import com.yh.mybatis.plus.domain.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户公告表
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 14:44
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/user/announcement")
@Api(description = "用户消息通知")
public class UserMessageController {

    private final UserMessageService userMessageService;

    /**
     * 分页查询
     *
     * @param pageParam     分页参数
     * @param userMessageQO 用户公告表查询对象
     * @return R 通用返回体
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageInfo<UserMessagePageVO> getUserAnnouncementPage(@Validated PageParam pageParam,
                                                               UserMessageQO userMessageQO) {
        return userMessageService.queryPage(pageParam, userMessageQO);
    }

    @PatchMapping("/read/{messageId}")
    @Operation(summary = "用户公告已读上报", description = "用户消息已读上报")
    @ApiOperation("用户公告已读上报")
    public void readAnnouncement(@PathVariable("messageId") Long messageId) {
        userMessageService.readAnnouncement(messageId);
    }


    @ApiOperation("用户消息列表")
    @PostMapping("/list")
    public List<UserMessage> getUserMessageList(@RequestBody UserMessageQO userMessageQO) {
        return userMessageService.getUserMessageList(userMessageQO);
    }

    @ApiOperation("生成站内待办消息")
    @PostMapping("/generate")
    public void generateUserMessage(@RequestBody MessageNotifyInfo messageNotifyInfo) {
        userMessageService.generateUserMessage(messageNotifyInfo);
    }
}