package com.liweisheng.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.liweisheng.mybatis.bean.Demand;

public interface IDemand {
	@Select("select * from table_demand")
	public List<Demand> getDemandList();
	
	@Insert("insert into table_demand(user_id,demand_content) values (#{user_id},#{demand_content})")
	public void insertDemand(Demand demand);
	
	@Delete("delete from table_demand where demand_id=#{id}")
	public void deleteDemand(int id);
}
