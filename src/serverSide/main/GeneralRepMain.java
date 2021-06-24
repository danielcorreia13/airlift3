package serverSide.main;

import interfaces.DestinationAirportRem;
import interfaces.GeneralRepRem;
import interfaces.PlaneRem;
import interfaces.Register;
import serverSide.objects.DestinationAirport;
import serverSide.objects.GeneralRep;
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
 *    Server side of the General Repository Main
 */

public class GeneralRepMain {

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
        

        GeneralRep genRep = new GeneralRep("log.txt");
        GeneralRepRem genRepInterface = null;

        try {
            genRepInterface = (GeneralRepRem) UnicastRemoteObject.exportObject (genRep, Integer.parseInt(args[2]));
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println("GeneralRep interface was generated!");

        String nameEntryBase = "RegisterHandler";
        String nameEntryObject = "GeneralRep";
        Registry registry = null;
        Register reg = null;

        try
        { registry = LocateRegistry.getRegistry (args[0], Integer.parseInt(args[1]));
        }
        catch (RemoteException e)
        { System.out.println ("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println ("RMI registry was created!");


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
            reg.bind (nameEntryObject, genRepInterface);
        } catch (RemoteException e) {
            System.out.println ("Remote exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (AlreadyBoundException e) {
            System.out.println ("Already bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println ("GeneralRep object was registered!");


        shutdown = false;

        try
        { while (!shutdown)
            synchronized (Class.forName ("serverSide.main.GeneralRepMain"))
            { try
            { (Class.forName ("serverSide.main.GeneralRepMain")).wait ();
            }
            catch (InterruptedException e)
            { System.out.println  ("GeneralRep main thread was interrupted!");
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
        System.out.println ("GeneralRep was deregistered!");

        try
        {
            shutdownDone = UnicastRemoteObject.unexportObject (genRep, true);
        }
        catch (NoSuchObjectException e)
        {
            System.out.println ("Unexport exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        if (shutdownDone)
            System.out.println ("GeneralRep was shutdown!");
    }

    /**
     *  Close of operations.
     */

    public static void shutdown ()
    {
        shutdown = true;
        try
        { synchronized (Class.forName ("serverSide.main.GeneralRepMain"))
        { (Class.forName ("serverSide.main.GeneralRepMain")).notify ();
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