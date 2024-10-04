public class TestInterlocking {
    public static void main(String[] args) {
        InterlockingImpl i = new InterlockingImpl();
        i.addTrain("TrainA", 1, 9);
        i.addTrain("TrainB", 9, 2);
        i.addTrain("TrainC", 3, 4);
        String[] trainNames = {"TrainA", "TrainB", "TrainC"};
        int n = i.moveTrains(trainNames);
//        Place p = i.findPlace(4);
//        System.out.println(p.getTokens());
        System.out.println(n);
        int section1 = i.getTrain("TrainA");
        System.out.println("TrainA is on track: "+section1);
        int section2 = i.getTrain("TrainB");
        System.out.println("TrainB is on track: "+section2);
        i.addTrain("TrainC", 3, 4);

        String[] trainNames2 = {"TrainA", "TrainB", "TrainC"};
        n = i.moveTrains(trainNames2);
        System.out.println(n);
//        section1 = i.getTrain("TrainA");
//        System.out.println("TrainA is on track: "+section1);
//        section2 = i.getTrain("TrainB");
//        System.out.println("TrainB is on track: "+section2);
//        int section3 = i.getTrain("TrainC");
//        System.out.println("TrainC is on track "+section3);
    }
}
