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

var basic_jobj = {
		messagetype : "",
		result : "",
		attach : ""
}



//구글에서 발행한 key
var server_access_key = 'AIzaSyAn_popA7yY_6O8U8yDcDv1Z3_lDGdtGxM';
var sender = new gcm.Sender(server_access_key);



//안드로이드 기계의 구글 플레이 서비스의 id
//var registration_id = 'APA91bFY7kMYOa_DhHqEOrP9QOia_oUI9eH41NcEulVxrxlDY4DdPaPFN8irJeFKIzs7GfTu5Q4-1XQRIThFUcvTSdxFeus4gyQCXylncybCA49FYvPf383ZagEiuYevMY8itcGWHU3b5HP0fu_FGq4snSCEevZvlTtd33ES3KhypCUw0m2u1KI';
//var registration_id = 'APA91bHVVW5FOCDW4P9LYnsycJvmoL43nzy0rzadNcNyI4aS-J0rzRMy1AUG5zRXrgPZqm-FgEg9_cLrWIALdB0NJQh53vlh9Xn8NJEO-kuqAwAu2MPJCP7H1RFg9-VmK2T2jiqVsvsM';
var registration_id = 'APA91bGiBhL4BhyqaDv7DSlVMwbwYKIUG24ymSUPox6K7u05XVB8NSGWylJDQnQiwrw0rd5c9Ok-039WjEolpRLl32aLYAlL1Kc2SDJZsFqVl1cylP_o5Yvhbf6Rd_ehf2ovdeFIrj2C';
//var registration_id = 'APA91bH_0SCR8Jl0oIP8c4L6Mx9BcNdQrAOR-TETrC2MxGWQODAF-AZ8E8vcS6qO78bPU1iQmLT39prFxVZx7dCv4qE3TSD3oDJ-zXW6cYWP0scoQUZs5yNvVAhA-hctC3tl9beRbHC4';

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

//https를 통해 정상적으로 request가 왔을 때 실행되는 함수, request 종류에 따라 알맞는 작업 수행 후 다시 app으로 response
function HttpsEventProcessCallback(req, res) {
	// console.log(req);
	res.writeHead(200, {
		'Content-Type' : 'text/plain; charset=utf-8'
	});

	//res.write('안녕하세요');
	var date = new Date();
	console.log("\r\nhttps request arriving! " + date.toUTCString() + "\r\n");

	
	
	 

	//post방식으로 메세지가 정상 도착 했을 때
	if (req.method === 'POST') {
		console.log('post!!');
		// use multiparty module, 멀티파티 사용하지 않을 예정
		// res.end('POST request');
		//var form = new multiparty.Form();
		//form.uploadDir = 'C:/Users/HJHOME/workspace/hello';

		/*
		 * form.parse(req, function(err, fields, files){ ProcessPostRequest(res,
		 * err, fields, files); });
		 */
		
		console.log('post request!');

		var chunk = ""; //json 받는 변수

		req.on('data', function(data) {
			//console.log(data.toString());
			var decoded_data = new Buffer(data.toString(), 'base64').toString(); // base64 인코딩 된 json을 base64로 다시 디코딩
			//console.log(decoded_data);
			chunk = JSON.parse(decoded_data);// json을 파싱하여 JSON OBJECT 형태로 변수에 저장
			console.log(chunk);
		});
		req.on('end', function() {
			res.writeHead(200, {
				'Content-Type' : 'text/plain; charset=utf-8'
			});
			var messagetype = chunk.messagetype;//messagetype, 이것에 따라서 하는 일이 바뀜
			console.log("messagetype : " + messagetype);
			
			if (messagetype == 'login') {
				//login 시;
				
				//gcm 이용 push 보내
				var registration_ids = [];
				var gcm_message = new gcm.Message({
					collapseKey : 'demo',
					delayWhileIdle : true,
					timeToLive : 3,
					data : {
						key1 : 'hello',
						key2 : 'no_permission_person'
					}
				});
				registration_ids.push(registration_id)
				sender.send(gcm_message, registration_ids, 4, function(err, result){
					console.log(result);
				});
				
				console.log("family_id : " + chunk.user_id);
				var sql_query = "SELECT * FROM user_info WHERE user_id=\'"
						+ chunk.user_id + "\';";
				console.log(sql_query);
				
				//id로 먼저 검색, 없을 시 아이디가 없다고 respond
				var is_exist_id = true;
				var query = connection.query(sql_query, function(err, rows, fields) {
					if (err) {
						console.log(chunk.user_id + ' log in error');
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "LOGIN_ERROR";
						res.end(JSON.stringify(jobj));
						//res.end('LOGIN_ERROR');
						//throw err;
					} else {
						console.log(rows.length);
						if (rows.length == 0) {
							//id가 없을 때 id가 없다고 메세지 보냄. 
							is_exist_id = false;
							console.log(chunk.user_id + ' no id');
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "LOGIN_FAIL_ID";
							res.end(JSON.stringify(jobj));
							//res.end('LOGIN_FAIL_ID');
						}
						else{
							console.log("id exist");
							console.log("family_id : " + chunk.user_id + " / password : " + chunk.password);
							sql_query = "SELECT * FROM user_info WHERE user_id=\'" + chunk.user_id + "\' and password=\'" + chunk.password + "\';";
							console.log(sql_query);
							query = connection.query(sql_query, function(err, rows, fields) {
								if (err) {
									console.log(chunk.user_id + ' log in error');
									var jobj = basic_jobj;
									jobj.messagetype = messagetype;
									jobj.result = "LOGIN_ERROR";
									res.end(JSON.stringify(jobj));
									//res.end('LOGIN_ERROR');
									//throw err;
								} else {
									if (rows.length == 0) {
										//id는 있는데 passwd와 함께 검색이 안된다면 비밀번호가 틀린 것;
										console.log(chunk.user_id + ' log in fail passwd');
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "LOGIN_FAIL_PASSWD";
										res.end(JSON.stringify(jobj));
										//res.end('LOGIN_FAIL_PASSWD');
									} else {
										//로그인 성공
										console.log(chunk.user_id + ' log in success');
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "LOGIN_SUCCESS";
										res.end(JSON.stringify(jobj));
										//res.end('LOGIN_SUCCESS');
									}
								}
							});
						}
					}
				});

				/*
				if (is_exist_id) {
					//id는 존재할 때 passwd와 함께 다시 검사;
					console.log("id exist");
					console.log("family_id : " + chunk.family_id + " / password : " + chunk.password);
					sql_query = "SELECT * FROM family_info WHERE family_id=\'" + chunk.family_id + "\' and password=\'" + chunk.password + "\';";
					console.log(sql_query);
					query = connection.query(sql_query, function(err, rows, fields) {
						if (err) {
							res.end('LOGIN_ERROR');
							//throw err;
						} else {
							if (rows.length < 1) {
								//id는 있는데 passwd와 함께 검색이 안된다면 비밀번호가 틀린 것;
								res.end('LOGIN_FAIL_PASSWD');
							} else {
								//로그인 성공
								res.end('LOGIN_SUCCESS');
							}
						}
					});
				}
				*/

				//res.end('로그인');
			} else if (messagetype == 'send_tts') {
				//app이 서버로 tts를 라즈베리파이로 전송하길 요청할 때
				console.log(encodeURI(chunk.sentense));
				tts.translate('ko', chunk.sentense, function(result) {
					if (result.success) { // check for success
						var response = {
							'audio' : result.data
						};
						console.log(result);
						//res.end('TTS SUCCESS ' + result.audio);
						res.end('SEND_TTS_SUCCESS');
						base64_decode(result.audio, 'decodetmp.mp3');
						// socket.emit('ttsResult', response); //emit
						// the audio to client

						//console.log(response);
					} else {
						//res.end('TTS FAIL ' + chunk.sentense);
						res.end('SEND_TTS_FAIL');
						console.log('google tts FAIL');
						console.log(result);
					}

				});
				console.log("sentense : " + chunk.sentense);
			}
			else if(messagetype == 'product_check'){
				var sql_query = "SELECT * FROM product_sub_info WHERE product_id=\'"+ chunk.product_id + "\' LIMIT 1;";
				console.log(sql_query);
				
				var query = connection.query(sql_query, function(err, rows, fields) {
					if (err) {
						console.log(chunk.product + 'product check error');
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "PRODUCT_ERROR";
						res.end(JSON.stringify(jobj));
						//res.end('PRODUCT_ERROR');
						//throw err;
					} else {
						console.log(rows.length);
						if (rows.length == 0) {
							//id가 없을 때 id가 없다고 메세지 보냄. 
							console.log(chunk.product_id + ' no product id');
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "PRODUCT_NOT_EXIST";
							res.end(JSON.stringify(jobj));
							//res.end('PRODUCT_NOT_EXIST');
						}
						else{
							console.log("id exist");
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "PRODUCT_EXIST//"+rows[0].mac_address+"//"+rows[0].uuid;
							res.end(JSON.stringify(jobj));
							//res.end('PRODUCT_EXIST');
						}
					}
				});
			}
			/*
			else if (messagetype == 'signup_first') {
				//가족 구성원중 첫 가입자. 기계를 등록하고 가족 id를 등록하고 본인의 id도 등록해야함.
				var sql_query = "INSERT INTO user_info values(\'"
						+ chunk.user_id + "\', \'" + chunk.family_id + "\', \'"
						+ chunk.registration_id + "\', \'" + chunk.RRN
						+ "\', \'" + chunk.user_name + "\', "
						+ chunk.gender_flag + ", " + chunk.representative_flag
						+ ", " + chunk.in_home_flag + ")";
				//var sql_query = "INSERT INTO user_info VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				//var data = [chunk.]
				console.log('query : ' + sql_query);

				//connection.query(sqlQueryTest, callback);

				var query = connection.query(sql_query, function(err) {
					if (err) {
						console.log('err : ' + err);
						//console.log(query.sql);
						res.end("SIGNUP_FIRST_FAIL");
						//throw err;
					} else {
						res.end("SIGNUP_FIRST_SUCCESS");
					}
				});

				console.log('sign_up OK ' + chunk.user_id);
				//res.end('sign_up OK');
			}
			*/ 
			else if (messagetype == 'signup') {
				//첫 가족 구성원이 가족의 대표 id를 만든 후의 가입자 생성
				var sql_query = "INSERT INTO user_info values(\'"
						+ chunk.user_id + "\', \'" + chunk.product_id + "\', \'"
						+ chunk.registration_id + "\', \'" + chunk.user_name + "\', "
						+ chunk.gender_flag + ", " + chunk.representative_flag
						+ ", " + chunk.in_home_flag + ", \'"+ chunk.device_id + "\', \'" + chunk.password +"\', now())";
				//var sql_query = "INSERT INTO user_info VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				//var data = [chunk.]
				console.log('query : ' + sql_query);

				//connection.query(sqlQueryTest, callback);

				var query = connection.query(sql_query, function(err) {
					if (err) {
						console.log('err : ' + err);
						//console.log(query.sql);
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "SIGNUP_FAIL";
						res.end(JSON.stringify(jobj));
						//res.end("SIGNUP_FAIL");
						//throw err;
					} else {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "SIGNUP_SUCCESS";
						res.end(JSON.stringify(jobj));
						//res.end("SIGNUP_SUCCESS");
					}
				});

				console.log('sign_up OK ' + chunk.user_id);
				//res.end('sign_up OK');
			} else if (messagetype == 'member_check') {
				//device_id로 회원이엿는지 검사
				//console.log("id_duplicate_check");
				var sql_query = "SELECT * FROM user_info WHERE device_id=\'" + chunk.device_id + "\';";
				//console.log("id : " + chunk.id + " / passwd : " + chunk.passwd);

				//id로 먼저 검색, 없을 시 아이디가 없다고 respond
				var query = connection.query(sql_query, function(err, rows,
						fields) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "MEMBER_CHECK_ERROR";
						res.end(JSON.stringify(jobj));
						//res.end('ID_DUPLICATE_ERROR');
						//throw err;
					} else {
						if (rows.length == 0) {
							//회원 아닌거같애
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "IS_NOT_MEMBER";
							res.end(JSON.stringify(jobj));
							//res.end('ID_NOT_DUPLICATE');
						} else {
							//회원인듯
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "IS_MEMBER";
							res.end(JSON.stringify(jobj));
							//res.end('ID_DUPLICATE');
						}
					}
				});
			}
			else if(messagetype == 'get_user_info'){
				//사용자 id를 받아서 그 id로 정보 받아서 앱으로 보내줘
				var sql_query = "SELECT * FROM user_info WHERE device_id=\'" + chunk.device_id + "\' LIMIT 1;";
				
				var query = connection.query(sql_query, function (err, rows, fields) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "GET_USER_INFO_ERROR";
						res.end(JSON.stringify(jobj));
						//throw err;
					}
					else{
						if(rows.length == 0){
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_USER_INFO_FAIL";
							res.end(JSON.stringify(jobj));
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_USER_INFO_SUCCESS";
							
							/*
							var user_jobj ={
								user_id : rows[0].user_id,
								product_id : rows[0].product_id,
								registration_id : rows[0].registration_id,
								user_name : rows[0].user_name,
								gender_flag : rows[0].gendar_flag,
								representative_flag : rows[0].represendative_flag,
								in_home_flag : rows[0].in_home_flag,
								device_id : rows[0].device_id,
								inout_time : rows[0].inout_time
							}
							*/
							jobj.attach = rows[0];
							
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							jobj.attach = "";
							
						}
						
					}
				});
				basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
				
				
			}
			else if(messagetype == 'get_family_inout'){
				//product id 받아서 가족들 목록 다보냄 시발
				var sql_query = "SELECT * FROM user_info WHERE product_id=\'" + chunk.product_id + "\' ;";
				console.log(sql_query);
				console.log("product id request : "+chunk.product_id);
				
				var query = connection.query(sql_query, function (err, rows, fields) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "GET_FAMILY_INOUT_ERROR";
						console.log("GET_FAMILY_INOUT_ERROR");
						res.end(JSON.stringify(jobj));
						//throw err;
					}
					else{
						if(rows.length == 0){
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_FAMILY_INOUT_FAIL";
							console.log("GET_FAMILY_INOUT_FAIL");
							res.end(JSON.stringify(jobj));
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							console.log("GET_FAMILY_INOUT_SUCCESS");
							jobj.result = "GET_FAMILY_INOUT_SUCCESS";
							
							
							var users = [];
							for (var i = 0; i < rows.length; i++) {
								console.log(rows[i].user_id);
								users.push(rows[i]);
							}
							/*
							var user_jobj ={
								user_id : rows[0].user_id,
								product_id : rows[0].product_id,
								registration_id : rows[0].registration_id,
								user_name : rows[0].user_name,
								gender_flag : rows[0].gendar_flag,
								representative_flag : rows[0].represendative_flag,
								in_home_flag : rows[0].in_home_flag,
								device_id : rows[0].device_id,
								inout_time : rows[0].inout_time
							}
							*/
							jobj.attach = rows;
							
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							jobj.attach = "";
						}
						
					}
				});
				basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
			}
			else if(messagetype == 'is_in_family'){
				//product id, device_id 받아서 이새끼 가족인가 판단함
				var sql_query = "SELECT * FROM user_info WHERE product_id=\'" + chunk.product_id + "\' and device_id=\'" + chunk.device_id + "\' ;";
				console.log("product_id, device_id request(is_in_family) : "+chunk.product_id + ", " + chunk.device_id);
			
				var moving_user_name;
				var moving_is_in_home;
				var query = connection.query(sql_query, function (err, rows, fields) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "IS_IN_FAMILY_ERROR";
						console.log('IS_IN_FAMILY_ERROR');
						res.end(JSON.stringify(jobj));
						//throw err;
					}
					else{
						if(rows.length == 0){
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "IS_IN_FAMILY_NO";
							console.log('IS_IN_FAMILY_NO');
							res.end(JSON.stringify(jobj));
							
							var get_regid_sql_query = "SELECT * FROM user_info WHERE product_id=\'" + chunk.product_id + "\'" 
							//+ " and device_id <> \'" + chunk.device_id + "\'"
							+ ";";
							
							var get_regid_query = connection.query(get_regid_sql_query, function (err, rows, fields) {
								if (err) {
									console.log("get_regid_query error");
									throw err;
								}
								else{
									console.log("get family regid by product_id success");
									
									var registration_ids = [];
									var gcm_message = new gcm.Message({
										collapseKey : 'demo',
										delayWhileIdle : true,
										timeToLive : 3,
										data : {
											key1 : 'is_in_family',
											key2 : 'no_permission_person'
										}
									});
									for (var i = 0; i < rows.length; i++) {
										console.log(rows[i].registration_id);
										registration_ids.push(rows[i].registration_id);
									}
									console.log("gcm to whom ? : " + registration_ids);
									
									//gcm 이용 push 보내
									sender.send(gcm_message, registration_ids, 4, function(err, result){
										console.log(result);
									});
									console.log("gcm send!");
								}
							});
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "IS_IN_FAMILY_OK";
							console.log('IS_IN_FAMILY_OK');
							res.end(JSON.stringify(jobj));
							
							moving_user_name = rows[0].user_name;
							moving_is_in_home = rows[0].in_home_flag;
							
							console.log('inoutsqlquery go');
							var in_out_sql_query = "UPDATE user_info SET inout_time = now(), in_home_flag=" + chunk.is_in_home+" WHERE device_id = \'"+chunk.device_id+"\' ;";
							
							console.log(in_out_sql_query);
							
							var in_out_query = connection.query(in_out_sql_query, function (err) {
								if (err) {
									console.log("in_out_query error");
									throw err;
								}
								else{
									//쿼리 잘 들어가면 product_id로 걸린사람들 다 찾아서 gcm
									console.log("in out update success");
									var get_regid_sql_query = "SELECT * FROM user_info WHERE product_id=\'" + chunk.product_id + "\'" 
									//+ " and device_id <> \'" + chunk.device_id + "\'"
									+ ";";
									
									var get_regid_query = connection.query(get_regid_sql_query, function (err, rows, fields) {
										if (err) {
											console.log("get_regid_query error");
											throw err;
										}
										else{
											console.log("get family regid by product_id success");
											
											var registration_ids = [];
											var gcm_message = new gcm.Message({
												collapseKey : 'demo',
												delayWhileIdle : true,
												timeToLive : 3,
												data : {
													key1 : 'is_in_family',
													key2 : 'refresh_in_out_status',
													key3 : moving_user_name,
													key4 : moving_is_in_home
												}
											});
											for (var i = 0; i < rows.length; i++) {
												console.log(rows[i].registration_id);
												registration_ids.push(rows[i].registration_id);
											}
											console.log("gcm to whom ? : " + registration_ids);
											
											//gcm 이용 push 보내
											sender.send(gcm_message, registration_ids, 4, function(err, result){
												console.log(result);
											});
											console.log("gcm send!");
										}
									});
									
								}
							});	
						}
						
					}
				});
			
			}
			else if(messagetype == 'set_temperature'){
				//온도갱신 product_id 받아서
				var sql_query = "UPDATE product_sub_info SET temperature=" + chunk.temperature+" WHERE product_id = \'"+chunk.product_id+"\' ;";
				console.log("product_id, temperature request : "+chunk.product_id + ", " + chunk.temperature);
				
				var query = connection.query(sql_query, function (err) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "SET_TEMPERATURE_ERROR";
						console.log("SET_TEMPERATURE_ERROR");
						res.end(JSON.stringify(jobj));
						//throw err;
					}
					else{
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "SET_TEMPERATURE_SUCCESS";
						console.log("SET_TEMPERATURE_SUCCESS");
						res.end(JSON.stringify(jobj));
					}
				});
			}
			else if(messagetype == 'get_temperature'){
				//product_id 받아서 온도가지고 ㄱㄱ
				var sql_query = "SELECT * FROM product_sub_info WHERE product_id=\'" + chunk.product_id + "\' LIMIT 1;";
				console.log("get_temp go");
				
				var query = connection.query(sql_query, function (err, rows, fields) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "GET_TEMPERATURE_ERROR";
						console.log("GET_TEMPERATURE_ERROR");
						res.end(JSON.stringify(jobj));
						//throw err;
					}
					else{
						if(rows.length == 0){
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_TEMPERATURE_FAIL";
							console.log("GET_TEMPERATURE_FAIL");
							res.end(JSON.stringify(jobj));
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							console.log("GET_TEMPERATURE_SUCCESS");
							jobj.result = "GET_TEMPERATURE_SUCCESS";
							
							jobj.attach = rows[0];
							
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							jobj.attach = "";
						}
						
					}
				});
				basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
			}
			else if(messagetype == 'delete_user'){
				//device id 받아서 디비에서 지워
				var sql_query = "DELETE FROM user_info" + " WHERE device_id = \'"+chunk.device_id+"\' ;";
				console.log("product_id  : "+chunk.product_id);
				
				var query = connection.query(sql_query, function (err) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "DELETE_USER_ERROR";
						console.log("DELETE_USER_ERROR");
						res.end(JSON.stringify(jobj));
						//throw err;
					}
					else{
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "DELETE_USER_SUCCESS";
						console.log("DELETE_USER_SUCCESS");
						res.end(JSON.stringify(jobj));
					}
				});
			}
			else if(messagetype == 'get_product_sub_info'){
				//product_id 받으면 부가정보 보내
				var sql_query = "SELECT * FROM product_sub_info WHERE product_id=\'" + chunk.device_id + "\' LIMIT 1;";
				
				var query = connection.query(sql_query, function (err, rows, fields) {
					if (err) {
						var jobj = basic_jobj;
						jobj.messagetype = messagetype;
						jobj.result = "GET_PRODUCT_SUB_INFO_ERROR";
						res.end(JSON.stringify(jobj));
						//throw err;
					}
					else{
						if(rows.length == 0){
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_PRODUCT_SUB_INFO_FAIL";
							res.end(JSON.stringify(jobj));
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_PRODUCT_SUB_INFO_SUCCESS";
							
							/*
							var user_jobj ={
								user_id : rows[0].user_id,
								product_id : rows[0].product_id,
								registration_id : rows[0].registration_id,
								user_name : rows[0].user_name,
								gender_flag : rows[0].gendar_flag,
								representative_flag : rows[0].represendative_flag,
								in_home_flag : rows[0].in_home_flag,
								device_id : rows[0].device_id,
								inout_time : rows[0].inout_time
							}
							*/
							jobj.attach = rows[0];
							
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							jobj.attach = "";
							
						}
						
					}
				});
				basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
			}
			else{
				//그냥 https 리퀘스트였을 때
				res.end('Hi There');
			}

		});
		// res.end("한글 ");
		//res.end('Hi There');

	}
	else {
		// 포스트가 아니면 에러
		res.writeHead(200, {
			'Content-Type' : 'text/plain; charset=utf-8'
		});
		console.log('NOT POST REQUEST');
		res.end('wrong request');
	}
}

function base64_decode(base64str, file) {
	// create buffer object from base64 encoded string, it is important to tell the constructor that the string is base64 encoded
	var bitmap = new Buffer(base64str, 'base64');
	// write buffer to file
	fs.writeFileSync(file, bitmap);
	console.log('******** File created from base64 encoded string ********');
}

//http 리퀘스트 함수. 임시.
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
connection.connect();
console.log('Server running at https://192.168.35.75:13337/');

// httpServer.listen(port, '192.168.35.75');
// console.log('Server running at http://192.168.35.75:13337/');

//connection.connect();
//connection.query(sqlQueryTest, callback);
//connection.end();