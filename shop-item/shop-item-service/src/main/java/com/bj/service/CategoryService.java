package com.bj.service;

import com.bj.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 动态条件查询
     * @param id
     * @return
     */
    List<Category> selectCategoryMapperTest(Long id);

    /**
     * 根据id集合获取分类名称
     * @param ids
     * @return
     */
    List<String> selectNameByIds(List<Long> ids);
}
