package com.bj.dao;

 import com.bj.pojo.Brand;
 import org.apache.ibatis.annotations.Insert;
 import org.apache.ibatis.annotations.Param;
 import org.apache.ibatis.annotations.Select;
 import tk.mybatis.mapper.common.Mapper;

 import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand(category_id,brand_id) values(#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid") Long id, @Param("bid") Long bid);

    @Select("select b.* from tb_brand b join  tb_category_brand c3 on c3.brand_id=b.id where c3.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);
}
