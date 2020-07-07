package tsypanov.strings.character;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import tsypanov.strings.source.utils.RandomStringGenerator;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g", "-XX:+UseParallelGC"})
public class CharByCharIterationBenchmark {

  @Benchmark
  public void toCharArray(Data data, Blackhole b) {
    char[] chars = data.string.toCharArray();
    for (char ch : chars) {
      b.consume(ch);
    }
  }

  @Benchmark
  public void charAt(Data data, Blackhole b) {
    String string = data.string;
    int length = string.length();
    for (int i = 0; i < length; i++) {
      b.consume(string.charAt(i));
    }
  }

  @State(Scope.Thread)
  public static class Data {
    String string;

    @Param({"true", "false"})
    private boolean latin;

    @Param({"5", "10", "50", "100"})
    private int length;

    @Setup
    public void setup() {
      String alphabet = latin
        ? "abcdefghijklmnopqrstuvwxyz"        // English
        : "абвгдеёжзиклмнопрстуфхцчшщьыъэюя"; // Russian

      RandomStringGenerator generator = new RandomStringGenerator();

      string = generator.randomString(alphabet, length);
    }
  }
}
