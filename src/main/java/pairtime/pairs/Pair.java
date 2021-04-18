package pairtime.pairs;

import java.util.List;

public class Pair<T> {

  private final T firstElement;
  private final T secondElement;

  public Pair(T firstElement, T secondElement) {
    this.firstElement = firstElement;
    this.secondElement = secondElement;
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
    return (this.firstElement.equals(that.firstElement)
        && this.secondElement.equals(that.secondElement))
        || (this.firstElement.equals(that.secondElement)
        && this.secondElement.equals(that.firstElement));
  }

  public List<T> getParticipants() {
    return List.of(firstElement, secondElement);
  }

  @Override
  public String toString() {
    return "Pair: " + firstElement + ", " + secondElement;
  }
}
