package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 岗位
 */
@Getter
@Setter
@TableName("sys_post")
public class SysPost implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "岗位id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "岗位编码")
    private String postCode;

    @Schema(title = "岗位名称")
    private String postName;

    @Schema(title = "岗位排序")
    private Integer postSort;

    @Schema(title = "状态：0=正常,1=停用")
    private Boolean status;

    @TableLogic
    private Boolean deleted;
}
