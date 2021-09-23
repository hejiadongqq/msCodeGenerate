package ${packageName};


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * ${entityName}DTO类
 *
 * @author: Automatically created By Albert utils
 * @time: ${dateTime}
 */
@ApiModel
@Data
public class ${className} ${extends} {

<#list fields as field>
    <#if field.name = "groupId" || field.name = "appId" || field.name = "deleted">
        @ApiModelProperty(value = "${field.desc}", hidden = true)
        @SwaggerIgnoreProperty
    <#else>
        @ApiModelProperty(value = "${field.desc}")
    </#if>
    private ${field.type} ${field.name};

</#list>

//---------------- 自动生成结束 -------------

}
