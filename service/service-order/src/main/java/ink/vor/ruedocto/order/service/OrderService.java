package ink.vor.ruedocto.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ink.vor.ruedocto.model.order.OrderInfo;
import ink.vor.ruedocto.vo.order.OrderCountQueryVo;
import ink.vor.ruedocto.vo.order.OrderQueryVo;

import java.util.Map;

/**
 * @author muquanrui
 * @date 2022/6/24 11:21
 */
public interface OrderService extends IService<OrderInfo> {
    //保存订单
    Long saveOrder(String scheduleId, Long patientId);

    /**
     * 分页列表
     */
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    /**
     * 获取订单详情
     */
    OrderInfo getOrderInfo(String id);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    Map<String,Object> show(Long orderId);

    /**
     * 就诊提醒
     */
    void patientTips();

    /**
     * 订单统计
     */
    Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo);
}
