package edu.cnm.deepdive.abqparks.service;

import edu.cnm.deepdive.abqparks.model.Amenity;
import edu.cnm.deepdive.abqparks.model.Park;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParksService {

  @GET("amenities")
  Call<List<Amenity>> getAmenities();

  @GET("parks/{amenities}")
  Call<List<Park>> getParks(@Path("amenities") String amenities);

  @GET("parks")
  Call<List<Park>> getAllParks();

}
