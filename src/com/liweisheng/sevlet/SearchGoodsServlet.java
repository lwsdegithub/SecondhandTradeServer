package com.liweisheng.sevlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.utils.MybatisUtils;

@WebServlet("/SearchGoodsServlet")
public class SearchGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SearchGoodsServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		Gson gson=new Gson();
		String KEY=request.getParameter("KEY");
		SqlSession session=MybatisUtils.initMybatis(IGoods.class);
		IGoods iGoods=session.getMapper(IGoods.class);
		try {
			KEY="%%"+KEY+"%%";
			List<Goods> goodsList=iGoods.searchGoods(KEY);
			response.getWriter().write(gson.toJson(goodsList));
			
		} finally {
			session.close();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
