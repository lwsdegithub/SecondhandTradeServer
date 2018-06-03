package com.liweisheng.sevlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.liweisheng.constant.Constant;
import com.liweisheng.gson.bean.Common;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.utils.MybatisUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class AddGoodsSevlet
 */
@WebServlet("/AddGoodsSevlet")
public class AddGoodsSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddGoodsSevlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		Gson gson = new Gson();

		String USER_ID = "";
		String GOODS_NAME = "";
		String DESCRIPTION = "";
		String PRICE = "";
		String GOODS_PHOTOS = "";

		// ��õ�ǰ���GOODS_ID
		SqlSession session = MybatisUtils.initMybatis(IGoods.class);
		IGoods iGoods = session.getMapper(IGoods.class);
		int GOODS_ID = iGoods.getMaxId();

		GOODS_ID = GOODS_ID + 1;

		// ����Ŀ¼
		String uploadPath = this.getServletContext().getRealPath("/") + "GoodsPhotos\\ID_" + GOODS_ID;
		File file = new File(uploadPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		// ���ݱ���
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		try {
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			servletFileUpload.setHeaderEncoding("UTF-8");
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);
			if (fileItems != null && fileItems.size() > 0) {
				// ����������
				for (FileItem fileItem : fileItems) {
					if (fileItem.isFormField()) {
						// ��ͨ������
						switch (fileItem.getFieldName()) {
						case "USER_ID":
							USER_ID = fileItem.getString("UTF-8");
							break;
						case "GOODS_NAME":
							GOODS_NAME = fileItem.getString("UTF-8");
							break;
						case "DESCRIPTION":
							DESCRIPTION = fileItem.getString("UTF-8");
							break;
						case "PRICE":
							PRICE = fileItem.getString("UTF-8");
							break;
						default:
							break;
						}
					} else {
						// �ļ�����
						String fileName = new File(fileItem.getName()).getName();
						// ���조ID_1/1.jpg������ʽ��д�����ݿ⣬�ԡ���������
						GOODS_PHOTOS = GOODS_PHOTOS + "ID_" + GOODS_ID + "\\" + fileName + ",";
						String filePath = uploadPath + "\\" + fileName;
						File storeFile = new File(filePath);
						// �����ļ���Ӳ��
						fileItem.write(storeFile);
						
					}
				}
			}
			//��������
			GOODS_PHOTOS = GOODS_PHOTOS.substring(0, GOODS_PHOTOS.lastIndexOf(","));
			//�洢�����ݿ��������URL��Ҳ����/�����ڷ���������\
			GOODS_PHOTOS=GOODS_PHOTOS.replaceAll("\\\\", "/");
			//System.out.println(GOODS_PHOTOS);
			Goods goods = new Goods();
			goods.setGoods_id(GOODS_ID);
			goods.setUser_id(Integer.parseInt(USER_ID));
			goods.setGoods_name(GOODS_NAME);
			goods.setGoods_description(DESCRIPTION);
			goods.setGoods_price(PRICE);
			goods.setGoods_photo(GOODS_PHOTOS);
			iGoods.insertGoods(goods);
			
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		response.getWriter().write(gson.toJson(new Common(Constant.UPLOAD_SUCCESS)));
	}

}
