
import java.util.*;

public class RadixSort {

  static int key(int element, int decimalPlace) {
    if (element < 0 || decimalPlace < 0) {
      throw new IllegalArgumentException();
    }
    int[] array = new int[String.valueOf(element).length()];
    if (array.length <= decimalPlace) {
      return 0;
    }
    int i = 0;
    do {
      array[i] = element % 10;
      element /= 10;
      i++;
    } while (element > 0);
    return array[decimalPlace];
  }

  static void concatenate(List<Integer>[] buckets, int[] elements) {
    int count = 0;
    for (int i = 0; i < buckets.length; i++) {
      for (int j = 0; j < buckets[i].size(); j++) {
        elements[count] = buckets[i].get(j);
        count++;
      }
    }
  }

  static void kSort(int[] elements, int decimalPlace) {
    List<Integer>[] buckets = new List[elements.length];
    for (int i = 0; i < elements.length; i++) {
      buckets[i] = new ArrayList<>();
    }
    for (int e : elements) {
      buckets[key(e, decimalPlace)].add(e);
    }
    concatenate(buckets, elements);
  }

  static int getMaxDecimalPlaces(int[] elements) {
    if (elements.length == 0) {
      return 0;
    }
    int max = elements[0];
    for (int i = 1; i < elements.length; i++) {
      if (max < elements[i]) {
        max = elements[i];
      }
    }
    return String.valueOf(max).length();
  }

  public static void sort(int[] elements, Result result) {
    for (int i = 0; i < getMaxDecimalPlaces(elements); i++) {
      kSort(elements, i);
      result.logArray(elements);
    }
  }

  public static void main(String[] args) {
    int SEED = 0;
    int N = 10;
    int[] numbers;
    numbers = new int[N];
    Random r = new Random(SEED);
    for (int i = 0; i < N; i++) {
      numbers[i] = r.nextInt((int) Math.pow(10, r.nextInt(4) + 3));
    }

    System.out.println("\n==== Radixsort ====\n\nSortiertest:");
    sort(numbers, new StudentResult());

    sort(new int[0], new StudentResult());
    sort(new int[]{0}, new StudentResult());
    System.out.println("Key: " + RadixSort.key(1, 2));
//    sort(new int[]{-9}, new StudentResult());
//    RadixSort.key(9, -9);
  }
}
