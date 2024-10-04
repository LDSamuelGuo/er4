import java.util.ArrayList;
import java.util.List;

public class Transition extends NetObject{

    protected Transition(String name) {
        super(name);
    }

    private List<Arc> incoming = new ArrayList<Arc>();
    private List<Arc> outgoing = new ArrayList<Arc>();
    private Train train = null;
    private Controller controller = null;
    
    public boolean canFire() {
        boolean canfire = true;
        boolean controllerTokens = true;
        if (this.isHasController()) {
            controllerTokens = this.controller.getTokens();
        }
        
        for (Arc arc : incoming) {
            canfire = controllerTokens & arc.canFire();
//            if (canfire) { System.out.println("Incoming arc: "+arc.getName());}
        }
        
        for (Arc arc : outgoing) {
            canfire = controllerTokens & arc.canFire();
//            if (canfire) { System.out.println("Outgoing arc: "+arc.getName());}
        }
        return canfire;
    }
    
    /**
     * fire the transition
     */
    public void fire() {
        if (this.controller != null) { controller.setTokens(false); }
        for (Arc arc : incoming) {
            arc.fire();
        }
        
        for (Arc arc : outgoing) {
            arc.fire();
        }
        if (this.controller != null) { controller.setTokens(true); }

    }

    public void setTrain(Train t) { this.train = t; }
    public Train getTrain() { return this.train; }

    /**
     * @param arc add incoming arc to selected transition
     */
    public void addIncoming(Arc arc) {
        this.incoming.add(arc);
    }
    public List<Arc> getIncoming() { return this.incoming; }
    
    /**
     * @param arc add outgoing arc to selected transition
     */
    public void addOutgoing(Arc arc) {
        this.outgoing.add(arc);
    }
    public List<Arc> getOutgoing() { return this.outgoing; }

    public boolean isHasController() {
        return !(controller == null);
    }

    public void setController(Controller controller) { this.controller = controller; }
}