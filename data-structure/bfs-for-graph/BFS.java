
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BFS {
  private Result result;
  private NodeInfo[] nodeInfoList;

  private class NodeInfo {
    private Node parent;
    private int depth;

    public NodeInfo(Node parent, int depth) {
      this.parent = parent;
      this.depth = depth;
    }
  }

  public BFS() {
    this(ignored -> {
    });
  }

  public BFS(Result result) {
    this.result = result;
  }

  public void sssp(Graph g, Node start) {
    if (start == null || start.getID() >= g.getAllNodes().size()) {
      throw new IllegalArgumentException();
    }

    nodeInfoList = new NodeInfo[g.getAllNodes().size()];
    boolean[] queued = new boolean[g.getAllNodes().size()];
    nodeInfoList[start.getID()] = new NodeInfo(null, 0);
    List<Node> nodeLevel = new ArrayList<>();
    nodeLevel.add(start);
    queued[start.getID()] = true;
    while (!nodeLevel.isEmpty()) {
      List<Node> currentNodeLevel = nodeLevel.stream().sorted().collect(Collectors.toList());
      nodeLevel = new ArrayList<>();
      for (int i = 0; i < currentNodeLevel.size(); ++i) {
        result.visit(currentNodeLevel.get(i).getID());

        List<Node> neighbours = g.getAllNeighbours(currentNodeLevel.get(i).getID());
        for (int j = 0; j < neighbours.size(); ++j) {
          if (!queued[neighbours.get(j).getID()]) {
            nodeInfoList[neighbours.get(j).getID()] = new NodeInfo(
              currentNodeLevel.get(i),
              nodeInfoList[currentNodeLevel.get(i).getID()].depth + 1);
            nodeLevel.add(neighbours.get(j));
            queued[neighbours.get(j).getID()] = true;
          }
        }
      }
    }
  }

  public int getDepth(Node n) {
    if (nodeInfoList == null ||
      n.getID() >= nodeInfoList.length ||
      nodeInfoList[n.getID()] == null) {
      throw new IllegalArgumentException();
    }

    return nodeInfoList[n.getID()].depth;
  }

  public Node getParent(Node n) {
    if (nodeInfoList == null ||
      n.getID() >= nodeInfoList.length ||
      nodeInfoList[n.getID()] == null) {
      throw new IllegalArgumentException();
    }

    return nodeInfoList[n.getID()].parent;
  }

/*  public static void main(String[] args) {
    Graph graph1 = new Graph();
    graph1.addNode();
    graph1.addNode();
    graph1.addNode();
    graph1.addNode();
    graph1.addNode();
    graph1.addNode();
    graph1.addNode();
    graph1.addNode();
    graph1.addEdge(0, 1);
    graph1.addEdge(0, 2);
    graph1.addEdge(0, 3);
    graph1.addEdge(1, 3);
    graph1.addEdge(1, 4);
    graph1.addEdge(1, 5);
    graph1.addEdge(2, 5);
    graph1.addEdge(2, 6);
    graph1.addEdge(3, 4);
    graph1.addEdge(3, 5);
    graph1.addEdge(3, 6);
    graph1.addEdge(4, 5);
    graph1.addEdge(4, 6);
    graph1.addEdge(4, 7);
    graph1.addEdge(5, 7);
    graph1.addEdge(6, 7);
    BFS bfs1 = new BFS(new StudentResult());
    bfs1.sssp(graph1, graph1.getNode(0));

    System.out.println("-----------------------------------------------------");

    Graph graph2 = new Graph();
    graph2.addNode();
    graph2.addNode();
    graph2.addNode();
    graph2.addNode();
    graph2.addNode();
    graph2.addNode();
    graph2.addNode();
    graph2.addNode();
    graph2.addEdge(0, 1);
    graph2.addEdge(0, 2);
    graph2.addEdge(0, 3);
    graph2.addEdge(2, 3);
    graph2.addEdge(2, 4);
    graph2.addEdge(2, 5);
    graph2.addEdge(1, 5);
    graph2.addEdge(1, 6);
    graph2.addEdge(3, 4);
    graph2.addEdge(3, 5);
    graph2.addEdge(3, 6);
    graph2.addEdge(4, 5);
    graph2.addEdge(4, 6);
    graph2.addEdge(4, 7);
    graph2.addEdge(5, 7);
    graph2.addEdge(6, 7);
    BFS bfs2 = new BFS(new StudentResult());
    bfs2.sssp(graph2, graph2.getNode(0));
  }*/
}
