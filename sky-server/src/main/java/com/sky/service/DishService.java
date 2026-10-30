package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/** 菜品业务接口。 */
public interface DishService {


    /** 启用或停售菜品。 */
    void startOrStop(Integer status, Long id);

    /** 新增菜品及其口味。 */
    void saveWithFlavor(DishDTO dishDTO);

    /** 分页查询菜品。 */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /** 批量删除菜品。 */
    void deleteBatch(List<Long> ids);

    /** 查询菜品及其口味。 */
    DishVO getByIdWithFlavor(Long id);

    /** 更新菜品及其口味。 */
    void updateWithFlavor(DishDTO dishDTO);

    /** 按分类查询菜品。 */
    List<Dish> list(Long categoryId);


    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

}
