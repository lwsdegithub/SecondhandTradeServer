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
import com.liweisheng.mybatis.bean.Comment;
import com.liweisheng.mybatis.dao.IComment;
import com.liweisheng.utils.MybatisUtils;

/**
 * Servlet implementation class CommentSevlet
 */
@WebServlet("/CommentSevlet")
public class CommentSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int ADD_NEW_COMMENT=0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentSevlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		Gson gson=new Gson();
		
		//请求内容类型
		String reqType=request.getParameter("TYPE");
		
		SqlSession session=MybatisUtils.initMybatis(IComment.class);
		IComment iComment=session.getMapper(IComment.class);
		
		switch (Integer.parseInt(reqType)) {
		case ADD_NEW_COMMENT:
			//新增评论 
			int GOODS_ID=Integer.parseInt(request.getParameter("GOODS_ID"));
			int USER_ID=Integer.parseInt(request.getParameter("USER_ID"));
			String content=request.getParameter("COMMENT_CONTENT");
			try {
				Comment comment=new Comment();
				comment.setGoods_id(GOODS_ID);
				comment.setUser_id(USER_ID);
				comment.setComment_content(content);
				iComment.addNewComment(comment);
				session.commit();
				response.getWriter().write(gson.toJson(new Common(Constant.OK)));
			} finally {
				session.close();
			}
			break;
		default:
			break;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
