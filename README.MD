# Список бенчмарков с краткими пояснениями | Benchmarks list with brief comments

Scroll down to read English version (not written yet)

## HashMapVsEnumMapInstantiationBenchmark

Показывает разницу между созданием `HashMap<String, Integer>` и `EnumMap<ConstantsEnum, Integer>`.

## HashMapVsEnumMapIterationBenchmark

Показывает разницу между перебором `HashMap<String, Integer>` и `EnumMap<ConstantsEnum, Integer>`.

## MethodToStringBenchmark

Позволяет измерить стоимость вызова `Method.toString()`.

## StringCompositeKeyBenchmark

Показывает разницу между склеиванием строки для создания составного ключа и использованием отдельного класса
измерить стоимость вызова `Method.toString()`.

Случаи из жизни:
- https://github.com/spring-projects/spring-framework/pull/22451
- http://cr.openjdk.java.net/~igerasim/6996807/00/webrev/

## StringBuilderAppendBenchmark

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
с помощью цикла в методе `AbstractStringBuilder.appendChars(CharSequence, int, int)`.

Пример из жизни: https://gitlab.ow2.org/asm/asm/-/merge_requests/236/diffs

## CharacterReplaceBenchmark
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

Второй способ проще и даёт лучшую производительность. Вот итог замера для класса `java.lang.String`:
```
CharacterReplaceBenchmark.manualReplace                        avgt   25    58,474 ±   4,184   ns/op
CharacterReplaceBenchmark.manualReplace:·gc.alloc.rate.norm    avgt   25   112,000 ±   0,001    B/op

CharacterReplaceBenchmark.stringReplace                        avgt   25    15,185 ±   0,067   ns/op
CharacterReplaceBenchmark.stringReplace:·gc.alloc.rate.norm    avgt   25    56,000 ±   0,001    B/op
```
Если брать класс с более длинным именем, то разница в потреблении памяти будет сглаживаться в пользу
`String.replace(char, char)`

