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
    assertThat(new Pair("Вася", "Петя"))
        .isEqualTo(new Pair("Петя", "Вася"));
  }
}