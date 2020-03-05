package pairtime;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import pairtime.pairs.Pair;
import pairtime.pairs.Pairs;

public class Coderetreat {

  private List<String> people;
  private List<Pair<String>> pairsToExclude = new ArrayList<>();
  @Getter
  private List<Pair<String>> currentRoundPairs = emptyList();
  private Random random;

  public Coderetreat(List<String> people) {
    this(people, new Random());
  }

  public Coderetreat(List<String> people, Random random) {
    this.people = people;
    this.random = random;
  }

  public List<Pair<String>> buildNewRoundPairs() {
    pairsToExclude.addAll(currentRoundPairs);
    return makePairs();
  }

  public List<Pair<String>> shufflePairs() {
    return currentRoundPairs.isEmpty() ? emptyList() : makePairs();
  }

  private List<Pair<String>> makePairs() {
    currentRoundPairs = Pairs.of(people)
        .excludePairs(pairsToExclude)
        .withRandom(random)
        .generate();
    return currentRoundPairs;
  }

  public String printCurrentRoundPairs() {
    StringBuilder sb = new StringBuilder();
    for (int index = 0; index < currentRoundPairs.size(); index++) {
      sb.append("#" + (index + 1) + " ")
          .append(currentRoundPairs.get(index).toString())
          .append("\n");
    }
    return sb.toString();
  }
}
