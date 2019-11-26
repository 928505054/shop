package com.bj.service;

import com.bj.dao.SpecGroupMapper;
import com.bj.dao.SpecParamMapper;
import com.bj.pojo.SpecGroup;
import com.bj.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

 @Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecParamMapper specParamMapper;

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> specGroupList = specGroupMapper.select(group);
        return specGroupList;
    }

    @Override
    public List<SpecParam> queryParam(Long gid,Long cid,Boolean searching,Boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setGroup_id(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
         return specParamMapper.select(specParam);
    }
}
