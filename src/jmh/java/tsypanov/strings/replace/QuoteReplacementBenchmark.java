package tsypanov.strings.replace;

import org.openjdk.jmh.annotations.*;
import tsypanov.strings.source.utils.RandomStringGenerator;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class QuoteReplacementBenchmark {

  @Benchmark
  public String quoteReplacement(Data data) {
    var string = data.string;
    return Matcher.quoteReplacement(string);
  }

  @State(Scope.Thread)
  public static class Data {
    @Param({"8", "64", "128", "256"})
    private int length;
    @Param({"true", "false"})
    private boolean latin;
    private String string;

    @Setup
    public void setup() {
      String alphabet = latin
        ? "abcdefghijklmnopqrstuvwxyz"        // English
        : "абвгдеёжзиклмнопрстуфхцчшщьыъэюя"; // Russian

      RandomStringGenerator generator = new RandomStringGenerator();

      string = '\\' + generator.randomString(alphabet, length - 2) + '$';
    }
  }
}
