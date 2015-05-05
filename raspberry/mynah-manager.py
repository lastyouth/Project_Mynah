# file: mynah-manager.py
# auth: sbh 

from bluetooth import *
import threading


# global variables

g_lock = threading.Lock()
g_Mynah = MynahManager()




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

        self.detectThreshold = 0.00
        self.directionFlag = DIRECTION_TYPE_NONE

    def sigHandler(signum, frame):
        return 0;

    def check(self):
        #thread safe
        g_lock.acquire()
        if self.directionFlag == DIRECTION_TYPE_NONE:
            if self.s1Activated == True:
                self.directionFlag == DIRECTION_TYPE_TO_OUT

            elif self.s2Activated == True:
                self.directionFlag == DIRECTION_TYPE_TO_IN
        else:
            if self.s1Activated == True and self.s2Activated:
                if self.directionFlag == DIRECTION_TYPE_TO_OUT:
                    print "To Out Processing"
                else:
                    print "To In Processing"
                self.s1Activated = False
                self.s2Activated = False
        g_lock.release()


    def callbackFromS1(self):
        self.s1Activated = True
        self.check()

    def callbackFromS2(self):
        self.s2Activated = True
        self.check()



serverthread = BTServerThread();
serverthread.daemon = True
serverthread.start();

while True:
    i=1

print "all done"
