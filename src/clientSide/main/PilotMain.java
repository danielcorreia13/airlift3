package clientSide.main;

import clientSide.entities.Pilot;
import interfaces.DepartureAirportRem;
import interfaces.DestinationAirportRem;
import interfaces.PlaneRem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Pilot Main
 */
public class PilotMain
{


    /**
     * Pilot Main function
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
        DestinationAirportRem destAirStub = null;
        PlaneRem planeStub = null;

        try {
            depAirStub = (DepartureAirportRem) registry.lookup ("DepartureAirport");
            destAirStub = (DestinationAirportRem) registry.lookup ("DestinationAirport");
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

        Pilot pilot = new Pilot("Pilot", depAirStub,destAirStub, planeStub);

        pilot.start();

        // Thread termination
        try {
            pilot.join();
        } catch (InterruptedException e) {
            System.out.println("HSomething went wrong");
        }

        System.out.println("Pilot terminated");
    }
}