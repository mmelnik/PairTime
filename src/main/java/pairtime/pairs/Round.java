package pairtime.pairs;

import java.util.Set;

public record Round(int number, Set<Pair> pairs) {

  public Round(int number, Set<Pair> pairs) {
    if (number < 1) throw new IllegalArgumentException("Round number must be positive");
    if (pairs == null || pairs.isEmpty()) throw new IllegalArgumentException("Pairs cannot be empty");

    this.number = number;
    this.pairs = Set.copyOf(pairs);
  }

  @Override
  public String toString() {
    int pairNumber = 0;
    var stringBuilder = new StringBuilder();
    for (Pair pair : pairs) {
        stringBuilder.append("#").append(++pairNumber).append(" ").append(pair.toString()).append("\n");
    }
    return stringBuilder.toString();
  }
}
