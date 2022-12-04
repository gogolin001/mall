package com.lam.mall.mbg.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志
 */
@Getter
@Setter
@TableName("sys_operate_log")
public class SysOperateLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 日志主键 */
    @Schema(title = "操作id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 操作模块 */
    @Schema(title = "操作模块")
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    @Schema(title = "业务类型:0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Byte businessType;

    /** 请求方法 */
    @Schema(title = "请求方法")
    private String method;

    /** 请求方式 */
    @Schema(title = "请求方式")
    private String requestMethod;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @Schema(title = "操作类别:0=其它,1=后台用户,2=手机端用户")
    private Integer operateType;

    /** 操作人员 */
    @Schema(title = "操作人员")
    private String operateName;

    /** 部门名称 */
    @Schema(title = "部门名称")
    private String deptName;

    /** 请求url */
    @Schema(title = "请求地址")
    private String url;

    /** 操作地址 */
    @Schema(title = "操作地址")
    private String ip;

    /** 操作地点 */
    @Schema(title = "操作地点")
    private String location;

    /** 请求参数 */
    @Schema(title = "请求参数")
    private String operateParam;

    /** 返回参数 */
    @Schema(title = "返回参数")
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    @Schema(title = "状态:1=正常,0=异常")
    private Integer status;

    /** 错误消息 */
    @Schema(title = "错误消息")
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(title = "操作时间")
    private LocalDateTime operateTime;
}
