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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class StringBuilderAppendBenchmark {

  @Benchmark
  @SuppressWarnings("StringBufferReplaceableByString")
  public String appendSubString(Data data) {
    int beginIndex = data.beginIndex;
    int endIndex = data.endIndex;

    String substring = data.string.substring(beginIndex, endIndex);

    return new StringBuilder()
            .append('L')
            .append(substring)
            .append(';')
            .toString();
  }

  @Benchmark
  public String appendBounds(Data data) {
    int beginIndex = data.beginIndex;
    int endIndex = data.endIndex;

    String appended = data.string;

    return new StringBuilder()
            .append('L')
            .append(appended, beginIndex, endIndex)
            .append(';')
            .toString();
  }

  @State(Scope.Thread)
  public static class Data {
    private String string;

    @Param({"true", "false"})
    private boolean latin;

    @Param({"10", "100", "1000"})
    private int length;

    private int beginIndex;
    private int endIndex;

    @Setup
    public void setup() {
      int length = this.length + 2;
      RandomStringGenerator generator = new RandomStringGenerator();

      string = latin ? generator.randomString("abcdefghijklmnopqrstuvwxyz", length)
                     : generator.randomString("абвгдеёжзиклмнопрстуфхцчшщьыъэюя", length);
      beginIndex = 1;
      endIndex = this.length + 1;
    }
  }

}
