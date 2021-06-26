sshpass -f password ssh sd401@192.168.8.176 'pkill -U sd401 java'

echo "Transfering data to the general repository node."
sshpass -f password ssh sd401@192.168.8.176 'mkdir -p test/AirLift'
sshpass -f password ssh sd401@192.168.8.176 'rm -rf test/AirLift/*'
sshpass -f password scp dirGeneralRepos.zip sd401@192.168.8.176:test/AirLift

echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd401@192.168.8.176 'cd test/AirLift ; unzip -uq dirGeneralRepos.zip'

echo "Executing program at the general repository node."
sshpass -f password ssh sd401@192.168.8.176 'cd test/AirLift/dirGeneralRepos ; ./repos_com_d.sh sd401'
#sshpass -f password ssh sd401@192.168.8.171 'cd test/AirLift/dirGeneralRepos ; ./bshop_com_d.sh ruib'