package com.liweisheng.sevlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.liweisheng.constant.Constant;
import com.liweisheng.gson.bean.Common;
import com.liweisheng.mybatis.bean.User;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class SignUpSevlet
 */
@WebServlet("/RegisterSevlet")
public class RegisterSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RegisterSevlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		request.setCharacterEncoding("utf8");
		
		String PHONE=request.getParameter("PHONE");
		String USER_NAME=request.getParameter("USER_NAME");
		String PASSWORD=request.getParameter("PASSWORD");
		//��������������Ҫ����Session���ύ
		SqlSession session=MybatisUtils.initMybatis(IUser.class);
		
		IUser iUser=session.getMapper(IUser.class);
		Gson gson=new Gson();
		//�Ƿ��Ѿ������ֻ���
		if (!iUser.getAccountExistedByPhone(PHONE)) {
			//�����ڲ�������
			User user=new User();
			user.setUserName(USER_NAME);
			user.setPhone(PHONE);
			user.setPassword(PASSWORD);
			user.setHeadIcon("default.jpg");
			iUser.insertUser(user);
			//�ύ
			session.commit();
			response.getWriter().write(gson.toJson(new Common(Constant.REGISTER_SUCCESS)));
		}else {
			//�Ѿ������˻�
			response.getWriter().write(gson.toJson(new Common(Constant.ACCOUNT_IS_EXISTED)));
		}
	}

}
