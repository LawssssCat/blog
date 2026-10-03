package org.example.goods.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.commons.model.Goods;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<Goods> selectAllGoods();
    Goods selectByPrimaryKey(@Param("id") Integer id);
    void deleteByPrimaryKey(@Param("id") Integer id);
    void insert(Goods record);
    void insertSelective(Goods record);
    void updateByPrimaryKey(Goods record);
    void updateByPrimaryKeySelective(Goods record);
}
