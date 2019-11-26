package com.bj.service;

import com.bj.pojo.SpecGroup;
import com.bj.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {

    List<SpecGroup> queryGroupByCid(Long cid);

    List<SpecParam> queryParam(Long gid,Long cid,Boolean searching,Boolean generic);
}
