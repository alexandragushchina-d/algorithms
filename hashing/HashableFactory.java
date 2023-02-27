
/**
 * Das Interface besteht aus einer Fabrik, die aus
 * einer Größe ein Hashable<K>-Objekt erzeugt.
 */
public interface HashableFactory<K> {
  DoubleHashable<K> create(int size);
}
