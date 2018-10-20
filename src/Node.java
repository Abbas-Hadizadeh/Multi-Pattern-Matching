import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abbas Hadizadeh on 10/19/2018.
 */
class Node {
    char edge;
    boolean isPattern;
    Map<Character, Node> edges;
    Node parent, failureLink;
    String label;

    public Node(char edge, Node parent){
        this.edge = edge;
        isPattern = false;
        edges = new HashMap();
        this.parent = parent;
        failureLink = null;
        if(parent == null)
            label = "";
        else
            label = parent.label + edge;
    }

    public Node addEdge(char child){
        if(edges.containsKey(child))
            return edges.get(child);
        Node newChild = new Node(child, this);
        edges.put(child, newChild);
        return newChild;
    }

    public int getDepth(){
        return label.length();
    }
}
