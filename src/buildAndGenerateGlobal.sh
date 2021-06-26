echo "Compiling source code."
javac */*.java */*/*.java
echo "Distributing intermediate code to the different execution environments."

echo "  RMI registry"
rm -rf dirRMIRegistry/interfaces
mkdir -p dirRMIRegistry/interfaces
cp interfaces/*.class dirRMIRegistry/interfaces

echo "  Register Remote Objects"
rm -rf dirRegistry/serverSide dirRegistry/interfaces
mkdir -p dirRegistry/serverSide dirRegistry/serverSide/main dirRegistry/serverSide/objects dirRegistry/interfaces
cp serverSide/main/ServerRegisterRemoteObject.class dirRegistry/serverSide/main
cp serverSide/objects/RegisterRemoteObject.class dirRegistry/serverSide/objects
cp interfaces/Register.class dirRegistry/interfaces

echo "  General Repository of Information"
rm -rf dirGeneralRepos/serverSide dirGeneralRepos/clientSide dirGeneralRepos/interfaces
mkdir -p dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/objects dirGeneralRepos/interfaces \
         dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities dirGeneralRepos/commInfra
cp serverSide/main/GeneralRepMain.class dirGeneralRepos/serverSide/main
cp serverSide/objects/GeneralRep.class dirGeneralRepos/serverSide/objects
cp interfaces/Register.class interfaces/GeneralRepRem.class dirGeneralRepos/interfaces
#???? (Check)
#cp clientSide/entities/HostessStates.class clientSide/entities/PassengerStates.class clientSide/entities/PilotStates.class dirGeneralRepos/clientSide/entities
cp commInfra/*.class dirGeneralRepos/commInfra

echo "  Departure Airport"
rm -rf dirDepartureAirport/serverSide dirDepartureAirport/clientSide dirDepartureAirport/interfaces dirDepartureAirport/commInfra
mkdir -p dirDepartureAirport/serverSide dirDepartureAirport/serverSide/main dirDepartureAirport/serverSide/objects dirDepartureAirport/interfaces \
         dirDepartureAirport/clientSide dirDepartureAirport/clientSide/entities dirDepartureAirport/commInfra
cp serverSide/main/DepartureAirportMain.class dirDepartureAirport/serverSide/main
cp serverSide/objects/DepartureAirport.class dirDepartureAirport/serverSide/objects
cp interfaces/*.class dirDepartureAirport/interfaces
#???? (Check)
#cp clientSide/entities/HostessStates.class clientSide/entities/PassengerStates.class clientSide/entities/PilotStates.class dirDepartureAirport/clientSide/entities
cp commInfra/*.class dirDepartureAirport/commInfra

echo "  Plane"
rm -rf dirPlane/serverSide dirPlane/clientSide dirPlane/interfaces dirPlane/commInfra
mkdir -p dirPlane/serverSide dirPlane/serverSide/main dirPlane/serverSide/objects dirPlane/interfaces \
         dirPlane/clientSide dirPlane/clientSide/entities dirPlane/commInfra
cp serverSide/main/PlaneMain.class dirPlane/serverSide/main
cp serverSide/objects/Plane.class dirPlane/serverSide/objects
cp interfaces/*.class dirPlane/interfaces
#???? States (Check)
#cp clientSide/entities/HostessStates.class clientSide/entities/PassengerStates.class clientSide/entities/PilotStates.class dirPlane/clientSide/entities
cp commInfra/*.class dirPlane/commInfra

echo "  Destination Airport"
rm -rf dirDestinationAirport/serverSide dirDestinationAirport/clientSide dirDestinationAirport/interfaces dirDestinationAirport/commInfra
mkdir -p dirDestinationAirport/serverSide dirDestinationAirport/serverSide/main dirDestinationAirport/serverSide/objects dirDestinationAirport/interfaces \
         dirDestinationAirport/clientSide dirDestinationAirport/clientSide/entities dirDestinationAirport/commInfra
cp serverSide/main/DestinationAirportMain.class dirDestinationAirport/serverSide/main
cp serverSide/objects/DestinationAirport.class dirDestinationAirport/serverSide/objects
cp interfaces/*.class dirDestinationAirport/interfaces
#???? States (Check)
#cp clientSide/entities/HostessStates.class clientSide/entities/PassengerStates.class clientSide/entities/PilotStates.class dirDestinationAirport/clientSide/entities
cp commInfra/*.class dirDestinationAirport/commInfra

echo "  Hostess"
rm -rf dirHostess/serverSide dirHostess/clientSide dirHostess/interfaces
mkdir -p dirHostess/serverSide dirHostess/serverSide/main dirHostess/clientSide dirHostess/clientSide/main dirHostess/clientSide/entities \
         dirHostess/interfaces dirHostess/commInfra
#cp dirHostess/serverSide/main
cp clientSide/main/HostessMain.class dirHostess/clientSide/main
#??? States (Check)
#cp clientSide/entities/Hostess.class clientSide/entities/HostessStates.class dirHostess/clientSide/entities
cp clientSide/entities/Hostess.class dirHostess/clientSide/entities
cp commInfra/*.class dirHostess/commInfra


cp interfaces/DepartureAirportRem.class interfaces/DestinationAirportRem.class interfaces/GeneralRepRem.class interfaces/PlaneRem.class dirHostess/interfaces

echo "  Passengers"
rm -rf dirPassenger/serverSide dirPassenger/clientSide dirPassenger/interfaces
mkdir -p dirPassenger/serverSide dirPassenger/serverSide/main dirPassenger/clientSide dirPassenger/clientSide/main dirPassenger/clientSide/entities \
         dirPassenger/interfaces dirPassenger/commInfra
#cp dirPassenger/serverSide/main
cp clientSide/main/PassengerMain.class dirPassenger/clientSide/main
#??? States (Check)
cp clientSide/entities/Passenger.class dirPassenger/clientSide/entities
cp clientSide/entities/Passenger.class dirPassenger/clientSide/entities
cp commInfra/*.class dirPassenger/commInfra

cp interfaces/DepartureAirportRem.class interfaces/DestinationAirportRem.class interfaces/GeneralRepRem.class interfaces/PlaneRem.class dirPassenger/interfaces

echo "  Pilot"
rm -rf dirPilot/serverSide dirPilot/clientSide dirPilot/interfaces
mkdir -p dirPilot/serverSide dirPilot/serverSide/main dirPilot/clientSide dirPilot/clientSide/main dirPilot/clientSide/entities \
         dirPilot/interfaces dirPilot/commInfra
#cp dirPilot/serverSide/main
cp clientSide/main/PilotMain.class dirPilot/clientSide/main
#??? States (Check)
cp clientSide/entities/Pilot.class dirPilot/clientSide/entities
cp commInfra/*.class dirPilot/commInfra
cp interfaces/DepartureAirportRem.class interfaces/DestinationAirportRem.class interfaces/GeneralRepRem.class interfaces/PlaneRem.class dirPilot/interfaces

echo "Compressing execution environments."

echo "  RMI registry"
rm -f  dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry

echo "  Register Remote Objects"
rm -f  dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry

echo "  General Repository of Information"
rm -f  dirGeneralRepos.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos

echo "  Departure Airport"
rm -f  dirDepartureAirport.zip
zip -rq dirDepartureAirport.zip dirDepartureAirport

echo "  Plane"
rm -f  dirPlane.zip
zip -rq dirPlane.zip dirPlane

echo "  Destination Airport"
rm -f  dirDestinationAirport.zip
zip -rq dirDestinationAirport.zip dirDestinationAirport