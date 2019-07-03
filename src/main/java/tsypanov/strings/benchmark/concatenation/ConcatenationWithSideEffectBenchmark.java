package tsypanov.strings.benchmark.concatenation;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class ConcatenationWithSideEffectBenchmark {

  @Benchmark
  public String measureWithSideEffect(Data data) {
    return " " + data.i++ + " ";
  }

  @Benchmark
  public String measureWithoutSideEffect(Data data) {
    int num = data.i++;
    return " " + num + " ";
  }

  @State(Scope.Thread)
  public static class Data {
    int i = 1000; //use long number to have String of constant length
  }
}
