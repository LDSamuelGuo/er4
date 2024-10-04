import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
* build Petri net
 */
public class Network {

    Petrinet pn = new Petrinet("Railway Model");
    List<Arc> arcs = new ArrayList<Arc>();
    public Network() {

        Place track_1 = pn.place("track 1", "track 8/track 9");
        Place track_2 = pn.place("track 2");
        Place track_3 = pn.place("track 3", "track 4/track 11");
        Place track_4 = pn.place("track 4", "track 3");
        Place track_5 = pn.place("track 5");
        Place track_6 = pn.place("track 6");
        Place track_7 = pn.place("track 7", "track 11/track 3");
        Place track_8 = pn.place("track 8", "track 8");
        Place track_9 = pn.place("track 9", "track 9/track 2");
        Place track_10 = pn.place("track 10", "track 2");
        Place track_11 = pn.place("track 11", "track 3");
        Place r1_left = pn.place("r1 left");
        Place r1_right = pn.place("r1 right");
        Place r1_up = pn.place("r1 up");
        Place r1_down = pn.place("r1 down");
        Place r2_left = pn.place("r2 left");
        Place r2_right = pn.place("r2 right");
        Place r2_up = pn.place("r2 up");
        Place r2_down = pn.place("r2 down");
        Place r3_up = pn.place("r3 up", "track 4/track 3");
        Place inter_1 = pn.place("inter 1");
        Place inter_2 = pn.place("inter 2");
        Place inter_3 = pn.place("inter 3");

        Transition _1_r1_left = pn.transition("1-r1 left");
        Transition _r1_left_r1_right = pn.transition("r1 left-r1 right");
        Transition _r1_right_5 = pn.transition("r1 right-5");
        Transition _5_inter_1 = pn.transition("5-inter 1");
        Transition _inter_1_8 = pn.transition("inter 1-8");
        Transition _inter_1_9 = pn.transition("inter 1-9");

        Transition _9_inter_2 = pn.transition("9-inter 2");
        Transition _10_inter_2 = pn.transition("10-inter 2");
        Transition _inter_2_6 = pn.transition("2-inter 6");
        Transition _6_r2_right = pn.transition("6-r2 right");
        Transition _r2_right_r2_left = pn.transition("r2 right-r2 left");
        Transition _r2_left_2 = pn.transition("r2 left-2");

        Transition _3_inter_3 = pn.transition("3-inter 3");
        Transition _inter_3_r3_up = pn.transition("inter 3-r3 up");
        Transition _r3_up_r2_down = pn.transition("r3 up-r2 down");
        Transition _r2_down_r2_up = pn.transition("r2 down-r2-up");
        Transition _r2_up_r1_down = pn.transition("r2 up-r1 down");
        Transition _r1_down_r1_up = pn.transition("r1 down-r1 up");
        Transition _r1_up_4 = pn.transition("r1 up-4");

        Transition _inter_3_7 = pn.transition("inter 3-7");
        Transition _7_11 = pn.transition("7-11");

        Transition _11_7 = pn.transition("11-7");
        Transition _7_inter_3 = pn.transition("7-inter 3");


        Transition _4_r1_up = pn.transition("4-r1 up");
        Transition _r1_up_r1_down = pn.transition("r1 up-r1 down");
        Transition _r1_down_r2_up = pn.transition("r2 down-r2 up");
        Transition _r2_up_r2_down = pn.transition("r2 up-r2 down");
        Transition _r2_down_r3_up = pn.transition("r2 down-r3 up");
        Transition _r3_up_inter_3 = pn.transition("r3 up-inter 3");
        Transition _inter_3_3 = pn.transition("inter 3-3");

        List<Transition> t1 = new ArrayList<Transition>() {{
            add(_r1_up_r1_down);
            add(_r1_down_r1_up);
            add(_r1_left_r1_right);
        }};
        Controller cont_1 = pn.controller("cont 1", t1);
        addController(cont_1, t1);

        List<Transition> t2 = new ArrayList<Transition>() {{
            add(_r2_down_r2_up);
            add(_r2_up_r2_down);
            add(_r2_right_r2_left);
        }};
        Controller cont_2 = pn.controller("cont 2", t2);
        addController(cont_2, t2);

        List<Transition> t3 = new ArrayList<Transition>() {{
            add(_9_inter_2);
            add(_10_inter_2);
        }};
        Controller cont_3 = pn.controller("cont 3", t3);
        addController(cont_3, t3);

        List<Transition> t4 = new ArrayList<Transition>() {{
            add(_inter_3_r3_up);
            add(_r3_up_inter_3);
            add(_inter_3_7);
            add(_7_inter_3);
        }};
        Controller cont_4 = pn.controller("cont 4", t4);
        addController(cont_4, t4);

        List<Place> road_p = new ArrayList<Place>() {{
            add(track_1);
            add(r1_left);
            add(r1_right);
            add(track_5);
            add(inter_1);
            add(track_8);
        }};
        List<Transition> road_t = new ArrayList<Transition>() {{
            add(_1_r1_left);
            add(_r1_left_r1_right);
            add(_r1_right_5);
            add(_5_inter_1);
            add(_inter_1_8);
        }};
        addArcs(road_p, road_t);
        addArc(inter_1, track_9, _inter_1_9);

        road_p = new ArrayList<Place>() {{
            add(track_9);
            add(inter_2);
            add(track_6);
            add(r2_right);
            add(r2_left);
            add(track_2);
        }};
        road_t = new ArrayList<Transition>() {{
            add(_9_inter_2);
            add(_inter_2_6);
            add(_6_r2_right);
            add(_r2_right_r2_left);
            add(_r2_left_2);
        }};
        addArcs(road_p, road_t);
        addArc(track_10, inter_2, _10_inter_2);

        road_p = new ArrayList<Place>() {{
            add(track_3);
            add(inter_3);
            add(track_7);
            add(track_11);
        }};
        road_t = new ArrayList<Transition>() {{
            add(_3_inter_3);
            add(_inter_3_7);
            add(_7_11);
        }};
        addArcs(road_p, road_t);

        Collections.reverse(road_p);
        road_t = new ArrayList<Transition>() {{
            add(_11_7);
            add(_7_inter_3);
            add(_inter_3_3);
        }};
        addArcs(road_p, road_t);

        road_p = new ArrayList<Place>() {{
            add(inter_3);
            add(r3_up);
            add(r2_down);
            add(r2_up);
            add(r1_down);
            add(r1_up);
            add(track_4);
        }};
        road_t = new ArrayList<Transition>() {{
            add(_inter_3_r3_up);
            add(_r3_up_r2_down);
            add(_r2_down_r2_up);
            add(_r2_up_r1_down);
            add(_r1_down_r1_up);
            add(_r1_up_4);
        }};
        addArcs(road_p, road_t);

        Collections.reverse(road_p);
        road_t = new ArrayList<Transition>() {{
            add(_4_r1_up);
            add(_r1_up_r1_down);
            add(_r1_down_r2_up);
            add(_r2_up_r2_down);
            add(_r2_down_r3_up);
            add(_r3_up_inter_3);
        }};
        addArcs(road_p, road_t);
    }

    private void addController(Controller c, List<Transition> ts) {
        for (Transition t : ts) {
            t.setController(c);
        }
    }

    private void addArcs(List<Place> road_p, List<Transition> road_t) {
        for (int i = 0; i < road_t.size(); i++) {
            Arc in = pn.arc(road_p.get(i).getName() +"-"+ road_t.get(i).getName(),
                    road_p.get(i), road_t.get(i));
            this.arcs.add(in);
            Arc out = pn.arc(road_t.get(i).getName() +"-"+ road_p.get(i + 1).getName(),
                    road_t.get(i), road_p.get(i + 1));
            this.arcs.add(out);
        }
    }

    private void addArc(Place p1, Place p2, Transition t1) {
        Arc in = pn.arc(p1.getName() +"-"+ t1.getName(), p1, t1);
        arcs.add(in);
        Arc out = pn.arc(t1.getName() +"-"+ p2.getName(), t1, p2);
        arcs.add(out);
    }

}
