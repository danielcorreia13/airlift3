CODEBASE="file:///home/"$1"/test/AirLift/dirRegistry/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerRegisterRemoteObject l040101-ws08.ua.pt 22000 22001
