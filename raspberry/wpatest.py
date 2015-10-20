import os

class wpa_info:
    def __init__(self):
        self.wpa_info_map={}
        self.addDefault()

    def set(self,key,value):
        self.wpa_info_map[key] = value
    def addDefault(self):
        self.wpa_info_map['ssid']=''
        self.wpa_info_map['psk']=''
        self.wpa_info_map['proto']='RSN'
        self.wpa_info_map['key_mgmt']='WPA-PSK'
        self.wpa_info_map['pairwise']='CCMP'
        self.wpa_info_map['auth_alg']='OPEN'
        self.wpa_info_map['disabled']='0'
    def getSavedString(self):
        ret = []
        ret.append('network={\n')
        ret.append('\t'+'ssid'+'='+self.wpa_info_map['ssid']+'\n')
        ret.append('\t'+'psk'+'='+self.wpa_info_map['psk']+'\n')
        ret.append('\t'+'proto'+'='+self.wpa_info_map['proto']+'\n')
        ret.append('\t'+'key_mgmt'+'='+self.wpa_info_map['key_mgmt']+'\n')
        ret.append('\t'+'pairwise'+'='+ self.wpa_info_map['pairwise']+'\n')
        ret.append('\t'+'auth_alg'+'='+self.wpa_info_map['auth_alg']+'\n')
        ret.append('\t'+'disabled'+'='+self.wpa_info_map['disabled']+'\n')
        ret.append('}\n')
        ret.append('\n')
        return ret
    def getValue(self,key):
        if key in self.wpa_info_map.keys():
            return self.wpa_info_map.get(key)
        return ''

g_wpalist = []

def parse():
    f = open('/etc/wpa_supplicant/wpa_supplicant.conf','r')
    lines = f.readlines()
    f.close()
    start = False

    info = wpa_info()

    for line in lines:
        line = line.replace('\t','')
        line = line.replace('\n','')
        print line
        print start
        if str.startswith(line,'network') == True:
            start = True
            continue

        if start == True and str.startswith(line,'}'):
            print 'makefalse'
            start = False
            #then append it
            g_wpalist.append(info)
            info = wpa_info()
            continue
        if start == True:
            #this we can do it only using parse
            spilted = line.split('=')
            print 'spilted : ',spilted
            info.set(spilted[0],spilted[1])


def updateWpa():
    res = []
    f = open('/etc/wpa_supplicant/wpa_supplicant.conf','r')

    lines = f.readlines()

    f.close()

    for line in lines:
        if str.startswith(line,'network') == True:
            break
        else:
            res.append(line)

    print 'pre-condition',res
    for wpa in g_wpalist:
        res = res+wpa.getSavedString()

    print 'final',res

    f = open('/etc/wpa_supplicant/wpa_supplicant.conf','w')

    for line in res:
        f.write(line)

    f.close()
    print 'finished!'



def ssidExist(ssid):
    for wpa in g_wpalist:
        print 'ssid : '+wpa.getValue('ssid')
        if wpa.getValue('ssid') == '"'+ssid+'"':
            return True
    return False

def setEnabled(ssid):
    print 'setEnabled ssid is '+ssid
    if ssidExist(ssid) == True:
        print 'ssid is exists'
        for i in range(len(g_wpalist)):
            if g_wpalist[i].getValue('ssid') == '"'+ssid+'"':
                g_wpalist[i].set('disabled','0')
            else:
                g_wpalist[i].set('disabled','1')
        updateWpa()

def addNewWifiAP(ssid,password):
    if ssidExist(ssid) == True:
        return False
    else:
        wpa = wpa_info()
        wpa.set('ssid','"'+ssid+'"')
        wpa.set('psk','"'+password+'"')

        g_wpalist.append(wpa)

        updateWpa()

        return True

parse()
setEnabled('G2_2075')
os.system('ifdown wlan0')
os.system('ifup wlan0')


