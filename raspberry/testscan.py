import os

f = os.popen('iwlist wlan0 scan','r')

lines = f.read()

lines = lines.replace('\t','')
lines = lines.replace(' ','')

splited = lines.split('\n')

for line in splited:
    if str.startswith(line,'ESSID') == True:
        print line.split('ESSID:')[1]


