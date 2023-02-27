
import java.util.*;

public class DoubleHashTable<K, V> {
  private int primeSize;
  private DoubleHashable<K> doubleHashable;
  private int collisionCounter = 0;
  private int maxRehashes = 0;
  private Pair<K, V>[] elements;


  public DoubleHashTable(int primeSize, HashableFactory<K> hashableFactory) {
    this.primeSize = primeSize;
    this.doubleHashable = hashableFactory.create(primeSize);
    this.elements = new Pair[primeSize];
  }

  public int hash(K key, int i) {
    return (int) (doubleHashable.hash(key) + i * doubleHashable.hashTick(key)) % primeSize;
  }

  public boolean insert(K k, V v) {
    for (int i = 0; i < primeSize; i++) {
      int index = hash(k, i);
      if (elements[index] == null) {
        elements[index] = new Pair<>(k, v);
        maxRehashes = Math.max(maxRehashes, i);
        if (i > 0) {
          ++collisionCounter;
        }
        return true;
      } else if (elements[index].getOne().equals(k)) {
        elements[index] = new Pair<>(k, v);
        return true;
      }
    }
    return false;
  }

  public Optional<V> find(K k) {
    for (int i = 0; i < primeSize; i++) {
      int index = hash(k, i);
      if (elements[index] == null) {
        return Optional.empty();
      } else if (elements[index].getOne().equals(k)) {
        return Optional.of(elements[index].getTwo());
      }
    }
    return Optional.empty();
  }

  public int collisions() {
    return collisionCounter;
  }

  public int maxRehashes() {
    return maxRehashes;
  }
}
