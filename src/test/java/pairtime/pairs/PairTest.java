package pairtime.pairs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PairTest {

  @Test
  void pairShouldBePrintedCorrectly() {
    assertThat(new Pair("Вася", "Петя"))
        .hasToString("Pair: Вася, Петя");
  }

  @Test
  void pairShouldBeEqualToInvertedPair() {
    Pair pair = new Pair("Вася", "Петя");
    assertThat(pair).isEqualTo(new Pair("Петя", "Вася"));
  }
}