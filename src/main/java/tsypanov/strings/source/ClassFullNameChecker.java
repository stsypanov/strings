package tsypanov.strings.source;

public class ClassFullNameChecker {
  public static boolean effectiveStartsWith(Class<?> klass, String prefix) {
    return klass.getName().startsWith(prefix);
  }

  public static boolean ineffectiveStartsWith(Class<?> klass, String prefix) {
    return klass.getPackage().getName().startsWith(prefix);
  }
}
