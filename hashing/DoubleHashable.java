
public interface DoubleHashable<K> {

  long hash(K key);

  long hashTick(K key);
}
