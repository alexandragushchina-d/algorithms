
import java.util.Arrays;
import java.util.Random;

public class Quicksort {

  public static void main(String[] args) {
    int SEED = 1;
    int N = 10;
    int MAX_V = 100;
    int[] numbers;

    numbers = new int[N];
    Random r = new Random(SEED);
    for (int i = 0; i < N; i++) {
      numbers[i] = r.nextInt(MAX_V) + 1;
    }

    System.out.println(Arrays.toString(numbers));
    System.out.println("\n==== Quicksort ====\n\nSortiertest:");
    new Quicksort().sort(numbers, new StudentResult());
    System.out.println(Arrays.toString(numbers));
  }

  public void sort(int[] numbers, Result result) {
    sort(numbers, 0, numbers.length - 1, result);
  }

  public void sort(int[] numbers, int left, int right, Result result) {
    if (left < right) {
      int i = left - 1;
      int j = right;
      do {
        do {
          i++;
        } while (numbers[i] < numbers[right]);
        do {
          j--;
        } while (j >= left && numbers[j] > numbers[right]);
        if (i < j) {
          swap(numbers, i, j);
        }
      } while (i < j);

      swap(numbers, i, right);
      sort(numbers, left, i - 1, result);
      sort(numbers, i + 1, right, result);
      result.logPartialArray(Arrays.copyOfRange(numbers, left, right + 1));
    }
  }

  public void swap(int[] numbers, int i, int j) {
    int temp = numbers[i];
    numbers[i] = numbers[j];
    numbers[j] = temp;
  }

  public void coolSwap(int[] numbers, int i, int j) {
    if (numbers[i] == numbers[j]) {
      return;
    }
    numbers[i] ^= numbers[j];
    numbers[j] ^= numbers[i];
    numbers[i] ^= numbers[j];
  }
}
