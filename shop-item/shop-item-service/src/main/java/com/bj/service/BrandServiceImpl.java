package com.bj.service;

 import com.bj.dao.BrandMapper;
import com.bj.pojo.Brand;
 import com.bj.util.Pager;
 import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.Banner;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 import tk.mybatis.mapper.entity.Example;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     *
     * @param key
     * @param page
     * @param rows
     * @param order
     * @param sort
     * @return
     */
    @Override
     public Pager<Brand> selectBrand(String key, Integer page, Integer rows,String order, Boolean sort){
         Example example = new Example(Brand.class);
         Example.Criteria criteria = example.createCriteria();

         if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
         }
         PageHelper.startPage(page,rows);
         if(StringUtils.isNotBlank(order)){
            example.setOrderByClause(order+" "+(sort?"desc":"asc"));
         }
         List<Brand> brandList = brandMapper.selectByExample(example);
         PageInfo<Brand> brandPageInfo = new PageInfo<>(brandList);
         return new Pager<>(brandPageInfo.getTotal(),brandPageInfo.getList());
     }

    @Override
    @Transactional
    public void brandSave(Brand brand, List<Long> cidList) {
        brandMapper.insertSelective(brand);
        cidList.forEach(cid-> {
            brandMapper.insertBrandAndCategory(cid, brand.getId());
        });
     }

    @Override
    public List<Brand> queryBrandByCid(Long cid) {
       return  brandMapper.selectBrandByCid(cid);
     }

}
