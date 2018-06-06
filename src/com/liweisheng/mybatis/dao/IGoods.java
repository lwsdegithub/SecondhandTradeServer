package com.liweisheng.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.liweisheng.mybatis.bean.Goods;

public interface IGoods {
	
	@Select("select * from table_goods order by build_time desc")
	public List<Goods> getAllGoods();
	
	@Select("select * from table_goods where goods_id=#{id}")
	public Goods getGoodsById(int id);
	
	@Select("select ifnull(MAX(goods_id),1) from table_goods")
	public int getMaxId();
	
	@Insert("insert into table_goods(goods_id,user_id,goods_name,goods_price,goods_photo,goods_description) values(#{goods_id},#{user_id},#{goods_name},#{goods_price},#{goods_photo},#{goods_description})")
	public void insertGoods(Goods goods);
	
	@Select("select collection_count from table_goods where goods_id=#{goods_id}")
	public void getCollectionCountByGoodsID(int goodsId);
	
	@Update("update table_goods set goods_name=#{goods_name},goods_price=#{goods_price},goods_photo=#{goods_photo},goods_description=#{goods_description},collection_count=#{collection_count},comment_count=#{comment_count}")
	public void updateGoods(Goods goods);
	
	@Select("select * from table_goods where goods_description like #{key} or goods_name like #{key}")
	public List<Goods> searchGoods(String key);
	
	@Delete("delete from table_goods where goods_id=#{goods_id}")
	public void deleteGoods(int goods_id);
}
