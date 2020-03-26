package tsypanov.strings.string;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g", "-XX:+UseParallelGC"})
public class StringBuilderCovariantAppendBenchmark {

  @Benchmark
  public String appendCovariant(Data data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.filename.length(); i++) {
      char c = data.filename.charAt(i);
      sb.append(c == '"' ? "\\\"" : c);
    }
    return sb.toString();
  }

  @Benchmark
  public String appendExact(Data data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.filename.length(); i++) {
      char c = data.filename.charAt(i);
      if (c == '"') {
        sb.append("\\\"");
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  @State(Scope.Thread)
  public static class Data {
    private String filename;

    @Param({"10", "50"})
    private int length;

    @Param({"true", "false"})
    private boolean latin;

    @Setup
    public void setup() {
      char[] chars = new char[length - 2];
      for (int i = 0; i < length - 2; i++) {
        chars[i] = latin ? 'a' : 'я';
      }
      filename = '"' + new String(chars) + '"';
    }
  }

}
