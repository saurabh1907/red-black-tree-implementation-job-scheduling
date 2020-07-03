import java.util.ArrayList;
import java.util.List;

// Min Heap arranged on basis of lowest building number and ties broken using lower building number

public class ConstructionService {
    public static List<QueueNode> constructionQueue = new ArrayList<>();

    // Min heap insert
    public void addToQueue(QueueNode newBuilding){
        constructionQueue.add(newBuilding); //This step adds new item at end of array
        Integer current = constructionQueue.size() - 1;

        while (current != 0 && parent(current) >=0 && constructionQueue.get(parent(current)).building.greaterThan(constructionQueue.get(current).building)){
            swap(current, parent(current));
            current = parent(current);
        }
    }
    // get lowest element
    public QueueNode peek() {
        return constructionQueue.get(0);
    }

    // remove lowest element
    public QueueNode extractMin(){
        if(constructionQueue.isEmpty())
            throw new RuntimeException("Invalid");
        QueueNode min = peek();
        int lastIndex = constructionQueue.size() - 1;
        constructionQueue.set(0, constructionQueue.get(lastIndex)); //replace root by last element
        constructionQueue.remove(lastIndex);
        heapify(0);
        return  min;
    }

    private void heapify(int i) {
        Integer size = constructionQueue.size();

        Integer leftIndex = left(i);
        Integer rightIndex = right(i);
        Integer min = i;
        Building left = (leftIndex < size)? constructionQueue.get(leftIndex).building : null;
        Building right = (rightIndex < size)? constructionQueue.get(rightIndex).building : null;

        if(left !=null && left.lessThan(constructionQueue.get(min).building)) {
            min = leftIndex;
        }
        if(right!=null && right.lessThan(constructionQueue.get(min).building)) {
            min = rightIndex;
        }
        if(min != i){
            swap(i, min);
            heapify(min);
        }
    }

    public Integer parent(Integer i){
        return (i -1)/2;
    }

    public Integer left(Integer i){
        return i*2 + 1;
    }

    public Integer right(Integer i){ return i*2 + 2; }

    public Boolean isQueueEmpty(){
        return constructionQueue.isEmpty();
    }

    public void swap(Integer index1, Integer index2){
        QueueNode temp = constructionQueue.get(index1);
        constructionQueue.set(index1, constructionQueue.get(index2));
        constructionQueue.set(index2, temp);
    }
}

