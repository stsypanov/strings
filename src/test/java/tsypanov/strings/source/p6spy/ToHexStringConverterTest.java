package tsypanov.strings.source.p6spy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToHexStringConverterTest {

  private String expected;
  private byte[] bytes;
  private ToHexStringConverter converter = new ToHexStringConverter();

  @BeforeEach
  void setUp() {
    bytes = freshBytes();
    expected = converter.toHexString(bytes);
  }

  @RepeatedTest(100)
  void patched_toHexString() {
    String actual = converter.patched_toHexString(bytes);
    assertEquals(expected, actual);
  }

  @RepeatedTest(100)
  void chars_toHexString() {
    String actual = converter.chars_toHexString(bytes);
    assertEquals(expected, actual);
  }

  @RepeatedTest(100)
  void bytes_toHexString() {
    String actual = converter.bytes_toHexString(bytes);
    assertEquals(expected, actual);
  }

  private byte[] freshBytes() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int length = random.nextInt(100500);

    byte[] bytes = new byte[length];
    random.nextBytes(bytes);
    return bytes;
  }
}