package com.bj.service;

import com.bj.bo.SpuBo;
import com.bj.dao.*;
import com.bj.pojo.*;
import com.bj.util.Pager;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper  stockMapper;

    @Override
    public Pager<SpuBo> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
        PageHelper.startPage(page,rows);
        List<Spu> spuList = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo(spuList);
        List<SpuBo> spuBoList = spuList.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu,spuBo);
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            List<String> categoryNameList = categoryService.selectNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
            String categoryExtension = StringUtils.join(categoryNameList,"-");
            spuBo.setCname(categoryExtension);
            return spuBo;
        }).collect(Collectors.toList());
        return new Pager<>(pageInfo.getTotal(),spuBoList);
    }

    @Override
    public void sendMessage(Long id, String type) {
        try{
            amqpTemplate.convertAndSend("item."+type,id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 新增商品
     * @param spuBo
     */
    @Transactional
    @Override
    public void saveGoods(SpuBo spuBo) {
        //新增spu
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insertSelective(spuBo);

        //新增spu_detail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
//        spuDetail.setSpecialSpec(spuBo.get);
        spuDetailMapper.insertSelective(spuDetail);

        spuBo.getSkus().forEach(skus -> {
            //新增sku
            Sku sku = new Sku();
             sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insertSelective(sku);
            //新增stock
            Stock stock = new Stock();
            stock.setStock(skus.getStock());
            stockMapper.insertSelective(stock);
        });

    }

    /**
     * 查询spuDetail详情
     * @param id
     * @return
     */
    @Override
    public SpuDetail querySpuDetailById(Long id) {
       return spuDetailMapper.selectByPrimaryKey(id);
     }


    /**
     * 查询sku
     * @param spuId
     * @return
     */
     @Override
    public List<Sku> querySkuBySpuId(Long spuId) {
        // 查询sku
        Sku record = new Sku();
        record.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(record);
        for (Sku sku : skus) {
            // 同时查询出库存
//            sku.setStock(this.stockMapper.selectByPrimaryKey(sku.getId()).getStock());
        }
        return skus;
    }


}
