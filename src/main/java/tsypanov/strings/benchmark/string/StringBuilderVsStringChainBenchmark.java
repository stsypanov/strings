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

import java.util.concurrent.TimeUnit;

import tsypanov.strings.source.string.StringConcat;
import tsypanov.strings.source.utils.RandomStringGenerator;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(value = Mode.AverageTime)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class StringBuilderVsStringChainBenchmark {

  @Benchmark
  public String stringBuilder(Data data) {
    return StringConcat.concatWithStringBuilder(data.stringArray);
  }

  @Benchmark
  public String stringChain(Data data) {
    return StringConcat.concatWithStringChain(data.stringArray);
  }

  @Benchmark
  public String stringChainDefault(Data data) {
    return StringConcat.concatWithStringChainDefault(data.stringArray);
  }

  @State(Scope.Thread)
  public static class Data {

    @Param({"true", "false"})
    private boolean latin;

    @Param({"10", "100", "1000"})
    private int stringCount;

    @Param({"1", "10", "50", "100"})
    private int stringLength;

    private String[] stringArray;

    @Setup
    public void setup() {
      RandomStringGenerator generator = new RandomStringGenerator();

      stringArray = new String[stringCount];

      String alphabet = latin
              ? "abcdefghijklmnopqrstuvwxyz"        //English
              : "абвгдеёжзиклмнопрстуфхцчшщьыъэюя"; //Russian

      for (int i = 0; i < stringCount; i++) {
        stringArray[i] = generator.randomString(alphabet, stringLength);
      }
    }

  }
}
