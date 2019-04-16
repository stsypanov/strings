package tsypanov.strings.source.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringConcatTest {

  @Test
  void concat() {
    assertEquals(StringConcat.concatWithStringBuilder(), StringConcat.concatWithStringChain());

    String[] strings = {""};
    assertEquals(StringConcat.concatWithStringBuilder(strings), StringConcat.concatWithStringChain(strings));

    strings = new String[]{"", ""};
    assertEquals(StringConcat.concatWithStringBuilder(strings), StringConcat.concatWithStringChain(strings));

    strings = new String[]{"1", "2"};
    assertEquals(StringConcat.concatWithStringBuilder(strings), StringConcat.concatWithStringChain(strings));

    strings = new String[]{"1", "2", ""};
    assertEquals(StringConcat.concatWithStringBuilder(strings), StringConcat.concatWithStringChain(strings));

    strings = new String[]{"1", null, ""};
    assertEquals(StringConcat.concatWithStringBuilder(strings), StringConcat.concatWithStringChain(strings));

    strings = new String[]{null};
    assertEquals(StringConcat.concatWithStringBuilder(strings), StringConcat.concatWithStringChain(strings));
  }

}