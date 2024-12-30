public class App {
    public static void main(String[] args) {
        NTree tree = new NTree("1");
    
        tree.head.addConnections(new Node("2"), new Node("3"), new Node("4"));

        tree.head.getConnections()[0].addConnections(new Node("2-1"), new Node("2-2"), new Node("2-3"));
        tree.head.getConnections()[1].addConnections(new Node("3-1"), new Node("3-2"));
        tree.head.getConnections()[2].addConnection("4-1");

    }
}

class Node {
    private Node[] connections = new Node[0];
    private Object value;
    private static int hashID = 0;
    private int id;

    @Override public String toString() { return value.toString(); }
    @Override public int hashCode() { return this.id; }

    @Override
    public boolean equals(Object node) {
        if (this == node) {return true;}
        if (!(node instanceof Node)) {return false;}

        Node castedNode = (Node) node;

        return this.value.equals(castedNode.value);
    }

    //CONSTRUCTORS
    public Node(Object value) {
        this.value = value;
        this.id = hashID;
        hashID++;
    }

    public Node() { this(null); }

    //ADD CONNECTIONS
    public void addConnection(Node newNode) { this.connections = this.generateNewArray(this.connections, newNode);}
    public void addConnection(Object value) { this.connections = this.generateNewArray(this.connections, new Node(value)); }
    public void append(Node newNode) { this.connections = this.generateNewArray(this.connections, newNode); }
    public void append(Object value) { this.connections = this.generateNewArray(this.connections, new Node(value)); }

    //Uses varargs to take in list of Nodes to add -- Varargs also works for types of Node[]
    public void addConnections(Node... nodes) { this.connections = this.generateNewArray(this.connections, nodes); }

    //REMOVE CONNECTIONS
    public void removeConnection(int index) { this.connections = this.generateNewArray(this.connections, index); }
    public void pop(int index) { this.connections = this.generateNewArray(this.connections, index); }

    //GETTERS & SETTERS
    public void setValue(Object value) { this.value = value; }
    public Object getValue() { return this.value; }
    public Node[] getConnections() { return this.connections; }

    //INTERNAL METHODS

    //Appends an element
    private Node[] generateNewArray(Node[] arr, Node newNode) {
        Node[] newArr = new Node[arr.length + 1];

        System.arraycopy(arr, 0, newArr, 0, arr.length);

        newArr[arr.length] = newNode;
        return newArr;
    }

    //Pops an element
    private Node[] generateNewArray(Node[] arr, int index) {
        Node[] newArr = new Node[arr.length - 1];

        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i != index) {
                newArr[j++] = arr[i];
            }
        }

        return newArr;
    }

    //Array appendation
    private Node[] generateNewArray(Node[] arr, Node[] nodes) {
        Node[] newArr = new Node[arr.length + nodes.length];

        System.arraycopy(arr, 0, newArr, 0, arr.length);
        System.arraycopy(nodes, 0, newArr, arr.length, nodes.length);

        return newArr;
    }
}

class NTree {
    public final Node head;
    private static int hashID = 0;
    private final int id;

    public NTree(Node head) {
        this.head = head; 
        this.id = hashID;
        hashID++;  //Increment the hashID variable to give each tree a unique ID for hashcodes
    }

    public NTree() { this(null); }
    public NTree(Object value) { this(new Node(value)); }

    //Object hashCode is dictated via the static variable hashID which increments by 1 each instance of NTree giving each consecutive tree a unique id
    @Override public int hashCode() { return this.id; }
    @Override public String toString() { return "Postorder Traversal: " + postorderTraversalAsString(); }

    @Override
    public boolean equals(Object tree) {
        if (tree == this) {return true;}  // These objects have the same HashID
        if (!(tree instanceof NTree)) {return false;}  // Not a valid Object type == Must be (NTree)

        NTree castedTree = (NTree) tree;  //Typecast to a NTree for method accessing

        //Use the postorderTraversalAsString to compare outputs and determine whether they are the same trees
        return this.postorderTraversalAsString().equals(castedTree.postorderTraversalAsString());

    }

    public void postorderTraversal(Node node) {
        if (node == null) {return;}

        for (Node connection : node.getConnections())  {
            postorderTraversal(connection);
        }

        System.out.print(node.getValue() + ", ");

    }
    
    public void postorderTraversal() { this.postorderTraversal(this.head); }

    //String traversals are used primarily for equals() method to compare trees
    public String postorderTraversalAsString(Node node) {
        if (node == null) {return "";}

        String str = "";
        for (Node connection : node.getConnections()) {
            str += postorderTraversalAsString(connection);
        }

        return str + node.getValue() + ", ";
    }

    public String postorderTraversalAsString() { return this.postorderTraversalAsString(this.head); }
}