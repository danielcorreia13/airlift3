sshpass -f password ssh sd401@192.168.8.177 'pkill -U sd401 java'

echo "Transfering data to the passenger node."

sshpass -f password ssh sd401@192.168.8.177 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.177 'rm -rf test/AirLift/*'
sshpass -f password scp dirPassenger.zip sd401@192.168.8.177:test/AirLift

echo "Decompressing data sent to the passenger node."
sshpass -f password ssh sd401@192.168.8.177 'cd test/AirLift ; unzip -uq dirPassenger.zip'

echo "Executing program at the passenger node."
sshpass -f password ssh sd401@192.168.8.177 'cd test/AirLift/dirPassenger ; ./passenger_com_d.sh'