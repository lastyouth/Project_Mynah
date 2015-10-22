# -*- coding: utf-8 -*-
# file: mynah-manager.py
# auth: sbh 

from bluetooth import *
import threading
import RPi.GPIO as GPIO
import time
import Queue
import signal
import os
import json
import base64
import httplib
import random
import datetime
import socket
import sbhwifi

# prevent long timeout of server response
socket.setdefaulttimeout(4)

# activate BLE advertising and scanning
os.system('sudo hciconfig hci0 piscan')
os.system('sudo hciconfig hci0 leadv')
# global variables

g_webhost = "211.189.20.165:13337" #server address

g_defaultpath = '/home/pi/share/mynahmedia/' #media default path

#this product id - holding
g_productid = 'product2'

g_productuser = {} # user who registers using g_productid

# lock for sensing and web request 
g_lock = threading.Lock()

# lock for accepting client
g_clientlock = threading.Lock()

GPIO.setmode(GPIO.BCM)

# client who is connected via Bluetooth
g_clientlist = {}

g_messagelist = []

g_currenttemp = 0


#LED control class
class LEDSensor(threading.Thread):
    def __init__(self,sensortype):
        threading.Thread.__init__(self)
        self.stype = sensortype
        if self.stype == 'left':
            self.out = 24
        elif self.stype == 'right':
            self.out = 27
        GPIO.setup(self.out,GPIO.OUT)
        GPIO.output(self.out,GPIO.LOW)

        self.toggle = False
        self.on = False

    def setToggle(self,flag,turn_on):
        self.toggle = flag
        self.after = turn_on

    def onLED(self):
        self.on = True
        GPIO.output(self.out,GPIO.HIGH)

    def offLED(self):
        self.on = False
        GPIO.output(self.out,GPIO.LOW)

    def run(self):
        while self.toggle == True:
            if self.on == True:
                self.offLED()
            else:
                self.onLED()
            time.sleep(0.75)
        self.offLED()
        if self.after == True:
            self.onLED()
            self.after = False


#left LED for wifi
g_ledLeftWifi = LEDSensor('left')

#right LED for bluetooth
g_ledRightBluetooth = LEDSensor('right')

def wifiIsAvailable():
    global g_ledLeftWifi
    hostname = '211.189.20.165'

    response = os.system('fping -c 1 -t 2000 '+hostname)

    if response == 0:
        g_ledLeftWifi.setToggle(False,True)
        return True
    else:
        g_ledLeftWifi.setToggle(False,False)
        g_ledLeftWifi = LEDSensor('left')
        g_ledLeftWifi.setToggle(True,False)
        g_ledLeftWifi.start()
        return False

def init():
    os.system('mplayer '+g_defaultpath+'online.mp3')
    g_ledLeftWifi.setToggle(True,False)
    g_ledRightBluetooth.setToggle(True,False)

    #g_ledLeftWifi.daemon = True
    #g_ledRightBluetooth.daemon = True

    g_ledLeftWifi.start()
    g_ledRightBluetooth.start()


# add client via bluetootn connection
def addClient(obj):
    g_clientlock.acquire()
    try:
        if obj.cid not in g_clientlist.keys():
            print "addClient id : ",obj.cid
            g_clientlist[obj.cid] = obj;
            print g_clientlist
    except:
        pass
    g_clientlock.release()
    # LED
    if len(g_clientlist) > 0:
        g_ledRightBluetooth.setToggle(False,True)


def removeClient(obj):
    g_clientlock.acquire()
    try:
        if obj.cid in g_clientlist.keys():
            print "removeClient id : ",obj.cid
            g_clientlist.pop(obj.cid,None)
            print g_clientlist
    except:
        pass
    g_clientlock.release()
    if len(g_clientlist) == 0:
        global g_ledRightBluetooth
        g_ledRightBluetooth.setToggle(False,False)
        g_ledRightBluetooth = LEDSensor('right')
        g_ledRightBluetooth.setToggle(True,False)
        g_ledRightBluetooth.start()

def broadcastCurrentClient(mtype):
    g_clientlock.acquire()
    for key, value in g_clientlist.iteritems():
        print "broadcast Key : ",key," type : ",mtype
        value.sendTo(mtype)
    g_clientlock.release()

def sendTargetClient(mtype,mid):
    g_clientlock.acquire()
    for key, value in g_clientlist.iteritems():
        if key == mid:
            print "Target Key : ",mid," Send : ",mtype
            value.sendTo(mtype)
    g_clientlock.release()

def requestHTTPS(mtype,tid,dtype,data):
    try:
        conn = httplib.HTTPSConnection(g_webhost)

        reqobj = {}
        reqobj['messagetype'] = mtype
        reqobj['product_id'] = 'product2'
        reqobj['device_id'] = tid
        reqobj[dtype] = data

        reqdata = base64.encodestring(json.dumps(reqobj))

        print "Request data : ",reqdata

        conn.request("POST","",reqdata)

        response = conn.getresponse()
        print "status : ",response.status," reason : ",response.reason
        if response.status != 200:
            print "Web wants to make this packet ignore"
            return False

        data = json.loads(response.read())

        print "result : ",data["result"]

        return data;
    except:
        print "Web request failure!"
        return False

class ClientProcessThread(threading.Thread):
    def __init__(self,sock,client_info):
        threading.Thread.__init__(self)
        self.sock = sock
        self.client_info = client_info

    def sendTo(self,rtype):
        data = rtype
        try:
            self.sock.send(data)
        except IOError:
            pass
    def run(self):
        try:
            while True:
                data = self.sock.recv(1024);
                if len(data) == 0: break;
                #print self.client_info, ": received [%s]" % data
                res = base64.decodestring(data)

                raw_data = json.loads(res)
                cid = raw_data["id"];
                ctype = raw_data["type"];
                cdata = raw_data["data"];
                crssi = int(raw_data["rssi"]);

                print "id : ",cid," type : ",ctype," data : ",cdata," rssi : ",crssi

                if ctype == "init":
                    self.cid = cid
                    addClient(self)
                elif ctype =="rssi":
                    print "rssi"
                elif ctype == "outtts":
                    g_messagelist.append([crssi,cdata,self.cid])
                    print g_messagelist
                    print "Out tts"
                elif ctype == "intts":
                    g_messagelist.append([crssi,cdata,self.cid])
                    print g_messagelist
                    print "in tts"
                elif ctype == "temp":
                    ret = "temp : "
                    ret += str(g_currenttemp)
                    self.sock.send(ret)
                    print "temp"
                elif ctype == "newwifi":
                    wifiinfo = cdata.split('//')
                    ret = 'wifiresultsuccess'
                    print 'wifiinfo : ',wifiinfo
                    if len(wifiinfo) != 2:
                        ret = 'wifiresultfail'
                    flag = sbhwifi.addNewWifiAP(wifiinfo[0],wifiinfo[1])

                    if flag == False:
                        ret = 'wifiresultfail'

                    print "addNewWifiAP resullt : ",ret
                    sendTargetClient(ret,cid)
                elif ctype == 'poweroff':
                    os.system('poweroff')
                elif ctype == 'stopsound':
                    print 'stopsound in'
                    os.system('pkill -9 omxplayer')
                    os.system('pkill -9 mplayer')
                    print 'Success'

                else:
                    print "BAD REQUEST FROM CLIENT"

        except IOError:
            pass
        self.sock.close()
        #g_productuser.pop(self.cid,None)
        removeClient(self)
        print self.client_info, ": disconnected"


class BTServerThread(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.activated = True

    def run(self):
	server_sock=BluetoothSocket( RFCOMM )
	server_sock.bind(("",PORT_ANY))
	server_sock.listen(1)

	port = server_sock.getsockname()[1]

	uuid = "94f39d29-7d6d-437d-973b-fba39e49daae"
	advertise_service( server_sock, "Mynah-raspberry-BT-Server",
                   service_id = uuid,
                   service_classes = [ uuid, SERIAL_PORT_CLASS ],
                   profiles = [ SERIAL_PORT_PROFILE ], #protocols = [ OBEX_UUID ] 
                   )
        print "Mynah-bluetooth server activated"
        while self.activated:
            client_sock, client_info = server_sock.accept()
            print "Accepted connection from ", client_info
            echo = ClientProcessThread(client_sock,client_info)
            echo.daemon = True;
            echo.start()

    def stopServer(self):
        server_sock.close();
        self.activated = False

class MynahManager:
    DIRECTION_TYPE_TO_OUT = 0x1010
    DIRECTION_TYPE_TO_IN = 0x1011
    DIRECTION_TYPE_NONE = 0x1111

    def __init__(self):
        self.s1Activated = False
        self.s2Activated = False
        self.t = 0
        #self.detectThreshold = 0.00
        self.directionFlag = self.DIRECTION_TYPE_NONE

    def sigHandler(self):
        g_lock.acquire()
        self.s1Activated = False
        self.s2Activated = False
        self.directionFlag = self.DIRECTION_TYPE_NONE
        print "Timeout"
        self.t = 0
        g_lock.release()
        return 0

    def processInternal(self):
        if self.t != 0:
            self.t.cancel()
            print "Timer Cancelled"
            self.t = 0

        #if self.directionFlag == self.DIRECTION_TYPE_TO_OUT:
        global g_messagelist
        global g_clientlist
        g_messagelist = []
        if self.directionFlag == self.DIRECTION_TYPE_TO_OUT:
            broadcastCurrentClient("outtts")
        else:
            broadcastCurrentClient("intts")

        targetlen = len(g_clientlist)
        print "target len : ",targetlen;
        while len(g_messagelist) < targetlen:
            time.sleep(0.1)
        brssi = -200
        msg = ""
        cid = ""
        for rssi,data,tid in g_messagelist:
            print "rssi : ",rssi," data : ",data
            if brssi < rssi:
                brssi = rssi
                msg = data
                cid = tid

        print "Best rssi measure : ",brssi
        #msg = g_messagelist[0]
        print "id : ",cid," must be nearest!!!"

        if self.directionFlag == self.DIRECTION_TYPE_TO_OUT:
            if brssi == -200:
                os.system('mplayer '+g_defaultpath+'no_connected_device.mp3')
            else:
                try:
                    usermediaobj = g_productuser[cid];
                    policy = usermediaobj.getPolicy();
                    print cid + ' policy is ' + policy
                    if policy == 'n':
                        os.system('mplayer '+g_defaultpath+'nothing.mp3')
                    elif policy == 't':
                        filename = usermediaobj.getTTSFileName()
                        if filename == '':
                            os.system('mplayer '+g_defaultpath+'nothing.mp3')
                        else:
                            os.system('mplayer '+g_defaultpath+filename)
                    elif policy == 'r':
                        filename = usermediaobj.getRECFileName()
                        if filename == '':
                            #os.system('mplayer '+g_defaultpath+'nothing.mp3')
                            print 'no REC files'
                        else:
                            os.system('mplayer '+g_defaultpath+filename)
                    elif policy == 'b':
                        ttsfilename = usermediaobj.getTTSFileName()
                        recfilename = usermediaobj.getRECFileName()
                        print 'ttsfilename : '+ttsfilename
                        if ttsfilename == '':
                            os.system('mplayer '+g_defaultpath+'nothing.mp3')
                        else:
                            os.system('mplayer '+g_defaultpath+ttsfilename)
                        if recfilename == '':
                            #os.system('mplayer '+g_defaultpath+'nothing.mp3')
                            print "NO REC files"
                        else:
                            os.system('mplayer '+g_defaultpath+recfilename)
                    else:
                        print "Unknown policy"
                    g_productuser[cid].makeClear()
                    time.sleep(2)
                except:
                    pass
            print "To Out Processing"
        else:
            os.system('mplayer '+g_defaultpath+'defaultin.mp3')
            ran = random.randrange(1,49)
            os.system('omxplayer -o local '+g_defaultpath+'music/T/'+str(ran)+'.mp3 --vol -500')
            print "Current Hour : "+str(datetime.datetime.now().hour)
            print "To In Processing"

    def check(self):
        #thread safe
        g_lock.acquire()
        if self.directionFlag == self.DIRECTION_TYPE_NONE:
            if self.s1Activated == True:
                self.directionFlag = self.DIRECTION_TYPE_TO_OUT

            elif self.s2Activated == True:
                self.directionFlag = self.DIRECTION_TYPE_TO_IN
            if self.t == 0:
                self.t = threading.Timer(7.0,self.sigHandler);
                self.t.start()
        else:
            if self.s1Activated == True and self.s2Activated == True:
                self.processInternal()
                self.s1Activated = False
                self.s2Activated = False
                self.directionFlag = self.DIRECTION_TYPE_NONE
        g_lock.release()

    def callbackFromS1(self):
        self.s1Activated = True
        print "callbackFromS1"
        self.check()

    def callbackFromS2(self):
        self.s2Activated = True
        print "callbackFromS2"
        self.check()

class DistanceSensor(threading.Thread):
    DETECT_THRESHOLD_VALUE = 0.265
    MAX_WAIT_VALUE = 10000
    MAX_RE_CAPTURE_TIME = 0.05

    def __init__(self,sensortype):
        threading.Thread.__init__(self)
        self.distance_sum = 0
        self.distance_cnt = 0
        self.seq_queue = Queue.Queue()
        self.sensortype = sensortype

        if sensortype == 1:
            self.ECHO = 23
            self.TRIG = 18
            self.validate = True
        elif sensortype == 2:
            self.ECHO = 22
            self.TRIG = 17
            self.validate = True
        else:
            self.ECHO = self.TRIG = -1
            self.validate = False

        #print "sensortype : ",sensortype,"validate",self.validate,"ECHO: ",self.ECHO,"TRIG",self.TRIG

    def ready(self):
        if self.validate == False:
            return;

        GPIO.setup(self.TRIG,GPIO.OUT)
        GPIO.setup(self.ECHO,GPIO.IN)
        GPIO.output(self.TRIG,GPIO.LOW)

    def trigger(self):
        if self.validate == False:
            return -1
        GPIO.output(self.TRIG,True)
        time.sleep(0.00001)
        GPIO.output(self.TRIG,False)

        try:
            ttp = 0;
            while GPIO.input(self.ECHO) == 0:
                pulse_start = time.time()
                ttp+=1
                if ttp >= self.MAX_WAIT_VALUE:
                    return -1

            ttp = 0

            while GPIO.input(self.ECHO) == 1:
                pulse_end = time.time()
                ttp+=1
                if ttp >= self.MAX_WAIT_VALUE:
                    return -1

            pulse_duration = pulse_end - pulse_start

            distance = pulse_duration*17150

            distance = round(distance,2)
            return distance
        except:
            return -1


    def run(self):
        while True:
            time.sleep(self.MAX_RE_CAPTURE_TIME)
            g_lock.acquire()
            dist = self.trigger()
            g_lock.release()
            #print "dist : ",dist
            if dist != -1:
                if self.distance_cnt < 5:
                    self.distance_cnt+=1
                    self.seq_queue.put(dist)
                    self.distance_sum += dist
                else:
                    #avg = self.distance_sum / self.distance_cnt
                    avg = 200
                    if dist < (avg*self.DETECT_THRESHOLD_VALUE):
                        print "sensor ",self.sensortype," Detect person : ",dist
                        if self.sensortype == 1:
                            g_Mynah.callbackFromS1()
                        else:
                            g_Mynah.callbackFromS2()
                    else:
                        dmin = avg*0.9
                        dmax = avg*1.1

                        #if dmin <= dist and dist <= dmax:
                        #    self.distance_sum -= self.seq_queue.get()
                        #    self.seq_queue.put(dist)
                        #    self.distance_sum += dist

                            #print "Sensor",self.sensortype," Distance : ",dist,"cm Avg :",avg

class UserMediaData:
    def __init__(self):
        self.ttsfile = ''
        self.recfile = ''
        self.filemap = {}
        self.policy = 'n'

    def isDuplicated(self,name):
        return name in self.filemap.keys()

    def setTTSFileName(self,name):
        if self.ttsfile != '' and self.ttsfile != name:
            os.remove(g_defaultpath+self.ttsfile)
        self.ttsfile = name

    def setRECFileName(self,name):
        if self.isDuplicated(name):
            print name+'is duplicated';
        else:
            self.filemap[name] = 1;
            self.recfile = name

    def setPolicy(self,pol):
        self.policy = pol
    def getPolicy(self):
        return self.policy
    def getRECFileName(self):
        return self.recfile
    def getTTSFileName(self):
        return self.ttsfile
    def makeClear(self):
        if self.recfile != '':
            os.remove(g_defaultpath+self.recfile)
        self.recfile = ''

g_Mynah = MynahManager()
g_serverthread = BTServerThread()
g_sensor1 = DistanceSensor(1)
g_sensor2 = DistanceSensor(2)

def main():
    #g_Mynah = MynahManager()

    #g_readtempthread = ReadCurrentTempThread("20:15:04:24:13:32")
    #g_readtempthread.daemon = True
    #g_readtempthread.start()

    #g_serverthread = BTServerThread();
    g_serverthread.daemon = True
    g_serverthread.start();

    #g_sensor1 = DistanceSensor(1)
    g_sensor1.ready()
    g_sensor1.daemon = True
    g_sensor1.start()

    #g_sensor2 = DistanceSensor(2)
    g_sensor2.ready()
    g_sensor2.daemon = True
    g_sensor2.start()

    while True:
        #g_lock.acquire()
        data = requestHTTPS('get_devices','','','')
        if data == False:
            print 'FATAL ERROR : Server is not responding'
            #g_lock.release()
        else:
            print 'Request deviceids from product2'
            for pp in data['attach']:
                if pp['device_id'] in g_productuser.keys():
                    print pp['device_id']+'is already in'
                else:
                    g_productuser[pp['device_id']] = UserMediaData()
            print g_productuser
            print 'Request file from server'
            for key in g_productuser.keys():
                print key +' request file'
                data = requestHTTPS('get_media',key,'','')
                if data == False:
                    print 'Current Web Request Failure'
                else:
                    targetlen = data['attach']
                    #record voice
                    recname = data['attach']['rec_file_name'];
                    if recname == '':
                        print key+' : No recorded voice'
                    else:
                        print key+' : recorded file exists ' +recname
                        if g_productuser[key].isDuplicated(recname):
                            print recname + 'is already exist'
                        else:
                            filedata = base64.decodestring(data['attach']['rec_file'])
                            g_lock.acquire()
                            fp = open(g_defaultpath+recname,'w')
                            fp.write(filedata)
                            fp.close()
                            g_lock.release();
                            g_productuser[key].setRECFileName(recname)
                    ttsname = data['attach']['tts_file_name']
                    if ttsname == '':
                        print key+' : No tts file'
                    else:
                        filedata = base64.decodestring(data['attach']['tts_file'])
                        g_lock.acquire()
                        fp = open(g_defaultpath+ttsname,'w')
                        fp.write(filedata)
                        fp.close()
                        g_lock.release()
                        g_productuser[key].setTTSFileName(ttsname)
                    policy = data['attach']['opt']
                    print key+' : policy ' +policy
                    g_productuser[key].setPolicy(policy)
        wifiIsAvailable()
        time.sleep(10)
        i=1

g_finalizeflag = False

def finalize():
    global g_finalizeflag
    print 'finalize : ',g_finalizeflag
    if g_finalizeflag:
        print 'finalize mynah, GOOD BYE!'
        global g_finalizeflag
        global g_ledRightBluetooth
        global g_ledLeftWifi

        g_finalizeflag = True

        g_ledRightBluetooth.setToggle(False,False)
        g_ledLeftWifi.setToggle(False,False)

        g_ledRightBluetooth.join()
        g_ledLeftWifi.join()

        GPIO.cleanup()

if __name__ == '__main__':
    try:
        init()
        main()
    except KeyboardInterrupt:
        GPIO.cleanup()
        g_serverthread.stopServer()
    GPIO.cleanup()
    g_serverthread.stopServer()
    print "all done"
