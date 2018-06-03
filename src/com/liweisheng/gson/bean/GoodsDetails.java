package com.liweisheng.gson.bean;

import java.util.List;

import com.liweisheng.mybatis.bean.Comment;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.bean.User;

public class GoodsDetails {
	public Goods goods;
	public User user;
	public List<CommentDetails> commentDetailsList;
	
	public GoodsDetails(Goods goods, User user, List<CommentDetails> comments) {
		super();
		this.goods = goods;
		this.user = user;
		this.commentDetailsList = comments;
	}
	
}
