package tsypanov.strings;

import tsypanov.strings.concatenation.BrokenConcatenationBenchmark;
import tsypanov.strings.concatenation.InvocationTraceBenchmark;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarkRunner {

  public static void main(String[] args) throws Exception {
    Options opt = new OptionsBuilder()
//            .include(ArrayInstantiationBenchmark.class.getSimpleName())
//            .include(BoxingBenchmark.class.getSimpleName())
//            .include(SaveAndFlushBenchmark.class.getSimpleName())
//            .include(FieldReflectorKeyBenchmark.class.getSimpleName())
//            .include(ChainedAppendBenchmark.class.getSimpleName())
//            .include(CharacterReplaceBenchmark.class.getSimpleName())
//            .include(StringEqualityBenchmark.class.getSimpleName())
//            .include(ArrayAllocationEliminationBenchmark.class.getSimpleName())
            .include(BrokenConcatenationBenchmark.class.getSimpleName())
//            .include(InvocationTraceBenchmark.class.getSimpleName())
//            .include(HashMapVsEnumMapInstantiationBenchmark.class.getSimpleName())
//            .include(HashMapVsEnumMapIterationBenchmark.class.getSimpleName())
//            .include(TornAppendBenchmark.class.getSimpleName())
            .warmupIterations(10)
            .warmupTime(TimeValue.seconds(1))
            .measurementIterations(10)
            .measurementTime(TimeValue.seconds(10))
            .forks(5) //0 makes debugging possible
            .shouldFailOnError(true)
//				.shouldDoGC(false)
				.jvmArgsAppend(
//						"-Xint"
//						,
//						"-XX:+UnlockDiagnosticVMOptions",
//						"-XX:TieredStopAtLevel=1"
//                ,
//						"-XX:+PrintCompilation",
//						"-XX:+PrintInlining",
//						"-XX:+LogCompilation"
				)
            .addProfiler(GCProfiler.class)// memory and GC profiler
            .build();

    new Runner(opt).run();
  }
}