# Список бенчмарков с краткими пояснениями | Benchmarks list with brief comments

Scroll down to read English version (not implemented yet)


##ArrayInstantiationBenchmark

Показывает разницу между созданием массива с помощью конструктора и вызовом платформенно-зависимого метода `Array::newInstance`:

```java
@Benchmark
public Object newInstance() {
  return Array.newInstance(Object.class, length);
}

@Benchmark
public Object constructor() {
  return new Object[length];
}
```

В коде, скомпилированном С2 между ними нет особой разницы, однако в режиме интерпретатора и С1 конструктор отрабатывает значительно быстрее.

Интересен больша разница во времени при создании небольшого массива и режиме С1:
```
             (length)  Mode  Cnt  Score  Error  Units
constructor        10  avgt   30    6,5 ±  0,1  ns/op
newInstance        10  avgt   30   68,3 ±  4,4  ns/op

constructor       100  avgt   30   34,8 ±  0,8  ns/op
newInstance       100  avgt   30   84,9 ±  4,1  ns/op
```

Платформенно-зависимый метод работает почти в 10 раз медленнее, чем конструктор, при создании массива из 10 объектов.
При создании же массива из 100 объектов разница между двумя способами составляет всего 2,4 раза.

Это поведение наблюдается только для С1.

См. итоги измерений в файле [./results/ArrayInstantiationBenchmark.txt](./results/ArrayInstantiationBenchmark.txt)

##ArithmeticSimplificationBenchmark

Показывает разницу между выражением с избыточным приведением и без него:

```java
@Benchmark
public int slow(Data data) {
  int size = data.size;
  int initialCapacity = data.initialCapacity;
  return (int) ((initialCapacity + size - 1L) / size);
}

@Benchmark
public int fast(Data data) {
  int size = data.size;
  int initialCapacity = data.initialCapacity;
  return (initialCapacity + size - 1) / size;
}
```

ВАЖНО! Способ, описанный в методе `fast` не всегда работает правильно, 
См. [ArithmeticTest](./benchmark-source/src/test/java/com/luxoft/logeek/ArithmeticTest.java)

Поэтому замена `1L` на `1` предпочтительна только для положительных чисел.

См. итоги измерений в файле [./results/ArithmeticSimplificationBenchmark.txt](./results/ArithmeticSimplificationBenchmark.txt)

##SubListToArrayBenchmark

Показывает проседание производительности при вызове `java.util.ArrayList$SubList::toArray`:

```java
@Benchmark
public Integer[] list(Data holder) {
  return holder.list.toArray(new Integer[0]);
}

@Benchmark
public Integer[] subList(Data holder) {
  return holder.list.subList(0, holder.size).toArray(new Integer[0]);
}
```

Несмотря на одинаковый объём передаваемых данных, производителньость второго метода значительно хуже первого.

См. обсуждение http://mail.openjdk.java.net/pipermail/core-libs-dev/2018-January/051102.html

Задача: https://bugs.openjdk.java.net/browse/JDK-8196207

Исправлено в JDK 11.

См. итоги измерений в файле [./results/SubListToArrayBenchmark.txt](./results/SubListToArrayBenchmark.txt)

##ZeroingEliminationBenchmark

Несколько странный пример, показывающий ненужность ручного обнуления содрежимого массива,
т.к. согласно спецификации его отдельные ячейки и так заполнены значениями по умолчанию.
Т.е. для целых чисел это 0.

См. итоги измерений в файле [./results/ZeroingEliminationBenchmark.txt](./results/ZeroingEliminationBenchmark.txt)

##BoxingBenchmark

Пример, показывающий значительное проседание производительности при использовании обёртки (`Long`) для
сложения чисел.

В боевом коде проблема была обнаружена в `org.springframework.data.jpa.repository.support.SimpleJpaRepository`.

См. https://github.com/spring-projects/spring-data-jpa/pull/269

Начиная с издания 2018.2 "Идея" умеет обнаруживать подобный код (см. https://youtrack.jetbrains.com/issue/IDEA-189336).

См. итоги измерений в файле [./results/BoxingBenchmark.txt](./results/BoxingBenchmark.txt)

##CollectionsAddAllVsAddAllBenchmark

Пример, показывающий неправдивость документации к методу `java.util.Collections::addAll` для коллекций, основанных на массиве
или предназначенных для использования в многопоточной среде.

Рассмотрим пример:
```java
@Benchmark
public boolean collectionsAddAll(Data data) {
  Collection<Integer> collection = data.freshCollection();
  Integer[] array = data.array;
  return Collections.addAll(collection, array);
}

@Benchmark
public boolean addAll(Data data) {
  Collection<Integer> collection = data.freshCollection();
  List<Integer> arrayAsList = Arrays.asList(data.array);
  return collection.addAll(arrayAsList);
}
```

В современных изданиях JDK метод `collectionsAddAll` работает быстрее только для `java.util.HashSet`, при это разница очень незначительна.

Для прочих коллекций `collectionsAddAll` работает значительно медленнее, хотя документация утверждает обратное.

См. задачу https://bugs.openjdk.java.net/browse/JDK-8193031

Предлагаемое изменение: http://cr.openjdk.java.net/~martin/webrevs/jdk/Collections-addAll/

Обсуждение: http://mail.openjdk.java.net/pipermail/core-libs-dev/2018-January/051197.html

См. итоги измерений в файлах для соответсвующих типов данных:
 - [./results/CollectionsAddAllVsAddAllBenchmark_HashSet.txt](./results/CollectionsAddAllVsAddAllBenchmark_HashSet.txt)
 - [./results/CollectionsAddAllVsAddAllBenchmark_COWList.txt](./results/CollectionsAddAllVsAddAllBenchmark_COWList.txt)
 - [./results/CollectionsAddAllVsAddAllBenchmark_ArrayList.txt](./results/CollectionsAddAllVsAddAllBenchmark_ArrayList.txt)

##ConcurrentHashMapBenchmark

Показывает падение производительности в JDK 8 из-за ошибки в реализации метода `ConcurrentHashMap::computeIfAbsent`.

Рассмотрим код:
```java
private ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

@Benchmark
@Threads(2)
public Integer computeIfAbsent() {
  return map.computeIfAbsent(key, function);
}

@Benchmark
@Threads(2)
public Integer getAndPut() {
  Integer key = this.key;
  Integer value = map.get(key);
  if (value == null) {
    value = function.apply(key);
    map.put(key, value);
  }
  return value;
}
```

Несмотря на то, что оба примера делают одно и тоже, использование `ConcurrentHashMap::computeIfAbsent`
сильно замедляет исполнение, т.к. блокировка применяется ко всему методу, а не только к той части, которая
кладёт значение при отсутствии ключа.

Описание: https://bugs.openjdk.java.net/browse/JDK-8161372

Исправление: http://hg.openjdk.java.net/jdk9/dev/jdk/rev/fe0d3813e6c3

Проблема, вызванная данной: https://jira.spring.io/browse/DATACMNS-1396

См. итоги измерений в файле [./results/ConcurrentHashMapBenchmark.txt](./results/ConcurrentHashMapBenchmark.txt)

##StringBuilderAppendBenchmark

Показывает разницу между двумя способами добавления подстроки к `StringBuilder`-у:
```java
//1 способ

String append(String str, int from, int to) {
  return new StringBuilder().append('.').append(str, from, to).append('.').toString();
}

//2 способ

String append(String str, int from, int to) {
  String subStr = str.substring(from, to);
  return new StringBuilder().append('.').append(str).append('.').toString();
}
```
Второй способ можно привести к ещё более простому виду:
```java
String append(String str, int from, int to) {
  return '.' + str.substring(from, to) + '.';
}
```

Опыт показывает, что несмотря на схожесть обоих подходов, второй показывает лучшую производительность,
см. итоги измерений в файле [./results/StringBuilderAppendBenchmark.txt](./results/StringBuilderAppendBenchmark.txt)

Причина в том, что метод `StringBuilder.append(String)` интринзифицирован, а `StringBuilder.append(String, int, int)`
делегирует вызов методу `AbstractStringBuilder.append(String, int, int)`, внутри которого строка добавляется познаково
с мопощью цикла в методе `AbstractStringBuilder.appendChars(CharSequence, int, int)`.

##CharacterReplaceBenchmark
Показывает разницу между двумя способами замены всех вхождений знака в строке на другой знак.
Первый способ:
```java
public StringBuilder manualReplace(Class<String> klass) {
  String name = klass.getName();
  int nameLength = name.length();
  StringBuilder stringBuilder = new StringBuilder(nameLength);
  for (int i = 0; i < nameLength; ++i) {
    char car = name.charAt(i);
    stringBuilder.append(car == '.' ? '/' : car);
  }
  return stringBuilder;
}
``` 
Второй способ:
```java
public String stringReplace(Class<String> klass) {
  return klass.getName().replace('.', '/');
}
```
Пример из жизни: https://github.com/spring-projects/spring-framework/pull/22299/files

Второй способ проще и даёт лучшую производительность. Вот итог замера для класс `java.lang.String`:
```
CharacterReplaceBenchmark.manualReplace                        avgt   25    58,474 ±   4,184   ns/op
CharacterReplaceBenchmark.manualReplace:·gc.alloc.rate.norm    avgt   25   112,000 ±   0,001    B/op

CharacterReplaceBenchmark.stringReplace                        avgt   25    15,185 ±   0,067   ns/op
CharacterReplaceBenchmark.stringReplace:·gc.alloc.rate.norm    avgt   25    56,000 ±   0,001    B/op
```