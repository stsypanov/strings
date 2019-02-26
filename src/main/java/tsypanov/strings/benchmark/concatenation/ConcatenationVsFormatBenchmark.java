package tsypanov.strings.benchmark.concatenation;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import tsypanov.strings.source.utils.RandomStringGenerator;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-XX:+UseParallelGC", "-Xms2g", "-Xmx2g"})
public class ConcatenationVsFormatBenchmark {

  @Benchmark
  public String concatenation(Data data) {
    return data.str1 + ' ' + data.str2;
  }

  @Benchmark
  public String stringFormat(Data data) {
    return String.format("%s %s", data.str1, data.str2);
  }

  @State(Scope.Thread)
  public static class Data {
    String str1;
    String str2;

    @Param({"10", "100", "1000"})
    private int length;

    @Setup
    public void setup() {
      String alphabet = "abcdefghijklmnopqrstuvwxyz";
      RandomStringGenerator generator = new RandomStringGenerator();

      str1 = generator.randomString(alphabet, length);
      str2 = generator.randomString(alphabet, length);
    }

  }
}
