package tsypanov.strings.concatenation;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LocaleToStringBenchmark {

  @Benchmark
  public Object concatenation(Data data) {
    return data.code + '_' + data.locale;
  }

  @Benchmark
  public Object compositeKey(Data data) {
    return new Key(data.code, data.locale);
  }

  @Benchmark
  public int hashCodeCompositeString(Data data) {
    return data.compositeString.hashCode();
  }

  @Benchmark
  public int hashCodeCompositeKey(Data data) {
    return data.compositeKey.hashCode();
  }

  @Benchmark
  public boolean equalsCompositeString(Data data) {
    return data.compositeString.equals(data.anotherCompositeString);
  }

  @Benchmark
  public boolean equalsCompositeKey(Data data) {
    return data.compositeKey.equals(data.anotherCompositeKey);
  }

  @State(Scope.Thread)
  public static class Data {
    private final String code = "code1";
    private final Locale locale = Locale.getDefault();

    private final Key compositeKey = new Key(code, locale);
    private final String compositeString = code + '_' + locale;

    private final Key anotherCompositeKey = new Key(code, locale);
    private final String anotherCompositeString = code + '_' + locale;
  }

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