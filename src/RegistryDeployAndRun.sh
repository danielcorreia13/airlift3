echo "Transfering data to the registry node."
sshpass -f password ssh sd401@192.168.8.178 'mkdir -p test/AirLift'
sshpass -f password scp dirRegistry.zip sd401@192.168.8.178:test/AirLift
echo "Decompressing data sent to the registry node."
sshpass -f password ssh sd401@192.168.8.178 'cd test/AirLift ; unzip -uq dirRegistry.zip'
echo "Executing program at the registry node."
sshpass -f password ssh sd401@192.168.8.178 'cd test/AirLift/dirRegistry ; ./registry_com_d.sh sd401'