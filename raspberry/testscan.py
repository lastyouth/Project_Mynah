import os

f = os.popen('iwlist wlan0 scan','r')

lines = f.read()

lines = lines.replace('\t','')
lines = lines.replace(' ','')

splited = lines.split('\n')

print splited
