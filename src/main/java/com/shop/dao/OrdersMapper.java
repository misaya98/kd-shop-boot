package com.shop.dao;

import com.shop.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OrdersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order
     *
     * @mbggenerated
     */
    int insert(Orders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order
     *
     * @mbggenerated
     */
    List<Orders> selectAll();


    List<Orders> selectOrdersByUserId(@Param("userId") Integer userId);

    Orders selectOrdersByGoodId(@Param("goodId") Integer goodId);


    Double getSpendByUserId(@Param("userId") Integer userId);

    Double getIncomeByUserId(@Param("userId") Integer userId);
}