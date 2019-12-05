package tsypanov.strings.source.p6spy;

public class ToHexStringConverter {

  private static final char[] HEX_CHARS = {
          '0', '1', '2', '3',
          '4', '5', '6', '7',
          '8', '9', 'A', 'B',
          'C', 'D', 'E', 'F'
  };

  private static final byte[] HEX_BYTES = {
          '0', '1', '2', '3',
          '4', '5', '6', '7',
          '8', '9', 'A', 'B',
          'C', 'D', 'E', 'F'
  };

  public String toHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      int temp = (int) b & 0xFF;
      sb.append(HEX_CHARS[temp / 16]);
      sb.append(HEX_CHARS[temp % 16]);
    }
    return sb.toString();
  }

  public String patched_toHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      int temp = (int) b & 0xFF;
      sb.append(HEX_CHARS[temp / 16]);
      sb.append(HEX_CHARS[temp % 16]);
    }
    return sb.toString();
  }

  public String chars_toHexString(byte[] bytes) {
    char[] result = new char[bytes.length * 2];
    int idx = 0;
    for (byte b : bytes) {
      int temp = (int) b & 0xFF;
      result[idx++] = HEX_CHARS[temp / 16];
      result[idx++] = HEX_CHARS[temp % 16];
    }
    return new String(result);
  }

  public String bytes_toHexString(byte[] bytes) {
    byte[] result = new byte[bytes.length * 2];
    int idx = 0;
    for (byte b : bytes) {
      int temp = (int) b & 0xFF;
      result[idx++] = HEX_BYTES[temp / 16];
      result[idx++] = HEX_BYTES[temp % 16];
    }
    return new String(result);
  }
}
