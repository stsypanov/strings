package tsypanov.strings.benchmark;

import tsypanov.strings.benchmark.string.CharacterReplaceBenchmark;
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
            .include(CharacterReplaceBenchmark.class.getSimpleName())
//            .include(HashMapVsEnumMapInstantiationBenchmark.class.getSimpleName())
//            .include(HashMapVsEnumMapIterationBenchmark.class.getSimpleName())
//            .include(TornAppendBenchmark.class.getSimpleName())
//            .include(ZeroingEliminationBenchmark.class.getSimpleName())
//            .include(CollectionsAddAllVsAddAllBenchmark.class.getSimpleName())
//            .include(CollectionsAddAllVsAddAllBenchmark.class.getSimpleName())
//            .include(IsObjectMethodBenchmark.class.getSimpleName())
//            .include(ZeroingEliminationBenchmark.class.getSimpleName())
//            .include(SubListToArrayBenchmark.class.getSimpleName())
            .warmupIterations(5)
            .warmupTime(TimeValue.seconds(1))
            .measurementIterations(5)
            .measurementTime(TimeValue.seconds(2))
            .forks(10) //0 makes debugging possible
            .shouldFailOnError(true)
//				.shouldDoGC(false)
//				.jvmArgsAppend(
//						"-Xint"
//						,
//						"-XX:+UnlockDiagnosticVMOptions",
//						"-XX:+PrintCompilation",
//						"-XX:+PrintInlining",
//						"-XX:+LogCompilation"
//				)
            .addProfiler(GCProfiler.class)// memory and GC profiler
            .build();

    new Runner(opt).run();
  }
}