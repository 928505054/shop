package com.bj.service;

import com.bj.pojo.Brand;
import com.bj.util.Pager;
import org.springframework.boot.Banner;

import java.util.List;

public interface BrandService {

    /**产品模糊查询
     * @param key
     * @param page
     * @param rows
     * @param order
     * @param sort
     * @return
     * */
     Pager<Brand> selectBrand(String key, Integer page, Integer rows, String order, Boolean sort);

    /**
     * 新增品牌列表
     * @param Brand
     * @param list
     */
    void brandSave(Brand Brand, List<Long> list);


    /**
     *根据cid查询品牌
     * @param cid
     */
    List<Brand> queryBrandByCid(Long cid);
}
