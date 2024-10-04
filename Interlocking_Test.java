public class Interlocking_Test {
    InterlockingImpl i = new InterlockingImpl();
    @Test
    public void testAddTrain() {
        i.addTrain("TrainA", 1, 8);
        Train t = i.findTrain("TrainA");
        assertEquals("TrainA", t.getTrainName());
        assertEquals(1, t.getEntryID());
        assertEquals("track 1", t.getCurrPos().getName());
        assertEquals("track 8", t.getDestName());
    }

    @Test
    public void testMoveTrain() {
        i.addTrain("TrainA", 1, 8);
        String[] trainNames = {"TrainA"};
        i.moveTrains(trainNames);
        Train t = i.findTrain("TrainA");
        assertEquals("track 5", t.getCurrPos().getName());
    }

    @Test
    public void testGetTrain() {
        i.addTrain("TrainA", 1, 8);
        String[] trainNames = {"TrainA"};
        i.moveTrains(trainNames);
        int ID = i.getTrain("TrainA");
        assertEquals(5, i.getTrain("TrainA"));
    }

    @Test
    public void testGetSection() {
        i.addTrain("TrainA", 1, 8);
        String[] trainNames = {"TrainA"};
        i.moveTrains(trainNames);
        String name= i.getSection(5);
        assertEquals("TrainA", name);
    }
}
