module strings.main {
  requires jmh.core;
  requires jdk.unsupported;
  requires org.apache.commons.lang3;
  requires spring.core;

  //bug in IDEA https://youtrack.jetbrains.com/issue/IDEA-211122, compilation is correct
  exports tsypanov.strings.benchmark.string.generated;
  exports tsypanov.strings.benchmark.concatenation.generated;
  exports tsypanov.strings.benchmark.reflection.generated;
  exports tsypanov.strings.benchmark.p6spy.generated;
}