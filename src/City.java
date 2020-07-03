import java.util.ArrayList;
import java.util.List;

public class City {
    RBTNode head;

    // Insert into Red black tree
    void insert(RBTNode newNode){
        RBTNode current = head;
        RBTNode parent = null;
        if(head == null) {
            head = newNode;
            return;
        }

        Integer buildingNum = newNode.getValue();
        while(current != null){
            parent = current;
            if(buildingNum < current.getValue()) {
                current = current.left;
            }
            else if(buildingNum > current.getValue()) {
                current = current.right;
            }
            else
                throw new RuntimeException("duplicate buildingNum");
        }
        newNode.parent = parent;
        newNode.color = Color.RED;
        if(buildingNum < parent.getValue())
            parent.left = newNode;
        else
            parent.right = newNode;

        fixRBTInsertProperty(newNode);
    }

    // Function used when Print operation is used for one Building. Returns the required building
    RBTNode search(Integer buildingNum){
        RBTNode current = head;
        if(head == null)
            throw new RuntimeException("Invalid");

        while(current != null){
            if(buildingNum < current.getValue()) {
                current = current.left;
            }
            else if(buildingNum > current.getValue()) {
                current = current.right;
            }
            else{
                return current;
            }
        }
        return null;
    }

    // Function used when Print in a Range operation is used. Returns all the required building
    List<RBTNode> search(Integer buildingNum1, Integer buildingNum2){
        return searchRangeRecursive(head, buildingNum1, buildingNum2, new ArrayList<RBTNode>());
    }

    List<RBTNode> searchRangeRecursive(RBTNode node, int num1, int num2, List<RBTNode> items) {
        if (node == null)
            return items;

        if (num1 < node.getValue()) {
            searchRangeRecursive(node.left, num1, num2, items);
        }

        if (num1 <= node.getValue() && num2 >= node.getValue() && node.data.rbtPointer!=null) {
            items.add(node);
        }

        if (num2 > node.getValue()) {
            searchRangeRecursive(node.right, num1, num2, items);
        }

        return items;
    }

    // Function having all cases of Red Black Tree Node deletion
    void deleteRBT(RBTNode toBeDeleted){
        RBTNode current = toBeDeleted;
        //reset the back pointer to min heap to null
        current.data.rbtPointer = null;

        if(current.left == null || current.right == null){
            RBTNode remainingTree = null;
            if (current.left == null && current.right == null) {
                remainingTree = null;
            } else if (current.left == null) {
                remainingTree = current.right;
            } else if (current.right == null) {
                remainingTree = current.left;
            }
            if(current.data.rbtPointer!=null) {
                if (remainingTree != null)
                    remainingTree.parent = current.parent;

                if (current.parent != null) {
                    //when remaining tree is not the root
                    if (current.parent.left == current) {
                        current.parent.left = remainingTree;
                    } else {
                        current.parent.right = remainingTree;
                    }
                }

                if (toBeDeleted.color == Color.BLACK)
                    fixRBTDeleteProperty(remainingTree, toBeDeleted.parent);
            }
        }
        else if(current.left != null && current.right != null && current.data.rbtPointer!=null){ //two child
            RBTNode predecessor = inorderPredecessor(current);
            predecessor.data.rbtPointer = current;
            current.data = predecessor.data;
            deleteRBT(predecessor);
        }
    }

    private void fixRBTInsertProperty(RBTNode newNode) {
        if(newNode.parent.color == Color.BLACK )
            return;

        RBTNode parent = newNode.parent;
        RBTNode grandParent = parent.parent;
        RBTNode uncle = (grandParent.left == parent)? grandParent.right : grandParent.left;
        if(uncle!=null && uncle.color == Color.RED){
            recolor(parent, grandParent, uncle );
        }
        else {

            if(grandParent.left == parent && parent.left == newNode){
                //LLb rotation
                rotationLL(newNode, parent, grandParent, "LLb");
            }
            else  if (grandParent.right==parent && parent.right == newNode) {
                //RRb rotation
                rotationRR(newNode,parent, grandParent, "RRb");
            }
             else if(grandParent.left==parent && parent.right == newNode)   {
                //LRb rotation
                 rotationLR(newNode, parent, grandParent, "LRb");
            }
             else {
                //RLb rotation
                rotationRL(newNode, parent, grandParent, "RLb");
            }
        }
    }

    private void fixRBTDeleteProperty(RBTNode remainingTree, RBTNode parent) {
        // deleted node is black
        if(remainingTree!=null && remainingTree.color == Color.RED) {
            remainingTree.color = Color.BLACK;
            return;
        }

        if(parent == null)
            return;

        //remaining tree root is black
        RBTNode sibling = (parent.left == remainingTree)? parent.right : parent.left;
        Integer siblingRedChildrenCount = 0;

        if(sibling.left != null && sibling.left.color == Color.RED)
            siblingRedChildrenCount++;
        if(sibling.right != null && sibling.right.color == Color.RED)
            siblingRedChildrenCount++;

        if(parent.right == remainingTree) {
            if (sibling.color == Color.BLACK) {
                if (siblingRedChildrenCount == 0) {
                    sibling.color = Color.RED;
                    if (parent.color == Color.BLACK)
                        fixRBTDeleteProperty(parent, parent.parent); //extra step needed. deficiency shifted to parent
                    else
                        parent.color = Color.BLACK;
                }
                else if (siblingRedChildrenCount == 1) {
                    if(sibling.left!=null && sibling.left.color == Color.RED){
                        //LL rotation
                        rotationLL(sibling.left, sibling, parent,"Rb1");
                    }
                    else {
                        //LR rotation
                        rotationLR(sibling.right, sibling, parent,"Rb1");
                    }
                }
                else if(siblingRedChildrenCount == 2){
                    //LR rotation
                    rotationLR(sibling.right, sibling, parent, "Rb2");
                }
            }
            else if(sibling.color == Color.RED){
                RBTNode siblingRight = sibling.right;
                int siblingRightChildRedChildrenCount = 0;//No of red children of uncle right child
                if(siblingRight!=null && siblingRight.left != null && siblingRight.left.color == Color.RED)
                    siblingRightChildRedChildrenCount++;
                if(siblingRight!=null && siblingRight.right != null && siblingRight.right.color == Color.RED)
                    siblingRightChildRedChildrenCount++;

                if(siblingRightChildRedChildrenCount == 0){
                    //LL rotation
                    rotationLL(sibling.left, sibling, parent, "Rr0");
                }
                else if(siblingRightChildRedChildrenCount == 1) {
                    if(siblingRight.left != null && siblingRight.left.color == Color.RED){
                        //LR rotation
                        rotationLR(sibling.right, sibling, parent, "Rr1Case1");
                    }
                    else{
                        //complex LR rotation
                        rotationLR(sibling.right.right, sibling.right, parent, "Rr1Case2");
                    }
                }
                else if(siblingRightChildRedChildrenCount == 2){
                    //complex LR rotation
                    rotationLR(sibling.right.right, sibling.right, parent, "Rr2");
                }
            }
        } //SYMMETRIC CASES BELOW
        else if(parent.left == remainingTree){
            if (sibling.color == Color.BLACK) {
                if (siblingRedChildrenCount == 0) {
                    sibling.color = Color.RED;
                    if (parent.color == Color.BLACK)
                        fixRBTDeleteProperty(parent, parent.parent); //extra step. deficiency shifted to parent
                    else
                        parent.color = Color.BLACK;
                }
                else if (siblingRedChildrenCount == 1) {
                    if(sibling.right!=null && sibling.right.color == Color.RED){
                        //RR rotation
                        rotationRR(sibling.right, sibling, parent,"Lb1");
                    }
                    else {
                        //RL rotation
                        rotationRL(sibling.left, sibling, parent,"Lb1");
                    }
                }
                else if(siblingRedChildrenCount == 2){
                    //RL rotation
                    rotationRL(sibling.left, sibling, parent, "Lb2");
                }
            }
            else if(sibling.color == Color.RED){
                RBTNode siblingLeft = sibling.left;
                int siblingLeftChildRedChildrenCount = 0;//No of red children of uncle right child
                if(siblingLeft!=null && siblingLeft.left != null && siblingLeft.left.color == Color.RED)
                    siblingLeftChildRedChildrenCount++;
                if(siblingLeft!=null && siblingLeft.right != null && siblingLeft.right.color == Color.RED)
                    siblingLeftChildRedChildrenCount++;

                if(siblingLeftChildRedChildrenCount == 0){
                    //RR rotation
                    rotationRR(sibling.right, sibling, parent, "Lr0");
                }
                else if(siblingLeftChildRedChildrenCount == 1) {
                    if(siblingLeft.right != null && siblingLeft.right.color == Color.RED){
                        //RL rotation
                        rotationRL(sibling.left, sibling, parent, "Lr1Case1");
                    }
                    else{
                        //complex RL rotation
                        rotationRL(sibling.left.left, sibling.left, parent, "Lr1Case2");
                    }
                }
                else if(siblingLeftChildRedChildrenCount == 2){
                    //complex RL rotation
                    rotationRL(sibling.left.left, sibling.left, parent, "Lr2");
                }
            }
        }
    }

    private void recolor(RBTNode parent, RBTNode grandParent, RBTNode uncle) {
        parent.color = (parent.color == Color.RED)? Color.BLACK : Color.RED;
        grandParent.color = (head == grandParent || grandParent.color == Color.RED)? Color.BLACK : Color.RED;
        uncle.color = (uncle.color == Color.RED)? Color.BLACK : Color.RED;

        //continue re-balancing
        if(grandParent.color == Color.RED)
            fixRBTInsertProperty(grandParent);
    }

    private void rotationLL(RBTNode child, RBTNode parent, RBTNode grandParent, String condition) {
        replace(grandParent, parent);
        grandParent.parent = parent;
        grandParent.left = parent.right;
        if(parent.right!=null)
            parent.right.parent = grandParent;
        parent.right = grandParent;

        switch (condition){
            case "Rb1":
                child.color = Color.BLACK;
            case "Rr0":
                grandParent.color = Color.BLACK;
                if(grandParent.left != null)
                    grandParent.left.color = Color.RED;
                break;
            default:break;
        }
    }

    private void rotationRR(RBTNode child, RBTNode parent, RBTNode grandParent, String condition) {
        replace(grandParent, parent);
        grandParent.parent = parent;
        grandParent.right = parent.left;
        if(parent.left!=null)
            parent.left.parent = grandParent;
        parent.left = grandParent;

        switch (condition){
            case "Lb1":
                child.color = Color.BLACK;
                break;
            case "Lr0":
                grandParent.color = Color.BLACK;
                if(grandParent.right != null)
                    grandParent.right.color = Color.RED;
                break;
            default:break;
        }
    }

    private void rotationLR(RBTNode child, RBTNode parent, RBTNode grandParent, String condition) {
        replace(grandParent, child);

        parent.right = child.left;
        if(child.left!=null)
            child.left.parent = parent;

        if(condition == "Rr1Case2" || condition == "Rr2"){
            //complex rotation
            RBTNode parentParent = parent.parent;
            child.left = parentParent;
            parentParent.parent = child;
        } else {
            child.left = parent;
            parent.parent = child;
        }

        grandParent.left = child.right;
        if(child.right!=null)
            child.right.parent = grandParent;
        child.right = grandParent;
        grandParent.parent = child;

        switch (condition){
            case "Rb1":
                grandParent.color = Color.BLACK;
                break;
            case "Rb2":
                grandParent.color = Color.BLACK;
                break;
            case "Rr1Case1":
                parent.right.color = Color.BLACK;
                break;
            case "Rr1Case2":
            case "Rr2":
                grandParent.color = Color.BLACK;
                break;
            default:break;
        }
    }

    private void rotationRL(RBTNode child, RBTNode parent, RBTNode grandParent, String condition) {
        replace(grandParent, child);

        parent.left = child.right;
        if(child.right!=null)
            child.right.parent = parent;

        if(condition == "Lr1Case2" || condition == "Lr2"){
            //complex rotation
            RBTNode parentParent = parent.parent;
            child.right = parentParent;
            parentParent.parent = child;
        } else {
            child.right = parent;
            parent.parent = child;
        }

        grandParent.right = child.left;
        if(child.left!=null)
            child.left.parent = grandParent;
        child.left = grandParent;
        grandParent.parent = child;

        switch (condition){
            case "Lb1":
                grandParent.color = Color.BLACK;
                break;
            case "Lb2":
                grandParent.color = Color.BLACK;
                break;
            case "Lr1Case1":
                parent.left.color = Color.BLACK;
                break;
            case "Lr1Case2":
            case "Lr2":
                grandParent.color = Color.BLACK;
                break;
            default:break;
        }
    }

    private RBTNode inorderPredecessor(RBTNode current) {
        current = current.left;
        while (current.right!=null){
            current = current.right;
        }
        return current;
    }

    private void replace(RBTNode toBeReplaced, RBTNode replacement){
        if(head == toBeReplaced){
            head = replacement;
            replacement.parent = null;
        }
        else{
            RBTNode node = toBeReplaced.parent;
            if(node.left == toBeReplaced){
                node.left = replacement;
                replacement.parent = node;
            } else {
                node.right = replacement;
                replacement.parent = node;
            }
        }
        Color temp = replacement.color;
        replacement.color = toBeReplaced.color;
        toBeReplaced.color = temp;
    }
}
