xterm  -T "General Repository" -hold -e "./GeneralReposDeployAndRun.sh" &
sleep 8
xterm  -T "Departure Airport" -hold -e "./DepartureAirportDeployAndRun.sh" &
sleep 4
xterm  -T "Plane" -hold -e "./PlaneDeployAndRun.sh" &
sleep 4
xterm  -T "Destination Airport" -hold -e "./DestinationAirportDeployAndRun.sh" &
sleep 4
xterm  -T "Passenger" -hold -e "./PassengerDeployAndRun.sh" &
sleep 1
xterm  -T "Pilot" -hold -e "./PilotDeployAndRun.sh" &
sleep 1
xterm  -T "Hostess" -hold -e "./HostessDeployAndRun.sh" &
