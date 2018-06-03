package com.liweisheng.sevlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.liweisheng.gson.bean.GoodsList;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.utils.MybatisUtils;

/**
 * Servlet implementation class GetGoodsListSevlet
 */
@WebServlet("/GetGoodsListSevlet")
public class GetGoodsListSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetGoodsListSevlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//³õÊ¼»¯
		response.setCharacterEncoding("utf8");
		IGoods iGoods = MybatisUtils.initMybatis(IGoods.class).getMapper(IGoods.class);
		
		Gson gson=new Gson();
		
		//GoodsList
		GoodsList goodsList=new GoodsList(1, iGoods.getAllGoods());
		
		String goodsJson=gson.toJson(goodsList);
		//·µ»ØJson×Ö·û´®
		response.getWriter().write(goodsJson);
	}

}
