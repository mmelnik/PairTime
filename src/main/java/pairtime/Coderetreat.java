package pairtime;

import static java.util.Collections.emptyList;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

import pairtime.pairs.Pair;
import pairtime.pairs.People;
import pairtime.pairs.Round;

public class Coderetreat {

  private final Deque<Round> rounds = new ArrayDeque<>();
  private final People people;

  @Getter @Setter
  private boolean doNotRepeatPairs = true;

  public Coderetreat(List<String> people) {
    this(people, new Random());
  }

  public Coderetreat(List<String> people, Random random) {
    this.people = new People(people, random);
  }

  public Round nextRound() {
    rounds.add(new Round(rounds.size() + 1, makePairs()));
    return rounds.getLast();
  }

  public Round reRollRound() {
    if (!rounds.isEmpty()) {
      rounds.removeLast();
    }
    return nextRound();
  }

  private List<Pair> makePairs() {
    if (doNotRepeatPairs) {
      return people.makePairsExcluding(pairsFromPreviousRounds());
    }
    return people.makePairs();
  }

  private List<Pair> pairsFromPreviousRounds() {
    return rounds.stream()
        .map(Round::getPairs)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  public List<Pair> getCurrentRoundPairs() {
    return rounds.isEmpty() ? emptyList() : rounds.getLast().getPairs();
  }

  public String printCurrentRound() {
    return rounds.isEmpty() ? "" : rounds.getLast().toString();
  }
}
