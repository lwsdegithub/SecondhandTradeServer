package com.liweisheng.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.liweisheng.gson.bean.mine.MyCollection.SimpleGoods;
import com.liweisheng.mybatis.bean.Collection;
import com.liweisheng.mybatis.bean.Demand;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.bean.User;

public interface IUser {
	@Select("select MAX(user_id) from table_user")
	public int getMaxId();
	
	@Select("select * from table_collection where user_id=#{id}")
	public Collection getCollection(int id);
	
	@Select("select * from table_user where user_id= #{id}")
	public User getUserById(int id);
	
	@Select("select * from table_user order by credit_score desc")
	public List<User> getUserList();
	
	@Insert("insert into table_user(user_name,phone,password,create_time,head_icon,credit_score) values (#{user_name},#{phone},#{password},#{create_time},#{head_icon},#{credit_score})")
	public void insertUser(User user);
	
	@Update("update table_user set user_name=#{user_name},phone=#{phone},password=#{password},create_time=#{create_time},head_icon=#{head_icon},credit_score=#{credit_score} where user_id = #{user_id}")
	public void updateUser(User user);
	
	@Delete("delete from table_user where user_id=#{user_id}")
	public void deleteUser(int id);
	//是否已经存在记录
	@Select("select exists(select * from table_user where phone=#{phone})")
	public Boolean getAccountExistedByPhone(String phone); 
	//密码
	@Select("select password from table_user where phone=#{phone}")
	public String getPasswordByPhone(String phone);
	
	@Select("select * from table_user where phone=#{phone}")
	public User getUserByPhone(String phone);
	
	@Select("select distinct * from table_collection where user_id=#{user_id} ")
	public List<Collection> getCollectionsById(int userId);
	
	@Select("select distinct * from table_goods where user_id=#{user_id}")
	public List<Goods> getGoodsByUserId(int userId);
	
	@Select("select distinct * from table_demand where user_id=#{user_id}")
	public List<Demand> getDemandByUserId(int userId);
	
}
