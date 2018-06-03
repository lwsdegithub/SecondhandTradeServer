package com.liweisheng.gson.bean;

import java.util.List;

import com.liweisheng.mybatis.bean.Goods;

public class GoodsList {
	public int status;
	//Goods是数据库实体类
	public List<Goods> goodsList;
	public GoodsList(int status, List<Goods> goodsList) {
		super();
		this.status = status;
		this.goodsList = goodsList;
	}
}
