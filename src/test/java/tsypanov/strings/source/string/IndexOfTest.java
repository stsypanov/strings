package tsypanov.strings.source.string;

import org.junit.jupiter.api.Test;

public class IndexOfTest {

  @Test
  void noSlash() {
    final String name = "no-slash";
    assert conventional(name) == improved(name);
  }

  @Test
  void slash() {
    final String name = "has/slash";
    assert conventional(name) != improved(name);
  }

  private int conventional(final String name) {
    return name.substring(2).indexOf('/');
  }

  private int improved(final String name) {
    return name.indexOf('/', 2);
  }

}
