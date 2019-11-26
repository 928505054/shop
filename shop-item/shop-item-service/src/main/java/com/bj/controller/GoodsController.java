package com.bj.controller;

import com.bj.bo.SpuBo;
import com.bj.pojo.Sku;
import com.bj.pojo.Spu;
import com.bj.pojo.SpuDetail;
import com.bj.service.GoodsService;
import com.bj.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class GoodsController {


    @Autowired
    private GoodsService goodsService;
    /**
     *分页查询SPU
     *@parampage
     *@paramrows
     *@paramsaleable
     *@paramkey
     *@return
     */
//    @ResponseBody
    @GetMapping("/spu/page")
    public ResponseEntity<Pager<SpuBo>> querySpuByPage(
            @RequestParam(value="page",defaultValue="1")Integer page,
            @RequestParam(value="rows",defaultValue="5")Integer rows,
            @RequestParam(value="saleable",required=false)Boolean saleable,
            @RequestParam(value="key",required=false)String key
            ){
        Pager<SpuBo> spuBo = goodsService.querySpuByPage(page,rows,saleable,key);
        if(spuBo==null && CollectionUtils.isEmpty(spuBo.getItem())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuBo);
    }


    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody() SpuBo spuBo){
        goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据spuid查询spudetail详情
     * @param id
     * @return
     */
    @RequestMapping("/goods/spu/detail/{id}")
    public ResponseEntity<SpuDetail> getSpuDetailById(@PathVariable("id")Long id){
        SpuDetail spuDetail = goodsService.querySpuDetailById(id);
        if(null==spuDetail){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }


    /**
     * 查询sku
     * @param id
     * @return
     */
    @GetMapping("/sku/list/{id}")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@PathVariable("id") Long id) {
        List<Sku> skus = this.goodsService.querySkuBySpuId(id);
//        if (skus == null || skus.size() == 0) {
        if(CollectionUtils.isEmpty(skus)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(skus);
    }

}
