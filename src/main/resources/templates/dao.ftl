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
            List<Predicate> predicateList = Lists.newArrayList();

            if (Objects.nonNull(spec.getGroupId())) {
                predicateList.add(criteriaBuilder.equal(root.get("groupId"), spec.getGroupId()));
            }
            if (Objects.nonNull(spec.getDeleted())) {
                predicateList.add(criteriaBuilder.equal(root.get("deleted"), spec.getDeleted()));
            }
            if (Objects.nonNull(spec.getAppId())) {
                predicateList.add(criteriaBuilder.equal(root.get("appId"), spec.getAppId()));
            }

            Predicate[] pre = new Predicate[predicateList.size()];
            pre = predicateList.toArray(pre);
            return query.where(pre).orderBy(criteriaBuilder.desc(root.get("createTime"))).getRestriction();
        };
    }

//---------------- 自动生成结束 -------------

}

