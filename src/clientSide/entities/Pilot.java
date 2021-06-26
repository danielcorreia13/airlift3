package clientSide.entities;

import commInfra.RunParameters;
import interfaces.DepartureAirportRem;
import interfaces.DestinationAirportRem;
import interfaces.PlaneRem;

import java.rmi.RemoteException;

/**
 *   Pilot thread.
 *
 *   It simulates the pilot life cycle.
 *
 */
public class Pilot extends Thread
{
    /**
     *  Reference to Departure Airport Stub
     */
    private final DepartureAirportRem depAirStub;

    /**
     *  Reference to Destination Airport Stub
     */
    private final DestinationAirportRem destAirStub;

    /**
     *  Reference to Plane Stub
     */
    private final PlaneRem planeStub;

    /**
     *  Pilot state
     */
    private int pilotState;


    /**
     * Pilots constructor
     * Creates a Pilot thread and initializes it's parameters
     * @param name name of the thread
     * @param depAirStub Reference to Departure Airport
     * @param destAirStub Reference to Destination Airport
     * @param planeStub Reference to Plane
     */
    public Pilot(String name, DepartureAirportRem depAirStub, DestinationAirportRem destAirStub, PlaneRem planeStub)
    {
        super(name);
        this.depAirStub = depAirStub;
        this.destAirStub = destAirStub;
        this.planeStub = planeStub;
        this.pilotState = commInfra.States.Pilot.AT_TRANSFER_GATE;
    }

    /**
     * Get the pilot's state
     * @return pilot's current state
     */
    public int getPilotState()
    {
        return this.pilotState;
    }

    /**
     * Set the pilot state
     * @param pState new state
     */
    public void setPilotState(int pState)
    {
        this.pilotState = pState;
    }

    /**
     * Main loop of the pilot that runs it's life cycle
     */
    public void run()
    {
        boolean stop = false;
        do {
            try {
                depAirStub.parkAtTransferGate();

                depAirStub.informPlaneReadyForBoarding();
                int nPass = planeStub.waitForAllInBoard();
                flyToDestinationPoint();
                planeStub.setAtDestination(true);
                destAirStub.announceArrival(nPass);
                flyToDeparturePoint();
                planeStub.setAtDestination(false);
                stop = destAirStub.getTotalPassengers() == RunParameters.nPassengers;
            }catch(RemoteException e) {
                System.out.println("Remote exception: "+ e.getMessage());
            }
        } while (!stop);
        try {
            depAirStub.parkAtTransferGate();
            depAirStub.shutdown();
            destAirStub.shutdown();
            planeStub.shutdown();
        }catch(RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());
        }
    }

    /**
     *  Operation fly to the destination point
     *
     *  Puts the thread a sleep for a random amount of time
     *
     */
    public synchronized void flyToDestinationPoint()
    {

//      System.out.println("\nPILOT: Flying to destination");
//      System.out.println("=================================\n");
        try
        {
            sleep ((long) (1 + 300 * Math.random ()));
        }
        catch (InterruptedException ignored) {}
    }

    /**
     *  Operation fly to the departure point
     *
     *  Puts the thread a sleep for a random amount of time
     *
     */
    public synchronized void flyToDeparturePoint() {
//        System.out.println("\nPILOT: Flying back to departure");
//        System.out.println("=================================\n");

        notifyAll();
        try
        {
            sleep ((long) (1 + 100 * Math.random ()));
        }
        catch (InterruptedException ignored) {}
    }
}
