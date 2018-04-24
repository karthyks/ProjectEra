package com.github.karthyks.project.era.grpc.client.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VehicleInfoServlet extends HttpServlet {

  public VehicleInfoServlet() {

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.getWriter().print("Vehicle servlet" + req.getServletPath());
  }
}
