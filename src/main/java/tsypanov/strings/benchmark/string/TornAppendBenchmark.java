package tsypanov.strings.benchmark.string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.util.DigestUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class TornAppendBenchmark {

  @Benchmark
  public String ineffective(Data data) {
    StringBuilder builder = new StringBuilder("\"0");
    DigestUtils.appendMd5DigestAsHex(data.content, builder);
    builder.append('"');
    return builder.toString();
  }

  @Benchmark
  public String effective(Data data) {
    return "\"0" + DigestUtils.md5DigestAsHex(data.content) + '"';
  }

  @State(Scope.Thread)
  public static class Data {
    private byte[] content;

    @Param({"10", "100", "1000"})
    private int length;

    @Setup
    public void setup() {
      content = new byte[length];
      ThreadLocalRandom.current().nextBytes(content);
    }
  }

  }
