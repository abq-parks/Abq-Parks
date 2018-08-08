package edu.cnm.deepdive.abqparks.model;

import com.google.gson.annotations.Expose;

/**
 * User model utilized by the Retrofit service.
 */
public class User {

  private String googleID;

  private String firstName;

  private String lastName;

  private String userEmail;

  @Expose(serialize = false, deserialize = true)
  private long id;

  /**
   * Get user ID.
   * @return
   */
  public long getId() {
      return id;
    }

  /**
   * Set user ID.
   * @param id
   */
  public void setId(long id) {
      this.id = id;
    }

  /**
   * Get user's Google ID.
   * @return
   */
  public String getGoogleID() {
    return googleID;
  }

  /**
   * Set user's Google ID.
   * @param googleID
   */
  public void setGoogleID(String googleID) {
    this.googleID = googleID;
  }

  /**
   * Get user's last name.
   * @return
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Set user's last name.
   * @param lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get user's first name.
   * @return
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Set user's first name.
   * @param firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get user's email.
   * @return
   */
  public String getUserEmail() {
    return userEmail;
  }

  /**
   * Set user's email.
   * @param userEmail
   */
  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }
}
