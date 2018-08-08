package edu.cnm.deepdive.abqparks.service;

import edu.cnm.deepdive.abqparks.model.Amenity;
import edu.cnm.deepdive.abqparks.model.Park;
import edu.cnm.deepdive.abqparks.model.Review;
import edu.cnm.deepdive.abqparks.model.User;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ParksService {

  @GET("amenities")
  Call<List<Amenity>> getAmenities();

  @GET("parks/{amenities}")
  Call<List<Park>> getParks(@Path("amenities") String amenities);

  @GET("parks")
  Call<List<Park>> getAllParks();

  @GET("reviews/{parkId}")
  Call<List<Review>> getReviews(@Path("parkId") long parkId);

  @GET("parks/{parkId}")
  Call<Park> getPark(@Path("parkId") long parkId);

  @POST("reviews/{args}")
  @Headers("accept:application/json")
  Call<Review> createReview(@Path("args")String args, @Body Review review);

  @POST("users/")
   Call<User> createUser(@Body User user, @Header("Authorization") String authorization);

}
