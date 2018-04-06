package com.github.karthyks.project.era.grpc.client.auth;

import com.github.karthyks.project.era.grpc.client.Client;
import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

import java.util.concurrent.Executor;

public class JwtCallCredential implements CallCredentials {
  private final String jwt;

  public JwtCallCredential(String jwt) {
    this.jwt = jwt;
  }

  @Override
  public void applyRequestMetadata(MethodDescriptor<?, ?> method, Attributes attrs,
                                   Executor appExecutor, MetadataApplier applier) {
    String authority = attrs.get(ATTR_AUTHORITY);
    System.out.println("Authority " + authority);
    appExecutor.execute(() -> {
      try {
        Metadata headers = new Metadata();
        Metadata.Key<String> jwtKey = Metadata.Key.of("jwt", Metadata.ASCII_STRING_MARSHALLER);
        headers.put(jwtKey, jwt);
        headers.put(Constant.CLIENT_ID_MD_KEY, Client.CLIENT_NAME);
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
