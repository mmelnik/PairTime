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
    return makePairsExcluding(emptyList());
  }

  public List<Pair> makePairsExcluding(List<Pair> excludedPairs) {
    List<Pair> pairs = new ArrayList<>();

    var people = new ArrayList<>(value);

    while (people.size() > 1) {
      String driver = people.remove(random.nextInt(people.size()));
      Optional<String> navigator = findNavigatorFor(driver, people, excludedPairs);

      if (navigator.isPresent()) {
        people.remove(navigator.get());
        pairs.add(new Pair(driver, navigator.get()));
      }
    }
    return pairs;
  }

  private Optional<String> findNavigatorFor(String driver, List<String> people,
      List<Pair> excludedPairs) {
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
