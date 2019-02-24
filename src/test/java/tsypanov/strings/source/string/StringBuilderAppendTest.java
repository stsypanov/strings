package tsypanov.strings.source.string;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StringBuilderAppendTest {
  private String str = "abcd";
  private String наРусском = "абвгдеё";
  private String expected = "abcdбвгд";

  @Test
  void appendCharSequence() {
    String string = new StringBuilder().append(str).append(наРусском, 1, 5).toString();

    assertEquals(expected, string);
  }

  @Test
  @SuppressWarnings("ALL")
  void appendSubString() {
    String substring = наРусском.substring(1, 5);
    String string = new StringBuilder().append(str).append(substring).toString();

    assertEquals(expected, string);
  }
}
