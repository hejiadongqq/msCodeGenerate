package com.datingpass.utils.contorller;

import com.datingpass.utils.services.EntityServices;
import com.datingpass.utils.utils.Utils;
import com.service.commons.config.auth.UserToken;
import com.service.commons.model.resp.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: Albert
 * @date: 2021-08-26 2:46 PM
 * @desc:
 */
@Api(tags = "拉黑 API")
@RestController
@RequestMapping("/test")
public class MainController extends BaseController {
    @Autowired
    private EntityServices entityServices;

    @ApiOperation(value = "生成entity")
    @PostMapping("/makeEntity")
    public Response makeEntity(@RequestBody @Valid EntityServices.MakeEntityRequest dto) {
        try {
            entityServices.makeEntity(dto);

            return Response.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failed(999, e.getMessage());
        }
    }

    @ApiOperation(value = "更新entity")
    @PostMapping("/updateEntity")
    public Response updateEntity(@RequestBody @Valid EntityServices.MakeEntityRequest dto) {
        try {
            entityServices.updateEntity(dto);

            return Response.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failed(999, e.getMessage());
        }
    }

    @ApiOperation(value = "移动备份文件到备份目录")
    @PostMapping("/moveBackup")
    public Response moveBackup() {
        try {
            entityServices.moveBackup();

            return Response.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failed(999, e.getMessage());
        }
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/getUser/{token}")
    public Response<UserToken> getUser(@PathVariable("token") String token) {
        return Response.success(Utils.getUserToken(token));
    }

    @ApiOperation(value = "根据id生成token")
    @GetMapping("/makeToken/{id}")
    public Response<String> getUser(@PathVariable("id") Long id) {
        return Response.success(Utils.makeToken(id));
    }

    @ApiOperation(value = "用户密码和编码后的密文md5比对是否一致")
    @GetMapping("/userPwd/match/{pwd}/{md5}")
    public Response<Boolean> getUser(@PathVariable("pwd") String pwd, @PathVariable("md5") String md5) {
        return Response.success(Utils.get_passwordEncoder().matches(pwd, md5));
    }

    @ApiOperation(value = "用户密码比对密码与md5是否一致")
    @GetMapping("/userPwd/encode/{pwd}")
    public Response<String> encode(@PathVariable("pwd") String pwd) {
        return Response.success(Utils.encode(pwd));
    }
}
