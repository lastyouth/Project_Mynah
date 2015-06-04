from bluetooth import *

client_socket = BluetoothSocket(RFCOMM)

client_socket.connect(("30:14:12:00:29:10",1))

print "connection established";

client_socket.send("hi")
buf = client_socket.recv(1024);

print buf

print "Finished"

client_socket.close()



