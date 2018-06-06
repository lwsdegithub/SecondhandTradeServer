package com.liweisheng.sevlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.liweisheng.constant.Constant;
import com.liweisheng.gson.bean.Common;
import com.liweisheng.gson.bean.DemandList;
import com.liweisheng.mybatis.bean.Demand;
import com.liweisheng.mybatis.bean.User;
import com.liweisheng.mybatis.dao.IDemand;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class IDemand
 */
@WebServlet("/DemandSevlet")
public class DemandSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//获取所有的需求列表
	private static final int GET_DEMAND_LIST=0;
	
	private static final int DELETE_DEMAND_BY_ID=1;
    public DemandSevlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		Gson gson=new Gson();
		
		SqlSession session=MybatisUtils.initMybatis(IDemand.class);
		IDemand iDemand = session.getMapper(IDemand.class);
		
		String reqType=request.getParameter("TYPE");
		try {
			switch (Integer.parseInt(reqType)) {
			case GET_DEMAND_LIST:
				//返回给客户端的实体类，包装后的
				DemandList demandList=new DemandList();
				demandList.demandDetailsList=new ArrayList<>();
				//从数据库得到的demands原始
				List<Demand> demands=iDemand.getDemandList();
				
				for(Demand demand:demands){
					//需要填充的数据
					int USER_ID=demand.getUser_id();
					int DEMAND_ID=demand.getDemand_id();
					String CONTENT=demand.getDemand_content();
					String TIME=demand.getDeamnd_time();
					
					
					IUser iUser=MybatisUtils.initMybatis(IUser.class).getMapper(IUser.class);
					User user=iUser.getUserById(USER_ID);
					
					//需要填充的数据
					String USER_NAME=user.getUserName();
					String USER_HEAD_ICON=user.getHeadIcon();
					String PHONE=user.getPhone();
					
					DemandList.DemandDetails demandDetails=new DemandList.DemandDetails(DEMAND_ID, USER_ID, USER_HEAD_ICON, USER_NAME, CONTENT, TIME,PHONE);
					//添加到GsonBean中
					demandList.demandDetailsList.add(demandDetails);
				}
				response.getWriter().write(gson.toJson(demandList));
				break;
			case DELETE_DEMAND_BY_ID:
				String DEMAND_ID=request.getParameter("DEMAND_ID");
				iDemand.deleteDemand(Integer.parseInt(DEMAND_ID));
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
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
