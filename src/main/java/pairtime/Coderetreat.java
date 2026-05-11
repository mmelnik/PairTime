package pairtime;

import static java.util.Collections.emptySet;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import pairtime.pairs.Pair;
import pairtime.pairs.People;
import pairtime.pairs.Round;

public class Coderetreat {

  private final Deque<Round> rounds = new ArrayDeque<>();
  private final People people;

  private boolean doNotRepeatPairs = true;

  public Coderetreat(List<String> people) {
    this(people, new Random());
  }

  public Coderetreat(List<String> people, Random random) {
    this.people = new People(people, random);
  }

  public Round nextRound() {
    var pairs = makePairs();
    if (pairs.isEmpty()) {
      throw new IllegalStateException("No more pairs available.");
    }

    rounds.add(new Round(rounds.size() + 1, pairs));
    return rounds.getLast();
  }

  public Round reRollRound() {
    if (!rounds.isEmpty()) {
      rounds.removeLast();
      people.shuffle();
    }
    return nextRound();
  }

  private Set<Pair> makePairs() {
    if (doNotRepeatPairs) {
      return people.makePairsWithExclusions(pairsFromPreviousRounds());
    }
    return people.makePairs();
  }

  private Set<Pair> pairsFromPreviousRounds() {
    return rounds.stream()
        .map(Round::pairs)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }

  public Set<Pair> getCurrentRoundPairs() {
    return rounds.isEmpty() ? emptySet() : rounds.getLast().pairs();
  }

  public int getPeopleCount() {
    return people.size();
  }

  public String printCurrentRound() {
    return rounds.isEmpty() ? "" : rounds.getLast().toString();
  }

  public void setDoNotRepeatPairs(boolean doNotRepeatPairs) {
    this.doNotRepeatPairs = doNotRepeatPairs;
  }
}
