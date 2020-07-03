import java.io.*;
import java.util.List;

//  Interprets every Instruction and prints when required. Offers function to insert, and print buildings

public class OperationsService {
    private static final String OUTPUT = "output_file.txt";

    ConstructionService constructionService;
    City rbt = new City();

    public OperationsService(ConstructionService constructionService) {
        this.constructionService = constructionService;
        initializePrintWriter();
    }

    // Interprets "Insert" command. insert element in min heap and red black tree
    // Heap has pointer to red black tree and red black tree has pointer to heap
    public void insert(Integer buildingNum, Integer total_time){
        Building building = new Building(buildingNum,total_time);
        RBTNode rbtNode = new RBTNode(null, null, null, null, Color.BLACK);
        QueueNode newBuilding = new QueueNode(building, rbtNode);
        rbtNode.data = newBuilding;
        constructionService.addToQueue(newBuilding);
        rbt.insert(rbtNode);
    }

    //Prints bulding if exist else print (0,0,0)
    public void print(Integer buildingNum){
        RBTNode node = rbt.search(buildingNum);
        if(node == null){
            //Building does not exist
            System.out.println("(0,0,0)");
        } else {
            printBuilding(node.data.building);
            System.out.println();
        }
    }

    //Print Range of building between given arguements
    public void print(Integer buildingNum1, Integer buildingNum2){
        List<RBTNode> items = rbt.search(buildingNum1, buildingNum2);
        if(items.size() == 0 ){
            //No Buildings in the range exist
            System.out.println("(0,0,0)");
        }
        else {
            for (int i = 0; i < items.size(); i++) {
                printBuilding(items.get(i).data.building);
                if (i + 1 != items.size()) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }

    public void printCompletion(Building building, int globalCounter) {
        System.out.println("("+building.buildingNum+","+globalCounter+")");
    }

    private void printBuilding(Building building) {
        System.out.print("("+building.buildingNum+","+building.executed_time+","+building.total_time+")");
    }

    private void initializePrintWriter() {
        try{
            PrintStream out = new PrintStream((new FileOutputStream(OUTPUT)));
            System.setOut(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
