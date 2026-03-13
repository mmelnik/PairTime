package pairtime.pairs;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class People {

  private final Random random;
  private final List<String> value;

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

    return pairUp(new ArrayList<>(value), new ArrayList<>(), excluded);
  }

  private List<Pair> pairUp(ArrayList<String> available, List<Pair> pairs, List<Pair> excluded) {
    while (available.size() > 1) {
      String driver = available.remove(random.nextInt(available.size()));
      Optional<String> navigator = findNavigatorFor(driver, available, excluded);

      if (navigator.isPresent()) {
        available.remove(navigator.get());
        pairs.add(new Pair(driver, navigator.get()));
      }
    }
    return pairs;
  }

  private Optional<String> findNavigatorFor(String driver, List<String> people, List<Pair> excludedPairs) {
    List<String> candidates = new ArrayList<>(people);
    while (!candidates.isEmpty()) {
      String navigator = candidates.remove(random.nextInt(candidates.size()));
      if (!excludedPairs.contains(new Pair(driver, navigator))) {
        return Optional.of(navigator);
      }
    }
    return Optional.empty();
  }

}
