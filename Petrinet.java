import java.util.ArrayList;
import java.util.List;

public class Petrinet extends NetObject {

    private static final String nl = "\n";
    List<Place> places              = new ArrayList<Place>();
    List<Transition> transitions    = new ArrayList<Transition>();
    List<Arc> arcs                  = new ArrayList<Arc>();
    List<Controller> controllers    = new ArrayList<Controller>();

    public Petrinet(String name) {
        super(name);
    }

    public void add(NetObject o) {
        if (o instanceof Arc) {
            arcs.add((Arc) o);
        } else if (o instanceof Place) {
            places.add((Place) o);
        } else if (o instanceof Transition) {
            transitions.add((Transition) o);
        }
    }
    
    public List<Transition> getTransitionsAbleToFire() {
        ArrayList<Transition> list = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.canFire()) {
                list.add(t);
            }
        }
        return list;
    }
    
    public Transition transition(String name) {
        Transition t = new Transition(name);
        transitions.add(t);
        return t;
    }
    
    public Place place(String name) {
        Place p = new Place(name);
        places.add(p);
        return p;
    }
    
    public Place place(String name, String destName) {
        Place p = new Place(name, destName);
        places.add(p);
        return p;
    }
    
    public Arc arc(String name, Place p, Transition t) {
        Arc arc = new Arc(name, p, t);
        arcs.add(arc);
        return arc;
    }
    
    public Arc arc(String name, Transition t, Place p) {
        Arc arc = new Arc(name, t, p);
        arcs.add(arc);
        return arc;
    }

    public Controller controller(String name, List<Transition> t) {
        Controller c = new Controller(name, t);
        controllers.add(c);
        return c;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Arc> getArcs() { return arcs; }

    public List<Controller> getControllers() { return controllers; }
}