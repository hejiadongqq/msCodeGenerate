package ${packageName};

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.datingpaas.model.dto.*;
import com.datingpaas.model.vo.*;
import com.service.commons.model.proxy.PageProxy;
import com.service.commons.model.vo.PageVO;
import java.util.*;

/**
* ${entityName}转换器
* @author Automatically created By Albert utils
* @date ${dateTime}
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className} {


    ${entityName}PersistDTO dto2dto(com.datingpaas.dto.${entityName}PersistDTO dto);

    Collection<${entityName}PersistDTO> dto2dto(Collection<com.datingpaas.dto.${entityName}PersistDTO> dto);

    ${entityName}UpdateDTO dto2dto(com.datingpaas.dto.${entityName}UpdateDTO dto);

    ${entityName}SearchDTO dto2dto(com.datingpaas.dto.${entityName}SearchDTO dto);

    com.datingpaas.vo.${entityName}VO vo2vo(${entityName}VO vo);

    Collection<com.datingpaas.vo.${entityName}VO> vo2vo(Collection<${entityName}VO> vo);

    PageVO<com.datingpaas.vo.${entityName}VO> page2PageVO(PageProxy<${entityName}VO> page);

//---------------- 自动生成结束 -------------

}
