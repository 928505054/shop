package com.bj.service;

import com.bj.bo.SpuBo;
import com.bj.pojo.Sku;
import com.bj.pojo.Spu;
import com.bj.pojo.SpuDetail;
import com.bj.util.Pager;

import java.util.List;

public interface GoodsService {

    Pager<SpuBo> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);

    /**
     * 发送新消息到mq
     * @param id
     * @param type
     */
    void sendMessage(Long id,String type);

    /**
     * 新增商品
     * @param spuBo
     */
    void saveGoods(SpuBo spuBo);

    /**
     * 根据spuid查询spudetail详情
     * @param id
     * @return
     */
    SpuDetail querySpuDetailById(Long id);

    /**
     * 查询sku
     * @param id
     * @return
     */
    List<Sku> querySkuBySpuId(Long id);
}
