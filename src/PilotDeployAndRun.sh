sshpass -f password ssh sd401@192.168.8.180 'pkill -U sd401 java'

echo "Transfering data to the pilot node."

sshpass -f password ssh sd401@192.168.8.180 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.180 'rm -rf test/AirLift/*'
sshpass -f password scp dirPilot.zip sd401@192.168.8.180:test/AirLift

echo "Decompressing data sent to the pilot node."
sshpass -f password ssh sd401@192.168.8.180 'cd test/AirLift ; unzip -uq dirPilot.zip'

echo "Executing program at the pilot node."
sshpass -f password ssh sd401@192.168.8.180 'cd test/AirLift/dirPilot ; ./pilot_com_d.sh'