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

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class InsertCharBenchmark {

  @Benchmark
  public String insertBetweenEachChar(Data data) {
    return data.string.replace("", "b");
  }

  @State(Scope.Thread)
  public static class Data {
    String string;

    @Param({"true", "false"})
    private boolean latin;

    @Param({"5", "10", "50"})
    private int length;

    @Setup
    public void setup() {
      string = latin ? "a".repeat(length) : "я".repeat(length);
    }
  }
}
