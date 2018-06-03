package com.liweisheng.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.liweisheng.mybatis.bean.Comment;

public interface IComment {
	@Select("select * from table_comment where goods_id=#{goods_id} order by comment_time desc")
	public List<Comment> getCommentListByGoodsId(int id);
	
	@Insert("insert into table_comment(goods_id,user_id,comment_content) values(#{goods_id},#{user_id},#{comment_content})")
	public void addNewComment(Comment comment);
}
