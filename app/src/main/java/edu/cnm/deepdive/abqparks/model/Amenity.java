package edu.cnm.deepdive.abqparks.model;

import java.util.List;

public class Amenity {

  private int id;

  private String name;

  private List<Park> parks;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Park> getParks() {
    return parks;
  }

  public void setParks(List<Park> parks) {
    this.parks = parks;
  }

  @Override
  public String toString() {
    return name;
  }
}
