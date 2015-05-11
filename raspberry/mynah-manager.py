# file: mynah-manager.py
# auth: sbh 

from bluetooth import *
import threading
import RPi.GPIO as GPIO
import time
import Queue


# global variables

g_lock = threading.Lock()
GPIO.setmode(GPIO.BCM)

class ClientProcessThread(threading.Thread):
    def __init__(self,sock,client_info):
        threading.Thread.__init__(self)
        self.sock = sock
        self.client_info = client_info

    def run(self):
        try:
            while True:
                data = self.sock.recv(1024);
                if len(data) == 0: break;
                print self.client_info, ": received [%s]" % data
        except IOError:
            pass
        self.sock.close()
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

        #self.detectThreshold = 0.00
        self.directionFlag = self.DIRECTION_TYPE_NONE

    def sigHandler(signum, frame):
        return 0;

    def check(self):
        #thread safe
        g_lock.acquire()
        if self.directionFlag == self.DIRECTION_TYPE_NONE:
            if self.s1Activated == True:
                self.directionFlag == self.DIRECTION_TYPE_TO_OUT

            elif self.s2Activated == True:
                self.directionFlag == self.DIRECTION_TYPE_TO_IN
        else:
            if self.s1Activated == True and self.s2Activated:
                if self.directionFlag == self.DIRECTION_TYPE_TO_OUT:
                    print "To Out Processing"
                else:
                    print "To In Processing"
                self.s1Activated = False
                self.s2Activated = False
                self.directionFlag = self.DIRECTION_TYPE_NONE
        g_lock.release()


    def callbackFromS1(self):
        self.s1Activated = True
        self.check()

    def callbackFromS2(self):
        self.s2Activated = True
        self.check()

class DistanceSensor(threading.Thread):
    DETECT_THRESHOLD_VALUE = 0.5
    MAX_WAIT_VALUE = 10000
    MAX_RE_CAPTURE_TIME = 0.5

    def __init__(self,sensortype):
        threading.Thread.__init__(self)
        self.distance_sum = 0
        self.distance_cnt = 0
        self.seq_queue = Queue.Queue()

        if sensortype == 1:
            self.ECHO = 26
            self.TRIG = 22
            self.validate = True
        elif sensortype == 2:
            self.ECHO = 16
            self.TRIG = 23
            self.validate = True
        else:
            self.ECHO = self.TRIG = -1
            self.validate = False

        print "sensortype : ",sensortype,"validate",self.validate,"ECHO: ",self.ECHO,"TRIG",self.TRIG

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


    def run(self):
        while True:
            time.sleep(self.MAX_RE_CAPTURE_TIME)
            dist = self.trigger()
            print "dist : ",dist
            if dist != -1:
                if self.distance_cnt < 5:
                    self.distance_cnt+=1
                    self.seq_queue.put(dist)
                    self.distance_sum += dist
                else:
                    avg = self.distance_sum / self.distance_cnt

                    if dist < (avg*self.DETECT_THRESHOLD_VALUE):
                        print "Detect person"
                    else:
                        dmin = avg*0.9
                        dmax = avg*1.1

                        if dmin <= dist and dist <= dmax:
                            self.distance_sum -= self.seq_queue.get()
                            self.seq_queue.put(dist)
                            self.distance_sum += dist

                            print "Distance : ",dist,"cm Avg :",avg


g_Mynah = MynahManager()

g_serverthread = BTServerThread();
g_serverthread.daemon = True
g_serverthread.start();

g_sensor1 = DistanceSensor(1)
g_sensor1.ready()
g_sensor1.daemon = True
g_sensor1.start()

while True:
    i=1

GPIO.cleanup()

print "all done"
