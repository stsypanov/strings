module strings.main {
  requires jmh.core;
  requires jdk.unsupported;

  exports tsypanov.strings.benchmark.string.generated;
}