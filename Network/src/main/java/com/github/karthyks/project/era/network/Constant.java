package com.github.karthyks.project.era.network;

import io.grpc.Context;
import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constant {
  public static final String JWT_SECRET = "nqOIjvhDQCsehmG1tbfMzFBLqdo=";
  public static final String ISSUER = "auth0";
  public static final Context.Key<String> USER_ID_CTX_KEY = Context.key("userId");
  public static final Context.Key<String> USER_TOKEN_CTX_KEY = Context.key("token");
  public static final Context.Key<String> SCOPE = Context.key("scope");
  public static final Context.Key<String> REMOTE_ADDRESS = Context.key("remote_address");
  public static final Context.Key<Integer> REMOTE_PORT = Context.key("remote_port");
  public static final Context.Key<String> REMOTE_SERVER_ADDRESS = Context
      .key("remote_server_address");
  public static final Context.Key<Integer> REMOTE_SERVER_PORT = Context.key("remote_server_port");
  public static final Context.Key<String> JWT_CTX_KEY = Context.key("jwt");
  public static final Context.Key<String> TRACE_ID_CTX_KEY = Context.key("traceId");
  public static final Context.Key<String> CLIENT_ID_KEY = Context.key("clientId");
  public static final Metadata.Key<String> CLIENT_ID_MD_KEY = Metadata.Key.of("client-id",
      ASCII_STRING_MARSHALLER);

  public static final Metadata.Key<String> JWT_METADATA_KEY = Metadata.Key.of("jwt",
      ASCII_STRING_MARSHALLER);
  public static final Metadata.Key<String> TOKEN_METADATA_KEY = Metadata.Key.of("authToken",
      ASCII_STRING_MARSHALLER);
  public static final Metadata.Key<String> TRACE_ID_METADATA_KEY = Metadata.Key.of("traceId",
      ASCII_STRING_MARSHALLER);
  public static final Metadata.Key<String> SERVER_PORT_MD_KEY = Metadata.Key.of("server_port",
      ASCII_STRING_MARSHALLER);
}
