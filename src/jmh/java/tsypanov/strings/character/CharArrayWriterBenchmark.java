package tsypanov.strings.character;

import org.openjdk.jmh.annotations.*;
import tsypanov.strings.source.utils.RandomStringGenerator;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class CharArrayWriterBenchmark {

  @Benchmark
  public String toString(Data data ) {
    return data.writer.toString();
  }

  @Benchmark
  public String toCharArray(Data data ) {
    return new String(data.writer.toCharArray());
  }

  @State(Scope.Thread)
  public static class Data {
    private String string;
    private CharArrayWriter writer;

    @Param({"5", "10", "50", "100", "1000"})
    private int length;

    @Setup
    public void setup() throws IOException {
      String alphabet = "abcdefghijklmnopqrstuvwxyz";

      RandomStringGenerator generator = new RandomStringGenerator();

      string = generator.randomString(alphabet, length);

      writer = new CharArrayWriter(length);
      writer.write(string);
    }
  }
}
