sshpass -f password ssh sd401@192.168.8.178 'pkill -U sd401 rmiregistry'

echo "Transfering data to the RMIregistry node."
sshpass -f password ssh sd401@192.168.8.178 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.178 'rm -rf test/AirLift/*'
sshpass -f password ssh sd401@192.168.8.178 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh sd401@192.168.8.178 'rm -rf Public/classes/interfaces/*'
sshpass -f password scp dirRMIRegistry.zip sd401@192.168.8.178:test/AirLift

echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh sd401@192.168.8.178 'cd test/AirLift ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh sd401@192.168.8.178 'cd test/AirLift/dirRMIRegistry ; cp interfaces/*.class /home/sd401/Public/classes/interfaces ; cp set_rmiregistry_d.sh /home/sd401'

echo "Executing program at the RMIregistry node."

# Porta ???
sshpass -f password ssh sd401@192.168.8.178 './set_rmiregistry_d.sh sd401 22000'