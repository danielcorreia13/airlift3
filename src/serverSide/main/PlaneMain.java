package serverSide.main;

import interfaces.GeneralRepRem;
import interfaces.PlaneRem;
import interfaces.Register;
import serverSide.objects.Plane;

import java.net.SocketTimeoutException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *    Server side of the Plane Main
 */

public class PlaneMain {

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

        Plane plane = new Plane(generalRepRem);
        PlaneRem planeInterface = null;

        try {
            planeInterface = (PlaneRem) UnicastRemoteObject.exportObject (plane, Integer.parseInt(args[2]));
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println("Plane interface was generated!");

        String nameEntryBase = "RegisterHandler";
        String nameEntryObject = "Plane";
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
            reg.bind (nameEntryObject, planeInterface);
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (AlreadyBoundException e) {
            System.out.println ("Already bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println ("Plane object was registered!");


        shutdown = false;

        try
        { while (!shutdown)
            synchronized (Class.forName ("serverSide.main.PlaneMain"))
            { try
            { (Class.forName ("serverSide.main.PlaneMain")).wait ();
            }
            catch (InterruptedException e)
            { System.out.println  ("Plane main thread was interrupted!");
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
        System.out.println ("Plane was deregistered!");

        try
        {
            shutdownDone = UnicastRemoteObject.unexportObject (plane, true);
        }
        catch (NoSuchObjectException e)
        {
            System.out.println ("Unexport exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        if (shutdownDone)
            System.out.println ("Plane was shutdown!");
    }

    /**
     *  Close of operations.
     */

    public static void shutdown ()
    {
        shutdown = true;
        try
        { synchronized (Class.forName ("serverSide.main.PlaneMain"))
        { (Class.forName ("serverSide.main.PlaneMain")).notify ();
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