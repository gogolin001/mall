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
 * 字典数据表
 */
@Getter
@Setter
@TableName("sys_dict_data")
public class SysDictData  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "字典id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "字典分类id")
    private Long typeId;

    @Schema(title = "字典排序")
    private Byte sort;

    @Schema(title = "字典标签")
    private String label;

    @Schema(title = "字典键值")
    private String dataValue;

    @Schema(title = "字典类型")
    private String dataType;

    @Schema(title = "是否默认:1=是,0=否")
    private Boolean isDefault;

    @Schema(title = "状态:1=正常,0=停用")
    private Boolean status;

    @TableLogic
    private Boolean deleted;
}
