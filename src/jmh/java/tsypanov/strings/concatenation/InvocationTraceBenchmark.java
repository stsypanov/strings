package tsypanov.strings.concatenation;

import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms4g", "-Xmx4g", "-XX:+UseParallelGC"})
public class InvocationTraceBenchmark {

  @Benchmark
  public String createInvocationTraceName(Data data) {
    MethodInvocation invocation = data.invocation;
    StringBuilder sb = new StringBuilder(invocation.getPrefix());
    Method method = invocation.getMethod();
    Class<?> clazz = method.getDeclaringClass();
    if (clazz.isInstance(invocation.getThis())) {
      clazz = invocation.getThis().getClass();
    }
    sb.append(clazz.getName());
    sb.append('.');
    sb.append(method.getName());
    sb.append(invocation.getSuffix());
    return sb.toString();
  }

  @Benchmark
  public String patchedCreateInvocationTraceName(Data data) {
    MethodInvocation invocation = data.invocation;
    Method method = invocation.getMethod();
    Class<?> clazz = method.getDeclaringClass();
    if (clazz.isInstance(invocation.getThis())) {
      clazz = invocation.getThis().getClass();
    }
    // вынос clazz.getName() в отдельную переменную решает проблему, описанную здесь
    // https://stackoverflow.com/questions/59157085/java-8-class-getname-slows-down-string-concatenation-chain
    // http://mail.openjdk.java.net/pipermail/core-libs-dev/2019-December/063763.html
    return invocation.getPrefix() + clazz.getName() + '.' + method.getName() + invocation.getSuffix();
  }

  @State(Scope.Thread)
  public static class Data {
    private final MethodInvocation invocation = new MethodInvocation();
  }

  private static class MethodInvocation {
    private final Method method;

    private MethodInvocation() {
      try {
        method = this.getClass().getMethod("toString");
      } catch (Exception e) {
        throw new Error(e);
      }
    }

    public Method getMethod() {
      return method;
    }

    Object getThis() {
      return this;
    }

    private String getSuffix() {
      return "";
    }

    private String getPrefix() {
      return "";
    }

  }
}
