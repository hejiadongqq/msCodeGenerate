package ${packageName};

import com.datingpaas.common.code.EmailCodeResponse;
import com.datingpaas.common.code.UserCodeResponse;
import com.datingpaas.model.dto.*;
import com.datingpaas.model.vo.*;
import com.datingpaas.service.*;
import com.datingpaas.model.entity.*;
import com.service.commons.api.BaseController;
import com.service.commons.model.resp.Response;
import com.service.commons.model.vo.PageVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import com.service.commons.model.proxy.PageProxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import javax.validation.constraints.*;


/**
* ${entityName}控制器
* @author Automatically created By Albert utils
* @date ${dateTime}
*/
@Api(tags = "${entityName} API")
@RestController
@RequestMapping("/v1/account")
@Slf4j
public class AccountController extends BaseController {
    @Autowired
    private ${entityName}Service service;

    @Autowired
    private ${entityName}Converter converter;

    @ApiOperation(value = "分页获取 ${entityName}")
    @GetMapping
    public Response<PageVO<${entityName}VO>> page${entityName}(@RequestBody @Valid ${entityName}SearchDTO request) {
        Page<${entityName}> search = service.search(request);
        return Response.success(converter.page2PageVO(new PageProxy<>(search)));
    }

    @ApiOperation(value = "不分页获取 ${entityName}")
    @GetMapping
    public Response<Collection<${entityName}VO>> list${entityName}(@RequestBody @Valid ${entityName}SearchDTO request) {
        List<${entityName}> search = service.list(request);
        return Response.success(converter.entity2VO(search));
    }

    @ApiOperation(value = "获取 ${entityName} 详情")
    @GetMapping("/{id}")
    public Response<${entityName}VO> get(@PathVariable("id") Long id) {
        ${entityName}VO vo = service.getById(id);
        return Response.success(vo);
    }

    @ApiOperation(value = "保存 ${entityName}")
    @PostMapping
    public Response<${entityName}VO> save(@RequestBody @Valid ${entityName}PersistDTO request) {
        ${entityName}VO save = service.save(request);
        return Response.success(save);
    }

    @ApiOperation(value = "修改 ${entityName}")
    @PutMapping
    public Response<${entityName}VO> update(@RequestBody @Valid ${entityName}UpdateDTO request) {
        ${entityName}VO save = service.update(request);
        return Response.success(save);
    }

}
