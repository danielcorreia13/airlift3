sshpass -f password ssh sd401@192.168.8.173 'pkill -U sd401 java'

echo "Transfering data to the destination airport node."
sshpass -f password ssh sd401@192.168.8.173 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.173 'rm -rf test/AirLift/*'
sshpass -f password scp dirDestinationAirport.zip sd401@192.168.8.173:test/AirLift

echo "Decompressing data sent to the destination airport node."
sshpass -f password ssh sd401@192.168.8.173 'cd test/AirLift ; unzip -uq dirDestinationAirport.zip'

echo "Executing program at the destination airport node."
sshpass -f password ssh sd401@192.168.8.173 'cd test/AirLift/dirDestinationAirport ; ./destAir_com_d.sh sd401'
#sshpass -f password ssh sd401@192.168.8.173 'cd test/AirLift/dirDestinationAirport ; ./bshop_com_d.sh ruib'