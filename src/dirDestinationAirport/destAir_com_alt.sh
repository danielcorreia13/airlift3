CODEBASE="file:///home/"$1"/test/AirLift/dirDestinationAirport/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.DestinationAirportMain localhost 22000 22011
