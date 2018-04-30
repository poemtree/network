package com.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	Logger logger = Logger.getLogger("work");
	
	@RequestMapping(value="main.do")
	@ResponseBody
	public void main(HttpServletRequest req) throws UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		String speed = req.getParameter("speed");
		String temp = req.getParameter("temp");
		System.out.println(speed);
		logger.debug("speed="+speed+", temp="+temp);
	}
	
}
