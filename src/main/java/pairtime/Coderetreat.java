package pairtime;

import static java.util.Collections.emptyList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import pairtime.pairs.Pair;
import pairtime.pairs.People;
import pairtime.pairs.Round;

public class Coderetreat {

  private People people;
  private Deque<Round> rounds = new ArrayDeque<>();
  private List<Pair> pairsToExclude = new ArrayList<>();

  @Getter @Setter
  private boolean doNotRepeatPairs = true;

  public Coderetreat(List<String> people) {
    this(people, new Random());
  }

  public Coderetreat(List<String> people, Random random) {
    this.people = new People(people, random);
  }

  public List<Pair> buildNewRoundPairs() {
    pairsToExclude.addAll(rounds.isEmpty() ? emptyList() : rounds.getLast().getPairs());
    rounds.add(new Round(rounds.size() + 1, makePairs()));
    return rounds.getLast().getPairs();
  }

  public List<Pair> reRollRound() {
    if (!rounds.isEmpty()) {
      rounds.removeLast();
    }
    rounds.add(new Round(rounds.size() + 1, makePairs()));
    return rounds.getLast().getPairs();
  }

  private List<Pair> makePairs() {
    if (doNotRepeatPairs) {
      return people.makePairsExcluding(pairsToExclude);
    }
    return people.makePairs();
  }

  public List<Pair> getCurrentRoundPairs() {
    return rounds.isEmpty() ? emptyList() : rounds.getLast().getPairs();
  }

  public String printCurrentRoundPairs() {
    StringBuilder sb = new StringBuilder();
    for (int index = 0; index < rounds.getLast().getPairs().size(); index++) {
      sb.append("#" + (index + 1) + " ")
          .append(rounds.getLast().getPairs().get(index).toString())
          .append("\n");
    }
    return sb.toString();
  }
}
