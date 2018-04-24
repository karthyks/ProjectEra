package com.github.karthyks.project.era.network.model;

public class PeerInfo {
  public String name;
  public String traceAddress;
  public int tracePort;
  public String serverAddress;
  public int serverPort;
  public double[] loadAverage;
  public long maxMemory;
  public long freeMemory;
  public long totalMemory;


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PeerInfo) {
      return this.traceAddress.equals(((PeerInfo) obj).traceAddress)
          && this.tracePort == ((PeerInfo) obj).tracePort;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (traceAddress + tracePort).hashCode();
  }
}
