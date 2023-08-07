package pairtime.pairs;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class People {

  private Random random;
  private List<String> value;

  public People(List<String> people, Random random) {
    this.random = random;
    this.value = new ArrayList<>(people);
  }

  public People(List<String> people) {
    this(people, new Random());
  }

  public List<Pair> generatePairs() {
    return generatePairsExcluding(emptyList());
  }

  public List<Pair> generatePairsExcluding(List<Pair> excludedPairs) {
    List<Pair> pairs = new ArrayList<>();

    while (value.size() > 1) {
      String driver = value.remove(random.nextInt(value.size()));
      Optional<String> navigator = findNavigatorFor(driver, value, excludedPairs);

      if (navigator.isPresent()) {
        value.remove(navigator.get());
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
