
import java.util.ArrayList;
import java.util.List;

import gad.dijkstra.Graph.Node;

public class Dijkstra {
  private NodeInfo[] nodeInfoList;
  private Graph.Node source;
  private Graph.Node destination;

  private class NodeInfo {
    private int distance;
    private Node parent;

    public NodeInfo(Node parent, int distance) {
      this.parent = parent;
      this.distance = distance;
    }
  }

  public Dijkstra() {
  }

  public void findRoute(Graph g, Graph.Node a, Graph.Node b, Result result) {
    source = a;
    destination = b;
    nodeInfoList = new NodeInfo[g.getAllNodes().size()];
    boolean[] visited = new boolean[g.getAllNodes().size()];
    nodeInfoList[a.getID()] = new NodeInfo(null, 0);
    result.addNode(a.getID(), 0);
    Graph.Node currentNode = a;

    while (currentNode != null) {
      List<Node> neighbours = g.getAllNeighbours(currentNode.getID());

      for (int i = 0; i < neighbours.size(); ++i) {
        int ID = neighbours.get(i).getID();

        if (visited[ID] || ID == currentNode.getID()) {
          continue;
        }

        int newDistance =
          nodeInfoList[currentNode.getID()].distance
            + g.getEdgeWeight(currentNode.getID(), ID);
        result.addNode(ID, newDistance);
        if (nodeInfoList[ID] != null && nodeInfoList[ID].distance <= newDistance) {
          continue;
        }

        nodeInfoList[ID] = new NodeInfo(currentNode, newDistance);
      }

      visited[currentNode.getID()] = true;
      if (currentNode.getID() == b.getID()) {
        return;
      }

      currentNode = null;
      for (int j = 0; j < nodeInfoList.length; j++) {
        if (nodeInfoList[j] == null || visited[j]) {
          continue;
        }

        if (currentNode == null
          || nodeInfoList[currentNode.getID()].distance
          > nodeInfoList[j].distance) {
          currentNode = g.getNode(j);
        }
      }
    }
    throw new RuntimeException();
  }

  public List<Node> getShortestPath() {
    List<Node> result = new ArrayList<>();
    if (nodeInfoList[destination.getID()] == null) {
      return result;
    }

    result.add(destination);
    while (nodeInfoList[result.get(0).getID()].parent != null) {
      result.add(0, nodeInfoList[result.get(0).getID()].parent);
    }
    return result;
  }

  public Integer getShortestPathLength() {
    if (nodeInfoList[destination.getID()] == null) {
      return 0;
    }
    return nodeInfoList[destination.getID()].distance;
  }
}
