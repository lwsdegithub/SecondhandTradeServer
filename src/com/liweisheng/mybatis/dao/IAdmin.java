package com.liweisheng.mybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.liweisheng.mybatis.bean.Admin;
import com.liweisheng.mybatis.bean.User;

public interface IAdmin {
	@Select("select MAX(admin_id) from table_admin")
	public int getMaxId();
	
	@Select("select * from table_admin where admin_id= #{id}")
	public Admin getAdminById(int id);
	
	@Insert("insert into table_admin(admin_id,admin_account,password) values (#{admin_id},#{admin_account},#{password})")
	public void insertAdmin(Admin admin);
	
	@Update("update table_admin set admin_id=#{admin_id},admin_account=#{admin_account},password=#{password}")
	public void updateAdmin(Admin user);
}
