package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

/** 订单业务接口。 */
public interface OrderService {

    /** 提交当前用户的购物车订单。 */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);


    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);


    /**
     * 用户端订单分页查询
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    PageResult pageQuery4User(int page, int pageSize, Integer status);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /** 用户取消订单。 */
    void userCancelById(Long id) throws Exception;

    /** 将历史订单商品重新加入购物车。 */
    void repetition(Long id);

    /** 按条件分页查询订单。 */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /** 统计各订单状态的数量。 */
    OrderStatisticsVO statistics();

    /** 商家接单。 */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /** 商家拒单。 */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /** 商家取消订单。 */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /** 将订单标记为派送中。 */
    void delivery(Long id);

    /** 完成订单。 */
    void complete(Long id);

    /** 向商家发送催单通知。 */
    void reminder(Long id);
}
