package tsypanov.strings.baos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
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
public class ByteArrayOutputStreamBenchmark {

  @Benchmark
  public String toString(Data data) throws UnsupportedEncodingException {
    return data.baos.toString(data.charset.name());
  }

  @Benchmark
  public String newString(Data data) {
    return new String(data.baos.toByteArray(), data.charset);
  }

  @Benchmark
  public String toString_noCS(Data data) {
    return data.baos.toString();
  }

  @Benchmark
  public String newString_noCS(Data data) {
    return new String(data.baos.toByteArray());
  }

  @State(Scope.Thread)
  public static class Data {

    @Param({"0", "10", "100", "1000"})
    private int length;

    private final Charset charset = Charset.defaultCharset();

    private ByteArrayOutputStream baos;

    @Setup
    public void setup() throws IOException {
      byte[] bytes = StringUtils.repeat('a', length).getBytes(charset);

      baos = new ByteArrayOutputStream(length);
      baos.write(bytes);
    }
  }

}