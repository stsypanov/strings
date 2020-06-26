package tsypanov.strings.concatenation;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class StringCompositeKeyBenchmark {

  @Benchmark
  public Object concatenation(Data data) {
    return data.stringObjectMap.get(data.code + '_' + data.locale);
  }

  @Benchmark
  public Object compositeKey(Data data) {
    return data.keyObjectMap.get(new Key(data.code, data.locale));
  }

  @Benchmark
  public Object list(Data data) {
    return data.listObjectMap.get(Arrays.asList(data.code, data.locale));
  }

//  @Benchmark
//  public Object record(Data data) {
//    return data.recordObjectMap.get(new KeyRec(data.code, data.locale));
//  }

  @Benchmark
  public Object mapInMap(Data data) {
    return data.mapInMap.get(data.code).get(data.locale);
  }

  @State(Scope.Thread)
  public static class Data {
    private final String code = "code1";
    private final Locale locale = Locale.getDefault();

    private final HashMap<String, Object> stringObjectMap = new HashMap<>();
    private final HashMap<Key, Object> keyObjectMap = new HashMap<>();
    private final HashMap<List<?>, Object> listObjectMap = new HashMap<>();
//    private final HashMap<KeyRec, Object> recordObjectMap = new HashMap<>();
    private final HashMap<String, Map<Locale, Object>> mapInMap = new HashMap<>();

    @Setup
    public void setUp() {
      stringObjectMap.put(code + '_' + locale, new Object());

      keyObjectMap.put(new Key(code, locale), new Object());

      listObjectMap.put(Arrays.asList(code, locale), new Object());

//      recordObjectMap.put(new KeyRec(code, locale), new Object());

      HashMap<Locale, Object> localeObjectMap = new HashMap<>();
      localeObjectMap.put(locale, new Object());
      mapInMap.put(code, localeObjectMap);
    }
  }

//  private static record KeyRec(String code, Locale locale) {
//  }

  private static final class Key {
    private final String code;
    private final Locale locale;

    private Key(String code, Locale locale) {
      this.code = code;
      this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Key key = (Key) o;

      if (!code.equals(key.code)) return false;
      return locale.equals(key.locale);
    }

    @Override
    public int hashCode() {
      return 31 * code.hashCode() + locale.hashCode();
    }
  }
}
