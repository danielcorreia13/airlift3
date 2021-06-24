package serverSide.main;


import interfaces.GeneralRepRem;
import interfaces.DepartureAirportRem;
import interfaces.Register;
import serverSide.objects.DepartureAirport;

import java.net.SocketTimeoutException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.SocketTimeoutException;

/**
 *    Server side of the Departure Airport Main
 */

public class DepartureAirportMain {

    /**
     * Flag to signal server shutdown
     */
    public static boolean shutdown;

    /**
     *  Main method.
     *
     *    @param args runtime arguments
     */

    public static void main(String[] args)
    {
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        System.out.println("Security manager was installed!");

        GeneralRepRem generalRepRem = null;
        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println ("registry created");

        try {
            generalRepRem = (GeneralRepRem) registry.lookup("GeneralRep");
        }
        catch (RemoteException e)
        {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        {
            System.out.println ("Not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        DepartureAirport depAir = new DepartureAirport(generalRepRem);
        DepartureAirportRem depAirInterface = null;

        try {
            depAirInterface = (DepartureAirportRem) UnicastRemoteObject.exportObject (depAir, Integer.parseInt(args[2]));
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println("DepartureAirport interface was generated!");

        String nameEntryBase = "RegisterHandler";
        String nameEntryObject = "DepartureAirport";
        Register reg = null;

        try {
            reg = (Register) registry.lookup (nameEntryBase);
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println ("Not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            reg.bind (nameEntryObject, depAirInterface);
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (AlreadyBoundException e) {
            System.out.println ("Already bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println ("DepartureAirport object was registered!");


        shutdown = false;

        try
        { while (!shutdown)
            synchronized (Class.forName ("serverSide.main.DepartureAirportMain"))
            { try
            { (Class.forName ("serverSide.main.DepartureAirportMain")).wait ();
            }
            catch (InterruptedException e)
            { System.out.println  ("DepartureAirport main thread was interrupted!");
            }
            }
        }
        catch (ClassNotFoundException e)
        {
            System.out.println ("Class Not found");
            e.printStackTrace ();
            System.exit (1);
        }

        boolean shutdownDone = false;

        try
        { reg.unbind (nameEntryObject);
        }
        catch (RemoteException e)
        {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        {
            System.out.println("Not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println ("DepartureAirport was deregistered!");

        try
        {
            shutdownDone = UnicastRemoteObject.unexportObject (depAir, true);
        }
        catch (NoSuchObjectException e)
        {
            System.out.println ("Unexport exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        if (shutdownDone)
            System.out.println ("DepartureAirport was shutdown!");
    }

    /**
     *  Close of operations.
     */

    public static void shutdown ()
    {
        shutdown = true;
        try
        { synchronized (Class.forName ("serverSide.main.DepartureAirportMain"))
        { (Class.forName ("serverSide.main.DepartureAirportMain")).notify ();
        }
        }
        catch (ClassNotFoundException e)
        {
            System.out.println ("Class Not found");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}
