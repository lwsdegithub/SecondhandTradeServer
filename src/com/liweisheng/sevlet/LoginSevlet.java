package com.liweisheng.sevlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.liweisheng.constant.Constant;
import com.liweisheng.gson.bean.Common;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;

/**
 * Servlet implementation class LoginSevlet
 */
@WebServlet("/LoginSevlet")
public class LoginSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginSevlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		Gson gson=new Gson();
		
		//��req�õ�������
		//request   
		String PHONE=request.getParameter("PHONE");
		String PASSWORD=request.getParameter("PASSWORD");
		
		Common common=new Common();
		
		PrintWriter printWriter=response.getWriter();
		IUser iUser=MybatisUtils.initMybatis(IUser.class).getMapper(IUser.class);
		if (iUser.getAccountExistedByPhone(PHONE)) {
			//�˻�����
			if (iUser.getPasswordByPhone(PHONE).equals(PASSWORD)) {
				//������ȷ
				common.setStatus(Constant.LOGIN_SUCCESS);
				printWriter.write(gson.toJson(common));
			}else {
				//���벻��ȷ
				common.setStatus(Constant.PASSWORD_ERROR);
				printWriter.write(gson.toJson(common));
			}
		}else {
			//�˻�������
			common.setStatus(Constant.ACCOUNT_IS_NOT_EXISTED);
			printWriter.write(gson.toJson(common));
		}
	}

}
