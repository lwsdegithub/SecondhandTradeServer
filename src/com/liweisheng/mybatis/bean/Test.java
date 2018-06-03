package com.liweisheng.mybatis.bean;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;

public class Test {

	public static void main(String[] args) {
		SqlSession session = MybatisUtils.initMybatis(IGoods.class);
		try {
			IGoods iGoods=session.getMapper(IGoods.class);
			for(Goods goods:iGoods.getAllGoods()){
				System.out.println(goods.toString());
			}
		} finally {
			session.close();
		}
	}

}
