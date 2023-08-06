package pairtime.pairs;

import java.util.List;

public class Pair<T> {

  private final T driver;
  private final T navigator;

  public Pair(T driver, T navigator) {
    this.driver = driver;
    this.navigator = navigator;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Pair<?> that = (Pair<?>) o;
    return (this.driver.equals(that.driver)
        && this.navigator.equals(that.navigator))
        || (this.driver.equals(that.navigator)
        && this.navigator.equals(that.driver));
  }

  public List<T> getParticipants() {
    return List.of(driver, navigator);
  }

  @Override
  public String toString() {
    return "Pair: " + driver + ", " + navigator;
  }
}
