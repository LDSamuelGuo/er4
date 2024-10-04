import java.util.ArrayList;
import java.util.List;

public class Train {
    private String train_name;
    private int entryID;
    private int destID;
    private Place currPos;
    private List<String> route = new ArrayList<String>();
    public Train(String trainName, int entryID, int destID, Place currPos) {
        this.train_name = trainName;
        this.entryID = entryID;
        this.currPos = currPos;
        this.destID = destID;
        this.route.add("track "+entryID);
    }
    public String getTrainName() { return train_name; }
    public int getEntryID() { return entryID; }
    public String getDestName() { return "track "+destID; }
    public Place getCurrPos() { return currPos; }
    public List<String> getRoute() { return route; }
    public void setCurrPos(Place currPos) { this.currPos = currPos; }
    public void addRoute(String placeName) { route.add(placeName); }

}
