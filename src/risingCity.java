import java.io.*;
import java.util.ArrayList;

public class risingCity {

    private static final String REGEX = "[^\\w\\s]";
    private static BufferedReader reader = null;
    private  static ConstructionService constructionService = new ConstructionService();
    private static OperationsService operationsService = new OperationsService(constructionService);

    public static void main(String[] args) throws IOException {
        String inputFile = args[0];
        ArrayList<String[]> operations = readInput(inputFile);


        //Initializing variables for main Loop
        int globalCounter = 0;
        int nextOperationIndex = 0;
        Boolean workInProgress = false;
        QueueNode presentJob = null;
        int daysWorked = 0;

        //Loop until there is no building to work on, No building under construction and all Input operations are read
        do {
            if (nextOperationIndex < operations.size()) {
                String[] nextOperation = operations.get(nextOperationIndex);
                if (Integer.valueOf(nextOperation[0]) == globalCounter) {
                    if (nextOperation[1].startsWith("Print")) {
                        if (nextOperation.length == 3)
                            operationsService.print(Integer.valueOf(nextOperation[2]));
                        else
                            operationsService.print(Integer.valueOf(nextOperation[2]), Integer.valueOf(nextOperation[3]));
                    } else {
                        operationsService.insert(Integer.valueOf(nextOperation[2]), Integer.valueOf(nextOperation[3]));
                    }
                    nextOperationIndex++;
                }
            }

            if (workInProgress) {
                if (presentJob.building.executed_time == presentJob.building.total_time) {
                    // completion day. Delete from tree and reset workInProgress and daysWorked
                    operationsService.printCompletion(presentJob.building, globalCounter);
                    workInProgress = false;
                    daysWorked = 0;
                    //RBT remove
                    operationsService.rbt.deleteRBT(presentJob.rbtPointer);
                } else if (daysWorked == 5) {
                    // Building wasn't complete but worked for 5 days. Add it back to queue and take new building to work on
                    constructionService.addToQueue(presentJob);
                    workInProgress = false;
                    daysWorked = 0;
                }
            }

            if (!workInProgress && !constructionService.isQueueEmpty()) {
                // Get building with lowest execution time
                presentJob = constructionService.extractMin();
                workInProgress = true;
            }

            globalCounter++;
            if(workInProgress) {
                daysWorked++;
                presentJob.building.executed_time += 1;
            }
        }
        while (!constructionService.isQueueEmpty() || workInProgress || nextOperationIndex < operations.size());
    }

    private static ArrayList<String[]> readInput(String inputFile) throws IOException {
        // Reads Input file given by user using bufferedReader and splits each instruction logically
        ArrayList<String[]> operations = new ArrayList<>();
        reader = new BufferedReader(new FileReader(inputFile));
        String input;

        while ((input = reader.readLine()) != null) {
            String[] tokens = input.split(REGEX);
            for(int i=0;i<tokens.length;i++)
                tokens[i] = tokens[i].trim();
            operations.add(tokens);
        }
        return operations;
    }
}
