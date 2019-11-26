package com.bj.controller;

import com.bj.pojo.SpecGroup;
import com.bj.pojo.SpecParam;
import com.bj.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * 商品规格类
 */

@Controller
@RequestMapping("/spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 查询分组
     * @param cid
     * @return
     */
    @ResponseBody
    @RequestMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> specGroups = specificationService.queryGroupByCid(cid);
        if(CollectionUtils.isEmpty(specGroups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroups);
    }

    /**
     * 根据cid查询规格参数(商品分类id)
     * @param cid
     * @return
     */
    @ResponseBody
    @RequestMapping("/params")
    public ResponseEntity<List<SpecParam>> queryParam(
            @RequestParam(value="gid", required = false) Long gid,
            @RequestParam(value="cid", required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching,
            @RequestParam(value="generic", required = false) Boolean generic){
        List<SpecParam> specParams = specificationService.queryParam(gid,cid,searching,generic);
        if(CollectionUtils.isEmpty(specParams)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specParams);
    }
}
