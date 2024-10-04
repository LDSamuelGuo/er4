import java.util.ArrayList;
import java.util.List;

public class Controller extends NetObject{
    private boolean tokens = true;
    List<Transition> transitions = new ArrayList<Transition>();
    public Controller(String name, List<Transition> T) {
        super(name);
        this.transitions = T;
    }
    public boolean getTokens() { return tokens; }
    public void setTokens(boolean tokens) { this.tokens = tokens; }
}
