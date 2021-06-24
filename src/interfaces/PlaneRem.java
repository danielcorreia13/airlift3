package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.RemoteException;

/**
 * Stub : Plane
 */
public interface PlaneRem extends Remote
{

    /*                                  HOSTESS                                      */
    /*-------------------------------------------------------------------------------*/

    /**
     *  Operation inform that the plane is ready for take off
     *
     *  It is called by the HOSTESS when all passengers are on board,
     *	Requiring minimum passengers on board and departure queue is empty
     *  Or if maximum passengers on board was reached
     *  Or if number of passengers on board is between min and max, and departure queue is empty
     * @param nPass number of passengers that boarded the plane
     *
     */
    public void informPlaneIsReadyToTakeOff(int nPass) throws RemoteException;


    //                                  PASSENGER                                    //
    //---------------------------------------------------------------------------------

    /**
     *  Operation inform that the passenger is waiting for the end of the flight
     *
     *  It is called by the PASSENGER when he is on the plane
     *
     */
    public void waitForEndOfFlight(int nPass) throws RemoteException;

    /**
     *  Operation inform that the passenger as entered the plane
     *
     *  It is called by the PASSENGER when enters the plane
     *
     */
    public void boardThePlane(int nPass) throws RemoteException;

    //                                   PILOT                                      //
    //--------------------------------------------------------------------------------

    /**
     *  Operation to set plane at destination
     *
     * It is called by pilot on destination point
     *
     * @param atDestination new value
     */
    public void setAtDestination(boolean atDestination) throws RemoteException;
    /**
     *  Operation inform that PILOT is waiting for the hostess signal, indicating that all passengers are on board.
     *
     *  It is called by the PILOT while waiting for all passengers on board
     *
     *    @return nPassengers
     */
    public int waitForAllInBoard() throws RemoteException;

    /**
     * Shutdown the server
     */
    public void shutdown() throws RemoteException;
}
