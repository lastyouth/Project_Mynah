import RPi.GPIO as GPIO
import time
import Queue

GPIO.setmode(GPIO.BCM)


TRIG = 23
ECHO = 18

print "Distance Measurement In Progress"

GPIO.setup(TRIG,GPIO.OUT)

GPIO.setup(ECHO,GPIO.IN)

GPIO.output(TRIG,GPIO.LOW)

distance_sum = 0

distance_cnt = 0

sq = Queue.Queue()



while True:
    print "Waiting For Sensor To Settle"

    time.sleep(0.07)

    GPIO.output(TRIG,True)
    time.sleep(0.001)
    GPIO.output(TRIG,False)

    while GPIO.input(ECHO)==0:
        pulse_start = time.time()

    while GPIO.input(ECHO)==1:
        pulse_end = time.time()

    pulse_duration = pulse_end - pulse_start

    distance = pulse_duration * 17150

    distance = round(distance,2)

    print distance,'cm'
    if distance_cnt < 5:
        distance_cnt+=1
        sq.put(distance)
        distance_sum += distance
    else:
        avg = distance_sum / distance_cnt

        if distance < (avg / 2.0):
            print "Detect person"
        else:
            dmin = avg*0.9
            dmax = avg*1.1

            if dmin <= distance and distance <= dmax:
                distance_sum -= sq.get()
                sq.put(distance)
                distance_sum += distance
                print "Distance:",distance,"cm Avg : ",avg

GPIO.cleanup()
