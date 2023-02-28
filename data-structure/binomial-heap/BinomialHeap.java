
import java.util.Arrays;
import java.util.Optional;

public class BinomialHeap {
  BinomialTreeNode[] heap;

  public BinomialHeap() {
    heap = new BinomialTreeNode[0];
  }

  public int min() {
    if (heap == null || heap.length == 0) {
      throw new IllegalStateException();
    }
    Optional<Integer> res = Optional.empty();
    for (int i = 0; i < heap.length; ++i) {
      if (heap[i] != null && (res.isEmpty() || res.get() > heap[i].min())) {
        res = Optional.of(heap[i].min());
      }
    }
    if (res.isEmpty()) {
      throw new IllegalStateException();
    }
    return res.get();
  }

  public void insert(int key, Result result) {
    result.startInsert(key, heap);
    BinomialTreeNode[] other = new BinomialTreeNode[1];
    other[0] = new BinomialTreeNode(key);
    BinomialTreeNode[] heapForLog = mergeForLog(other, heap);
    result.logIntermediateStep(heapForLog);
    heap = normalizeForMerge(heap);
    merge(other, result);
    heap = normalizeForHeap(heap);
    //result.logIntermediateStep(heap);
  }

  public int deleteMin(Result result) {
    result.startDeleteMin(heap);
    if (heap == null || heap.length == 0) {
      throw new IllegalStateException();
    }
    int min = min();
    int i = 0;
    for (; i < heap.length; ++i) {
      if (heap[i] != null && heap[i].min() == min) {
        break;
      }
    }
    BinomialTreeNode[] children = heap[i].getChildren();
    heap[i] = null;
    if (i == (heap.length - 1)) {
      heap = Arrays.copyOf(heap, heap.length - 1);
    }
    BinomialTreeNode[] heapForLog = mergeForLog(children, heap);
    result.logIntermediateStep(heapForLog);
    if (children == null) {
      return min;
    }

    heap = normalizeForMerge(heap);
    merge(children, result);
    heap = normalizeForHeap(heap);
    //result.logIntermediateStep(heap);
    return min;
  }

  private BinomialTreeNode[] normalizeForMerge(BinomialTreeNode[] array) {
    int rank = 0;
    for (int i = 0; i < array.length; ++i) {
      if (array[i] != null)
        rank = array[i].rank();
    }
    BinomialTreeNode[] result = new BinomialTreeNode[rank + 1];
    for (int i = 0; i < array.length; ++i) {
      if (array[i] != null)
        result[array[i].rank()] = array[i];
    }
    return result;
  }

  private BinomialTreeNode[] normalizeForHeap(BinomialTreeNode[] array) {
    int count = 0;
    for (int i = 0; i < array.length; ++i) {
      if (array[i] != null)
        ++count;
    }
    BinomialTreeNode[] result = new BinomialTreeNode[count];
    for (int i = 0, j = 0; i < array.length; ++i) {
      if (array[i] != null) {
        result[j] = array[i];
        ++j;
      }
    }
    return result;
  }

  private BinomialTreeNode[] mergeForLog(BinomialTreeNode[] array1, BinomialTreeNode[] array2) {
    BinomialTreeNode[] result = new BinomialTreeNode[0];
    for (int i = 0; array1 != null && i < array1.length; ++i) {
      if (array1[i] != null) {
        result = Arrays.copyOf(result, result.length + 1);
        result[result.length - 1] = array1[i];
      }
    }
    for (int i = 0; array2 != null && i < array2.length; ++i) {
      if (array2[i] != null) {
        result = Arrays.copyOf(result, result.length + 1);
        result[result.length - 1] = array2[i];
      }
    }
    return result;
  }

  private BinomialTreeNode[] mergeForLog(BinomialTreeNode[] array1, BinomialTreeNode[] array2, BinomialTreeNode[] array3) {
    return mergeForLog(mergeForLog(array1, array2), array3);
  }

  private void merge(BinomialTreeNode[] other, Result res) {
    if (other == null || other.length == 0) {
      return;
    }
    if (heap == null || heap.length == 0) {
      heap = other;
      return;
    }
    BinomialTreeNode[] result = new BinomialTreeNode[Math.max(heap.length, other.length)];
    int i = 0;
    for (; i < Math.min(heap.length, other.length); ++i) {
      BinomialTreeNode node;
      boolean log_flag = false;
      if (heap[i] == null) {
        node = other[i];
        other[i] = null;
      } else if (other[i] == null) {
        node = heap[i];
        heap[i] = null;
      } else {
        node = BinomialTreeNode.merge(heap[i], other[i]);
        heap[i] = other[i] = null;
        log_flag = true;
      }

      if (node == null) {
        continue;
      }

      int rank = node.rank();
      if (rank < result.length && result[rank] != null) {
        node = BinomialTreeNode.merge(result[rank], node);
        result[rank] = null;
        log_flag = true;
      }

      rank = node.rank();
      if (rank >= result.length) {
        result = Arrays.copyOf(result, rank + 1);
      }

      result[rank] = node;
      BinomialTreeNode[] heapForLog = mergeForLog(heap, other, result);
      if (log_flag)
        res.logIntermediateStep(heapForLog);
    }

    for (; i < heap.length; ++i) {
      if (heap[i] == null) {
        continue;
      }
      BinomialTreeNode node = heap[i];
      heap[i] = null;
      int rank = node.rank();
      boolean log_flag = false;
      if (rank < result.length && result[rank] != null) {
        node = BinomialTreeNode.merge(result[rank], node);
        result[rank] = null;
        log_flag = true;
      }

      rank = node.rank();
      if (rank >= result.length) {
        result = Arrays.copyOf(result, rank + 1);
      }

      result[rank] = node;
      BinomialTreeNode[] heapForLog = mergeForLog(heap, result);
      if (log_flag)
        res.logIntermediateStep(heapForLog);
    }

    for (; i < other.length; ++i) {
      if (other[i] == null) {
        continue;
      }
      BinomialTreeNode node = other[i];
      other[i] = null;
      int rank = node.rank();
      boolean log_flag = false;
      if (rank < result.length && result[rank] != null) {
        node = BinomialTreeNode.merge(result[rank], node);
        result[rank] = null;
        log_flag = true;
      }

      rank = node.rank();
      if (rank >= result.length) {
        result = Arrays.copyOf(result, rank + 1);
      }

      result[rank] = node;
      BinomialTreeNode[] heapForLog = mergeForLog(other, result);
      if (log_flag)
        res.logIntermediateStep(heapForLog);
    }

    heap = result;
  }

  public void print_heap() {
    System.out.println("*****************************************************");
    System.out.println("|- Heap");
    for (int i = 0; i < heap.length; ++i) {
      if (heap[i] != null) {
        heap[i].print_node("  ");
      }
    }
    System.out.println("*****************************************************");
  }

  public static void main(String[] args) {
    BinomialHeap heap = new BinomialHeap();
    heap.heap = new BinomialTreeNode[2];
    heap.heap[0] = new BinomialTreeNode(0);
    heap.heap[1] = BinomialTreeNode.merge(new BinomialTreeNode(1), new BinomialTreeNode(2));
    StudentResult result = new StudentResult();
    heap.insert(1, result);

    BinomialHeap heap2 = new BinomialHeap();
    heap2.heap = new BinomialTreeNode[2];
    heap2.heap[0] = new BinomialTreeNode(5);
    heap2.heap[1] = heap.heap[0];
    heap2.deleteMin(result);
  }
//    StudentResult result = new StudentResult();
//    BinomialHeap heap = new BinomialHeap();
//    heap.insert(8, result);
//    heap.insert(-8, result);
//    heap.insert(8, result);
//    System.out.println(heap.min());
//    heap.deleteMin(result);
//    for (int i = 0; i < 100; ++i) {
//      //heap.insert(-i, result);
//      heap.insert((i & 1) == 1 ? -i : i, result);
//      // heap.print_heap();
//    }
//    heap.print_heap();
//
//    for (int i = 0; i < 90; ++i) {
//      heap.deleteMin(result);
//    }
//  }

}
