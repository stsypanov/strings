package tsypanov.strings.benchmark.string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

/**
 * On Java 8 {@link EqualsIgnoreCaseBenchmark#equalsIgnoreCase} is faster and consumes less memory
 * On Java 11 {@link EqualsIgnoreCaseBenchmark#equalsIgnoreCase} is slower and consumes less memory
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class EqualsIgnoreCaseBenchmark {

  @Benchmark
  public boolean equalsIgnoreCase(Data data) {
    return data.str1.equalsIgnoreCase(data.str2);
  }

  @Benchmark
  public boolean toLowerCaseEquals(Data data) {
    return data.str1.toLowerCase().equals(data.str2);
  }

  @State(Scope.Thread)
  public static class Data{
    private final String str1 = org.apache.commons.lang3.StringUtils.repeat("A", 10);
    private final String str2 = org.apache.commons.lang3.StringUtils.repeat("a", 10);
  }

}
