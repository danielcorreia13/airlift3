package interfaces;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Stub : Departure Airport
 */
public interface DepartureAirportRem extends Remote
{


    /*                                   HOSTESS                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     * Operation to check if Passenger Queue is empty
     *
     * @return true if the passenger queue is empty
     */
    public boolean empty() throws RemoteException;

    /**
     * Operation called by hostess to get the number of passengers
     *
     * @return the number of passengers that have been checked
     */
    public int getnPassengers() throws RemoteException;

    /**
     *  Operation inform that the hostess needs do check the passenger documents
     *
     *  It is called by the HOSTESS when she is requesting a passenger documents
     *
     */
    public void checkDocuments() throws RemoteException;
    /**
     *  Operation inform that the hostess is waiting for a passenger on the departure queue
     *
     *  It is called by the HOSTESS when she is waiting for a passenger on the queue
     *
     */
    public void waitForNextPassenger() throws RemoteException;

    /**
     *  Operation inform that the passenger is waiting for the end of the flight
     *
     *  It is called by the PASSENGER when he is on flight waiting to reach the destination
     *
     */
    public void waitForNextFlight() throws RemoteException;


    /*                                 PASSENGER                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform the hostess that a passenger is waiting on departure airport queue
     *
     *  It is called by the PASSENGER when is on the queue
     *
     */
    public void waitInQueue(int passId) throws RemoteException;


    /*                                     PILOT                                       */
    /*---------------------------------------------------------------------------------*/

    /**
     *  Operation inform that the plane is ready for boarding
     *
     *  It is called by the PILOT when plane is parked on departure and ready for boarding
     *
     */
    public void informPlaneReadyForBoarding()  throws RemoteException;

    /**
     * Operation performed by the pilot to park the plane at the transfer gate
     */
    public void parkAtTransferGate()  throws RemoteException;
    /**
     * Shutdown the server
     */
    public void shutdown() throws RemoteException;
}
