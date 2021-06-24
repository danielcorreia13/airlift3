package commInfra;

import java.util.Date;

public class RunParameters
{
    /**
     *  Number of passengers to be transported
     */
    public static final int nPassengers = 21;

    /**
     *  Minimum number of passengers to be transported in the plane
     */
    public static final int minPassengers = 5;

    /**
     *  Maximum number of passengers to be transported in the plane
     */
    public static final int maxPassengers = 10;

    /**
     * Log filename
     */
    public static final String logFile = "log_" + new Date().toString().replace(' ', '_') + ".txt";

    public static final int base_port = 22402;

    /**
     * Departure Airport Port
     */
    public static final int DepartureAirportPort = base_port+1;

    /**
     * Departure Airport Hostname
     */
    public static final String DepartureAirportHostName = "l040101-ws04.ua.pt";
//    public static final String DepartureAirportHostName = "localhost";

    /**
     * Destination Airport Port
     */
    public static final int DestinationAirportPort = base_port+2;

    /**
     * Destination Airport Hostname
     */
    public static final String DestinationAirportHostName = "l040101-ws03.ua.pt";
//    public static final String DestinationAirportHostName = "localhost";


    /**
     * Plane Port
     */
    public static final int PlanePort = base_port+3;

    /**
     * Plane Hostname
     */
    public static final String PlaneHostName = "l040101-ws02.ua.pt";
//    public static final String PlaneHostName = "localhost";

    /**
     * Repository Port
     */
    public static final int RepositoryPort = base_port+4;

    /**
     * Repository Hostname
     */
    public static final String RepositoryHostName = "l040101-ws09.ua.pt";
//    public static final String RepositoryHostName = "localhost";
}
