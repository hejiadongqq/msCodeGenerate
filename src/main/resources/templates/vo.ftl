package ${packageName};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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

<#include "module/fields.ftl">

//---------------- 自动生成结束 -------------

}
