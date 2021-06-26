CODEBASE="file:///home/"$1"/test/AirLift/dirPassenger/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.PassengerMain localhost 22000
