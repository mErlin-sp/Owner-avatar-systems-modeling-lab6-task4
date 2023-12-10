import PetriObj.*;

import java.util.ArrayList;
import java.util.Objects;

public class Task4 {

    private static final int timeModeling = 1000;
    private static final double additionalServerProb = 0.3;

    public static void main(String[] args) throws ExceptionInvalidTimeDelay {
        PetriObjModel model = getModel();
        model.setIsProtokol(false);
        model.go(timeModeling);

        System.out.println("Time modeling: " + timeModeling);
        System.out.println();
        System.out.println("Input values:");
        System.out.println("Additional Server Probability: " + additionalServerProb);
        System.out.println("\nOutput values:");

        for (PetriP p : model.getListObj().getFirst().getNet().getListP()) {
            if (Objects.equals(p.getName(), "P3")) {
                System.out.printf("Total processed: %d \n", p.getMark());
            }
        }

    }

    public static PetriObjModel getModel() throws ExceptionInvalidTimeDelay {
        ArrayList<PetriSim> list = new ArrayList<>();
        list.add(new PetriSim(getNet(additionalServerProb)));
        return new PetriObjModel(list);
    }


    public static PetriNet getNet(double additionalServerProb) throws ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 1));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("P3", 0));
        d_T.add(new PetriT("T1", 1.0));
        d_T.add(new PetriT("T2", 5.0));
        d_T.get(1).setProbability(0.7);
        d_T.add(new PetriT("T3", 10.0));
        d_T.get(2).setProbability(additionalServerProb);
        d_In.add(new ArcIn(d_P.get(1), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(2), 1));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(2), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        PetriNet d_Net = new PetriNet("task4", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }
}

