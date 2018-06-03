package com.liweisheng.sevlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.liweisheng.constant.Constant;
import com.liweisheng.gson.bean.Common;
import com.liweisheng.mybatis.bean.Collection;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.dao.ICollection;
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.utils.MybatisUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class CollectionServlet
 */
@WebServlet("/CollectionServlet")
public class CollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int COLLECT = 0;
	private static final int DELETE_COLLECTION=1;

	public CollectionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		SqlSession session = MybatisUtils.initMybatis(ICollection.class);
		ICollection iCollection = session.getMapper(ICollection.class);
		// 请求的类型
		int TYPE = Integer.parseInt(request.getParameter("TYPE"));

		try {
			switch (TYPE) {
			case COLLECT:
				try {
						int GOODS_ID = Integer.parseInt(request.getParameter("GOODS_ID"));
						int USER_ID = Integer.parseInt(request.getParameter("USER_ID"));
						Collection collection = new Collection();
						collection.setGoods_id(GOODS_ID);
						// collection.setGoods_id(1);
						collection.setUser_id(USER_ID);
						// 数据库使用触发器，不需要在手动添加
						// 先更新table_goods,防止线程阻塞，加一条收藏数
						// SqlSession
						// session2=MybatisUtils.initMybatis(IGoods.class);
						// IGoods iGoods=session2.getMapper(IGoods.class);
						// Goods goods=iGoods.getGoodsById(GOODS_ID);
						// int count=goods.getCollection_count();
						// count=count+1;
						// goods.setCollection_count(count);
						// iGoods.updateGoods(goods);
						// session2.commit();
						// session2.close();
						// 插入数据
						iCollection.insertCollection(collection);
						session.commit();
						response.getWriter().write(gson.toJson(new Common(Constant.OK)));
				} catch (Exception e) {
					//已经收藏过
					response.getWriter().write(gson.toJson(new Common(Constant.HAS_COLLECTED)));
				}
				break;
			case DELETE_COLLECTION:
				    try {
				    	int COLLECTION_ID=Integer.parseInt(request.getParameter("COLLECTION_ID"));
						iCollection.deleteCollection(COLLECTION_ID);
						session.commit();
						response.getWriter().write(gson.toJson(new Common(Constant.OK)));
					} catch (Exception e) {
						response.getWriter().write(gson.toJson(new Common(Constant.ERROR)));
					}
				break;
			}
		} finally {
			session.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
