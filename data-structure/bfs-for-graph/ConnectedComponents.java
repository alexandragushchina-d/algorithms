
import java.util.List;

public class ConnectedComponents implements Result {
  private BFS search;
  private boolean[] visited;

  public ConnectedComponents() {
    search = new BFS(this);
  }

  public int countConnectedComponents(Graph g) {
    List<Graph.Node> nodeList = g.getAllNodes();
    visited = new boolean[nodeList.size()];
    int partCount = 0;
    while (true) {
      int nextNode = 0;
      for (; nextNode < nodeList.size(); ++nextNode) {
        if (!visited[nextNode]) {
          break;
        }
      }

      if (nextNode == nodeList.size()) {
        break;
      }

      ++partCount;
      search.sssp(g, g.getNode(nextNode));
    }

    return partCount;
  }

  @Override
  public void visit(int node) {
    visited[node] = true;
  }
}
