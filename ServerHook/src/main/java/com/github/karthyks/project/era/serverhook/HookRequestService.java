package com.github.karthyks.project.era.serverhook;

import com.github.karthyks.project.era.proto.hook.HookRequest;
import io.grpc.stub.StreamObserver;

import javax.annotation.Nonnull;
import java.util.Scanner;

public class HookRequestService implements Runnable {

  private StreamObserver<HookRequest> requestStreamObserver;
  private String clientName;

  HookRequestService(@Nonnull StreamObserver<HookRequest> requestStreamObserver,
                     @Nonnull String clientName) {
    this.requestStreamObserver = requestStreamObserver;
    this.clientName = clientName;
  }

  @Override
  public void run() {
    HookRequest hookRequest = HookRequest.newBuilder()
        .setSystemLoad("" + execCmd("uptime"))
        .setFreeMemory("" + Runtime.getRuntime().freeMemory())
        .setMaxMemory("" + Runtime.getRuntime().maxMemory())
        .setTotalMemory("" + Runtime.getRuntime().totalMemory())
        .setName(clientName).build();
    requestStreamObserver.onNext(hookRequest);
    if (ServerHook.hookQueue.size() > 0) {
      try {
        ServerHook.hookQueue.take().run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private String execCmd(String cmd) {
    String value = "";
    try {
      Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream())
          .useDelimiter("\\A");
      value = s.hasNext() ? s.next() : "";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }
}