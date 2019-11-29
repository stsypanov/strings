package tsypanov.strings.benchmark.concatenation;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class BrokenConcatenationBenchmark {

  @Benchmark
  public String slow(Data data) {
    return "class " + data.clazz.getName();
  }

  @Benchmark
  public String fast(Data data) {
    final String clazzName = data.clazz.getName();
    return "class " + clazzName;
  }

  @State(Scope.Thread)
  public static class Data {
    final Class<? extends Data> clazz = getClass();

    @Setup
    public void setup() {
      //explicitly load name via native method Class.getName0()
      clazz.getName();
    }
  }
}
