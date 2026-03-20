package pairtime.pairs;

import java.util.ArrayList;
import java.util.List;

public record Round(int number, List<Pair> pairs) {

    public Round(int number, List<Pair> pairs) {
      if (pairs.isEmpty()) {
        throw new IllegalArgumentException("pairs cannot be empty");
      }
      this.number = number;
      this.pairs = new ArrayList<>(pairs);
    }

    @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int pairNumber = 0; pairNumber < pairs.size(); pairNumber++) {
      sb.append("#").append(pairNumber + 1).append(" ")
          .append(pairs.get(pairNumber).toString())
          .append("\n");
    }
    return sb.toString();
  }
}
