package tsypanov.strings.string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

import static tsypanov.strings.source.string.CharacterReplacer.effective;
import static tsypanov.strings.source.string.CharacterReplacer.ineffective;


@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(value = Mode.AverageTime)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class CharacterReplaceBenchmark {
  private final Class<?> klass = String.class;
  //try these to see the difference
//    private final Class<?> klass = CharacterReplaceBenchmark.class;
//    private final Class<?> klass = org.springframework.objenesis.instantiator.perc.PercSerializationInstantiator.class;

  @Benchmark
  public StringBuilder manualReplace() {
    return ineffective(klass, new StringBuilder());
  }

  @Benchmark
  public StringBuilder stringReplace() {
    return effective(klass, new StringBuilder());
  }
}
