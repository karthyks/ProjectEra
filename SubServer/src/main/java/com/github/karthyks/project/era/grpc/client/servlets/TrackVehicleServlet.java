package com.github.karthyks.project.era.grpc.client.servlets;

import com.github.karthyks.project.era.grpc.client.SubServer;
import com.github.karthyks.project.era.network.model.PeerInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class TrackVehicleServlet extends HttpServlet {

  public TrackVehicleServlet() {

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Set<PeerInfo> peerSet = SubServer.peersMap.keySet();
    for (PeerInfo peer : peerSet) {
      if (peer.name.equals("vehicle")) {
//        resp.getWriter().println(peer.traceAddress + ":" + peer.tracePort);
        resp.getWriter().println(peer.serverAddress + ":" + peer.serverPort);
      }
    }
  }
}
