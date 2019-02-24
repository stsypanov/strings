package tsypanov.strings.source.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterReplacerTest {

  @Test
  void test() {
    Class<String> stringClass = String.class;

    StringBuilder foo = CharacterReplacer.ineffective(stringClass, new StringBuilder());
    StringBuilder bar = CharacterReplacer.effective(stringClass, new StringBuilder());

    assertEquals(foo.toString(), bar.toString());
  }
}