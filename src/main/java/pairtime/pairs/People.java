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

  public People(List<String> people) {
    this.value = new ArrayList<>(people);
    excludedPairs = emptyList();
    random = new Random();
  }

  public static People of(List<String> people) {
    return new People(people);
  }

  public People excludePairs(List<Pair> pairsToExclude) {
    this.excludedPairs = new ArrayList<>(pairsToExclude);
    return this;
  }

  public People withRandom(Random random) {
    this.random = random;
    return this;
  }

  public List<Pair> generate() {
    List<Pair> pairs = new ArrayList<>();

    while (value.size() > 1) {
      String driver = value.remove(random.nextInt(value.size()));
      findNewCompanionFor(driver)
          .ifPresent(navigator -> {
            value.remove(navigator);
            pairs.add(new Pair(driver, navigator));
          });
    }
    return pairs;
  }

  private Optional<String> findNewCompanionFor(String firstPerson) {
    List<String> candidates = new ArrayList<>(value);
    while (!candidates.isEmpty()) {
      String secondPerson = candidates.remove(random.nextInt(candidates.size()));
      if (!excludedPairs.contains(new Pair(firstPerson, secondPerson))) {
        return Optional.of(secondPerson);
      }
    }
    return Optional.empty();
  }
}
