var http = require('http');
var https = require('https');
var fs = require('fs');
var crypto = require('crypto');
var multiparty = require('multiparty');
var util = require('util');
var mysql = require('mysql');
var tts = require('node-google-text-to-speech')
var gcm = require('node-gcm');

//var gcm_message = new gcm.Message();

var gcm_message = new gcm.Message({
	collapseKey : 'demo',
	delayWhileIdle : true,
	timeToLive : 3,
	data: {
		key1 : 'Hello',
		key2 : 'push demo 죽고싶다'
	}
});
d
var server_access_key = 'AIzaSyAn_popA7yY_6O8U8yDcDv1Z3_lDGdtGxM';
var sender = new gcm.Sender(server_access_key);

var registration_ids = [];

var registration_id = 'APA91bFY7kMYOa_DhHqEOrP9QOia_oUI9eH41NcEulVxrxlDY4DdPaPFN8irJeFKIzs7GfTu5Q4-1XQRIThFUcvTSdxFeus4gyQCXylncybCA49FYvPf383ZagEiuYevMY8itcGWHU3b5HP0fu_FGq4snSCEevZvlTtd33ES3KhypCUw0m2u1KI';

registration_ids.push(registration_id);

var connection = mysql.createConnection({
	host : "192.168.35.75",
	port : 3306,
	user : "mynah",
	password : "mynah",
	database : "mynah_test"
});

var sqlQueryTest = "SELECT * FROM users";

function callback(err, rows, fields) {
	if (err) {
		throw err;
	}
	for (var i = 0; i < rows.length; i++) {
		console.log(rows[i].seq + "|" + rows[i].id + "|" + rows[i].passwd);
	}
}

var httpsServer;
var httpServer;

// https verification options
var options = {
	key : fs.readFileSync('private_key.pem'),
	cert : fs.readFileSync('cert.pem')
};

// http port
var port = 13337;
// https port
var sslport = 13337;

function ProcessPostRequest(res, err, fields, files) {
	if (fields === undefined) {
		// 이 경우는 무시
		console.log("essential property is missing!");
		console.log(fields);

		res.writeHead(200, {
			'type' : 'text',
			'src' : 'NONE',
			'order' : 'null'
		});
		res.end('EMISSING');
		console.log("-status : EMISSING");
		return;
	}
	// 잘못된 요청

	if (fields.hasOwnProperty('id') === false || fields.hasOwnProperty('passwd') === false) {
		console.log("essential property is missing!");
		console.log(fields);

		res.writeHead(200, {
			'type' : 'text',
			'src' : 'NONE',
			'order' : 'null'
		});
		res.end('EMISSING');
		console.log("-status : EMISSING");
	} else {
		var uniqueNumber = fields.uniqueNumber;
		var type = fields.type;

		console.log("uniqueNumber : " + uniqueNumber);
		console.log("type : " + type);

		uniqueNumber = uniqueNumber.toString();
		type = type.toString();
	}
}

// functions
function HttpsEventProcessCallback(req, res) {
	// console.log(req);
	res.writeHead(200, {
		'Content-Type' : 'text/plain; charset=utf-8'
	});
	
	//res.write('안녕하세요');
	var date = new Date();
	console.log("\r\nhttps request arriving! " + date.toUTCString() + "\r\n");

	/*
	//gcm 이용 push 보내
	sender.send(gcm_message, registration_ids, 4, function(err, result){
		console.log(result);
	});
	*/
	
	if (req.method === 'POST') {
		// use multiparty module
		// res.end('POST request');
		var form = new multiparty.Form();
		form.uploadDir = 'C:/Users/HJHOME/workspace/hello';

		/*
		 * form.parse(req, function(err, fields, files){ ProcessPostRequest(res,
		 * err, fields, files); });
		 */

		var chunk = "";

		req.on('data', function(data) {
			console.log(data.toString());
			var decoded_data = new Buffer(data.toString(), 'base64').toString();
			console.log(decoded_data);
			chunk = JSON.parse(decoded_data);
		});

		req.on('end', function() {
					res.writeHead(200, {
						'Content-Type' : 'text/plain; charset=utf-8'
					});
					var messagetype = chunk.messagetype;
					console.log("messagetype : " + messagetype);

					if (messagetype == 'login') {
						console.log("id : " + chunk.id + " / passwd : " + chunk.passwd);
						res.end('로그인');
					} else if (messagetype == 'send_tts') {
						console.log(encodeURI(chunk.sentense));
						tts.translate('ko', chunk.sentense , function(result) {
							
							if (result.success) { // check for success
								var response = {
									'audio' : result.data 
								};
								console.log(result);
								res.end('TTS SUCCESS ' + result.audio);
								base64_decode(result.audio, 'decodetmp.mp3');
								// socket.emit('ttsResult', response); //emit
								// the audio to client
								
								//console.log(response);
							}
							else{
								res.end('TTS FAIL ' + chunk.sentense);
								console.log('google tts FAIL');
								console.log(result);
							}
							
						});
						console.log("sentense : " + chunk.sentense);
					}
				})
		//res.end("한글 ");
		//res.end('JSON OK');

	} else {
		// 포스트가 아니면 에러
		res.writeHead(200, {
			'Content-Type' : 'text/plain; charset=utf-8'
		});
		console.log('jal mot jup geun');
		res.end('한글 wrong request');
	}
}

function base64_decode(base64str, file) {
    // create buffer object from base64 encoded string, it is important to tell the constructor that the string is base64 encoded
    var bitmap = new Buffer(base64str, 'base64');
    // write buffer to file
    fs.writeFileSync(file, bitmap);
    console.log('******** File created from base64 encoded string ********');
}

function HttpEventProcessCallback(req, res) {
	// http request from port 8010

	res.writeHead(200, {
		'Content-Type' : 'text/plain'
	});
	res.end('request');
	var date = new Date();
	console.log("\r\nhttp request arriving!" + date.toUTCString() + "\r\n");
	// res.end('wrong request');

}

httpsServer = https.createServer(options, HttpsEventProcessCallback);
// httpServer = http.createServer(HttpEventProcessCallback);

// http.createServer(function handler(req, res) {
// res.writeHead(200, {'Content-Type': 'text/plain'});
// res.end('Hello World HJHOME\n');
// }).listen(sslport, '192.168.35.75');

httpsServer.listen(sslport, '192.168.35.75');
console.log('Server running at https://192.168.35.75:13337/');

// httpServer.listen(port, '192.168.35.75');
// console.log('Server running at http://192.168.35.75:13337/');

connection.connect();
connection.query(sqlQueryTest, callback);
connection.end();