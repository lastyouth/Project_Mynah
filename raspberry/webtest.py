import httplib
import base64
import json

c = httplib.HTTPSConnection("1.227.248.51:13337")

reqobj = {}

reqobj['messagetype'] = "is_in_family"
reqobj['product_id'] = 'product2'
reqobj['device_id'] = "dba274cd84f7ea8c"#"6a5823bb465b4777"
reqobj['is_in_home'] = 0

#json.dumps(reqobj)

reqdata = base64.encodestring(json.dumps(reqobj));

print reqdata

c.request("POST","",reqdata)

response = c.getresponse()

print response.status, response.reason

data = response.read()

print data
