package edu.cnm.deepdive.abqparks.model;

import java.util.Date;
import java.util.List;

public class Review {

  private int id;

  private Date date;

  private String review;

  private List<Park> parks;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public List<Park> getParks() {
    return parks;
  }

  public void setParks(List<Park> parks) {
    this.parks = parks;
  }
}
