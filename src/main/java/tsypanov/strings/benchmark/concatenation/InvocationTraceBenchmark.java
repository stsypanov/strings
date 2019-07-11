package tsypanov.strings.benchmark.concatenation;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class InvocationTraceBenchmark {
  private final MethodInvocation invocation = new MethodInvocation();

  @Benchmark
  public String createInvocationTraceName() throws Exception {
    MethodInvocation invocation = this.invocation;
    StringBuilder sb = new StringBuilder(getPrefix());
    Method method = invocation.getMethod();
    Class<?> clazz = method.getDeclaringClass();
    if (clazz.isInstance(invocation.getThis())) {
      clazz = invocation.getThis().getClass();
    }
    sb.append(clazz.getName());
    sb.append('.').append(method.getName());
    sb.append(getSuffix());
    return sb.toString();
  }

  @Benchmark
  public String patchedCreateInvocationTraceName() throws Exception {
    MethodInvocation invocation = this.invocation;
    Method method = invocation.getMethod();
    Class<?> clazz = method.getDeclaringClass();
    if (clazz.isInstance(invocation.getThis())) {
      clazz = invocation.getThis().getClass();
    }
    return getPrefix() + clazz.getName() + '.' + method.getName() + getSuffix();
  }

  private String getSuffix() {
    return "";
  }

  private String getPrefix() {
    return "";
  }

  private static class MethodInvocation {
    Method getMethod() throws Exception {
      return this.getClass().getMethod("toString");
    }

    Object getThis() {
      return new Object();
    }
  }
}
