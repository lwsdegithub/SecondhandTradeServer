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
import com.liweisheng.mybatis.bean.Demand;
import com.liweisheng.mybatis.dao.IDemand;
import com.liweisheng.utils.MybatisUtils;

/**
 * Servlet implementation class AddDemandSevlet
 */
@WebServlet("/AddDemandSevlet")
public class AddDemandSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddDemandSevlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		request.setCharacterEncoding("utf-8");
		Gson gson=new Gson();
		
		SqlSession session=MybatisUtils.initMybatis(IDemand.class);
		IDemand iDemand=session.getMapper(IDemand.class);
		
		Demand demand=new Demand();
		try {
			String USER_ID=request.getParameter("USER_ID");
			String CONTENT=request.getParameter("CONTENT");
			
			demand.setUser_id(Integer.parseInt(USER_ID));
			demand.setDemand_content(CONTENT);
			iDemand.insertDemand(demand);
			
			session.commit();
			
			response.getWriter().write(gson.toJson(new Common(Constant.UPLOAD_SUCCESS)));
		} finally {
			session.close();
		}
	}

}
