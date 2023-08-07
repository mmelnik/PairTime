package pairtime.pairs;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Round {

  int number;
  List<Pair> pairs;

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
