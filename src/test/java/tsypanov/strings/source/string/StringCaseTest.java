package tsypanov.strings.source.string;

import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * Изменение регистра для некоторых локализаций приводит к изменению длины строки
 */
class StringCaseTest {

  @Test
  void toLowerCase() {
    String str = "\u00cc"; // Ì

    assert str.length() == 1;

    String strLowerCase = str.toLowerCase(new Locale("lt"));

    assert strLowerCase.length() == 3; // i̇̀
  }

}
