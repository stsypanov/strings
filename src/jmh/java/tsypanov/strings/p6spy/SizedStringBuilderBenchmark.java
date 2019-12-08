package tsypanov.strings.p6spy;

import tsypanov.strings.source.p6spy.ToHexStringConverter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseParallelGC"})
public class SizedStringBuilderBenchmark {

  private byte[] bytes;
  private ToHexStringConverter converter;

  @Setup
  public void init() {
    bytes = new byte[1024 * 1024];
    converter = new ToHexStringConverter();
    ThreadLocalRandom.current().nextBytes(bytes);
  }

  @Benchmark
  public String original() {
    return converter.toHexString(bytes);
  }

  @Benchmark
  public String patched() {
    return converter.patched_toHexString(bytes);
  }

  @Benchmark
  public String chars() {
    return converter.chars_toHexString(bytes);
  }

  @Benchmark
  public String bytes() {
    return converter.bytes_toHexString(bytes);
  }

}
