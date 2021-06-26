CODEBASE="file:///home/"$1"/test/AirLift/dirGeneralRepos/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.GeneralRepMain localhost 22000 22002
