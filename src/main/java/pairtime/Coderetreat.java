package pairtime;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import pairtime.pairs.Pair;
import pairtime.pairs.People;

public class Coderetreat {

  private List<String> people;
  private List<Pair> pairsToExclude = new ArrayList<>();
  @Getter
  private List<Pair> currentRoundPairs = emptyList();
  private Random random;

  @Getter @Setter
  private boolean doNotRepeatPairs = true;

  public Coderetreat(List<String> people) {
    this(people, new Random());
  }

  public Coderetreat(List<String> people, Random random) {
    this.people = people;
    this.random = random;
  }

  public List<Pair> buildNewRoundPairs() {
    pairsToExclude.addAll(currentRoundPairs);
    return makePairs();
  }

  public List<Pair> shufflePairs() {
    return currentRoundPairs.isEmpty() ? emptyList() : makePairs();
  }

  private List<Pair> makePairs() {
    if (doNotRepeatPairs) {
      currentRoundPairs = new People(people, random).makePairsExcluding(pairsToExclude);
    } else {
      currentRoundPairs = new People(people, random).makePairs();
    }
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
