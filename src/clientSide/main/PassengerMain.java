package clientSide.main;


import clientSide.entities.Passenger;
import commInfra.RunParameters;
import interfaces.DepartureAirportRem;
import interfaces.DestinationAirportRem;
import interfaces.PlaneRem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Passenger Main
 */
public class PassengerMain
{
    /**
     *  References to Passengers
     */
    private static Passenger[] passenger;


    /**
     * Passenger Main function
     *
     * @param args
     */
    public static void main(String[] args)
    {
        Registry registry = null;

        passenger = new Passenger[RunParameters.nPassengers];

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

        for (int i = 0; i < RunParameters.nPassengers; i++)
        {
            passenger[i] = new Passenger( "Passenger" + i, i, depAirStub, destAirStub, planeStub);
        }

        for (Passenger p : passenger)
        {
            p.start();
        }

        try {
            for (Passenger p : passenger)
            {
                p.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Something went wrong");
        }
    }
}