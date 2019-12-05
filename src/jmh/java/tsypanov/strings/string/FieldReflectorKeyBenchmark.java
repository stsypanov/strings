package tsypanov.strings.string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class FieldReflectorKeyBenchmark {

  private ObjectStreamField[] fields;

  @Setup
  public void setup() {
    String signature = "Ljava/lang/String;";
    fields = new ObjectStreamField[3];
    fields[0] = new ObjectStreamField("groupId", signature);
    fields[1] = new ObjectStreamField("artifactId", signature);
    fields[2] = new ObjectStreamField("version", signature);
  }

  @Benchmark
  public Object original() {
    return new FieldReflectorKey(fields);
  }

  @Benchmark
  public Object patched() {
    return new FieldReflectorKeyPatched(fields);
  }

  private static class FieldReflectorKey {
    private final String sigs;
    private final int hash;

    FieldReflectorKey(ObjectStreamField[] fields) {
      StringBuilder sb = new StringBuilder();
      for (ObjectStreamField field : fields) {
        sb.append(field.getName()).append(field.getSignature());
      }
      sigs = sb.toString();
      hash = sigs.hashCode();
    }
  }

  private static class FieldReflectorKeyPatched {
    private final String[] sigs;
    private final int hash;

    FieldReflectorKeyPatched(ObjectStreamField[] fields) {
      sigs = new String[2 * fields.length];
      for (int i = 0; i < fields.length; i++) {
        ObjectStreamField f = fields[i];
        sigs[2 * i] = f.getName();
        sigs[2 * i + 1] = f.getSignature();
      }
      hash = Arrays.hashCode(sigs);
    }
  }

  private static class ObjectStreamField {
    private final String name;
    private final String signature;

    ObjectStreamField(String name, String signature) {
      this.name = name;
      this.signature = signature;
    }

    String getName() {
      return name;
    }

    String getSignature() {
      return signature;
    }
  }
}
