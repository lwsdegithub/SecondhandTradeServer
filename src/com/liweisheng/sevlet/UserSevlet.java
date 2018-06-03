package com.liweisheng.sevlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument.BranchElement;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.liweisheng.constant.Constant;
import com.liweisheng.gson.bean.Common;
import com.liweisheng.gson.bean.mine.MyCollection;
import com.liweisheng.gson.bean.mine.MyGoods;
import com.liweisheng.gson.bean.mine.MyCollection.SimpleGoods;
import com.liweisheng.mybatis.bean.Collection;
import com.liweisheng.mybatis.bean.Demand;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.bean.User;
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;

/**
 * Servlet implementation class UserSevlet
 */
@WebServlet("/UserSevlet")
public class UserSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 请求类型
	private static final int GET_USER_BY_ID = 0;
	private static final int GET_USER_BY_PHONE = 1;
	private static final int GET_COLLECTION_BY_ID = 2;
	private static final int GET_GOODS_BY_ID = 3;
	private static final int GET_DEMAND_BY_ID = 4;
	private static final int UPDATE_USER_NAME = 5;
	private static final int UPDATE_PASSWORD=6;
	
	private static final int ADMIN_GET_USER=7;
	private static final int ADMIN_DELETE_USER=8;
	public UserSevlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		Gson gson = new Gson();

		// 请求内容类型
		String reqType = request.getParameter("TYPE");

		SqlSession session = MybatisUtils.initMybatis(IUser.class);
		IUser iUser = session.getMapper(IUser.class);
		User user = new User();

		try {
			switch (Integer.parseInt(reqType)) {
			case GET_USER_BY_ID:
				String USER_ID_1 = request.getParameter("USER_ID");
				user = iUser.getUserById(Integer.parseInt(USER_ID_1));
				response.getWriter().write(gson.toJson(user));
				break;
			case GET_USER_BY_PHONE:
				String PHONE = request.getParameter("PHONE");
				user = iUser.getUserByPhone(PHONE);
				response.getWriter().write(gson.toJson(user));
				break;
			case GET_COLLECTION_BY_ID:
				List<Collection> collections = new ArrayList<>();
				// 需要传输数据的封装类
				MyCollection myCollection = new MyCollection();
				List<SimpleGoods> simpleGoodsList = new ArrayList<>();
				List<Integer> collectionIdList = new ArrayList<>();

				String USER_ID_2 = request.getParameter("USER_ID");
				collections = iUser.getCollectionsById(Integer.parseInt(USER_ID_2));
				if (!collections.isEmpty()) {
					for (Collection collection : collections) {
						int GOODS_ID = collection.getGoods_id();
						Goods goods = MybatisUtils.initMybatis(IGoods.class).getMapper(IGoods.class)
								.getGoodsById(GOODS_ID);
						SimpleGoods simpleGoods = new SimpleGoods();
						// 填充数据
						simpleGoods.setGoods_id(GOODS_ID);
						simpleGoods.setGoods_name(goods.getGoods_name());
						collectionIdList.add(collection.getCollection_id());
						simpleGoodsList.add(simpleGoods);
					}
				}
				myCollection.setSimpleGoodsList(simpleGoodsList);
				myCollection.setCollectionIdList(collectionIdList);
				response.getWriter().write(gson.toJson(myCollection));
				break;
			case GET_GOODS_BY_ID:
				MyGoods myGoods = new MyGoods();

				List<Goods> goodsList = new ArrayList<>();
				List<SimpleGoods> simpleGoodsList2 = new ArrayList<>();
				String USER_ID_3 = request.getParameter("USER_ID");
				goodsList = iUser.getGoodsByUserId(Integer.parseInt(USER_ID_3));
				if (!goodsList.isEmpty()) {
					for (Goods goods : goodsList) {
						SimpleGoods simpleGoods = new SimpleGoods();
						simpleGoods.setGoods_id(goods.getGoods_id());
						simpleGoods.setGoods_name(goods.getGoods_name());
						simpleGoodsList2.add(simpleGoods);
					}
				}
				myGoods.setSimpleGoodsList(simpleGoodsList2);
				response.getWriter().write(gson.toJson(myGoods));
				break;
			case GET_DEMAND_BY_ID:
				String USER_ID_4 = request.getParameter("USER_ID");
				List<Demand> demandList = iUser.getDemandByUserId(Integer.parseInt(USER_ID_4));
				response.getWriter().write(gson.toJson(demandList));
				break;
			case UPDATE_USER_NAME:
				String USER_ID_5 = request.getParameter("USER_ID");
				user = iUser.getUserById(Integer.parseInt(USER_ID_5));
				String USER_NAME = request.getParameter("USER_NAME");
				
				user.setUserName(USER_NAME);
				
				iUser.updateUser(user);
				// 必须提交
				session.commit();

				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
				break;
			case UPDATE_PASSWORD:
				String USER_ID_6 = request.getParameter("USER_ID");
				user = iUser.getUserById(Integer.parseInt(USER_ID_6));
				String PASSWORD = request.getParameter("PASSWORD");
				
				user.setPassword(PASSWORD);
				
				iUser.updateUser(user);
				// 必须提交
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
				break;
			case ADMIN_GET_USER:
				List<User> users=iUser.getUserList();
				response.getWriter().write(gson.toJson(users));
				break;
			case ADMIN_DELETE_USER:
				String USER_ID_7= request.getParameter("USER_ID");
				iUser.deleteUser(Integer.parseInt(USER_ID_7));
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
				break;
			}
		} finally {
			// 释放资源
			session.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
