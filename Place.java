public class Place extends NetObject {

    public static final int MAXTOKENS = 1;
    
    private int trainTokens = 0;
    private int noTrainTokens = MAXTOKENS;
    private Train train = null;
    private String destName = null;

    protected Place(String name) {
        super(name);
        this.train = null;
    }

    protected Place(String name, String destName) {
        this(name);
        this.destName = destName;
    }

    public boolean hasTrainToken() {
        return (trainTokens > 0);
    }

    public boolean hasNoTrainToken() {
        return (noTrainTokens > 0);
    }

    public Train getTrain() {
        return this.train;
    }

    public void setTrain(Train t) { this.train = t; }
    
    public String getTokens() {
        return "Train Tokens = " + this.trainTokens +
               "No_Train Tokens = " + this.noTrainTokens;
    }

    public void setTokens(Train t) {
        this.trainTokens = 1;
        this.noTrainTokens = 0;
        this.train = t;
    }

    public void removeTokens() {
        this.trainTokens = 0;
        this.noTrainTokens = 1;
        this.train = null;
    }

    public String getDestName() { return destName; }
}