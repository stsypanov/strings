package tsypanov.strings.source.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomStringGenerator {

  public String randomString(String alphabet, int length) {
    char[] chars = alphabet.toCharArray();

    ThreadLocalRandom random = ThreadLocalRandom.current();

    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      char c = chars[random.nextInt(chars.length)];
      sb.append(c);
    }

    return sb.toString();
  }
}
