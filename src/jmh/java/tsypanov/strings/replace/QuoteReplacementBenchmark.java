package tsypanov.strings.replace;

import org.openjdk.jmh.annotations.*;
import tsypanov.strings.source.utils.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class QuoteReplacementBenchmark {

  @Benchmark
  public String quoteReplacement(Data data) {
    return Matcher.quoteReplacement(data.string);
  }

  @State(Scope.Thread)
  public static class Data {
    @Param({"8", "64", "128", "256"})
    private int length;
    private String string;

    @Setup
    public void setup() {
      string = "$" + StringUtils.repeat('z', length - 2) + "$";
    }
  }
}
