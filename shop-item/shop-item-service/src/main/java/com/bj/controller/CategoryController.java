package com.bj.controller;


import com.bj.pojo.Category;
import com.bj.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ResponseBody
    @RequestMapping("/list")
    public ResponseEntity<? extends Object> selectCateGory(@RequestParam(value="pid",defaultValue = "0") Long id){
        try{
            if(id==null||id<0){
                return ResponseEntity.badRequest().build();
             }
            List<Category> categoryList = categoryService.selectCategoryMapperTest(id);
            if(CollectionUtils.isEmpty(categoryList)){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                  return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categoryList);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
