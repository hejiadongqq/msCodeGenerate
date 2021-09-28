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

<#include "module/fields.ftl">

//---------------- 自动生成结束 -------------

}
