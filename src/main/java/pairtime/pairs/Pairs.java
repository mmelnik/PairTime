package pairtime.pairs;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Pairs {

  private List<Pair> excludedPairs;
  private List<String> people;
  private Random random;

  public Pairs(List<String> people) {
    this.people = new ArrayList<>(people);
    excludedPairs = emptyList();
    random = new Random();
  }

  public static Pairs of(List<String> people) {
    return new Pairs(people);
  }

  public Pairs excludePairs(List<Pair> pairsToExclude) {
    this.excludedPairs = new ArrayList<>(pairsToExclude);
    return this;
  }

  public Pairs withRandom(Random random) {
    this.random = random;
    return this;
  }

  public List<Pair> generate() {
    List<Pair> pairs = new ArrayList<>();

    while (people.size() > 1) {
      String driver = people.remove(random.nextInt(people.size()));
      findNewCompanionFor(driver)
          .ifPresent(navigator -> {
            people.remove(navigator);
            pairs.add(new Pair(driver, navigator));
          });
    }
    return pairs;
  }

  private Optional<String> findNewCompanionFor(String firstPerson) {
    List<String> candidates = new ArrayList<>(people);
    while (!candidates.isEmpty()) {
      String secondPerson = candidates.remove(random.nextInt(candidates.size()));
      if (!excludedPairs.contains(new Pair(firstPerson, secondPerson))) {
        return Optional.of(secondPerson);
      }
    }
    return Optional.empty();
  }
}
