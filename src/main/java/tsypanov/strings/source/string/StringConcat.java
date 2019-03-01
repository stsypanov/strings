package tsypanov.strings.source.string;

public class StringConcat {

  public static String concatWithStringBuilder(String... stringArray) {
    StringBuilder sb = new StringBuilder();
    for (String str : stringArray) {
      sb.append(str);
    }
    return sb.toString();
  }

  public static String concatWithStringChain(String... stringArray) {
    StringChain sc = new StringChain(stringArray.length);
    for (String str : stringArray) {
      sc.add(str);
    }
    return sc.toString();
  }
}
