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
import com.liweisheng.gson.bean.CommentDetails;
import com.liweisheng.gson.bean.GoodsDetails;
import com.liweisheng.mybatis.bean.Comment;
import com.liweisheng.mybatis.bean.Goods;
import com.liweisheng.mybatis.bean.User;
import com.liweisheng.mybatis.dao.IComment;
import com.liweisheng.mybatis.dao.IGoods;
import com.liweisheng.mybatis.dao.IUser;
import com.liweisheng.utils.MybatisUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class GoodsDetailsSevlet
 */
@WebServlet("/GoodsDetailsSevlet")
public class GoodsDetailsSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GoodsDetailsSevlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		
		String temp=request.getParameter("GOODS_ID");
		int GOODS_ID=Integer.parseInt(temp);
		//Goods
		IGoods iGoods=MybatisUtils.initMybatis(IGoods.class).getMapper(IGoods.class);
		Goods goods=iGoods.getGoodsById(GOODS_ID);
		//User
		int USER_ID=goods.getUser_id();
		IUser iUser=MybatisUtils.initMybatis(IUser.class).getMapper(IUser.class);
		User user=iUser.getUserById(USER_ID);
		//Comment
		IComment iComment=MybatisUtils.initMybatis(IComment.class).getMapper(IComment.class);
		
		List<Comment> comments=iComment.getCommentListByGoodsId(GOODS_ID);
		List<CommentDetails> commentDetailsList=new ArrayList<>();
		if (!comments.isEmpty()) {
			for(Comment comment:comments){
				User commentUser=iUser.getUserById(comment.getUser_id());
				CommentDetails commentDetails=new CommentDetails();
				commentDetails.setHeadIcon(commentUser.getHeadIcon());
				commentDetails.setUserName(commentUser.getUserName());
				commentDetails.setContent(comment.getComment_content());
				commentDetails.setTime(comment.getComment_time());
				commentDetailsList.add(commentDetails);
			}
		}
		//Gson
		Gson gson=new Gson();
		String json=gson.toJson(new GoodsDetails(goods, user, commentDetailsList));
		response.getWriter().write(json);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
