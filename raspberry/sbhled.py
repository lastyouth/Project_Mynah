import threading
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)

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

    def setToggle(self,flag):
        self.toggle = True

    def onLED(self):
        self.on = True
        GPIO.output(self.out,GPIO.HIGH)

    def offLED(self):
        self.on = False
        GPIO.output(self.out,GPIO.LOW)

    def run(self):
        while self.toggle == True:
            print self.toggle,self.stype,self.out
            if self.on == True:
                self.offLED()
            else:
                self.onLED()
            time.sleep(0.5)



g_ledLeft = LEDSensor('left')
g_ledRight = LEDSensor('right')

g_ledLeft.daemon = True
g_ledRight.daemon = True

g_ledLeft.setToggle(True)
g_ledLeft.start()
g_ledRight.setToggle(True)
g_ledRight.start()

time.sleep(10)

g_ledLeft.setToggle(False)
g_ledRight.setToggle(False)

time.sleep(3)

GPIO.cleanup()
