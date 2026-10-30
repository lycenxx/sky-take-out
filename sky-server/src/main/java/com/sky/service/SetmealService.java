package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/** 套餐业务接口。 */
public interface SetmealService {


    /** 启用或停售套餐。 */
    void startOrStop(Integer status, Long id);

    /** 新增套餐及其菜品。 */
    void saveWithDish(SetmealDTO setmealDTO);

    /** 分页查询套餐。 */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /** 批量删除套餐。 */
    void deleteBatch(List<Long> ids);

    /** 查询套餐及其菜品。 */
    SetmealVO getByIdWithDish(Long id);

    /** 更新套餐及其菜品。 */
    void update(SetmealDTO setmealDTO);


    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
