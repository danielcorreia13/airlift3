package serverSide.objects;


import commInfra.MemException;
import commInfra.MemFIFO;
import commInfra.RunParameters;
import interfaces.DepartureAirportRem;
import interfaces.GeneralRepRem;
import serverSide.main.DepartureAirportMain;

import static commInfra.States.Passenger.*;
import static commInfra.States.Hostess.*;
import static commInfra.States.Pilot.*;

import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * Server side, Shared region : Departure Airport
 */
public class DepartureAirport implements DepartureAirportRem
{
    /**
     * Departure Passenger Queue
     */
    private MemFIFO<Integer> passengerQueue;

    
    /**
     * Ready for boarding flag
     */
    private boolean readyForBoardig;


    /**
     * Keeps track of how many passengers are entering the plane
     */
    private int nPassengers;

    /**
     * Reference to General Repository
     */
    private final GeneralRepRem generalRep;

    /**
     * Show documents flags
     */
    private final boolean[] showDocuments;

    /**
     * Allowed to board flags
     */
    private final boolean[] canBoard;



    /*                                  CONSTRUCTOR                                    */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Departure Airport instantiation.
     *
     *    @param repos reference to the general repository
     */
    public DepartureAirport(GeneralRepRem repos)
    {
        generalRep = repos;

        try {
            passengerQueue = new MemFIFO<>(new Integer [RunParameters.nPassengers]);
        } catch (MemException e) {
            System.err.println("Instantiation of waiting FIFO failed: " + e.getMessage ());
            passengerQueue = null;
            System.exit (1);
        }
        
        showDocuments = new boolean[RunParameters.nPassengers];
        canBoard = new boolean[RunParameters.nPassengers];
        Arrays.fill(canBoard, false);
        Arrays.fill(showDocuments, false);

        readyForBoardig = false;
        nPassengers = 0;
    }

    /**
     *
     * @return true if the passenger queue is empty
     */
    public boolean empty(){
        return passengerQueue.empty();
    }

    /**
     *
     * @return the number of passengers that have been checked
     */
    public int getnPassengers() {
        return nPassengers;
    }

    /*                                   HOSTESS                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform that the hostess needs do check the passenger documents
     *
     *  It is called by the HOSTESS when she is requesting a passenger documents
     *
     *
     */
    public synchronized void checkDocuments()
    {
        int passId = -1;

        try {
            passId = passengerQueue.read();
        } catch (MemException e) {
            System.err.println("Retrieval of passenger from waiting queue failed: " + e.getMessage());
            System.exit(1);
        }

//        System.out.println("HOSTESS: Passenger "+ passId +" is next on queue");

        showDocuments[passId] = true;

        try {
            generalRep.writeLog("Passenger " + passId + " checked");
        } catch (RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());

        }
//        ((DepartureAirportClientProxy) Thread.currentThread()).setEntityState(CHECK_PASSENGER);
        try {
            generalRep.setHostess(CHECK_PASSENGER);
        } catch (RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());
        }

        notifyAll();

        while (showDocuments[passId])
        {
//        	System.out.println("HOSTESS: Checking passenger "+ passId +" documents");

            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

//        System.out.println("	HOSTESS: Passenger "+ passId +" documents checked!");
//        System.out.println("		HOSTESS: Passenger "+ passId +" allowed to board");

        canBoard[passId] = true;

        nPassengers++;
        notifyAll();
    }

    /**
     *  Operation inform that the hostess is waiting for a passenger on the departure queue
     *
     *  It is called by the HOSTESS when she is waiting for a passenger on the queue
     *
     *
     */
    public synchronized void waitForNextPassenger()
    {
//    	System.out.println("HOSTESS: Checking if queue not empty");
//        ((DepartureAirportClientProxy) Thread.currentThread()).setEntityState(WAIT_FOR_PASSENGER);
        try {
            generalRep.setHostess(WAIT_FOR_PASSENGER);
        } catch (RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());

        }

        while (passengerQueue.empty())
        {
//        	System.out.println("HOSTESS: Waiting for next passenger");

            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

//        System.out.println("HOSTESS: Passenger on Queue!");
    }

    /**
     *  Operation inform that the passenger is waiting for the end of the flight
     *
     *  It is called by the PASSENGER when he is on flight waiting to reach the destination
     *
     *
     */
    public synchronized void waitForNextFlight()
    {
//        System.out.println("HOSTESS: Waiting for next flight");
//        ((DepartureAirportClientProxy) Thread.currentThread()).setEntityState(WAIT_FOR_NEXT_FLIGHT);
        try {
            generalRep.setHostess(WAIT_FOR_NEXT_FLIGHT);
        } catch (RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());
        }
        while (!readyForBoardig)
        {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        readyForBoardig = false;
        nPassengers = 0;

//        ((DepartureAirportClientProxy) Thread.currentThread()).setEntityState(WAIT_FOR_PASSENGER);

        try {
            generalRep.setHostess(WAIT_FOR_PASSENGER);
        } catch (RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());
        }

        notifyAll();

    }


    /*                                 PASSENGER                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform the hostess that the passenger is waiting on departure airport queue
     *
     *  It is called by the PASSENGER when he is on the queue
     *
     *
     */
    public synchronized void waitInQueue(int passId)
    {
//        DepartureAirportClientProxy passenger = (DepartureAirportClientProxy) Thread.currentThread();
//        passenger.setEntityState(IN_QUEUE);

//        System.out.println("[!] PASSENGER " + passId + ": Arrived at departure airport");


        try {
            passengerQueue.write(passId);
            generalRep.setPassengerState(passId,IN_QUEUE);
        } catch (MemException e){
            System.err.println("Insertion of passenger in waiting queue failed: " + e.getMessage());
            System.exit(1);
        }catch(RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());
        }

        notifyAll();

        while (!showDocuments[passId]){
            try{
                wait();
            }catch (InterruptedException ignored){

            }
        }

        showDocuments(passId);

        while (!canBoard[passId]){
            try{
                wait();
            }catch (InterruptedException ignored){

            }
        }

        notifyAll();
        //nPassengers++;
    }

    /**
     *  Operation inform that the passenger is showing his documents
     *
     *  It is called by the PASSENGER when he need to show his documents to the hostess
     *
     *
     */
    public synchronized void showDocuments(int passId )
    {
//        int passId = ((DepartureAirportClientProxy)Thread.currentThread()).getPassId();
        showDocuments[passId] = false;
//        System.out.println("PASSENGER "+ passId +": Shows documents");
        notifyAll();
    }


    /*                                     PILOT                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform that the plane is ready for boarding
     *
     *  It is called by the PILOT when plane is parked on departure and ready for boarding
     *
     *
     */
    public synchronized void informPlaneReadyForBoarding()
    {
        //        ((DepartureAirportClientProxy) Thread.currentThread()).setEntityState(READY_FOR_BOARDING);

        readyForBoardig = true;
        try{
            generalRep.nextFlight();
            generalRep.writeLog("Boarding Started");
            generalRep.setPilotState(READY_FOR_BOARDING);
        }catch(RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());
        }

//        System.out.println("PILOT: Plane is ready for boarding");
        notifyAll();
    }

    /**
     * It's called by the pilot to park the plane at the transfer gate
     */

    public void parkAtTransferGate() {
//        ((DepartureAirportClientProxy) Thread.currentThread()).setEntityState(AT_TRANSFER_GATE);
        try {
            generalRep.setPilotState(AT_TRANSFER_GATE);
        } catch (RemoteException e) {
            System.out.println("Remote exception: "+ e.getMessage());
        }
//        System.out.println("PILOT: Park transfer gate");

    }

    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public synchronized void shutdown ()
    {
        DepartureAirportMain.shutdown();
        notifyAll ();
    }
}
