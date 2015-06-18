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

# global variables

g_webhost = "1.227.248.51:13337"

g_lock = threading.Lock()

g_clientlock = threading.Lock()

GPIO.setmode(GPIO.BCM)

g_clientlist = {}

g_messagelist = []

g_currenttemp = 0

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

def broadcastCurrentClient(mtype):
    g_clientlock.acquire()
    for key, value in g_clientlist.iteritems():
        print "broadcast Key : ",key," type : ",mtype
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

        data = json.loads(response.read())

        print "result : ",data["result"]
        if data["result"] == "IS_IN_FAMILY_OK":
            return True
        return False
    except:
        print "Web request failure!"

#get temp from arduino

class ReadCurrentTempThread(threading.Thread):
    def __init__(self,mac_addr):
        threading.Thread.__init__(self)
        self.mac_addr = mac_addr

    def run(self):
        while(True):
            try:
                global g_currenttemp
                temp_socket = BluetoothSocket(RFCOMM)
                temp_socket.connect((self.mac_addr,1))
                temp_socket.send("hi")
                temperature = temp_socket.recv(3)
                temperature = int(temperature)
                temp_socket.close();
                #temperature = 28
                print "Current Temperature is updated : ",temperature
                g_currenttemp = temperature
                broadcastCurrentClient("temp : "+str(g_currenttemp))
                requestHTTPS("set_temperature","","temperature",str(g_currenttemp));
            except:
                pass

            time.sleep(10)



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
                else:
                    print "bad"

        except IOError:
            pass
        self.sock.close()
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
        print "Timeout";
        self.t = 0
        g_lock.release()
        return 0;

    def check(self):
        #thread safe
        g_lock.acquire()
        if self.directionFlag == self.DIRECTION_TYPE_NONE:
            if self.s1Activated == True:
                self.directionFlag = self.DIRECTION_TYPE_TO_OUT

            elif self.s2Activated == True:
                self.directionFlag = self.DIRECTION_TYPE_TO_IN
            if self.t == 0:
                #temp_socket = BluetoothSocket(RFCOMM)
                #temp_socket.connect(("30:14:12:00:29:10",1))
                #temp_socket.send("hi")
                #temperature = temp_socket.recv(1024)
                #print temperature
                #temp_socket.close()
                self.t = threading.Timer(7.0,self.sigHandler);
                self.t.start()
        else:
            if self.s1Activated == True and self.s2Activated == True:
                if self.t != 0:
                    self.t.cancel()
                    print "Timer Cancelled"
                    self.t = 0

                quote_str = " endend"
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
                print "id : ",cid," first message : ",msg," len : ",len(msg)
                #http request

                self.isNotFamily = False

                if self.directionFlag == self.DIRECTION_TYPE_TO_OUT:
                    self.isNotFamily = requestHTTPS("is_in_family",cid,"is_in_home","0")
                else:
                    self.isNotFamily = requestHTTPS("is_in_family",cid,"is_in_home","1")

                if self.isNotFamily == False:
                    print "Access Denied - Unauthorized Person Approching detected"
                    os.system('omxplayer -o local /home/pi/share/dog.mp3')
                    self.s1Activated = False
                    self.s2Activated = False
                    self.directionFlag = self.DIRECTION_TYPE_NONE
                    g_lock.release()
                    return
                self.usedefault = False
                if msg == "":
                    msg = "오늘도, 좋은하루, 되세요aa,."
                    self.usedefault = True
                remain_str = []

                if len(msg) - 3 <= 100:
                    #global remain_str
                    msg = msg.replace("///","")
                    remain_str.append(msg)
                else:
                    remain_str = msg.split("///")

                print "Msg preprocessed : ",remain_str," len : ",len(remain_str)

                for partial_msg in remain_str:
                    #partial_msg = ""
                    #if len(remain_str) >= 100:
                    #    partial_msg = remain_str[:100]
                    #    remain_str = remain_str.replace(partial_msg,"")
                    #else:
                    #    partial_msg = remain_str
                    #    remain_str = ""

                    print "partial message : ",partial_msg," len : ",len(partial_msg)


                    query = 'wget -q -U Mozilla -O hello_ko.mp3 "http://translate.google.com/translate_tts?ie=UTF-8&tl=ko&q='
                    query += partial_msg
                    #if remain_str == "":
                    query += quote_str+'"'
                    #else:
                    #    query += '"'
                    print "query : ",query
                    if self.usedefault:
                        os.system(query)
                    else:
                        os.system(query.encode("utf-8"))

                    os.system('omxplayer -o local hello_ko.mp3 --vol 1000')
                    os.remove('hello_ko.mp3')

                if self.directionFlag == self.DIRECTION_TYPE_TO_OUT:
                    print "To Out Processing"
                else:
                    os.system('omxplayer -o local --vol 250 /home/pi/share/rs.mp3')
                    #msg = '어서오세요. 오늘 하루도 수고하셨습니다.'
                    #msg+= quote_str + '"'
                    #query = 'wget -q -U Mozilla -O welcome.mp3 "http://translate.google.com/translate_tts?ie=UTF-8&tl=ko&q='
                    #query+=msg
                    #print "query : ",query
                    #os.system(query)
                    #os.system('omxplayer -o local welcome.mp3 --vol 1000')
                    #os.remove('welcome.mp3')

                    print "To In Processing"

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
    DETECT_THRESHOLD_VALUE = 0.30
    MAX_WAIT_VALUE = 10000
    MAX_RE_CAPTURE_TIME = 0.07

    def __init__(self,sensortype):
        threading.Thread.__init__(self)
        self.distance_sum = 0
        self.distance_cnt = 0
        self.seq_queue = Queue.Queue()
        self.sensortype = sensortype

        if sensortype == 1:
            self.ECHO = 26
            self.TRIG = 22
            self.validate = True
        elif sensortype == 2:
            self.ECHO = 21
            self.TRIG = 20
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
                    avg = self.distance_sum / self.distance_cnt

                    if dist < (avg*self.DETECT_THRESHOLD_VALUE):
                        print "sensor ",self.sensortype," Detect person : ",dist
                        if self.sensortype == 1:
                            g_Mynah.callbackFromS1()
                        else:
                            g_Mynah.callbackFromS2()
                    else:
                        dmin = avg*0.9
                        dmax = avg*1.1

                        if dmin <= dist and dist <= dmax:
                            self.distance_sum -= self.seq_queue.get()
                            self.seq_queue.put(dist)
                            self.distance_sum += dist

                            #print "Sensor",self.sensortype," Distance : ",dist,"cm Avg :",avg


g_Mynah = MynahManager()

g_readtempthread = ReadCurrentTempThread("20:15:04:24:13:32")
g_readtempthread.daemon = True
g_readtempthread.start()

g_serverthread = BTServerThread();
g_serverthread.daemon = True
g_serverthread.start();

g_sensor1 = DistanceSensor(1)
g_sensor1.ready()
g_sensor1.daemon = True
g_sensor1.start()

g_sensor2 = DistanceSensor(2)
g_sensor2.ready()
g_sensor2.daemon = True
g_sensor2.start()

while True:
    time.sleep(10000)
    #broadcastCurrentClient()
    #sleep(100)
    i=1

GPIO.cleanup()

print "all done"
