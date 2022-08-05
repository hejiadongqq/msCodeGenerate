package ${packageName};

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.service.commons.model.proxy.PageProxy;
import com.service.commons.model.vo.PageVO;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;

/**
 * ${entityName}Service类
 *
 * @author Automatically created By Albert utils
 * @date ${dateTime}
 */
@Service
@Slf4j
public class ${className} extends BaseService<${entityName}, ${dtoName}> {
    @Resource
    private ${entityName}Converter converter;

    @Resource
    private ${entityName}DAO dao;

    @Transactional(rollbackFor = Exception.class)
    public Long save(${entityName}PersistDTO dto) {
        ${entityName} entity = save(converter.dto2Entity(dto));
        return entity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Collection<${entityName}VO> saveList(Collection<${entityName}PersistDTO> dtos) {
        Collection<${entityName}> medias = save(converter.dto2Entity(dtos));
        return converter.entity2VO(medias);
    }

    public ${entityName}VO getById(Long id) {
        ${entityName} entity = get(id);
        if (Objects.nonNull(entity)) {
            return converter.entity2VO(entity);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(${entityName}UpdateDTO updateDTO) {
        update(converter.dto2Entity(updateDTO));
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

