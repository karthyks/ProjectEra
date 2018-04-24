package com.github.karthyks.project.era.serverhook.auth;

import com.github.karthyks.project.era.network.Constant;
import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

import java.util.concurrent.Executor;

public class HookCredential implements CallCredentials {
  private String token;
  private String name;
  private int serverPort;

  public HookCredential(String token, String name, int serverPort) {
    this.token = token;
    this.name = name;
    this.serverPort = serverPort;
  }

  @Override
  public void applyRequestMetadata(MethodDescriptor<?, ?> method, Attributes attrs,
                                   Executor appExecutor, MetadataApplier applier) {
    String authority = attrs.get(ATTR_AUTHORITY);
    System.out.println("Authority " + authority);
    appExecutor.execute(() -> {
      try {
        Metadata headers = new Metadata();
        headers.put(Constant.TOKEN_METADATA_KEY, token);
        headers.put(Constant.SERVER_PORT_MD_KEY, "" + serverPort);
        headers.put(Constant.CLIENT_ID_MD_KEY, name);
        applier.apply(headers);
      } catch (Throwable e) {
        applier.fail(Status.UNAUTHENTICATED.withCause(e));
      }
    });

  }

  @Override
  public void thisUsesUnstableApi() {

  }
}
