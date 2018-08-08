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

/**
 * Interface implemented by Retrofit for webservice consumption.
 */
public interface ParksService {

  /**
   * Get all amenities.
   * @return
   */
  @GET("amenities")
  Call<List<Amenity>> getAmenities();

  /**
   * Get parks with user-defined amenities.
   * @param amenities
   * @return
   */
  @GET("parks/{amenities}")
  Call<List<Park>> getParks(@Path("amenities") String amenities);

  /**
   * Get all parks.
   * @return
   */
  @GET("parks")
  Call<List<Park>> getAllParks();

  /**
   * Get all reviews for user-selected park.
   * @param parkId
   * @return
   */
  @GET("reviews/{parkId}")
  Call<List<Review>> getReviews(@Path("parkId") long parkId);

  /**
   * Get park matching park ID.
   * @param parkId
   * @return
   */
  @GET("parks/{parkId}")
  Call<Park> getPark(@Path("parkId") long parkId);

  /**
   * Post user review of park.
   * @param args
   * @param review
   * @return
   */
  @POST("reviews/{args}")
  @Headers("accept:application/json")
  Call<Review> createReview(@Path("args")String args, @Body Review review);

  /**
   * Send user credentials to backend for account creation/verification.
   * @param user
   * @param authorization
   * @return
   */
  @POST("users/")
   Call<User> createUser(@Body User user, @Header("Authorization") String authorization);

}
