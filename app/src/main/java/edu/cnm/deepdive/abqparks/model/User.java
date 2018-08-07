package edu.cnm.deepdive.abqparks.model;

import com.google.gson.annotations.Expose;

public class User {

  private String googleID;

  private String firstName;

  private String lastName;

  private String userEmail;

  @Expose(serialize = false, deserialize = true)
  private long id;

  public long getId() {
      return id;
    }

  public void setId(long id) {
      this.id = id;
    }

  public String getGoogleID() {
    return googleID;
  }

  public void setGoogleID(String googleID) {
    this.googleID = googleID;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }
}
