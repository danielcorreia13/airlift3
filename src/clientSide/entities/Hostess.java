package clientSide.entities;

import commInfra.RunParameters;
import interfaces.DepartureAirportRem;
import interfaces.PlaneRem;

import java.rmi.RemoteException;


/**
 *   Passenger thread.
 *
 *   It simulates the hostess life cycle.
 *
 */
public class Hostess extends Thread
{

    /**
     *  Reference to Departure Airport Stub
     */
    private final DepartureAirportRem depAirStub;

    /**
     *  Reference to Plane Stub
     */
    private final PlaneRem planeStub;



    /**
     *  Hostess state
     */
    private int hState;

    /**
     * Hostess constructor
     * Creates a Hostess thread and initializes it's parameters
     * @param name name of the thread
     * @param depAir Reference to Departure Airport
     * @param plane Reference to Plane
     */
    public Hostess(String name, DepartureAirportRem depAir, PlaneRem plane)
    {
        super(name);
        this.depAirStub = depAir;
        this.planeStub = plane;
        this.hState = commInfra.States.Hostess.WAIT_FOR_NEXT_FLIGHT;
    }

    /**
     * Get Hostess state
     * @return hostess current state
     */
    public int gethState() {
        return hState;
    }

    /**
     * Ste Hostess state
     * @param hState new state
     */
    public void sethState(int hState)
    {
        this.hState = hState;

    }

    /**
     * Main loop of the hostess that runs it's life cycle
     */
    @Override
    public void run()
    {
        int count = 0;
        do {
            try {
                depAirStub.waitForNextFlight();

                prepareForPassBoarding();
                int max = RunParameters.maxPassengers;
                int min = RunParameters.minPassengers;
                while (true) {
                    if (depAirStub.getnPassengers() == max) {
//                    System.out.println("HOSTESS: Plane full, informing pilot");
                        break;
                    }
                    if (depAirStub.empty() && depAirStub.getnPassengers() > min) {
//                    System.out.println("HOSTESS: No passengers waiting, informing pilot");
                        break;
                    }
                    if (count == RunParameters.nPassengers) {
//                    System.out.println("HOSTESS: Last passenger boarding, informing pilot");
                        break;
                    }
                    count++;
//                System.out.println("Waiting for next passenger");
                    depAirStub.waitForNextPassenger();

                    depAirStub.checkDocuments();
                }
                planeStub.informPlaneIsReadyToTakeOff(depAirStub.getnPassengers());
            }catch(RemoteException e) {
                System.out.println("Remote exception: "+ e.getMessage());
            }
        }while (count < RunParameters.nPassengers);

    }

    /**
     * Hostess prepares for pass boarding
     *
     * The thread is put to sleep for a random amount of time
     */
    public void prepareForPassBoarding()
    {
//    	System.out.println("HOSTESS: Preparing for pass board");
        try
        { sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException ignored) {}


    }
}

