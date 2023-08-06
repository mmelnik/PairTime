package pairtime.pairs;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Pair {

  private final String driver;
  private final String navigator;

  public List<String> getPeople() {
    return List.of(driver, navigator);
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
  public String toString() {
    return "Pair: " + driver + ", " + navigator;
  }
}
