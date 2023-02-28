
import java.util.ArrayList;
import java.util.List;

public class ABTreeInnerNode extends ABTreeNode {
  private List<Integer> keys;
  private List<ABTreeNode> children;

  public ABTreeInnerNode(List<Integer> keys, List<ABTreeNode> children, int a, int b) {
    super(a, b);
    this.keys = keys;
    this.children = children;
  }

  public ABTreeInnerNode(int key, ABTreeNode left, ABTreeNode right, int a, int b) {
    super(a, b);
    keys = new ArrayList<>();
    children = new ArrayList<>();
    keys.add(key);
    children.add(left);
    children.add(right);
  }

  public ABTreeInnerNode(int key, int a, int b) {
    this(key, new ABTreeLeaf(a, b), new ABTreeLeaf(a, b), a, b);
  }

  public List<ABTreeNode> getChildren() {
    return children;
  }

  public List<Integer> getKeys() {
    return keys;
  }

  @Override
  public void insert(int key) {
    int index = 0;
    for (; index < keys.size(); index++) {
      if (keys.get(index) == key) {
        return;
      } else if (keys.get(index) > key) {
        break;
      }
    }

    if (children.get(index).height() == 0) {
      keys.add(index, key);
      children.add(new ABTreeLeaf(a, b));
      return;
    } else {
      children.get(index).insert(key);
    }

    ABTreeInnerNode child = (ABTreeInnerNode) children.get(index);
    if (child.getKeys().size() < b) {
      return;
    }

    ABTreeInnerNode newChild = new ABTreeInnerNode(
      new ArrayList<>(child.getKeys().subList(0, b / 2)),
      new ArrayList<>(child.getChildren().subList(0, b / 2 + 1)), a, b);
    children.add(index, newChild);
    keys.add(index, child.getKeys().get(b / 2));
    child.getKeys().subList(0, b / 2 + 1).clear();
    child.getChildren().subList(0, b / 2 + 1).clear();
  }

  @Override
  public boolean canSteal() {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Integer steal(boolean left) {
    if (height() == 1) {
      if (children.size() > a) {
        Integer result = keys.remove(left ? keys.size() - 1 : 0);
        children.remove(children.size() - 1);
        return result;
      }

      return null;
    }

    return children.get(left ? children.size() - 1 : 0).steal(left);
  }

  @Override
  public boolean find(int key) {
    //throw new RuntimeException("Not Implemented");
    int index = 0;
    for (; index < keys.size(); index++) {
      if (keys.get(index) == key) {
        return true;
      } else if (keys.get(index) > key) {
        break;
      }
    }

    return children.get(index).find(key);
  }

  public boolean remove(int key) {
    //throw new RuntimeException("Not Implemented");
    int index = 0;
    for (; index < keys.size(); index++) {
      if (keys.get(index) >= key) {
        break;
      }
    }

    if (index < keys.size() && keys.get(index) == key) {
//      if (children.get(index).height() == 0 && children.size() > a) {
//        keys.remove(index);
//        children.remove(children.size() - 1);
//        return true;
//      }

      Integer leftNumber = children.get(index).steal(true);
      if (leftNumber != null) {
        keys.set(index, leftNumber);
        return true;
      }

      Integer rightNumber = children.get(index + 1).steal(false);
      if (rightNumber != null) {
        keys.set(index, rightNumber);
        return true;
      }

      keys.remove(index);
      if (children.get(index).height() == 0) {
        children.remove(children.size() - 1);
      }

      return true;
    }

    boolean result = children.get(index).remove(key);
    if (result) {
      ABTreeInnerNode newChild = (ABTreeInnerNode) children.get(index);
      //if ((newChild.getKeys().size() + 1) != newChild.getChildren().size()) {
      if (newChild.getChildren().size() < a) {
        if ((index + 1) < children.size()) {
          newChild.getKeys().add(keys.get(index));
          newChild.getKeys().addAll(((ABTreeInnerNode) children.get(index + 1)).getKeys());
          newChild.getChildren().addAll(((ABTreeInnerNode) children.get(index + 1)).getChildren());
          keys.remove(index);
          children.remove(index + 1);
        } else {
          newChild.getKeys().add(0, keys.get(index - 1));
          newChild.getKeys().addAll(0, ((ABTreeInnerNode) children.get(index - 1)).getKeys());
          newChild.getChildren().addAll(0, ((ABTreeInnerNode) children.get(index - 1)).getChildren());
          keys.remove(index - 1);
          children.remove(index - 1);
        }
      }
    }

    return result;
  }

  @Override
  public int height() {
    //throw new RuntimeException("Not Implemented");
    return children.get(0).height() + 1;
  }

  @Override
  public Integer min() {
    //throw new RuntimeException("Not Implemented");
    Integer childMin = children.get(0).min();
    return childMin == null ? keys.get(0) : childMin;
  }

  @Override
  public Integer max() {
    //throw new RuntimeException("Not Implemented");
    Integer childMax = children.get(children.size() - 1).max();
    return childMax == null ? keys.get(keys.size() - 1) : childMax;
  }

  @Override
  public boolean validAB(boolean root) {
    int minChildCount = root ? 2 : a;
    if (children.size() < minChildCount || children.size() > b) {
      return false;
    }

    if ((children.size() - 1) != keys.size()) {
      return false;
    }

    int childHeight = 0;
    for (int i = 0; i < children.size(); i++) {
      if (children.get(i) == null || !children.get(i).validAB(false)) {
        return false;
      }

      if (i == 0) {
        childHeight = children.get(i).height();
      } else if (children.get(i).height() != childHeight) {
        return false;
      }
    }

    for (int i = 0; i < keys.size(); i++) {
      if (childHeight != 0 &&
        (children.get(i).max() > keys.get(i) ||
          children.get(i + 1).min() < keys.get(i))) {
        return false;
      }

      if (i > 0 && keys.get(i - 1) >= keys.get(i)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int dot(StringBuilder sb, int from) {
    int mine = from++;
    sb.append(String.format("\tstruct%s [label=\"", mine));
    for (int i = 0; i < 2 * keys.size() + 1; i++) {
      if (i > 0)
        sb.append("|");
      sb.append(String.format("<f%d> ", i));
      if (i % 2 == 1)
        sb.append(keys.get(i / 2));
    }
    sb.append("\"];\n");
    for (int i = 0; i < children.size(); i++) {
      int field = 2 * i;
      sb.append(String.format("\tstruct%d:<f%d> -> struct%d;\n", mine, field, from));
      from = children.get(i).dot(sb, from);
    }
    return from;
  }
}
