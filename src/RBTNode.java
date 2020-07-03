public class RBTNode {
    RBTNode parent;
    QueueNode data;
    RBTNode left;
    RBTNode right;
    Color color;

    public RBTNode(RBTNode parent, QueueNode data, RBTNode left, RBTNode right, Color color) {
        this.parent = parent;
        this.data = data;
        this.left = left;
        this.right = right;
        this.color = color;
    }

    int getValue(){
        return data.building.getBuildingNum();
    }
}


