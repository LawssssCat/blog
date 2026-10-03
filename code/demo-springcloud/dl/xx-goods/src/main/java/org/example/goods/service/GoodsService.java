package org.example.goods.service;

import org.example.commons.model.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> getAllGoods();

    Goods getGoodsById(Integer id);
}
