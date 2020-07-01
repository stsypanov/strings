package tsypanov.strings.string;


import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

/**
 * @see https://github.com/spring-projects/spring-framework/pull/24870
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g", "-XX:+UseParallelGC"})
public class DeleteAnyBenchmark {

  private final String input = "key1=value1 ";
  private final String replacement = "\"";

  @Benchmark
  public String original() {
    return deleteAny(input, replacement);
  }

  @Benchmark
  public String patched() {
    return deleteAnyPatched(input, replacement);
  }

  private static String deleteAny(String inString, String charsToDelete) {
    StringBuilder sb = new StringBuilder(inString.length());
    for (int i = 0; i < inString.length(); i++) {
      char c = inString.charAt(i);
      if (charsToDelete.indexOf(c) == -1) {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  private static String deleteAnyPatched(String inString, String charsToDelete) {
    int lastCharIndex = 0;
    char[] result = new char[inString.length()];
    for (int i = 0; i < inString.length(); i++) {
      char c = inString.charAt(i);
      if (charsToDelete.indexOf(c) == -1) {
        result[lastCharIndex++] = c;
      }
    }
    return new String(result, 0, lastCharIndex);
  }

}