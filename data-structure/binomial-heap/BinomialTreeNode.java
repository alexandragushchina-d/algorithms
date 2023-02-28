
public class BinomialTreeNode {
  private BinomialTreeNode[] children;
  private int element;

  public BinomialTreeNode(int element) {
    this.element = element;
  }

  public int min() {
/*    int min = element;
    for (int i = 0; children != null && i < children.length; ++i) {
      if (children[i] == null) {
        continue;
      }
      int childMin = children[i].min();
      if (min > childMin) {
        min = childMin;
      }
    }
    return min;*/
    return element;
  }

  public int rank() {
/*    int rank = 0;
    for (int i = 0; i < children.length; ++i) {
      int childRank = children[i].rank() + 1;
      if (rank < childRank) {
        rank = childRank;
      }
    }*/
    if (children == null) {
      return 0;
    }
    int rank = children.length - 1;
    for (; rank >= 0; --rank) {
      if (children[rank] != null) {
        break;
      }
    }
    return rank + 1;
  }

  public BinomialTreeNode getChildWithRank(int rank) {
    return children[rank];
  }

  private void addTreeNode(BinomialTreeNode node) {
    int nodeRank = node.rank();
    if (children == null || nodeRank >= children.length) {
      BinomialTreeNode[] newChildren = new BinomialTreeNode[nodeRank + 1];
      newChildren[nodeRank] = node;
      for (int i = 0; children != null && i < children.length; i++) {
        newChildren[i] = children[i];
      }
      children = newChildren;
      return;
    }
    if (children[nodeRank] == null) {
      children[nodeRank] = node;
      return;
    }
    BinomialTreeNode child = children[nodeRank];
    children[nodeRank] = null;
    addTreeNode(merge(child, node));
  }

  public void print_node(String prefix) {
    System.out.println(prefix + "|- " + element);
    for (int i = 0; children != null && i < children.length; ++i) {
      if (children[i] != null) {
        children[i].print_node(prefix + "  ");
      }
    }
  }

  /*
   * public ... deleteMin() {
   * // TODO
   * return null;
   * }
   */

  public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
    if (a == null || b == null || a == b) {
      throw new IllegalArgumentException();
    }
    if (a.min() > b.min()) {
      b.addTreeNode(a);
      return b;
    }
    if (a.min() == b.min() && a.rank() < b.rank()) {
      b.addTreeNode(a);
      return b;
    }
    a.addTreeNode(b);
    return a;
  }

  public BinomialTreeNode[] getChildren() {
    return children;
  }

/*  public static void main(String[] args) {
    System.out.println("Expected min() = 0: " + new BinomialTreeNode(0).min());
    System.out.println("Expected rank() = 0: " + new BinomialTreeNode(0).rank());
    BinomialTreeNode node1 = new BinomialTreeNode(1);
    BinomialTreeNode node2 = new BinomialTreeNode(2);
    BinomialTreeNode node3  = merge(node1, node2);
    System.out.println("Expected min() = 1: " + node3.min());
    System.out.println("Expected rank() = 1: " + node3.rank());
    BinomialTreeNode node4 = new BinomialTreeNode(4);
    BinomialTreeNode node5 = merge(node3, node4);
    System.out.println("Expected min() = 1: " + node5.min());
    System.out.println("Expected rank() = 2: " + node5.rank());
  }*/
}
