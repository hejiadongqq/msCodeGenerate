package ${packageName};

import java.util.*;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import com.google.common.collect.Lists;
import javax.persistence.criteria.Predicate;

/**
 * ${entityName}DAO类
 *
 * @author Automatically created By Albert utils
 * @date ${dateTime}
 */
@Repository
public class ${className} extends BaseDAO<${entityName}, ${dtoName}> {

    @Override
    public Specification<${entityName}> buildSpecification(${dtoName} spec) {
        return (root, query, criteriaBuilder) -> {
            Set<Predicate> predicateList = buildSpecification(spec, root, criteriaBuilder);

            Predicate[] pre = new Predicate[predicateList.size()];
            pre = predicateList.toArray(pre);
            return query.where(pre).orderBy(criteriaBuilder.desc(root.get("createTime"))).getRestriction();
        };
    }

//---------------- 自动生成结束 -------------

}

