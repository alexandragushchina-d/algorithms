
public class DoubleHashInt implements DoubleHashable<Integer> {
  private int size;

  public DoubleHashInt(int size) {
    this.size = size;
  }

  @Override
  public long hash(Integer key) {
    return ((key % size) + size) % size;
  }

  @Override
  public long hashTick(Integer key) {
    int newSize = size - 2;
   return  1+ ((1 + (key % newSize)) + newSize) % newSize;
  }

/*  public static void main(String[] args) {
    DoubleHashInt doubleHashInt = new DoubleHashInt(701);
    System.out.println("----------------Value: -9 ------------------------------");
    System.out.println(doubleHashInt.hash(-9));
    System.out.println(doubleHashInt.hashTick(-9));

    System.out.println("----------------Value: 9 ------------------------------");
    System.out.println(doubleHashInt.hash(9));
    System.out.println(doubleHashInt.hashTick(9));

    System.out.println("----------------Value: -702 ------------------------------");
    System.out.println(doubleHashInt.hash(-702));
    System.out.println(doubleHashInt.hashTick(-702));

    System.out.println("----------------Value: 0 ------------------------------");
    System.out.println(doubleHashInt.hash(0));
    System.out.println(doubleHashInt.hashTick(0));

    System.out.println("----------------Value: 702 ------------------------------");
    System.out.println(doubleHashInt.hash(702));
    System.out.println(doubleHashInt.hashTick(702));

    System.out.println("----------------Value: 701 ------------------------------");
    System.out.println(doubleHashInt.hash(701));
    System.out.println(doubleHashInt.hashTick(701));

    System.out.println("----------------Value: -701 ------------------------------");
    System.out.println(doubleHashInt.hash(-701));
    System.out.println(doubleHashInt.hashTick(-701));

    System.out.println("----------------Value: -1 ------------------------------");
    System.out.println(doubleHashInt.hash(-1));
    System.out.println(doubleHashInt.hashTick(-1));

    System.out.println("----------------Value: -700 ------------------------------");
    System.out.println(doubleHashInt.hash(-700));
    System.out.println(doubleHashInt.hashTick(-700));

    System.out.println("----------------Value: -1399 ------------------------------");
    System.out.println(doubleHashInt.hash(-1399));
    System.out.println(doubleHashInt.hashTick(-1399));
  }*/
}
