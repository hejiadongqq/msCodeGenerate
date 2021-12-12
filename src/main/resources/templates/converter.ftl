package ${packageName};

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.service.commons.model.proxy.PageProxy;
import com.service.commons.model.vo.PageVO;
import java.util.*;

/**
 * ${entityName}转换器
 *
 * @author Automatically created By Albert utils
 * @date ${dateTime}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className} {

    ${entityName} dto2Entity(${entityName}PersistDTO dto);

    ${entityName}SearchDTO dto2dto(${entityName}PersistDTO dto);

    ${entityName}PersistDTO dto2dto(${entityName}SearchDTO dto);

    Collection<${entityName}> dto2Entity(Collection<${entityName}PersistDTO> dto);

    ${entityName} dto2Entity(${entityName}UpdateDTO dto);

    ${entityName} dto2Entity(${entityName}SearchDTO dto);

    ${entityName}VO entity2VO(${entityName} entity);

    Collection<${entityName}VO> entity2VO(Collection<${entityName}> entity);

    PageVO<${entityName}VO> page2PageVO(PageProxy<${entityName}> page);
//---------------- 自动生成结束 -------------

}
