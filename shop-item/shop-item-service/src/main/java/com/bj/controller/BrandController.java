package com.bj.controller;

import com.bj.pojo.Brand;
import com.bj.service.BrandService;
import com.bj.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ResponseBody
    @RequestMapping("/page")
    public ResponseEntity<Pager<Brand>> brandPager(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value="page",defaultValue ="1")Integer page,
            @RequestParam(value="rows",defaultValue = "20")Integer rows,
            @RequestParam(value="sortBy",defaultValue ="key" )String order,
            @RequestParam(value="desc",required = false)Boolean sort){
        Pager<Brand> brandPager = brandService.selectBrand(key,page,rows,order,sort);
        if(CollectionUtils.isEmpty(brandPager.getItem())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPager);
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam("cid") List<Long> cids){
        brandService.brandSave(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类查询手机品牌
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> getBrand(@PathVariable("cid") Long cid){
        List<Brand> brandList = brandService.queryBrandByCid(cid);
        if(brandList==null || CollectionUtils.isEmpty(brandList)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return   ResponseEntity.ok(brandList);
    }
}
