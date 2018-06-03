package com.liweisheng.mybatis.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.liweisheng.mybatis.bean.Collection;

public interface ICollection {
	@Insert("insert into table_collection(goods_id,user_id) values(#{goods_id},#{user_id})")
	public void insertCollection(Collection collection);
	
	@Delete("delete from table_collection where collection_id=#{collection_id}")
	public void deleteCollection(int collection_id);
	
}
