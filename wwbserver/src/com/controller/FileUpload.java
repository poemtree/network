package com.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUpload {
	
	@RequestMapping(value="fileupload.do", method= RequestMethod.POST)
	@ResponseBody
	public void fileupload(HttpServletResponse  res, @RequestParam MultipartFile mf) {
		String filename = mf.getOriginalFilename();
		byte[] data;
		PrintWriter out = null;
		try(FileOutputStream fos = new FileOutputStream("c:/"+filename)){
			out = res.getWriter();
			data = mf.getBytes();
			fos.write(data);
			out.write("1");
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			out.write("2");
			e.printStackTrace();
		} finally {
			if(out != null)
				out.close();
		}
	}
	
}
