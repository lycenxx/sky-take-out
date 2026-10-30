package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/** 购物车业务接口。 */
public interface ShoppingCartService {

    /** 添加商品或增加数量。 */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /** 查询当前用户的购物车。 */
    List<ShoppingCart> showShoppingCart();

    /** 清空当前用户的购物车。 */
    void cleanShoppingCart();

    /** 减少商品数量或删除该商品。 */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
