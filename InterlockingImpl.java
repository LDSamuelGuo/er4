import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InterlockingImpl implements Interlocking {
    private List<String> trainNames = new ArrayList<String>();
    private List<Train> trains = new ArrayList<Train>();
    private Network net = new Network();
    /**
     * Adds a train to the rail corridor.
     *
     * @param trainName               A String that identifies a given train. Cannot be the same as any other train present.
     * @param entryTrackSection       The id number of the track section that the train is entering into.
     * @param destinationTrackSection The id number of the track section that the train should exit from.
     * @throws IllegalArgumentException if the train name is already in use, or there is no valid path from the entry to the destination
     * @throws IllegalStateException    if the entry track is already occupied
     */
    @Override
    public void addTrain(String trainName, int entryTrackSection, int destinationTrackSection) throws IllegalArgumentException, IllegalStateException {

        if (trainNames.contains(trainName)) { throw new IllegalArgumentException(); }

        Place p = findPlace(entryTrackSection);
        if (p == null) { throw new IllegalArgumentException(); }
        if (!p.getDestName().contains(String.valueOf(destinationTrackSection))) { throw new IllegalArgumentException(); }
        if (p.getTrain() != null) { throw new IllegalStateException(); }
        Place entryPlace = findPlace(entryTrackSection);
        Train train = new Train(trainName, entryTrackSection, destinationTrackSection, entryPlace);
        entryPlace.setTokens(train);
        trainNames.add(trainName);
        trains.add(train);
    }

    /**
     * The listed trains proceed to the next track section.
     * Trains only move if they are able to do so, otherwise they remain in their current section.
     * When a train reaches its destination track section, it exits the rail corridor next time it moves.
     *
     * @param trainNames The names of the trains to move.
     * @return The number of trains that have moved.
     * @throws IllegalArgumentException if the train name does not exist or is no longer in the rail corridor
     */
    @Override
    public int moveTrains(String[] trainNames) throws IllegalArgumentException {
        List<String> tNames = Arrays.asList(trainNames);
        if (!this.trainNames.containsAll(tNames)) { throw new IllegalArgumentException(); }
        List<Train> trs = new ArrayList<Train>();
        for (String tName : tNames) { trs.add(findTrain(tName)); }

        ExecutorService executor = Executors.newFixedThreadPool(tNames.size());
        int movedTrains = 0;
        try {
            boolean onTrack;
            do {
                onTrack = true;
                movedTrains = Math.max(moveEachTrain(tNames, executor), movedTrains);
                tNames = new ArrayList<String>();
                Iterator<Train> iterator = trs.iterator();
                while (iterator.hasNext()) {
                    Train t = iterator.next();
                    if (!t.getCurrPos().getName().startsWith("track")) {
                        onTrack = false;
                        tNames.add(t.getTrainName());
                    }
                    if (t.getCurrPos().getName().equals(t.getDestName())) {
                        tNames.remove(t.getTrainName());
                        System.out.println(t.getTrainName()+" exit from "+t.getDestName());
                        this.trainNames.remove(t.getTrainName());
                        this.trains.remove(t);
                        t.getCurrPos().removeTokens();
                        iterator.remove();
                    }
                }
            } while (!onTrack);
        } finally {
            executor.shutdown();
        }
        return movedTrains;
    }

//    private int moveEachTrain(List<String> tNames, ExecutorService executor) {
//        List<Transition> ts = checkFire(tNames);
//        int movedTrains = ts.size();
//        for (Transition t : ts) {
//            Future<?> future = executor.submit(() -> fireTransition(t));
////            fireTransition(t);
//        }
//        return movedTrains;
//    }

    private int moveEachTrain(List<String> tNames, ExecutorService executor) {
        List<Transition> ts = checkFire(tNames);
        int movedTrains = ts.size();
        List<Callable<Void>> callableTasks = new ArrayList<>();
        for (Transition t : ts) {
            callableTasks.add(() -> {
                fireTransition(t);
                return null;
            });
        }

        try {
            List<Future<Void>> futures = executor.invokeAll(callableTasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return movedTrains;
    }

    private void fireTransition(Transition t) {
        if (t.canFire()) {
            t.fire();
            Place pre_p = t.getIncoming().getFirst().getPlace();
            Place post_p = t.getOutgoing().getFirst().getPlace();
            post_p.getTrain().addRoute(post_p.getName());
            post_p.getTrain().setCurrPos(post_p);
        }
    }

    private List<Transition> checkFire(List<String> tNames) {
        List<Transition> ts = new ArrayList<Transition>();
        List<Train> trs = new ArrayList<Train>();
        List<Place> ps = new ArrayList<Place>();
        for (String tName : tNames) {
            Train t = findTrain(tName);
            ps.add(t.getCurrPos());
        }
        for (Transition t : net.pn.transitions) {
            if (!t.canFire()) { continue; }
            List<Arc> incomingArcs = t.getIncoming();
            List<Arc> outgoingArcs = t.getOutgoing();
            for (Arc arc : incomingArcs) {
                if (ps.contains(arc.getPlace())) {
                    Train curTrain = arc.getPlace().getTrain();
                    Place curPlace = curTrain.getCurrPos();
                    Place nextPlace = outgoingArcs.getFirst().getPlace();
                    if(!curTrain.getRoute().contains(nextPlace.getName()) &&
                            (curTrain.getDestName().equals(nextPlace.getName()) ||
                        (nextPlace.getDestName()==null ||
                                nextPlace.getDestName().contains(curTrain.getDestName())))) {
                        ts.add(t);
                    }
                }
            }
        }
        return ts;
    }

    /**
     * Returns the name of the Train currently occupying a given track section
     *
     * @param trackSection The id number of the section of track.
     * @return The name of the train currently in that section, or null if the section is empty/unoccupied.
     * @throws IllegalArgumentException if the track section does not exist
     */
    @Override
    public String getSection(int trackSection) throws IllegalArgumentException {
        Place p = findPlace(trackSection);
        if (p == null) { throw new IllegalArgumentException(); }
        if (p.getTrain() == null) { return "No train on this track"; }
        return p.getTrain().getTrainName();
    }

    /**
     * Returns the track section that a given train is occupying
     *
     * @param trainName The name of the train.
     * @return The id number of section of track the train is occupying, or -1 if the train is no longer in the rail corridor
     * @throws IllegalArgumentException if the train name does not exist
     */
    @Override
    public int getTrain(String trainName) throws IllegalArgumentException {
        if (!trainNames.contains(trainName)) { throw new IllegalArgumentException(); }
        Train t = findTrain(trainName);
        String placeName = t.getCurrPos().getName();
        return Integer.parseInt(placeName.substring(placeName.length()-1));
    }

    public Place findPlace(int trackID) {
        for (Place p : net.pn.places) {
            if (p.getName().equals("track " + trackID)) {
                return p;
            }
        }
        return null;
    }

    public Transition findTransition(String name) {
        for (Transition t : net.pn.transitions) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public Train findTrain(String trainName) {
        for (Train train : trains) {
            if (train.getTrainName().equals(trainName)) { return train; }
        }
        return null;
    }

}
