CODEBASE="file:///home/"$1"/test/AirLift/dirPlane/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.PlaneMain localhost 22000 22012
