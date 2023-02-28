
public abstract class ABTreeNode {

  protected int a;
  protected int b;

  ABTreeNode(int a, int b) {
    this.a = a;
    this.b = b;
  }

  public abstract void insert(int key);

  public abstract boolean canSteal();
  public abstract Integer steal(boolean left);

  public abstract boolean find(int key);

  public abstract boolean remove(int key);

  public abstract int height();

  public abstract Integer min();

  public abstract Integer max();

  public abstract boolean validAB(boolean root);

  /*
   * @return der nächste freie Index für das Graphviz-Format
   */
  public abstract int dot(StringBuilder sb, int from);
}
