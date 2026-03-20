package pairtime.pairs;

import java.util.List;

public record Pair(String driver, String navigator) {

  public Pair {
    if (driver == null || driver.isEmpty() || navigator == null || navigator.isEmpty()) {
      throw new IllegalArgumentException("Driver and Navigator cannot be null or empty");
    }
    if (driver.equals(navigator)) {
      throw new IllegalArgumentException("Navigator and Driver is same person");
    }
  }

  public List<String> getPeople() {
    return List.of(driver, navigator);
  }

  public boolean contains(String person) {
    if (person == null) {
      return false;
    }
    return driver.equals(person) || navigator.equals(person);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Pair that = (Pair) o;
    return (this.driver.equals(that.driver)
        && this.navigator.equals(that.navigator))
        || (this.driver.equals(that.navigator)
        && this.navigator.equals(that.driver));
  }

  @Override
  public int hashCode() {
    return driver.hashCode() + navigator.hashCode();
  }

  @Override
  public String toString() {
    return "Pair: " + driver + ", " + navigator;
  }
}
