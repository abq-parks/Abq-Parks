package edu.cnm.deepdive.abqparks.model;

import java.util.Date;

/**
 * Review model utilized by the Retrofit service.
 */
public class Review {

  private int id;
  private Date date;
  private String review;


  /**
   * Get review ID.
   * @return review ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Set review ID.
   * @param id review ID.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get review ID.
   * @return review ID.
   */
  public Date getDate() {
    return date;
  }

  /**
   * Set review date.
   * @param date review date.
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * Get review text.
   * @return review text.
   */
  public String getReview() {
    return review;
  }

  /**
   * Set review text.
   * @param review review text.
   */
  public void setReview(String review) {
    this.review = review;
  }

  @Override
  public String toString() {
    return review;
  }
}
