package com.wuzx.msg.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 11:59
 **/

@Data
@ApiModel(description = "用户查询对象")
@Builder
public class UserQO {

    @ApiModelProperty(value = "用户ids")
    private List<Integer> userIds;


    @ApiModelProperty(value = "部门ids")
    private List<Integer> deptIds;
}
