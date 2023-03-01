
import java.util.*;

public class Graph {
  private List<Node> nodes;
  private Map<Integer, Map<Integer, Integer>> nodeEdges;

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
    nodeEdges = new HashMap<>();
  }

  public int addNode() {
    nodes.add(new Node(nodes.size()));
    return nodes.size() - 1;
  }

  public Node getNode(int id) {
    return id >= nodes.size() ? null : nodes.get(id);
  }

  public List<Node> getAllNodes() {
    return Collections.unmodifiableList(nodes);
  }

  public List<Node> getAllNeighbours(int id) {
    if (id >= nodes.size()) {
      throw new IllegalArgumentException();
    }

    if (!nodeEdges.containsKey(id)) {
      return Collections.unmodifiableList(new ArrayList<>());
    }

    SortedSet<Integer> nodeIds = new TreeSet<>(nodeEdges.get(id).keySet());
    List<Node> result = new ArrayList<>();
    for (Integer node : nodeIds) {
      result.add(nodes.get(node));
    }
    return Collections.unmodifiableList(result);
  }

  public void addEdge(int idA, int idB, int weight) {
    if (idA >= nodes.size() || idB >= nodes.size() || weight < 0) {
      throw new IllegalArgumentException("ID is invalid.");
    }

    if (!nodeEdges.containsKey(idA)) {
      nodeEdges.put(idA, new HashMap<>());
    }

    nodeEdges.get(idA).put(idB, weight);
  }

  public int getEdgeWeight(int idA, int idB) {
    if (idA >= nodes.size() || idB >= nodes.size()) {
      throw new IllegalArgumentException("ID is invalid.");
    }

    if (!nodeEdges.containsKey(idA) || !nodeEdges.get(idA).containsKey(idB)) {
      throw new IllegalArgumentException("Edge does not exist.");
    }

    return nodeEdges.get(idA).get(idB).intValue();
  }

}
