echo "Transfering data to the departure airport node."
sshpass -f password ssh sd401@192.168.8.174 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.174 'rm -rf test/AirLift/*'
sshpass -f password scp dirDepartureAirport.zip sd401@192.168.8.174:test/AirLift
echo "Decompressing data sent to the departure airport node."
sshpass -f password ssh sd401@192.168.8.174 'cd test/AirLift ; unzip -uq dirDepartureAirport.zip'
echo "Executing program at the departure airport node."
sshpass -f password ssh sd401@192.168.8.174 'cd test/AirLift/dirDepartureAirport ; ./depAir_com_d.sh sd401'
#sshpass -f password ssh sd401@192.168.8.174 'cd test/AirLift/dirDepartureAirport ; ./bshop_com_d.sh ruib'