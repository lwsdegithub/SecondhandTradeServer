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
import com.liweisheng.gson.bean.admin.ReportItem;
import com.liweisheng.mybatis.bean.Report;
import com.liweisheng.mybatis.bean.User;
import com.liweisheng.mybatis.dao.IReport;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;

/**
 * Servlet implementation class ReportSevlet
 */
@WebServlet("/ReportSevlet")
public class ReportSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int INSERT_REPORT = 0;
	private static final int ADMIN_GET_REPORT_LIST = 1;
	private static final int ADMIN_DID_HANDLED=2;

	public ReportSevlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		SqlSession session = MybatisUtils.initMybatis(IReport.class);
		IReport iReport = session.getMapper(IReport.class);
		int TYPE = Integer.parseInt(request.getParameter("TYPE"));

		switch (TYPE) {
		case INSERT_REPORT:
			try {
				int USER_ID = Integer.parseInt(request.getParameter("USER_ID"));
				int GOODS_ID = Integer.parseInt(request.getParameter("GOODS_ID"));
				String CONTENT = request.getParameter("CONTENT");
				Report report = new Report();
				report.setGoods_id(GOODS_ID);
				report.setUser_id(USER_ID);
				report.setReport_content(CONTENT);
				iReport.insertReport(report);
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
			} finally {
				session.close();
			}
			break;
		case ADMIN_GET_REPORT_LIST:
			try {
				List<Report> reportList = iReport.getReportList();
				List<ReportItem> reportItems = new ArrayList<>();
				for (Report report : reportList) {
					int userId = report.getUser_id();
					User user = MybatisUtils.initMybatis(IUser.class).getMapper(IUser.class).getUserById(userId);
					ReportItem reportItem = new ReportItem();

					reportItem.setReportId(report.getReport_id());
					reportItem.setGoodsId(report.getGoods_id());
					reportItem.setReporterName(user.getUserName());
					reportItem.setReportContent(report.getReport_content());
					reportItem.setHeadIcon(user.getHeadIcon());

					reportItems.add(reportItem);
				}
				response.getWriter().write(gson.toJson(reportItems));
			} catch (Exception e) {

			} finally {
				session.close();
			}
			break;
		case ADMIN_DID_HANDLED:
			try {
				int REPORT_ID=Integer.parseInt(request.getParameter("REPORT_ID"));
				iReport.setDidHandled(REPORT_ID);
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
			} finally {
			}
			break;
		}
	}

}
