package com.hdp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CommonController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract void doService(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		doService(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		doService(request, response);
	}
}
