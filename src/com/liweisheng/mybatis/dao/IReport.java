package com.liweisheng.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.liweisheng.mybatis.bean.Report;

public interface IReport {
	@Insert("insert into table_report(user_id,goods_id,report_content) values (#{user_id},#{goods_id},#{report_content})")
	public void insertReport(Report report);
	
	@Select("select * from table_report where did_handled=0")
	public List<Report> getReportList();
	
	@Select("select * from table_report where report_id=#{id}")
	public Report getReportById(int id);
	
	@Update("update table_report set did_handled=1 where report_id=#{id}")
	public void setDidHandled(int id);
}
