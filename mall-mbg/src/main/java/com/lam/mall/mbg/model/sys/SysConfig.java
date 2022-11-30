package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统参数表
 */
@Getter
@Setter
@TableName("sys_config")
public class SysConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @Schema(title = "参数id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 参数名称 */
    @Schema(title = "参数名称")
    private String configName;

    /** 参数键名 */
    @Schema(title = "参数键名")
    private String configKey;

    /** 参数键值 */
    @Schema(title = "参数键值")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @Schema(title = "系统内置：true=是,false=否")
    private Boolean isDefault;

    @Schema(title = "所属客户端")
    private String clientType;

    @Schema(title = "参数类型：String Boolean Number File")
    private String configType;
}
