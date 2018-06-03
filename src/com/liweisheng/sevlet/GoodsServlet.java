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
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.utils.MybatisUtils;
import com.sun.org.apache.bcel.internal.generic.InstructionTargeter;

@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int DELETE_GOODS_BY_ID=0;
	
	public GoodsServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		Gson gson=new Gson();
		int TYPE=Integer.parseInt(request.getParameter("TYPE"));
		SqlSession session=MybatisUtils.initMybatis(IGoods.class);
		IGoods iGoods=session.getMapper(IGoods.class);
		
		try {
			switch (TYPE) {
			case DELETE_GOODS_BY_ID:
				int GOODS_ID=Integer.parseInt(request.getParameter("GOODS_ID"));
				iGoods.deleteGoods(GOODS_ID);
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
				break;

			default:
				break;
			}
		} finally {
			session.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
