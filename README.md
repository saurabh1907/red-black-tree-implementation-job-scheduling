<h1>Red Black Tree Implementation | Job Scheduling</h1>

## Objective: Implementation of a City Development application called Rising City using Priority Queue and Red Black Tree

## Problem Statement :
Wayne Enterprises is developing a new city. They are constructing many buildings and plan to use software to keep track of all buildings under construction in this new city. A building record has the following fields:
buildingNum: unique integer identifier for each building.
executed_time: total number of days spent so far on this building.
total_time: the total number of days needed to complete the construction of the building.
The needed operations are:
1. Print (buildingNum) prints the triplet buildingNume,executed_time,total_time.
2. Print (buildingNum1, buildingNum2) prints all triplets bn, executed_tims, total_time for which buildingNum1 <= bn <= buildingNum2.
3. Insert (buildingNum,total_time) where buildingNum is different from existing building
numbers and executed_time = 0.

A min heap is used to store (buildingNum,executedTime,totalTime) triplets ordered by executedTime. A Red Black Tree (RBT) is used store (buildingNum,executedTime,totalTime) triplets ordered by buildingNum.

### Constraints
Wayne Enterprises works on one building at a time. When it is time to select a building to work on, the building with the lowest executedTime (ties are broken by selecting the building with the lowest buildingNum) is selected. The selected building is worked on until complete or for 5 days, whichever happens first. If the building completes during this period its number and day of completion is output and it is removed from the data structures. Otherwise, the building’s executedTime is updated. In both cases, Wayne Construction selects the next building to work on using the selection rule.

## Development Environment Used:
- Language : Java
- Data Structures : MinHeap, Red Black Tree

## Steps to Compile -
1. Copy input file to main directory
2. Run make
3. Run java risingCity input_filename.txt
4. Output generated in output_file.txt
## Complexity Analysis-
- n is number of active buildings and S is the number of triplets printed
- PrintBuilding (buildingNum) : O(log(n)) and
- PrintBuilding (buildingNum1 1, buildingNum2): O(log(n)+S)
- Insertion O(log(n) + log(n)). Due to insertion in heap followed by red black tree
- Deletion- O(log(n) + log(n)). A pointer of Red black tree in heap element ensures the complexity is log(n)

## Implementation Details
- Four Entity classes are used-
1. Building
  -  buildingNum
  - executed_time
  - total_time
  - QueueNode
  - Building
  - rbtPointer
2. RBTNode
  - RBTNode parent
  - QueueNode data
  - RBTNode left
  - RBTNode right
  - Color color
3. Color (Enum) Red or Black

## Classes-
- risingCity - contains the main method and logic for looping over instructions and waiting till all buildings are completed
- OperationService- used for interacting with other components. Contains the logic to process input instructions
- ConstructionService- the min heap which stores the building with lowest execution time at the root
- City- contains information of all building to be construction using Red black tree for efficient searching

## The Application Flow and Salient Features of the Project -
- The construction service provides the next building to be worked on. Once its finished its removed from queue as well as red black tree.
- All Insertion and deletion cases in red black tree are handled by referring the class notes and ppt provided by Dr. Sahni
- At any point a building current status can be checked using print operation which searches in red black tree
- To find information of buildings in a range, a search is performed in RBT and it enters only those subtrees that may possibly have a building in the specified range.
- A lot of reusable utility functions are used and red black tree cases are handled by reusing the right rotation logic for any cases
