public class QueueNode {
    Building building;
    RBTNode rbtPointer;

    public QueueNode(Building building, RBTNode rbtPointer) {
        this.building = building;
        this.rbtPointer = rbtPointer;
    }
}
