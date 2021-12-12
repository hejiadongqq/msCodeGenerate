package ${packageName};


import com.service.commons.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ${projectName} 验单实现类
 *
 * @author Automatically created By Albert utils
 * @date ${dateTime}
 */
@Component("${componentName}")
@Slf4j
public class ${className} extends CommonStrategy {

    @Override
    public void doAction(VerifyOrderVO result) {
        log.info("收到${projectName}项目订单回调信息");
        Goods goods = result.getGoods();
        log.info("订单回调信息{}", GsonUtils.toJson(goods));
        Long userId = result.getOrder().getUserId();
        if (goods.getType().equals(GoodsType.MEMBER_CARD)) {
            log.info("${projectName}会员卡回调信息");
            log.info("------用户[{}]充值会员[{}]天------", userId, goods.getDeadline());
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
            userUpdateDTO.setId(userId);
            userUpdateDTO.setVip(VIP.VIP);
            userService.update(userUpdateDTO);

            // 更新card
            addUserCard(result);

        }
    }
    // end --//
//---------------- 自动生成结束 -------------


}
