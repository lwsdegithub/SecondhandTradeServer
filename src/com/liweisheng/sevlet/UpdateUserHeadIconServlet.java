package com.liweisheng.sevlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.liweisheng.constant.Constant;
import com.liweisheng.gson.bean.Common;
import com.liweisheng.mybatis.bean.User;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;

/**
 * Servlet implementation class UpdateUserHeadIconServlet
 */
@WebServlet("/UpdateUserHeadIconServlet")
public class UpdateUserHeadIconServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateUserHeadIconServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		Gson gson = new Gson();

		SqlSession session = MybatisUtils.initMybatis(IUser.class);
		IUser iUser = session.getMapper(IUser.class);

		User user = new User();

		int USER_ID = 0;
		String HEAD_ICON = "default.jpg";

		String uploadPath = this.getServletContext().getRealPath("/") + "HeadIcons";
		// 数据保存
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		try {
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			servletFileUpload.setHeaderEncoding("UTF-8");
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);
			if (fileItems != null && fileItems.size() > 0) {
				// 迭代表单数据
				for (FileItem fileItem : fileItems) {
					if (fileItem.isFormField()) {
						// 普通的数据
						switch (fileItem.getFieldName()) {
						case "USER_ID":
							USER_ID = Integer.parseInt(fileItem.getString("UTF-8"));
							break;
						}
					} else {
						// 头像名称
						HEAD_ICON = new File(fileItem.getName()).getName();

						String filePath = uploadPath + "\\" + HEAD_ICON;
						File storeFile = new File(filePath);
						// 保存文件到硬盘
						fileItem.write(storeFile);
					}
				}
			}
			user = iUser.getUserById(USER_ID);
			user.setHeadIcon(HEAD_ICON);
			iUser.updateUser(user);
			session.commit();
			response.getWriter().write(gson.toJson(new Common(Constant.OK)));
		} catch (Exception e) {
		} finally {
			session.close();
		}
	}

}
