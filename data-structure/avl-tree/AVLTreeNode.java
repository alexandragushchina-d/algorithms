
public class AVLTreeNode {
  private int key;
  private int balance = 0;
  private AVLTreeNode left = null;
  private AVLTreeNode right = null;
  private boolean underCheck = false;

  public AVLTreeNode(int key) {
    this.key = key;
  }

  public AVLTreeNode getLeft() {
    return left;
  }

  public AVLTreeNode getRight() {
    return right;
  }

  public int getBalance() {
    return balance;
  }

  public int getKey() {
    return key;
  }

  public void setLeft(AVLTreeNode left) {
    this.left = left;
  }

  public void setRight(AVLTreeNode right) {
    this.right = right;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public int height() {
    int leftHeight = 0;
    int rightHeight = 0;
    if (getLeft() != null) {
      leftHeight = getLeft().height();
    }
    if (getRight() != null) {
      rightHeight = getRight().height();
    }
    return Math.max(leftHeight, rightHeight) + 1;
  }

  public boolean validAVL() {
    // check if cycle exists
    if (underCheck) {
      underCheck = false;
      return false;
    }
    underCheck = true;

    // check if subtrees are valid
    if (getLeft() != null && !getLeft().validAVL()) {
      underCheck = false;
      return false;
    }
    if (getRight() != null && !getRight().validAVL()) {
      underCheck = false;
      return false;
    }

    // check heights of right and left subtrees
    int leftHeight = getLeftHeight(this);
    int rightHeight = getRightHeight(this);
    if (Math.abs(leftHeight - rightHeight) > 1) {
      underCheck = false;
      return false;
    }

    // check balance
    if (Math.abs(balance) != Math.abs(rightHeight - leftHeight)) {
      underCheck = false;
      return false;
    }

    // check if all left nodes are less that
    if (getLeft() != null) {
      AVLTreeNode node = getLeft();
      do {
        if (node.getKey() >= this.key) {
          underCheck = false;
          return false;
        }
        node = node.getRight();
      } while (node != null);
    }

    // check if all right nodes are more or equal that
    if (getRight() != null) {
      AVLTreeNode node = getRight();
      do {
        if (node.getKey() < this.key) {
          underCheck = false;
          return false;
        }
        node = node.getLeft();
      } while (node != null);
    }
    underCheck = false;
    return true;
  }

  public boolean find(int key) {
    if (key == this.key) {
      return true;
    }
    if (key > this.key && getRight() != null) {
      return getRight().find(key);
    } else if (key < this.key && getLeft() != null) {
      return getLeft().find(key);
    }
    return false;
  }

  public AVLTreeNode insert(int key) {
    if (this.key == key) {
      return this;
    }
    if (key < this.key) {
      if (getLeft() != null) {
        setLeft(getLeft().insert(key));
      } else {
        setLeft(new AVLTreeNode(key));
      }
    } else {
      if (getRight() != null) {
        setRight(getRight().insert(key));
      } else {
        setRight(new AVLTreeNode(key));
      }
    }

    calculateBalance();
    AVLTreeNode res = this;
    if (balance == 2 && getLeftHeight(getRight()) <= getRightHeight(getRight())) {
      res = rotateLeft();
    } else if (balance == -2 && getRightHeight(getLeft()) <= getLeftHeight(getLeft())) {
      res = rotateRight();
    }
    calculateBalance();
    return res;
  }

  private void calculateBalance() {
    balance = getRightHeight(this) - getLeftHeight(this);
  }

  private AVLTreeNode rotateLeft() {
    AVLTreeNode res = getRight();
    this.setRight(res.getLeft());
    res.setLeft(this);
    res.calculateBalance();
    return res;
  }

  private AVLTreeNode rotateRight() {
    AVLTreeNode res = getLeft();
    this.setLeft(res.getRight());
    res.setRight(this);
    res.calculateBalance();
    return res;
  }

  private static int getLeftHeight(AVLTreeNode node) {
    int height = 0;
    if (node.getLeft() != null) {
      height = node.getLeft().height();
    }
    return height;
  }

  private static int getRightHeight(AVLTreeNode node) {
    int height = 0;
    if (node.getRight() != null) {
      height = node.getRight().height();
    }
    return height;
  }

  // public ... insert(int key) {
  //
  // }

  /*
   * @param sb der StringBuilder
   */
  public void dot(StringBuilder sb) {
    dotNode(sb, 0);
  }

  private int dotNode(StringBuilder sb, int idx) {
    sb.append(String.format("\t%d [label=\"%d, b=%d\"];\n", idx, key, balance));
    int next = idx + 1;
    if (left != null)
      next = left.dotLink(sb, idx, next, "l");
    if (right != null)
      next = right.dotLink(sb, idx, next, "r");
    return next;
  }

  private int dotLink(StringBuilder sb, int idx, int next, String label) {
    sb.append(String.format("\t%d -> %d [label=\"%s\"];\n", idx, next, label));
    return dotNode(sb, next);
  }

}
