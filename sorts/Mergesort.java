
import java.util.Arrays;
import java.util.Random;

public class Mergesort {

  public static void main(String[] args) {
    int SEED = 0;
    int N = 10;
    int MAX_V = 100;
    int[] numbers;

    numbers = new int[N];
    Random r = new Random(SEED);
    for (int i = 0; i < N; i++) {
      numbers[i] = r.nextInt(MAX_V) + 1;
    }

    System.out.println(Arrays.toString(numbers));
    System.out.println("\n==== Mergesort ====\n\nSortiertest:");
    new Mergesort().sort(numbers, new StudentResult());
    System.out.println(Arrays.toString(numbers));
  }

  public void sort(int[] numbers, Result result) {
    int[] helper = new int[numbers.length];
    sort(numbers, 0, numbers.length - 1, helper, result);
  }

  public void sort(int[] numbers, int left, int right, int[] helper, Result result) {
    if (left < right) {
      final int middle = ((left + right) % 2) == 1 ?  (left + right - 1) / 2 : (left + right) / 2;
      sort(numbers, left, middle, helper, result);
      sort(numbers, middle + 1, right,  helper, result);
      merge(numbers, left, middle, right, helper);
      result.logPartialArray(Arrays.copyOfRange(numbers, left, right + 1));
    }
  }

  public void merge(int[] numbers, int left, int mid, int right, int[] helper) {
    int helperSize = right - left + 1;
    for (int i = 0; i < helperSize; i++) {
      helper[i] = numbers[left + i];
    }

    int helperLeft = 0;
    int helperMid = mid - left;
    int helperRight = helperMid + 1;
    int current = left;

    while (helperLeft <= helperMid && helperRight < helperSize) {
      if (helper[helperLeft] <= helper[helperRight]) {
        numbers[current] = helper[helperLeft++];
      } else {
        numbers[current] = helper[helperRight++];
      }
      current++;
    }

    while (helperLeft <= helperMid) {
      numbers[current++] = helper[helperLeft++];
    }

    while (helperRight < helperSize) {
      numbers[current++] = helper[helperRight++];
    }
  }
}
