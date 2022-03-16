package ${packageName};

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.service.commons.model.proxy.PageProxy;
import com.service.commons.model.vo.PageVO;
import java.util.*;

/**
 * ${before}${entityName}转换器
 *
 * @author Automatically created By Albert utils
 * @date ${dateTime}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className} {

    // bff to dto
    ${entityName}PersistDTO bff2dto(${before}${entityName}PersistDTO bff);

    Collection<${entityName}PersistDTO> bff2dtoList(Collection<${before}${entityName}PersistDTO> bff);

    ${entityName}UpdateDTO bff2dto(${before}${entityName}UpdateDTO bff);

    ${entityName}SearchDTO bff2dto(${before}${entityName}SearchDTO bff);

    ${entityName}VO bff2dto(${before}${entityName}VO bff);

    Collection<${entityName}VO> bff2VoList(Collection<${before}${entityName}VO> bff);

    PageVO<${entityName}VO> bff2dto(PageProxy<${before}${entityName}VO> bff);

    PageVO<${entityName}VO> bff2dto(PageVO<${before}${entityName}VO> bff);

    // end --//

    // dto to bff
    ${before}${entityName}PersistDTO dto2bff(${entityName}PersistDTO dto);

    Collection<${before}${entityName}PersistDTO> dto2bff(Collection<${entityName}PersistDTO> dto);

    ${before}${entityName}UpdateDTO dto2bff(${entityName}UpdateDTO dto);

    ${before}${entityName}SearchDTO dto2bff(${entityName}SearchDTO dto);

    ${before}${entityName}VO dto2bff(${entityName}VO dto);

    Collection<${before}${entityName}VO> dto2bffList(Collection<${entityName}VO> dto);

    PageVO<${before}${entityName}VO> dto2bff(PageProxy<${entityName}VO> dto);

    PageVO<${before}${entityName}VO> dto2bff(PageVO<${entityName}VO> dto);

    // end --//
//---------------- 自动生成结束 -------------


}
