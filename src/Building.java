public class Building {
    public int buildingNum;
    public int executed_time = 0;
    public int total_time;

    public Building(Integer buildingNum, Integer total_time) {
        this.buildingNum = buildingNum;
        this.total_time = total_time;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    // Utility function to check current object is greater than parameter object.
    // Lowest chosen on basis of lowest building number and ties broken using lower building number
    public Boolean greaterThan(Building target){
        return this.executed_time > target.executed_time || (this.executed_time == target.executed_time && this.buildingNum > target.buildingNum);
    }

    // Utility function to check current object is smaller than parameter object.
    public Boolean lessThan(Building target){
        return this.executed_time < target.executed_time || (this.executed_time == target.executed_time && this.buildingNum < target.buildingNum);
    }
}
