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
import com.liweisheng.mybatis.bean.User;
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.mybatis.dao.IUser;
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
		switch (TYPE) {
		case DELETE_GOODS_BY_ID:
			try {
				int GOODS_ID=Integer.parseInt(request.getParameter("GOODS_ID"));
				//½µµÍÐÅÓþ·Ö
				int USER_ID=iGoods.getGoodsById(GOODS_ID).getUser_id();
				try {
					SqlSession session2=MybatisUtils.initMybatis(IUser.class);
					IUser iUser=session2.getMapper(IUser.class);
					User user=iUser.getUserById(USER_ID);
					int score=user.getCreditScore();
					score=score-1;
					user.setCreditScore(score);
					iUser.updateUser(user);
					session2.commit();
					session2.close();
				}catch (Exception e) {
				}
				iGoods.deleteGoods(GOODS_ID);
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
			}
			finally {
				session.close();
			}
			
			break;
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
