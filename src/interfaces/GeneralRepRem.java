package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GeneralRepRem extends Remote{


    /**
     *   Set Passenger state.
     *
     *     @param id passenger id
     *     @param state passenger state
     */
    public void setPassengerState (int id, int state) throws RemoteException;

    /**
     *   Set pilot state.
     *
     *     @param state pilot state
     */
    public void setPilotState (int state) throws RemoteException;

    /**
     *  Set hosstess state
     *
     *	@param state hostess state
     */
    public void setHostess (int state)throws RemoteException;

    /**
     *  Increment next id flight
     */
    public void nextFlight() throws RemoteException;

    /**
     *  Write to the logging file, called by another instance.
     *
     *	@param msg message to write in log
     */
    public void writeLog(String msg) throws RemoteException;

    /*                                     STATUS                                    */
    /*-------------------------------------------------------------------------------*/


    /**
     *  Close the logging file
     */
    public void endReport() throws RemoteException;
}
