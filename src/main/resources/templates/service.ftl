package ${packageName};

import com.datingpaas.model.converter.mapstruct.*;
import com.datingpaas.dao.*;
import com.datingpaas.model.dto.*;
import com.datingpaas.model.entity.*;
import com.datingpaas.model.vo.*;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.service.commons.model.proxy.PageProxy;
import com.service.commons.model.vo.PageVO;
import com.service.commons.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
* ${entityName}Service类
* @author Automatically created By Albert utils
* @date ${dateTime}
*/
@Service
public class ${className} extends BaseService<${entityName}, ${dtoName}> {
    @Autowired
    private ${entityName}Converter converter;

    @Autowired
    private ${entityName}DAO dao;

    @Transactional(rollbackFor = Exception.class)
    public ${entityName}VO save(${entityName}PersistDTO dto) {
        ${entityName} entity = get(converter.dto2dto(dto));
        if (Objects.nonNull(entity)) {
            return getById(entity.getId());
        }
        entity = save(converter.dto2Entity(dto));
        return getById(entity.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public Collection<${entityName}VO> saveList(Collection<${entityName}PersistDTO> dtos) {
        Collection<${entityName}> medias = save(converter.dto2Entity(dtos));
        return converter.entity2VO(medias);
    }

    public ${entityName}VO getById(Long id) {
        ${entityName} entity = get(id);
        if (Objects.nonNull(entity)) {
            ${entityName}VO vo = converter.entity2VO(entity);
            return vo;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ${entityName}VO update(${entityName}UpdateDTO updateDTO) {
        update(converter.dto2Entity(updateDTO));
        return getById(updateDTO.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(${entityName}SearchDTO searchDTO) {
        ${entityName} entity = get(searchDTO);
        if (Objects.nonNull(entity)) {
            delete(entity.getId());
        }
    }
//---------------- 自动生成结束 -------------

}

