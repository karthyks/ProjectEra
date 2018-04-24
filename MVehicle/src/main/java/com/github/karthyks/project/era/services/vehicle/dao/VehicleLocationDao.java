package com.github.karthyks.project.era.services.vehicle.dao;

import com.github.karthyks.project.era.proto.vehicle.SaveLocationRequest;
import com.github.karthyks.project.era.proto.vehicle.VehicleLocation;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

public class VehicleLocationDao {
  public static final String COLLECTION_NAME = "vehicle.locations";

  private MongoCollection<Document> collection;

  public VehicleLocationDao(MongoDatabase mongoDatabase) {
    this.collection = mongoDatabase.getCollection(COLLECTION_NAME);
  }


  public void saveLocations(SaveLocationRequest request) {
    List<VehicleLocation> vehicleLocations = request.getVehicleLocationsList();
    List<Document> locations = new LinkedList<>();
    for (VehicleLocation location : vehicleLocations) {
      Document locationObject = new Document();
      locationObject.append("vehicleId", location.getVehicleId());
      locationObject.append("latitude", location.getLatitude());
      locationObject.append("longitude", location.getLongitude());
      locationObject.append("address", location.getAddress());
      locationObject.append("time", location.getTime());
      locations.add(locationObject);
    }
    collection.insertMany(locations);
  }
}
