package tsypanov.strings.source.utils;

import java.util.Arrays;

public class StringUtils {
  public static String repeat(char c, int times) {
    char[] chars = new char[times];
    Arrays.fill(chars, c);
    return new String(chars);
  }
}
