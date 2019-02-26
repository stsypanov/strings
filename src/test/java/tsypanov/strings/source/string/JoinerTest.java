package tsypanov.strings.source.string;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import tsypanov.strings.source.utils.RandomStringGenerator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class JoinerTest {

  @ParameterizedTest
  @MethodSource("params")
  void join(boolean latin, int length) {
    String[] strings = setUp(latin, length);
    String str2 = Joiner.joinWithStringJoiner(strings);
    String str1 = Joiner.joinWithStringBuilder(strings);

    assertEquals(str1, str2);
  }

  private String[] setUp(boolean latin, int length) {
    String[] stringArray = new String[length];

    RandomStringGenerator generator = new RandomStringGenerator();

    for (int i = 0; i < length; i++) {
      stringArray[i] = latin
              ? generator.randomString("abcdefghijklmnopqrstuvwxyz", length)
              : generator.randomString("абвгдеёжзиклмнопрстуфхцчшщьыъэюя", length);
    }
    return stringArray;
  }


  private static Stream<Arguments> params() {
    return Stream.of(
            Arguments.of(true, 10),
            Arguments.of(true, 100),
            Arguments.of(true, 1000),
            Arguments.of(false, 10),
            Arguments.of(false, 100),
            Arguments.of(false, 1000)
    );
  }

}