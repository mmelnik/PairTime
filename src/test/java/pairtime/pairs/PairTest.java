package pairtime.pairs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PairTest {

  @Test
  void pairShouldBePrintedCorrectly() {
    assertThat(new Pair("Вася", "Петя").toString()).isEqualTo("Pair: Вася, Петя");
  }
}