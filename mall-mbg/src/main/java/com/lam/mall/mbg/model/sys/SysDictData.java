package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典数据表
 */
@Getter
@Setter
@TableName("sys_dict_data")
public class SysDictData  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典id")
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;

    @ApiModelProperty(value = "字典排序")
    private Byte sort;

    @ApiModelProperty(value = "字典标签")
    private String label;

    @ApiModelProperty(value = "字典键值")
    private String value;

    @ApiModelProperty(value = "字典类型")
    private String type;

    @ApiModelProperty(value = "是否默认:1=是,0=否")
    private boolean isDefault;

    @ApiModelProperty(value = "状态:0=正常,1=停用")
    private boolean status;

    @TableLogic
    private boolean deleted;
}
