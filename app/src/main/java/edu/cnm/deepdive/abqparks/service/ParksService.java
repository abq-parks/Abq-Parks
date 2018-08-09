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
   * @return all amenities.
   */
  @GET("amenities")
  Call<List<Amenity>> getAmenities();

  /**
   * Get parks with user-defined amenities.
   * @param amenities list of selected amenities.
   * @return parks with user-defined amenities.
   */
  @GET("parks/{amenities}")
  Call<List<Park>> getParks(@Path("amenities") String amenities);

  /**
   * Get all parks.
   * @return all parks.
   */
  @GET("parks")
  Call<List<Park>> getAllParks();

  /**
   * Get all reviews for user-selected park.
   * @param parkId ID of selected park.
   * @return all reviews for user-selected park.
   */
  @GET("reviews/{parkId}")
  Call<List<Review>> getReviews(@Path("parkId") long parkId);

  /**
   * Get park matching park ID.
   * @param parkId ID of selected park.
   * @return park matching park ID.
   */
  @GET("parks/{parkId}")
  Call<Park> getPark(@Path("parkId") long parkId);

  /**
   * Post user review of park.
   * @param args comma seperated string containing parkId and userId.
   * @param review {@link Review} object containing the user's review.
   * @return user review of park.
   */
  @POST("reviews/{args}")
  @Headers("accept:application/json")
  Call<Review> createReview(@Path("args")String args, @Body Review review);

  /**
   * Send user credentials to backend for account creation/verification.
   * @param user {@link User} object.
   * @param authorization authorization string.
   * @return {@link User} object.
   */
  @POST("users/")
   Call<User> createUser(@Body User user, @Header("Authorization") String authorization);

}
