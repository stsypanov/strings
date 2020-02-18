package tsypanov.strings.string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

import tsypanov.strings.source.string.Joiner;
import tsypanov.strings.source.utils.RandomStringGenerator;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(value = Mode.AverageTime)
@Fork(jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseParallelGC"})
public class StringBuilderVsStringJoinerBenchmark {

  @Benchmark
  public String stringBuilder(Data data) {
    String[] stringArray = data.stringArray;

    return Joiner.joinWithStringBuilder(stringArray);
  }

  @Benchmark
  public String stringJoiner(Data data) {
    String[] stringArray = data.stringArray;

    return Joiner.joinWithStringJoiner(stringArray);
  }

  @State(Scope.Thread)
  public static class Data {

    @Param({"true", "false"})
    private boolean latin;

    @Param({"10", "100", "1000"})
    private int length;

    private int count = 10;

    private String[] stringArray;

    @Setup
    public void setup() {
      RandomStringGenerator generator = new RandomStringGenerator();

      stringArray = new String[count];

      String alphabet = latin
              ? "abcdefghijklmnopqrstuvwxyz"        //English
              : "абвгдеёжзиклмнопрстуфхцчшщьыъэюя"; //Russian

      for (int i = 0; i < count; i++) {
        String string = generator.randomString(alphabet, length);
        stringArray[i] = string;
      }
    }

  }
}
