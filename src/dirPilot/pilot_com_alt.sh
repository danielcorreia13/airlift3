CODEBASE="file:///home/"$1"/test/AirLift/dirPilot/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.PilotMain localhost 22000
