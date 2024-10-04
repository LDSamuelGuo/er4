public class Arc extends NetObject {

    Place place;
    Transition transition;
    Direction direction;
    
    enum Direction {
        
        /**
         * check if the given direction arc (can) fire
         */
        
        PLACE_TO_TRANSITION {
            @Override
            public boolean canFire(Place p) {
                return p.hasTrainToken();
            }

            @Override
            public void fire(Place p, Transition t) {
                t.setTrain(p.getTrain());
                p.removeTokens();
            }

        },
        
        TRANSITION_TO_PLACE {
            @Override
            public boolean canFire(Place p) {
                return p.hasNoTrainToken();
            }

            @Override
            public void fire(Place p, Transition t) {
                p.setTokens(t.getTrain());
            }

        };

        public abstract boolean canFire(Place p);

        public abstract void fire(Place p, Transition t);

    }
    
    private Arc(String name, Direction d, Place p, Transition t) {
        super(name);
        this.direction = d;
        this.place = p;
        this.transition = t;
    }

    protected Arc(String name, Place p, Transition t) {
        this(name, Direction.PLACE_TO_TRANSITION, p, t);
        t.addIncoming(this);
    }

    protected Arc(String name, Transition t, Place p) {
        this(name, Direction.TRANSITION_TO_PLACE, p, t);
        t.addOutgoing(this);
    }

    public boolean canFire() {
        return direction.canFire(place);
    }
    
    public void fire() {
        this.direction.fire(place, transition);
        System.out.println(this.getName() + " fire");
    }

    public Place getPlace() { return this.place; }
}