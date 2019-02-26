package tsypanov.strings.benchmark.string;

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


@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(value = Mode.AverageTime)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class CharacterReplaceBenchmark {

  @Benchmark
  public StringBuilder manualReplace(Data data) {
    return ineffective(data.klass, new StringBuilder());
  }

  @Benchmark
  public StringBuilder stringReplace(Data data) {
    return effective(data.klass, new StringBuilder());
  }

  @State(Scope.Thread)
  public static class Data {
    private final Class<?> klass = String.class;
//    private final Class<?> klass = CharacterReplaceBenchmark.class; //try this to see the difference
//    private final Class<?> klass = org.springframework.objenesis.instantiator.perc.PercSerializationInstantiator.class; //try this to see the difference
  }
}
