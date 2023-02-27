
public class DoubleHashString implements DoubleHashable<String> {
  private final int[] a;
  private int size;

  public DoubleHashString(int size) {
    this.size = size;
    this.a = new int[]{2, size + 1, size};
  }

  @Override
  public long hash(String key) {
    char[] arrayOfChars = key.toCharArray();
    int res = 0;
    for (int i = 0; i < arrayOfChars.length; i++) {
      res += arrayOfChars[i] * a[i % a.length];
      res = res % size;
    }
    return res;
  }

  @Override
  public long hashTick(String key) {
    char[] arrayOfChars = key.toCharArray();
    int res = 0;
    for (int i = 0; i < arrayOfChars.length; i++) {
      res += arrayOfChars[i] * a[i % a.length];
      res = res % (size - 2);
    }
    return 1 + (res % (size - 2));
  }

  /*public static void main(String[] args) {
    System.out.println("----------------Value: \"!!!\" ------------------------------");
    DoubleHashString doubleHashString = new DoubleHashString(701);
    System.out.println(doubleHashString.hash("!!!"));
    System.out.println(doubleHashString.hashTick("!!!"));

    System.out.println("----------------Value: \"!\" ------------------------------");
    System.out.println(doubleHashString.hash("!"));
    System.out.println(doubleHashString.hashTick("!"));

    System.out.println("----------------Value: \"Empty string\" ------------------------------");
    System.out.println(doubleHashString.hash(""));
    System.out.println(doubleHashString.hashTick(""));

    System.out.println("----------------Value: \"!!!000000000000000000000000000000000000000000000000\" -------------");
    System.out.println(doubleHashString.hash("!!!000000000000000000000000000000000000000000000000"));
    System.out.println(doubleHashString.hashTick("!!!000000000000000000000000000000000000000000000000"));

  }*/
}
