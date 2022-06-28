package ink.vor.ruedocto.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.vor.ruedocto.common.result.Result;
import ink.vor.ruedocto.common.utils.AuthContextHolder;
import ink.vor.ruedocto.enums.OrderStatusEnum;
import ink.vor.ruedocto.model.order.OrderInfo;
import ink.vor.ruedocto.order.service.OrderService;
import ink.vor.ruedocto.vo.order.OrderCountQueryVo;
import ink.vor.ruedocto.vo.order.OrderQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author muquanrui
 * @date 2022/6/24 11:24
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "创建订单")
    @PostMapping("auth/submitOrder/{scheduleId}/{patientId}")
    public Result submitOrder(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable String scheduleId,
            @ApiParam(name = "patientId", value = "就诊人id", required = true)
            @PathVariable Long patientId) {
        return Result.ok(orderService.saveOrder(scheduleId, patientId));
    }

    //订单列表（条件查询带分页）
    @GetMapping("auth/{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       OrderQueryVo orderQueryVo, HttpServletRequest request) {
        //设置当前用户id
        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        IPage<OrderInfo> pageModel =
                orderService.selectPage(pageParam,orderQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取订单状态")
    @GetMapping("auth/getStatusList")
    public Result getStatusList() {
        return Result.ok(OrderStatusEnum.getStatusList());
    }

    //根据订单id查询订单详情
    @GetMapping("auth/getOrders/{orderId}")
    public Result getOrders(@PathVariable String orderId) {
        OrderInfo orderInfo = orderService.getOrderInfo(orderId);
        return Result.ok(orderInfo);
    }

    @Api(tags = "订单接口")
    @RestController
    @RequestMapping("/admin/order/orderInfo")
    public class OrderController {

        @Autowired
        private OrderService orderService;

        @ApiOperation(value = "获取分页列表")
        @GetMapping("{page}/{limit}")
        public Result index(
                @ApiParam(name = "page", value = "当前页码", required = true)
                @PathVariable Long page,
                @ApiParam(name = "limit", value = "每页记录数", required = true)
                @PathVariable Long limit,
                @ApiParam(name = "orderCountQueryVo", value = "查询对象", required = false) OrderQueryVo orderQueryVo) {
            Page<OrderInfo> pageParam = new Page<>(page, limit);
            IPage<OrderInfo> pageModel = orderService.selectPage(pageParam, orderQueryVo);
            return Result.ok(pageModel);
        }

        @ApiOperation(value = "获取订单状态")
        @GetMapping("getStatusList")
        public Result getStatusList() {
            return Result.ok(OrderStatusEnum.getStatusList());
        }
    }

    @ApiOperation(value = "获取订单")
    @GetMapping("show/{id}")
    public Result get(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @PathVariable Long id) {
        return Result.ok(orderService.show(id));
    }

    @ApiOperation(value = "获取订单统计数据")
    @PostMapping("inner/getCountMap")
    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo) {
        return orderService.getCountMap(orderCountQueryVo);
    }
}
