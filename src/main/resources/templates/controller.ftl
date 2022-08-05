package ${packageName};

import com.service.commons.model.resp.Response;
import com.service.commons.model.vo.PageVO;
import io.swagger.annotations.*;
import com.service.commons.config.auth.UserAuthContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import javax.validation.constraints.*;
import com.service.commons.model.proxy.PageProxy;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import javax.validation.constraints.*;


/**
 * ${entityName}控制器
 *
 * @author Automatically created By Albert utils
 * @date ${dateTime}
 */
@Api(tags = "${entityName} API")
@RestController
@RequestMapping("/v1/${controllerUrl}")
@Slf4j
public class ${entityName}Controller extends BaseController {
    @Resource
    private ${entityName}Service service;

    @Resource
    private ${entityName}Converter converter;

    @Resource
    private ${before}${entityName}Converter bffConverter;

    @ApiOperation(value = "分页获取")
    @PostMapping("/search")
    public Response<PageVO<${entityName}VO>> page${entityName}(@RequestBody @Valid ${before}${entityName}SearchDTO request) {
        Long userId = UserAuthContext.getId();
        Page<${entityName}> search = service.search(bffConverter.bff2dto(request));
        return Response.success(converter.page2PageVO(new PageProxy(search)));
    }

    @ApiOperation(value = "不分页获取")
    @PostMapping("/get-all")
    public Response<Collection<${entityName}VO>> list${entityName}(
        @RequestBody @Valid ${before}${entityName}SearchDTO request) {
        List<${entityName}> search = service.list(bffConverter.bff2dto(request));
        return Response.success(converter.entity2VO(search));
    }

    @ApiOperation(value = "获取 详情")
    @GetMapping("/{id}")
    public Response<${entityName}VO> get(@NotBlank(message = "id not null!")@PathVariable("id") String id) {
        ${entityName}VO vo = service.getById(Long.valueOf(id));
        return Response.success(vo);
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public Response<${entityName}VO> save(@RequestBody @Valid ${before}${entityName}PersistDTO request) {
        final Long id = service.save(bffConverter.bff2dto(request));
        return Response.success(service.getById(id);
    }

    @ApiOperation(value = "修改")
    @PutMapping
    public Response update(@RequestBody @Valid ${entityName}UpdateDTO request) {
        service.update(request);
        return Response.success();
    }

//---------------- 自动生成结束 -------------

}
