package tsypanov.strings.source.string;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringChainTest {

  private static final String EMPTY = "";
  private static final String ONE = "One";
  private static final String TWO = "Two";
  private static final String THREE = "Three";
  private static final String FOUR = "Four";
  private static final String FIVE = "Five";

  private static final int ONE_LEN = ONE.length();
  private static final int TWO_LEN = TWO.length();

  @Test
  void add1_noArgConstructor() {
    StringChain sc = new StringChain();

    sc.add(ONE);

    assertEquals(sc.toString(), ONE);
  }

  @Test
  void add1_sizePassedToConstructor() {
    StringChain sc1 = new StringChain(1);
    sc1.add(ONE);

    assertEquals(sc1.toString(), ONE);

    StringChain sc2 = new StringChain();
    sc2.add(ONE);

    assertEquals(sc1.toString(), sc2.toString());
  }

  @Test
  void add3() {
    StringChain sc = new StringChain();

    sc.add(ONE);
    sc.add(TWO);
    sc.add(THREE);

    String expected = ONE + TWO + THREE;

    assertEquals(expected, sc.toString());
  }

  @Test
  void addObject() {
    Object o = "123";
    StringChain sc = new StringChain();

    sc.add(o);
    sc.add(null);

    assertEquals("123" + null, sc.toString());
  }

  @Test
  void addAlladd() {
    StringChain sc = new StringChain(1);

    Arrays.asList(ONE, TWO).forEach(sc::add);

    sc.add(THREE);

    String expected = ONE + TWO + THREE;
    assertEquals(expected, sc.toString());
  }

  @Test
  void add3addAll() {
    StringChain sc = new StringChain();
    sc.add(ONE);
    sc.add(TWO);
    sc.add(THREE);

    ArrayList<String> nextOne = new ArrayList<>();
    nextOne.add(FOUR);
    nextOne.add(FIVE);
    nextOne.forEach(sc::add);

    String expected = ONE + TWO + THREE + FOUR + FIVE;
    assertEquals(sc.toString(), expected);
  }

  @Test
  void addCharSequence() {
    StringChain sc = new StringChain(10); //more chunks than actually needed
    CharSequence cs_one = ONE;
    CharSequence cs_two = TWO;

    sc.add(cs_one);
    sc.add(cs_two);

    assertEquals(ONE + TWO, sc.toString());

    sc = new StringChain();
    sc.add(cs_one);
    sc.add(cs_two);

    assertEquals(ONE + TWO, sc.toString());

    StringBuilder builder = new StringBuilder(ONE);
    StringBuffer buffer = new StringBuffer(THREE);

    sc = new StringChain();
    sc.add(builder).add(buffer);

    builder.append(TWO);
    buffer.append(FOUR);

    assertEquals(ONE + THREE, sc.toString(), "CharSequence is copied when add");

    sc.add(builder);

    assertEquals(ONE + THREE + ONE + TWO, sc.toString());
  }

  @Test
  void addCharSequenceWithEmptyValue() {
    StringChain sj = new StringChain();
    CharSequence cs_one = ONE;
    CharSequence cs_two = TWO;

    sj.add(cs_one);
    sj.add(cs_two);

    assertEquals(ONE + TWO, sj.toString());

    sj = new StringChain();
    sj.add(cs_one);
    sj.add(cs_two);

    assertEquals(ONE + TWO, sj.toString());

    sj = new StringChain();

    assertEquals(EMPTY, sj.toString());

    sj.add(cs_one);
    sj.add(cs_two);

    assertEquals(ONE + TWO, sj.toString());
  }

  @Test
  void addString() {
    StringChain sj = new StringChain();
    sj.add(ONE);

    assertEquals(ONE, sj.toString());

    sj.add(TWO);

    assertEquals(ONE + TWO, sj.toString());
  }

  @Test
  void lengthWithCustomEmptyValue() {
    StringChain sj = new StringChain();
    assertEquals(sj.length(), 0);

    sj.add("");
    assertEquals(sj.length(), EMPTY.length());

    sj.add("");
    assertEquals(sj.length(), EMPTY.length());

    sj.add(ONE);
    assertEquals(sj.length(), ONE_LEN);
    assertEquals(sj.toString().length(), sj.length());

    sj.add(TWO);
    assertEquals(sj.length(), ONE_LEN + TWO_LEN);
    assertEquals(sj.toString().length(), sj.length());

    sj = new StringChain();
    assertEquals(sj.length(), 0);
    assertEquals(sj.toString().length(), sj.length());

    sj.add("abcdef");
    assertEquals(sj.length(), 6);
    assertEquals(sj.toString().length(), sj.length());

    sj.add("xyz");
    assertEquals(sj.length(), 9);
    assertEquals(sj.toString().length(), sj.length());
  }

  @Test
  void noAddAndEmptyValue() {
    StringChain sj = new StringChain();
    assertEquals(sj.toString(), EMPTY);

    sj = new StringChain();
    assertEquals(sj.toString(), EMPTY);
  }

  @Test
  void stringFromtoString() {
    StringChain sj = new StringChain();
    assertEquals(sj.toString(), EMPTY);

    sj = new StringChain();
    assertEquals(sj.toString(), EMPTY);

    sj = new StringChain();
    sj.add(ONE);
    assertEquals(sj.toString(), ONE);

    sj.add(TWO);
    assertEquals(sj.toString(), ONE + TWO);

    sj = new StringChain();
    sj.add(ONE);
    sj.add(TWO);
    assertEquals(sj.toString(), ONE + TWO);
  }

  @Test
  void stringFromToStringWithEmptyValue() {
    StringChain sj = new StringChain();
    assertEquals(sj.toString(), "");

    sj = new StringChain();
    assertEquals(sj.toString(), "");

    sj = new StringChain();
    assertEquals(sj.toString(), "");

    sj = new StringChain();
    sj.add(ONE);
    assertEquals(sj.toString(), ONE);

    sj.add(TWO);
    assertEquals(sj.toString(), ONE + TWO);
  }

  @Test
  void toStringWithCustomEmptyValue() {
    StringChain sj = new StringChain();

    assertEquals(EMPTY, sj.toString());

    sj.add("");
    assertEquals("", sj.toString());

    sj.add("");
    assertEquals("", sj.toString());
  }

  @Test
  void merge() {
    StringChain sc = new StringChain();
    sc.add("Александр ");

    StringChain stringChain = new StringChain();
    stringChain.add("Пушкин");

    StringChain merged = sc.merge(stringChain);

    assertEquals("Александр Пушкин", merged.toString());
  }

  @Test
  void mergeSized() {
    StringChain sc = new StringChain(1);
    sc.add("Фёдор");

    StringChain stringChain = new StringChain(1);
    stringChain.add(" Достоевский");

    StringChain merged = sc.merge(stringChain);

    assertEquals("Фёдор Достоевский", merged.toString());
  }

  @Test
  void mergeSizedWithEmpty() {
    StringChain sc = new StringChain(1);
    sc.add("Афанасий Никитин");

    StringChain stringChain = new StringChain();

    StringChain merged = sc.merge(stringChain);

    assertEquals("Афанасий Никитин", merged.toString());
  }

  @Test
  void mergeSizedWithZeroSized() {
    StringChain sc = new StringChain(1);
    sc.add("Иван Тургенев");

    StringChain stringChain = new StringChain(0);

    StringChain merged = sc.merge(stringChain);

    assertEquals("Иван Тургенев", merged.toString());
  }

  @Test
  void mergeNonEmptyToEmpty() {
    StringChain sc = new StringChain();

    StringChain stringChain = new StringChain();
    stringChain.add("Антон Чехов");

    StringChain merged = sc.merge(stringChain);

    assertEquals("Антон Чехов", merged.toString());
  }

  @Test
  void mergeTwoEmpty() {
    StringChain sc = new StringChain();

    StringChain stringChain = new StringChain();

    StringChain merged = sc.merge(stringChain);

    assertTrue(merged.toString().isEmpty());
  }

}