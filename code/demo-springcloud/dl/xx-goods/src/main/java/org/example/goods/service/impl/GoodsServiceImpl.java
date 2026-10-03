package org.example.goods.service.impl;

import org.example.goods.service.GoodsService;
import org.example.goods.mapper.GoodsMapper;
import org.example.commons.model.Goods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.selectAllGoods();
    }

    @Override
    public Goods getGoodsById(Integer id) {
        return goodsMapper.selectByPrimaryKey(id);
    }
}
