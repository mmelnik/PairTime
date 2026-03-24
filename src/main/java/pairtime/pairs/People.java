package pairtime.pairs;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class People {

  private final Random random;

  private List<String> value;

  public People(List<String> people, Random random) {
    this.random = random;
    this.value = new ArrayList<>(people);
  }

  public People(List<String> people) {
    this(people, new Random());
  }

  public List<Pair> makePairs() {
    return makePairsWithExclusions(emptyList());
  }

  public List<Pair> makePairsWithExclusions(List<Pair> excludedPairs) {
    var excluded = excludedPairs != null ? Set.copyOf(excludedPairs) : Set.<Pair>of();

    return findPairsForNextDriver(new ArrayList<>(value), List.of(), excluded).orElse(emptyList());
  }

  private Optional<List<Pair>> findPairsForNextDriver(List<String> availableParticipants,
                                                      List<Pair> accumulatedPairs, Set<Pair> excludedPairs) {
    if (availableParticipants.size() < 2) {
      return Optional.of(accumulatedPairs);
    }

    for (var driver : availableParticipants) {
      var navigatorCandidates = new ArrayList<>(availableParticipants);
      navigatorCandidates.remove(driver);

      var pairs = findPairsForNextNavigator(driver, navigatorCandidates, accumulatedPairs, excludedPairs);
      if (pairs.isPresent()) {
        return pairs;
      }
    }
    return Optional.empty();
  }

  private Optional<List<Pair>> findPairsForNextNavigator(String driver, List<String> navigatorCandidates,
                                                         List<Pair> accumulatedPairs, Set<Pair> excludedPairs) {
    for (var navigator : navigatorCandidates) {
      var pair = new Pair(driver, navigator);

      if (excludedPairs.contains(pair)) {
        continue;
      }

      var nextAvailableParticipants = new ArrayList<>(navigatorCandidates);
      nextAvailableParticipants.remove(navigator);

      var nextAccumulatedPairs = new ArrayList<>(accumulatedPairs);
      nextAccumulatedPairs.add(pair);

      var pairs = findPairsForNextDriver(nextAvailableParticipants, nextAccumulatedPairs, excludedPairs);
      if (pairs.isPresent()) {
        return pairs;
      }
    }
    return Optional.empty();
  }

  public void shuffle() {
    var shuffled = new ArrayList<>(value);
    Collections.shuffle(shuffled, random);
    value = shuffled;
  }

  public List<String> getValue() {
    return new ArrayList<>(value);
  }
}
