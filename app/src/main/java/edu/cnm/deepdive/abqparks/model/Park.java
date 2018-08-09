package edu.cnm.deepdive.abqparks.model;

import java.util.List;

/**
 * Park model utilized by the Retrofit service.
 */
public class Park {

  private long id;

  private String name;

  private double latitude;

  private double longitude;

  private List<Amenity> amenities;

  /**
   * Get park ID.
   * @return park ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Set park ID.
   * @param id park ID.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Get park name.
   * @return park name.
   */
  public String getName() {
    return name;
  }

  /**
   * Set park name.
   * @param name park name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get latitude coordinate of park.
   * @return latitude coordinate of park.
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Set latitude coordinate of park.
   * @param latitude latitude coordinate of park.
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * Get longitude coordinate of park.
   * @return longitude coordinate of park.
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Set longitude coordinate for park.
   * @param longitude longitude coordinate for park.
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /**
   * Get list of amenities this park has.
   * @return list of amenities this park has.
   */
  public List<Amenity> getAmenities() {
    return amenities;
  }

  /**
   * Set list of amenities that this park has.
   * @param amenities list of amenities that this park has.
   */
  public void setAmenities(List<Amenity> amenities) {
    this.amenities = amenities;
  }
}
