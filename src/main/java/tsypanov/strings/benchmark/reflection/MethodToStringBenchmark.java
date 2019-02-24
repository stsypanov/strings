package tsypanov.strings.benchmark.reflection;

import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MethodToStringBenchmark {

  private Method method;

  @Setup
  public void setup() throws Exception {
    method = getClass().getMethod("toString");
  }

  @Benchmark
  public Object methodToString() {
    return method.toString();
  }

}
