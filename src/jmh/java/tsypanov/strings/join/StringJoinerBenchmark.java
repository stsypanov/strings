package tsypanov.strings.join;

import org.openjdk.jmh.annotations.*;
import tsypanov.strings.source.string.Joiner;
import tsypanov.strings.source.utils.RandomStringGenerator;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StringJoinerBenchmark {

  @Benchmark
  public String stringJoiner(Data data) {
    return Joiner.joinWithStringJoiner(data.stringArray);
  }

  @State(Scope.Thread)
  public static class Data {

    @Param({"true", "false"})
    private boolean latin;

    @Param({"1", "5", "10", "100"})
    private int length;

    @Param({"1", "5", "10", "100"})
    private int count;

    private String[] stringArray;

    @Setup
    public void setup() {
      RandomStringGenerator generator = new RandomStringGenerator();

      stringArray = new String[count];

      String alphabet = latin
              ? "abcdefghijklmnopqrstuvwxyz"        // English
              : "абвгдеёжзиклмнопрстуфхцчшщьыъэюя"; // Russian

      for (int i = 0; i < count; i++) {
        stringArray[i] = generator.randomString(alphabet, length);
      }
    }

  }
}
