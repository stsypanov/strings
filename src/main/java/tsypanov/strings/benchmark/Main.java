package tsypanov.strings.benchmark;

import java.lang.reflect.Method;

/**
 * Пример с рефлексией: на "восьмёрке" все элементы в массиве разные
 */
public class Main {

  public static void main(String[] args) throws Exception {
    Object[] booleans = new Object[1000];
    Method method = Main.class.getMethod("readOnly");

    for (int i = 0; i < 1000; i++) {
      booleans[i] = method.invoke(null);
    }
  }

  public static boolean readOnly() {
    return true;
  }

}