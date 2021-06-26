sshpass -f password ssh sd401@192.168.8.172 'pkill -U sd401 java'

echo "Transfering data to the plane node."
sshpass -f password ssh sd401@192.168.8.172 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.172 'rm -rf test/AirLift/*'
sshpass -f password scp dirPlane.zip sd401@192.168.8.172:test/AirLift

echo "Decompressing data sent to the plane node."
sshpass -f password ssh sd401@192.168.8.172 'cd test/AirLift ; unzip -uq dirPlane.zip'

echo "Executing program at the plane node."
sshpass -f password ssh sd401@192.168.8.172 'cd test/AirLift/dirPlane ; ./plane_com_d.sh sd401'
#sshpass -f password ssh sd401@192.168.8.172 'cd test/AirLift/dirPlane ; ./bshop_com_d.sh ruib'