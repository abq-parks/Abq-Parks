package edu.cnm.deepdive.abqparks.model;

import java.util.List;

/**
 * Park amenity model utilized by Retrofit service.
 */
public class Amenity {

  private int id;

  private String name;

  private String displayName;

  private List<Park> parks;

  /**
   * Get amenity ID.
   * @return Amenity ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Set amenity ID.
   * @param id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get amenity name.
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Set amenity name.
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get display name.
   * @return
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Set display name.
   * @param displayName
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Get list of parks with this amenity.
   * @return
   */
  public List<Park> getParks() {
    return parks;
  }

  /**
   * Set list of parks with this amenity.
   * @param parks
   */
  public void setParks(List<Park> parks) {
    this.parks = parks;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
