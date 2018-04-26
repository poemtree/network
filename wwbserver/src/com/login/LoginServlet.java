/*package com.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		String name = req.getParameter("name");
		String result;
		if(id.equals("qq") && pwd.equals("11")) {
			result = name + " 통과";
		} else {
			result = name + " 실패";
		}

		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.print(result);
		out.close();
	}
}
*/