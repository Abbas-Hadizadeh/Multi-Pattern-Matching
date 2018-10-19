import java.util.*;

/**
 * Created by Abbas Hadizadeh on 10/19/2018.
 */
class KeywordTree {
    Set<String> patterns;
    Node root;

    public KeywordTree(){
        patterns = new HashSet();
    }

    public void addPattern(String pattern){
        patterns.add(pattern);
    }

    public void constructKeywordTree(){
        root = new Node('0', null);
        Node node;
        for(String pattern : patterns){ //adding each pattern to the tree
            node = root;
            for(int i=0; i< pattern.length(); i++){
                node = addEdge(node, pattern.charAt(i));
            }
            node.isPattern = true;  //the last character of the pattern
        }
        addFailureLinks();
    }

    private Node addEdge(Node parent, char edge){
        if(parent.edges.containsKey(edge))
            return parent.edges.get(edge);
        Node child = new Node(edge, parent);
        parent.addChild(child);
        return child;
    }

    private void addFailureLinks(){ //adding failure links to search faster
        Queue<Node> queue = new LinkedList();
        for(char c : root.edges.keySet())
            queue.add(root.edges.get(c));
        Node node, nv;
        while(!queue.isEmpty()){
            node = queue.poll();
            node.failureLink = root;
            nv = node.parent;
            while(nv!=root) {
                nv = nv.failureLink;
                if (nv.edges.containsKey(node.edge)) {
                    node.failureLink = nv.edges.get(node.edge);
                    break;
                }
            }
            for(char c : node.edges.keySet())
                queue.add(node.edges.get(c));
        }
    }

    public TreeMap<Pair, String> searchText(String text){    //search the text for occurrences of any of the patterns
        TreeMap<Pair, String> out = new TreeMap(new Comparator<Pair>() {    //using a TreeMap to store occurrences in order
            @Override
            public int compare(Pair a, Pair b) {
                if(a.begin == b.begin)
                    return (new Integer(a.end)).compareTo(b.end);
                return a.begin<b.begin?-1:1;
            }
        });
        Node node = root;
        char c;
        for(int i=0; i<text.length(); i++){
            c = text.charAt(i);
            if(node.edges.containsKey(c)){
                node = node.edges.get(c);
            }
            else{
                while(node!=root){
                    node = node.failureLink;
                    if(node.edges.containsKey(c)){
                        node = node.edges.get(c);
                        break;
                    }
                }
                if(node == root)
                    continue;
            }
            if(node.isPattern)     //a pattern is matched
                out.put(new Pair(i-node.label.length()+2, i+1), node.label);    //store 1-based begin and end of the pattern
        }
        return out;
    }

    public String toString(){   //printing the tree
        StringBuilder out = new StringBuilder();
        Stack<Node> stack = new Stack();
        for(char c : root.edges.keySet())
            stack.push(root.edges.get(c));
        Node node;
        char[] emptySpace;
        boolean newLine = false;
        while(!stack.isEmpty()){
            node = stack.pop();
            if(newLine) {
                emptySpace = new char[(node.getDepth()-1) * 2];
                Arrays.fill(emptySpace, ' ');
                out.append(emptySpace);
            }
            out.append(node.edge);
            if(node.edges.size() == 0) {
                out.append('\n');
                newLine = true;
            }
            else{
                out.append(' ');
                newLine = false;
                for(char c : node.edges.keySet())
                    stack.push(node.edges.get(c));
            }
        }

        if(out.charAt(out.length()-1) == '\n')
            out.deleteCharAt(out.length() - 1);
        return out.toString();
    }

}
