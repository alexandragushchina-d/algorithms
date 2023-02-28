
import java.util.ArrayList;

/**
 * Diese Klasse implementiert einen (a,b)-Baum.
 */
public class ABTree {

  private final int a;
  private final int b;

  private ABTreeInnerNode root;

  public ABTree(int a, int b) {
    // throw new RuntimeException("Not Implemented");
    this.a = a;
    this.b = b;
  }

  public ABTreeInnerNode getRoot() {
    return root;
  }

  public void setRoot(ABTreeInnerNode root) {
    this.root = root;
  }

  public boolean validAB() {
    //throw new RuntimeException("Not Implemented");
    if (root == null) {
      return true;
    }

    return root.validAB(true);
  }

  public int height() {
    //throw new RuntimeException("Not Implemented");
    if (root == null) {
      return 0;
    }

    return root.height();
  }

  public boolean find(int key) {
    //throw new RuntimeException("Not Implemented");
    if (root == null) {
      return false;
    }
    return root.find(key);
  }

  public void insert(int key) {
    if (root == null) {
      root = new ABTreeInnerNode(key, a, b);
      return;
    }

    root.insert(key);
    if (root.getKeys().size() < b) {
      return;
    }

    ABTreeInnerNode leftChild = new ABTreeInnerNode(
      new ArrayList<>(root.getKeys().subList(0, b / 2)),
      new ArrayList<>(root.getChildren().subList(0, b / 2 + 1)), a, b);
    ABTreeInnerNode oldRoot = root;
    root = new ABTreeInnerNode(
      oldRoot.getKeys().get(b / 2), leftChild, oldRoot, a, b);
    oldRoot.getKeys().subList(0, b / 2 + 1).clear();
    oldRoot.getChildren().subList(0, b / 2 + 1).clear();
  }

  public boolean remove(int key) {
    // throw new RuntimeException("Not Implemented");
    if (root == null) {
      return false;
    }

    boolean result = root.remove(key);
    if (root.getKeys().size() == 0) {
      if (root.getChildren().get(0).height() == 0) {
        root = null;
      } else {
        if (root.getChildren().size() == 1) {
          root = (ABTreeInnerNode) root.getChildren().get(0);
        } else {
          ABTreeInnerNode newRoot = (ABTreeInnerNode) root.getChildren().get(0);
          newRoot.getKeys().addAll(((ABTreeInnerNode) root.getChildren().get(1)).getKeys());
          newRoot.getChildren().addAll(((ABTreeInnerNode) root.getChildren().get(1)).getChildren());
          newRoot.getChildren().remove(0);
          root = newRoot;
        }
      }
    }

    return result;
  }

  /*
   * @return der Baum im Graphiz-Format
   */
  public String dot() {
    StringBuilder sb = new StringBuilder();
    sb.append("digraph {\n");
    sb.append("\tnode [shape=record];\n");
    if (root != null)
      root.dot(sb, 0);
    sb.append("}");
    return sb.toString();
  }

  @Override
  public String toString() {
    return dot();
  }

//  public static void main(String[] args) {
//    ABTree tree1 = new ABTree(2, 3);
//    int tree1Height1 = tree1.height();
//    tree1.insert(19);
//    tree1.insert(11);
//    tree1.insert(28);
//    tree1.insert(38);
//    tree1.insert(37);
//    tree1.insert(30);
//    tree1.insert(7);
//    tree1.insert(59);
//    tree1.insert(41);
//    int tree1Height2 = tree1.height();
//    boolean tree1Check1 = tree1.validAB();
//    boolean tree1Remove1 = tree1.remove(130);
//    boolean tree1Remove2 = tree1.remove(7);
//    boolean tree1Remove3 = tree1.remove(37);
//    boolean tree1Remove4 = tree1.remove(59);
//    boolean tree1Remove5 = tree1.remove(41);
//    boolean tree1Remove6 = tree1.remove(11);
//    boolean tree1Remove7 = tree1.remove(19);
//    boolean tree1Remove8 = tree1.remove(30);
//    boolean tree1Remove9 = tree1.remove(38);
//    boolean tree1Remove10 = tree1.remove(28);
//
//    ABTree tree2 = new ABTree(4, 5);
//    tree2.insert(19);
//    tree2.insert(21);
//    tree2.insert(23);
//    tree2.remove(19);
//    tree2.remove(21);
//    tree2.remove(23);
//  }
}
