package com.wuzx.msg.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yh.mybatis.plus.alias.TableAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:48
 **/
@Data
@TableAlias(User.TABLE_ALIAS)
@TableName(value = "tb_user", autoResultMap = true)
@Schema(title = "用户信息")
public class User {

    public static final String TABLE_ALIAS = "user";

    @TableId
    @Schema(title = "ID")
    private Integer id;

    @Schema(title = "名称")
    private String nickName;

    private String headImage;

    private String username;

    private String password;

    private String passWd;

    private String phone;

    private String email;

    private Date lastLoginTime;
    private String lastLoginIp;

    private String status;

    private Integer pwdFailnum;

    private Date expDate;


    private Date createTime;


    private Date updateTime;

    private Integer createUserId;
    private String createUserName;

    private Integer updateUserId;
    private String updateUserName;

    private Integer deptId;

    private String nickNameCn;

    private String wechatNo;

    /**
     * 修改密码次数
     */
    private Integer modifyPassNum;

    private Integer relationId;

    private Date modifyPassLastTime;
}
