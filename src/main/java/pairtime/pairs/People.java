package pairtime.pairs;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class People {

  private Random random;
  private List<String> value;
  private List<Pair> excludedPairs;

  public People(List<String> people, Random random) {
    this.random = random;
    this.value = new ArrayList<>(people);
    excludedPairs = emptyList();
  }

  public static People of(List<String> people) {
    return new People(people, new Random());
  }

  public static People of(List<String> people, Random random) {
    return new People(people, random);
  }

  public People excludePairs(List<Pair> pairsToExclude) {
    this.excludedPairs = new ArrayList<>(pairsToExclude);
    return this;
  }

  public List<Pair> generatePairs() {
    List<Pair> pairs = new ArrayList<>();

    while (value.size() > 1) {
      String driver = value.remove(random.nextInt(value.size()));
      findNavigatorFor(driver)
          .ifPresent(navigator -> {
            value.remove(navigator);
            pairs.add(new Pair(driver, navigator));
          });
    }
    return pairs;
  }

  private Optional<String> findNavigatorFor(String driver) {
    List<String> candidates = new ArrayList<>(value);
    while (!candidates.isEmpty()) {
      String navigator = candidates.remove(random.nextInt(candidates.size()));
      if (!excludedPairs.contains(new Pair(driver, navigator))) {
        return Optional.of(navigator);
      }
    }
    return Optional.empty();
  }
}
