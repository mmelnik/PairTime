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

  private Optional<List<Pair>> pairUp(List<String> available, List<Pair> accumulated, List<Pair> excluded) {
    if (available.size() < 2) {
      return Optional.of(accumulated);
    }

    var driver = available.removeFirst();

    return available.stream()
      .map(navigator -> new Pair(driver, navigator))
      .filter(pair -> !excluded.contains(pair))
      .flatMap(pair -> {
        var remaining = new ArrayList<>(available);
        remaining.remove(pair.navigator());

        var result = new ArrayList<>(accumulated);
        result.add(pair);

        return pairUp(remaining, result, excluded).stream();
      })
      .findFirst()
      .or(() -> pairUp(available, accumulated, excluded));
  }

    public void shuffle() {
        var shuffled = new ArrayList<>(value);
        Collections.shuffle(shuffled, random);
        value = shuffled;
    }
}
