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
import tsypanov.strings.source.utils.RandomStringGenerator;

import java.util.concurrent.TimeUnit;

/**
 * @see <a href="https://youtrack.jetbrains.com/issue/IDEA-240288">https://youtrack.jetbrains.com/issue/IDEA-240288</a>
 * <br/>
 * @see <a href="https://github.com/spring-projects/spring-framework/pull/25024/files">https://github.com/spring-projects/spring-framework/pull/25024/files</a>
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g", "-XX:+UseParallelGC"})
public class StringBuilderSubstringBenchmark {

  @Benchmark
  public String original(Data data) {
    StringBuilder sb = data.sb;
    return sb.toString().substring(0, sb.length() - 1);
  }

  @Benchmark
  public String simplified(Data data) {
    StringBuilder sb = data.sb;
    return sb.substring(0, sb.length() - 1);
  }

  @State(Scope.Thread)
  public static class Data {

    @Param({"1", "5", "10", "100"})
    private int length;

    private StringBuilder sb;

    @Setup
    public void setup() {
      RandomStringGenerator generator = new RandomStringGenerator();

      String alphabet = "abcdefghijklmnopqrstuvwxyz";

      sb = new StringBuilder(generator.randomString(alphabet, length));
    }

  }
}
