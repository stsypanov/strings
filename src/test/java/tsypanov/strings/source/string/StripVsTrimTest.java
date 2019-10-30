package tsypanov.strings.source.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StripVsTrimTest {
  @Test
  public void testSame() {
    String s = "\t abc \n";

    assertEquals("abc", s.trim());
    assertEquals("abc", s.strip());
  }

  @Test
  public void testDifferent() {
    char c = '\u2000';
    String s = c + "abc" + c;

    assertTrue(Character.isWhitespace(c));
    assertEquals(s, s.trim());
    assertEquals("abc", s.strip());
  }

  @Test
  public void testBlank() {
    char c = '\u200A';
    String s = "\u200A";

    boolean empty = s.trim().isEmpty();
    boolean blank = s.isBlank();
    assertNotEquals(empty, blank);

    assertTrue(Character.isWhitespace(c));
    assertEquals(s, s.trim());
  }
}