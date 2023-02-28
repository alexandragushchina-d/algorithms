
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graph {
  private List<List<Node>> nodes;

  public class Node implements Comparable<Node> {
    private int id;

    private Node(int id) {
      this.id = id;
    }

    public int getID() {
      return id;
    }

    @Override
    public int compareTo(Node o) {
      return Integer.compare(id, o.id);
    }
  }

  public Graph() {
    nodes = new ArrayList<>();
  }

  public int addNode() {
    nodes.add(new ArrayList<>());
    nodes.get(nodes.size() - 1).add(new Node(nodes.size() - 1));
    return nodes.size() - 1;
  }

  public Node getNode(int id) {
    return id >= nodes.size() ? null : nodes.get(id).get(0);
  }

  public List<Node> getAllNodes() {
    return Collections.unmodifiableList(
      IntStream.range(0, nodes.size()).mapToObj(i -> nodes.get(i).get(0))
        .collect(Collectors.toList()));
  }

  public List<Node> getAllNeighbours(int id) {
    return
      id >= nodes.size()
        ? null
        : Collections.unmodifiableList(
        IntStream.range(1, nodes.get(id).size())
          .mapToObj(i -> nodes.get(id).get(i)).collect(Collectors.toList()));
  }

  public void addEdge(int idA, int idB) {
    if (idA >= nodes.size() || idB >= nodes.size()) {
      throw new IllegalArgumentException("ID is invalid.");
    }

    for (int i = 1; i < nodes.get(idA).size(); ++i) {
      if (nodes.get(idA).get(i).getID() == idB) {
        return;
      }
    }

    nodes.get(idA).add(nodes.get(idB).get(0));
    if (idA != idB) {
      nodes.get(idB).add(nodes.get(idA).get(0));
    }
  }
}
