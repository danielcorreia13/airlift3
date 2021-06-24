package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Stub: Destination Airport
 */
public interface DestinationAirportRem extends Remote
{

    /*                                 PASSENGER                                     */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Operation of passenger leaving the plane
     *
     *  It is called by the PASSENGER when he leaves the plane
     *
     */
    public void leaveThePlane(int passId) throws RemoteException;


    /*                                    PILOT                                      */
    /*-------------------------------------------------------------------------------*/
    /**
     *  Operation inform that plane reached the destionation
     *
     *  It is called by the pilot when plane was landed
     *
     *  @param nPass number of passengers in the plane
     */
    public void announceArrival(int nPass) throws RemoteException;

    /**
     * Get number of total transported passengers
     *
     * @return number of total transported passengers
     */
    public int getTotalPassengers() throws RemoteException;
}
