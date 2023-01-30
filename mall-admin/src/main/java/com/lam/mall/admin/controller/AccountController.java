package com.lam.mall.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lam.mall.admin.dto.CaptchaParam;
import com.lam.mall.admin.dto.UpdateUserPasswordParam;
import com.lam.mall.admin.dto.UserLoginParam;
import com.lam.mall.admin.dto.UserParam;
import com.lam.mall.admin.service.RoleService;
import com.lam.mall.admin.service.SysUserService;
import com.lam.mall.common.api.CommonPage;
import com.lam.mall.common.api.CommonResult;
import com.lam.mall.mbg.model.sys.SysRole;
import com.lam.mall.mbg.model.sys.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "账号模块")
@RestController
@RequestMapping("/api/v1/admin/account")
public class AccountController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final SysUserService userService;

    private final RoleService roleService;

    @Autowired
    public AccountController(SysUserService sysUserService, RoleService roleService){
        this.userService = sysUserService;
        this.roleService = roleService;
    }

    @Operation(summary = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<SysUser> register(@Validated UserParam userParam) {
        SysUser SysUser = userService.register(userParam);
        if (SysUser == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(SysUser);
    }

    @Operation(summary = "登录以后返回token")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody UserLoginParam userLoginParam){
        String token = userService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }


    @Operation(summary = "刷新token")
    @GetMapping(value = "/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = userService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    public CommonResult getAdminInfo(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        SysUser SysUser = userService.getUserByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", SysUser.getUsername());
        data.put("menus", userService.getMenu(SysUser.getId()));
        data.put("icon", SysUser.getIcon());
        List<SysRole> roleList = userService.getRoleList(SysUser.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(SysRole::getRoleName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }

    @Operation(summary = "登出功能")
    @PostMapping(value = "/logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @Operation(summary = "根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<SysUser>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage<SysUser> adminList = userService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @Operation(summary = "获取指定用户信息")
    @GetMapping(value = "/{id}")
    public CommonResult<SysUser> getItem(@PathVariable Long id) {
        SysUser admin = userService.getItem(id);
        return CommonResult.success(admin);
    }

    @Operation(summary = "修改指定用户信息")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody SysUser admin) {
        int count = userService.update(id, admin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "修改指定用户密码")
    @PostMapping(value = "/updatePassword")
    public CommonResult updatePassword(@Validated @RequestBody UpdateUserPasswordParam updatePasswordParam) {
        int status = userService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @Operation(summary = "删除指定用户信息")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        int count = userService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "修改帐号状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Boolean status) {
        SysUser SysUser = new SysUser();
        SysUser.setStatus(status);
        int count = userService.update(id,SysUser);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "给用户分配角色")
    @PostMapping(value = "/role/update")
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = userService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "获取指定用户的角色")
    @GetMapping(value = "/role/{adminId}")
    public CommonResult<List<SysRole>> getRoleList(@PathVariable Long adminId) {
        List<SysRole> roleList = userService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    /**
     * 获取验证码图片
     * @return
     */
    @GetMapping(value = "/captchaImage")
    public CommonResult<CaptchaParam> captchaImage(){
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(100, 40, 4, 4);
        captcha.setGenerator(new MathGenerator(1)); // 自定义验证码内容为四则运算方式
        captcha.createCode(); // 生成code

        return CommonResult.success(new CaptchaParam(IdUtil.simpleUUID(),captcha.getImageBase64()));
    }
}
