sshpass -f password ssh sd401@192.168.8.175 'pkill -U sd401 java'

echo "Transfering data to the hostess node."

sshpass -f password ssh sd401@192.168.8.175 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.175 'rm -rf test/AirLift/*'
sshpass -f password scp dirHostess.zip sd401@192.168.8.175:test/AirLift

echo "Decompressing data sent to the hostess node."
sshpass -f password ssh sd401@192.168.8.175 'cd test/AirLift ; unzip -uq dirHostess.zip'

echo "Executing program at the hostess node."
sshpass -f password ssh sd401@192.168.8.175 'cd test/AirLift/dirHostess ; ./hostess_com_d.sh'