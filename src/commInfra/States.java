package commInfra;

public class States {
    public class Passenger{
        /**
         * The customer takes the bus to go to the departure airport.
         */
        public static final int GOING_TO_AIRPORT = 0;

        /**
         * The customer queue at the boarding gate waiting for the flight to be announced.
         */
        public static final int IN_QUEUE = 1;

        /**
         * The customer flies to the destination airport.
         */
        public static final int IN_FLIGHT = 2;

        /**
         * The customer arrives at the destination airport, disembarks and leaves the airport.
         */
        public static final int AT_DESTINATION = 3;
    }
    public class Hostess {
        /**
         *   The hostess waits for the next flight.
         */
        public static final int WAIT_FOR_NEXT_FLIGHT = 0;

        /**
         *   The hostess waits for a passenger to arrive.
         */
        public static final int WAIT_FOR_PASSENGER = 1;

        /**
         *   The hostess checks the passenger's documents.
         */
        public static final int CHECK_PASSENGER = 2;

        /**
         *   The hostess tells the pilot that all the passengers have boarded the plane.
         */
        public static final int READY_TO_FLY = 3;
    }
    public class Pilot{
        /**
         *   The plane is at transfer gate.
         */
        public static final int AT_TRANSFER_GATE = 0;

        /**
         *   The pilot informs that the plane is ready for boarding.
         */
        public static final int READY_FOR_BOARDING = 1;

        /**
         *   The pilot waits for the boarding to be complete.
         */
        public static final int WAIT_FOR_BOARDING = 2;

        /**
         *   The pilot flies the plane to the destination airport.
         */
        public static final int FLYING_FORWARD = 3;

        /**
         *   The pilot waits for the last passenger to exit the plane.
         */
        public static final int DEBOARDING = 4;

        /**
         *   The pilot flies the plane back to the departure airport.
         */
        public static final int FLYING_BACK = 5;
    }
}
