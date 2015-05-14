import httplib
import base64
import json

c = httplib.HTTPSConnection("211.108.75.252:13337")

reqobj = {}

reqobj["messagetype"] = "login"
reqobj["family_id"] = "family01"
reqobj["password"] = "1"

#json.dumps(reqobj)

reqdata = base64.encodestring(json.dumps(reqobj));

print reqdata

c.request("POST","",reqdata)

response = c.getresponse()

print response.status, response.reason

data = response.read()

print data
