package com.bj.service;

 import com.bj.dao.CategoryMapper;
 import com.bj.pojo.Category;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> selectCategoryMapperTest(Long id){
       Category category = new Category();
       category.setParentId(id);
       return categoryMapper.select(category);
    }

    @Override
    public List<String> selectNameByIds(List<Long> ids) {
        List<Category> categoryList = categoryMapper.selectByIdList(ids);
        List<String> categoryNames = new ArrayList<String>();
        categoryNames = categoryList.stream().map(category -> category.getName()).collect(Collectors.toList());
        return categoryNames;
    }
}
