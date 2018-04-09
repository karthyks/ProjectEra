package com.github.karthyks.project.era.grpc.server.model;

public class PeerInfo {
  public String name;
  public String address;


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PeerInfo) {
      if (this.address.equals(((PeerInfo) obj).address)) {
        return true;
      }
    } else return obj.equals(address);
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return address.hashCode();
  }
}
