package tsypanov.strings.source.string;

import java.util.StringJoiner;

public class Joiner {

  public static String joinWithStringBuilder(String[] stringArray) {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < stringArray.length; i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(stringArray[i]);
    }
    sb.append("]");
    return sb.toString();
  }

  public static String joinWithStringJoiner(String[] stringArray) {
    StringJoiner joiner = new StringJoiner(",", "[", "]");
    for (String str : stringArray) {
      joiner.add(str);
    }
    return joiner.toString();
  }

}
