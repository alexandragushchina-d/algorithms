
/**
 * Diese Klasse repräsentiert ein Blatt des Baumes.
 */
public class ABTreeLeaf extends ABTreeNode {

  public ABTreeLeaf(int a, int b) {
    super(a, b);
  }

  @Override
  public void insert(int key) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public boolean canSteal() {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Integer steal(boolean left) {
    return null;
  }

  @Override
  public boolean find(int key) {
    //throw new RuntimeException("Not Implemented");
    return false;
  }

  @Override
  public boolean remove(int key) {
    //throw new RuntimeException("Not Implemented");
    return false;
  }

  @Override
  public int height() {
    //throw new RuntimeException("Not Implemented");
    return 0;
  }

  @Override
  public Integer min() {
    //throw new RuntimeException("Not Implemented");
    return null;
  }

  @Override
  public Integer max() {
    //throw new RuntimeException("Not Implemented");
    return null;
  }

  @Override
  public boolean validAB(boolean root) {
    //throw new RuntimeException("Not Implemented");
    return true;
  }

  @Override
  public int dot(StringBuilder sb, int from) {
    sb.append(String.format("\tstruct%d [label=leaf, shape=ellipse];\n", from));
    return from + 1;
  }
}
