package tsypanov.strings.source.string;

import java.util.Arrays;
import java.util.Objects;

public class StringChain {

  /**
   * Contains all the string components added so far.
   */
  private String[] elts;

  /**
   * The number of string components added so far.
   */
  private int size;

  /**
   * Total length in chars so far, excluding prefix and suffix.
   */
  private int len;

  /**
   * Constructs a {@code StringChain} with no characters in it, with no {@code prefix} or {@code suffix}, and a
   * copy of the supplied {@code delimiter}. If no characters are added to the {@code StringChain} and methods
   * accessing the value of it are invoked, it will not return a {@code prefix} or {@code suffix} (or properties
   * thereof) in the result, unless {@code setEmptyValue} has first been called.
   */
  public StringChain() {
    this(8);
  }

  /**
   * Constructs a {@code StringChain} with no characters in it using copies of the supplied {@code prefix},
   * {@code delimiter} and {@code suffix}. If no characters are added to the {@code StringChain}
   * and methods accessing the string value of it are invoked, it will return the {@code prefix + suffix}
   * (or properties thereof) in the result, unless {@code setEmptyValue} has first been called.
   */
  public StringChain(int chunks) {
    if (chunks < 0) {
      throw new IllegalArgumentException("Illegal capacity: " + chunks);
    }
    this.elts = new String[chunks];
  }

  private static int getChars(String s, char[] chars, int start) {
    int len = s.length();
    s.getChars(0, len, chars, start);
    return len;
  }

  /**
   * Returns the current value, consisting of the {@code prefix}, the values added so far separated by the {@code
   * delimiter}, and the {@code suffix}, unless no elements have been added in which case, the {@code prefix + suffix}
   * or the {@code emptyValue} characters are returned.
   *
   * @return the string representation of this {@code StringChain}
   */
  @Override
  public String toString() {
    final int size = this.size;
    if (size == 0) {
      return "";
    }
    final String[] elts = this.elts;
    final char[] chars = new char[len];
    int startFrom = getChars(elts[0], chars, 0);
    for (int i = 1; i < size; i++) {
      startFrom += getChars(elts[i], chars, startFrom);
    }
    return new String(chars);
  }

  /**
   * Adds a copy of the given {@code CharSequence} value as the next element of the {@code StringChain} value. If
   * {@code cs} is {@code null}, then {@code "null"} is added.
   *
   * @param cs The element to add
   * @return a reference to this {@code StringChain}
   */
  public StringChain add(CharSequence cs) {
    final String elt = String.valueOf(cs);

    if (size == elts.length) {
      if (size == 0) {
        elts = new String[8];
      } else {
        elts = Arrays.copyOf(elts, 2 * size);
      }
    }
    len += elt.length();
    elts[size++] = elt;
    return this;
  }

  /**
   * Adds String representation of the given {@code Object} as the next element of the {@code StringChain} value.
   * If {@code o} is {@code null}, then {@code "null"} is added.
   *
   * @param  o The element to add
   * @return a reference to this {@code StringChain}
   */
  public StringChain add(Object o) {
    return this.add(String.valueOf(o));
  }

  /**
   * Adds the contents of the given {@code StringChain} without prefix and suffix as the next element if it is
   * non-empty. If the given {@code StringChain} is empty, the call has no effect.
   *
   * <p>A {@code StringChain} is empty if {@link #add(CharSequence) add()}
   * has never been called, and if {@code merge()} has never been called with a non-empty {@code StringChain}
   * argument.
   *
   * <p>If the other {@code StringChain} is using a different delimiter,
   * then elements from the other {@code StringChain} are concatenated with that delimiter and the result is
   * appended to this {@code StringChain} as a single element.
   *
   * @param other The {@code StringChain} whose contents should be merged into this one
   * @return This {@code StringChain}
   * @throws NullPointerException if the other {@code StringChain} is null
   */
  public StringChain merge(StringChain other) {
    Objects.requireNonNull(other);
    if (other.size == 0) {
      return this;
    }
    other.compactElts();
    return add(other.elts[0]);
  }

  private void compactElts() {
    if (size > 1) {
      final char[] chars = new char[len];
      int i = 1;
      int startFrom = getChars(elts[0], chars, 0);
      do {
        startFrom += getChars(elts[i], chars, startFrom);
        elts[i] = null;
      } while (++i < size);
      size = 1;
      elts[0] = new String(chars);
    }
  }

  /**
   * Returns the length of the {@code String} representation of this {@code StringChain}. Note that if no add
   * methods have been called, then the length of the {@code String} representation (either {@code prefix + suffix} or
   * {@code emptyValue}) will be returned. The value should be equivalent to {@code toString().length()}.
   *
   * @return the length of the current value of {@code StringChain}
   */
  public int length() {
    //todo just return len; ?
    return size == 0 ? 0 : len;
  }

}
