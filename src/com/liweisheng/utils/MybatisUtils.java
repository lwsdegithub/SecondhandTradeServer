package com.liweisheng.utils;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.liweisheng.mybatis.dao.IGoods;

public class  MybatisUtils {
	
	public static  SqlSession initMybatis(Class clazz){
		SqlSessionFactory sessionFactory;
		Reader reader;
		try {
			reader = Resources.getResourceAsReader("mybatis_config.xml");
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			sessionFactory.getConfiguration().addMapper(clazz);
			SqlSession sqlSession=sessionFactory.openSession();
			return sqlSession;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
