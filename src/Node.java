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

    public void addChild(Node node){
        edges.put(node.edge, node);
    }

    public int getDepth(){
        return label.length();
    }
}
