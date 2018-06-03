package com.liweisheng.gson.bean.mine;

import java.util.List;

import com.liweisheng.mybatis.bean.Goods;

public class MyCollection {
	public List<Integer> collectionIdList;
	public List<SimpleGoods> simpleGoodsList;
	
	public List<Integer> getCollectionIdList() {
		return collectionIdList;
	}

	public void setCollectionIdList(List<Integer> collectionIdList) {
		this.collectionIdList = collectionIdList;
	}

	public List<SimpleGoods> getSimpleGoodsList() {
		return simpleGoodsList;
	}

	public void setSimpleGoodsList(List<SimpleGoods> simpleGoodsList) {
		this.simpleGoodsList = simpleGoodsList;
	}

	public static class SimpleGoods{
		
		public int getGoods_id() {
			return goods_id;
		}
		public void setGoods_id(int goods_id) {
			this.goods_id = goods_id;
		}
		public String getGoods_name() {
			return goods_name;
		}
		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}
		int goods_id;
		String goods_name;
	}
}
