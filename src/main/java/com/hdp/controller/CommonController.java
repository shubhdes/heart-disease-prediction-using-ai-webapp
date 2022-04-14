package com.hdp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hdp.utils.ApplicationError;

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
		try {
			doService(request, response);
		} catch (ApplicationError appError) {
			final String exMsg = appError.getMessage();
			final String msg = "<div class='alert alert-danger' style='text-align: center;'>" + exMsg + "</div>";
			request.setAttribute("msg", msg);
			final RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/result.jsp");
			// redirect to result page
			rqsDispatcher.forward(request, response);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		try {
			doService(request, response);
		} catch (ApplicationError appError) {
			final String exMsg = appError.getMessage();
			final String msg = "<div class='alert alert-danger' style='text-align: center;'>" + exMsg + "</div>";
			request.setAttribute("msg", msg);
			final RequestDispatcher rqsDispatcher = request.getRequestDispatcher("jsp/result.jsp");
			// redirect to result page
			rqsDispatcher.forward(request, response);
		}
	}
}
