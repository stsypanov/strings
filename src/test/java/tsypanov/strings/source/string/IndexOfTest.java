package tsypanov.strings.source.string;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class IndexOfTest {

  @ParameterizedTest
  @MethodSource("testArguments")
  void noSlash(String name, int from) {
    int conventional = conventional(name, from);
    int improved = improved(name, from);
    assert conventional == improved || conventional < 0 && improved < 0;
  }

  private int conventional(String name, int from) {
    return name.substring(from).indexOf('/');
  }

  private int improved(String name, int from) {
    return name.indexOf('/') - from;
  }

  static Stream<Arguments> testArguments() {
    return Stream.of(
            Arguments.of("no-slash", 0),
            Arguments.of("no-slash", 0),
            Arguments.of("no-slash", 1),
            Arguments.of("no-slash", 1),
            Arguments.of("no-slash", 2),
            Arguments.of("no-slash", 2),
            Arguments.of("no-slash", 3),
            Arguments.of("no-slash", 3),
            Arguments.of("no-slash", 4),
            Arguments.of("no-slash", 4),
            Arguments.of("no-slash", 5),
            Arguments.of("no-slash", 5),

            Arguments.of("has/slash", 0),
            Arguments.of("has/slash", 0),
            Arguments.of("has/slash", 1),
            Arguments.of("has/slash", 1),
            Arguments.of("has/slash", 2),
            Arguments.of("has/slash", 2),
            Arguments.of("has/slash", 3),
            Arguments.of("has/slash", 3),
            Arguments.of("has/slash", 4),
            Arguments.of("has/slash", 4),
            Arguments.of("has/slash", 5),
            Arguments.of("has/slash", 5)

    );
  }
}
