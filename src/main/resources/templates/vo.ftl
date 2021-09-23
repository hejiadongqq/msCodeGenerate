package ${packageName};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import com.service.commons.model.vo.BaseVO;

/**
 * ${entityName}VO类
 *
 * @author Automatically created By Albert utils
 * @date ${dateTime}
 */
@ApiModel
@Data
public class ${className} ${extends} {

<#list fields as field>
    @ApiModelProperty(value = "${field.desc}")
    <#if field.isEnum>
    </#if>
    private ${field.type} ${field.name};

</#list>
//---------------- 自动生成结束 -------------

}
