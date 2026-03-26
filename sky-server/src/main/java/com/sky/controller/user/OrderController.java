package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "用户端订单相关接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("用户下单，参数为：{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        // 注意：不再立即调用 paySuccess，而是由前端轮询检查支付状态
        return Result.success(orderPaymentVO);
    }

    /**
     * 历史订单查询
     *
     * @param page
     * @param pageSize
     * @param status   订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
     * @return
     */
    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> page(int page, int pageSize, Integer status) {
        PageResult pageResult = orderService.pageQuery4User(page, pageSize, status);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 用户取消订单
     *
     * @return
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancel(@PathVariable("id") Long id) throws Exception {
        orderService.userCancelById(id);
        return Result.success();
    }

    /**
     * 删除已取消的订单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除订单")
    public Result delete(@PathVariable("id") Long id) throws Exception {
        orderService.deleteById(id);
        return Result.success();
    }

    /**
     * 再来一单
     *
     * @param id
     * @return
     */
    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }

    /**
     * 客户催单
     * @param id
     * @return
     */
    @GetMapping("/reminder/{id}")
    @ApiOperation("客户催单")
    public Result reminder(@PathVariable("id") Long id){
        orderService.reminder(id);
        return Result.success();
    }

    /**
     * 检查订单支付状态
     * @param orderNumber 订单号
     * @return true-已支付，false-未支付
     */
    @GetMapping("/paymentStatus")
    @ApiOperation("检查订单支付状态")
    public Result<Boolean> checkPaymentStatus(String orderNumber) {
        log.info("检查订单支付状态：{}", orderNumber);
        Boolean isPaid = orderService.checkPaymentStatus(orderNumber);
        return Result.success(isPaid);
    }

    /**
     * 模拟扫码支付（测试用）
     * @param orderNumber 订单号
     * @return
     */
    @PostMapping("/mockScanPay")
    @ApiOperation("模拟扫码支付")
    public Result mockScanPay(String orderNumber) throws Exception {
        log.info("模拟用户扫码支付：{}", orderNumber);
        orderService.mockScanPay(orderNumber);
        return Result.success();
    }

    /**
     * 支付成功回调（兼容旧版本小程序）
     * @param orderNumber 订单号
     * @return
     */
    @PutMapping("/paySuccess/{orderNumber}")
    @GetMapping("/paySuccess/{orderNumber}") // 支持浏览器直接访问测试
    @ApiOperation("支付成功回调")
    public Result paySuccess(@PathVariable String orderNumber) throws Exception {
        log.info("支付成功回调：{}", orderNumber);
        orderService.paySuccess(orderNumber);
        return Result.success();
    }
}
