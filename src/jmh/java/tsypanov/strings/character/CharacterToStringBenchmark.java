package tsypanov.strings.character;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g", "-XX:+UseParallelGC"})
public class CharacterToStringBenchmark {

  private static final char latinChar = 'a';
  private static final char utf8Char = 'я';

  private static final Character latin = latinChar;
  private static final Character utf8 = utf8Char;

  @Benchmark
  public String toString_latin() {
    return latin.toString();
  }

  @Benchmark
  public String valueOf_latin() {
    return String.valueOf(latinChar);
  }

  @Benchmark
  public String toString_utf8() {
    return utf8.toString();
  }

  @Benchmark
  public String valueOf_utf8() {
    return String.valueOf(utf8Char);
  }


}
