package clientSide.main;


import clientSide.entities.Hostess;
import interfaces.DepartureAirportRem;
import interfaces.PlaneRem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Hostess Main
 */
public class HostessMain
{
    /**
     * Hostess Main function
     *
     * @param args
     */
    public static void main(String[] args)
    {

        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry (args[0], Integer.parseInt(args[1]));
        }
        catch (RemoteException e) {
            System.out.println ("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        DepartureAirportRem depAirStub = null;
        PlaneRem planeStub = null;

        try {
            depAirStub = (DepartureAirportRem) registry.lookup ("DepartureAirport");
            planeStub = (PlaneRem) registry.lookup ("Plane");
        } catch (RemoteException e) {
            System.out.println ("Look up exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println ("Not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        Hostess hostess = new Hostess("Hostess", depAirStub, planeStub);

        hostess.start();

        // Thread termination
        try {
            hostess.join();
        } catch (InterruptedException e) {
            System.out.println("HSomething went wrong");
        }

        System.out.println("Hostess terminated");
    }
}
