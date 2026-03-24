package pairtime.pairs;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    var excluded = excludedPairs != null ? excludedPairs : List.<Pair>of();

    return pairUp(new ArrayList<>(value), List.of(), excluded).orElse(emptyList());
  }

  private Optional<List<Pair>> pairUp(List<String> availableParticipants,
                                      List<Pair> accumulatedPairs, List<Pair> excludedPairs) {

    if (availableParticipants.size() < 2) {
      return Optional.of(accumulatedPairs);
    }

    return availableParticipants.stream().flatMap(driver -> {

      var navigatorCandidates = new ArrayList<>(availableParticipants);
      navigatorCandidates.remove(driver);

      return navigatorCandidates.stream()
              .map(navigator -> new Pair(driver, navigator))
              .filter(pair -> !excludedPairs.contains(pair))
              .flatMap(pair -> {

                var nextAvailableParticipants = new ArrayList<>(navigatorCandidates);
                nextAvailableParticipants.remove(pair.navigator());

                var nextAccumulatedPairs = new ArrayList<>(accumulatedPairs);
                nextAccumulatedPairs.add(pair);

                return pairUp(nextAvailableParticipants, nextAccumulatedPairs, excludedPairs).stream();
            });
    }).findFirst();
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
