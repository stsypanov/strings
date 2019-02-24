package tsypanov.strings.source.string;

public class CharacterReplacer {

  public static StringBuilder ineffective(Class<?> clazz, StringBuilder sb) {
    String name = clazz.getName();
    int nameLength = name.length();
    for (int i = 0; i < nameLength; ++i) {
      char car = name.charAt(i);
      sb.append(car == '.' ? '/' : car);
    }
    return sb;
  }

  public static StringBuilder effective(Class<?> clazz, StringBuilder sb) {
    return sb.append(clazz.getName().replace('.', '/'));
  }
}
