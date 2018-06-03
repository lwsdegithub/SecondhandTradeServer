package com.liweisheng.gson.bean.mine;

import java.util.List;

import com.liweisheng.gson.bean.mine.MyCollection.SimpleGoods;
import com.liweisheng.mybatis.bean.Goods;

public class MyGoods {
	public List<SimpleGoods> simpleGoodsList;

	public List<SimpleGoods> getSimpleGoodsList() {
		return simpleGoodsList;
	}

	public void setSimpleGoodsList(List<SimpleGoods> simpleGoodsList) {
		this.simpleGoodsList = simpleGoodsList;
	}
	
}