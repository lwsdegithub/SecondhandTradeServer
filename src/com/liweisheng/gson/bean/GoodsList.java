package com.liweisheng.gson.bean;

import java.util.List;

import com.liweisheng.mybatis.bean.Goods;

public class GoodsList {
	public int status;
	//Goods�����ݿ�ʵ����
	public List<Goods> goodsList;
	public GoodsList(int status, List<Goods> goodsList) {
		super();
		this.status = status;
		this.goodsList = goodsList;
	}
}
