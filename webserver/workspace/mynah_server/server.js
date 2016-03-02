var http = require('http');
var https = require('https');
var fs = require('fs');
var crypto = require('crypto');
var multiparty = require('multiparty');
var util = require('util');
var mysql = require('mysql');
var gcm = require('node-gcm');

var formidable = require('formidable'); //file upload 위한 formidable

//var gcm_message = new gcm.Message();

var basic_jobj = {
		messagetype : "",
		result : "",
		attach : ""
}

//String을 Base64로 인코딩
function base64_encode(req_str){
	return new Buffer(req_str).toString('base64');
}

//구글에서 발행한 key
var server_access_key = 'AIzaSyAn_popA7yY_6O8U8yDcDv1Z3_lDGdtGxM';
var sender = new gcm.Sender(server_access_key);

//안드로이드 기계의 구글 플레이 서비스의 id
var registration_id = 'APA91bGiBhL4BhyqaDv7DSlVMwbwYKIUG24ymSUPox6K7u05XVB8NSGWylJDQnQiwrw0rd5c9Ok-039WjEolpRLl32aLYAlL1Kc2SDJZsFqVl1cylP_o5Yvhbf6Rd_ehf2ovdeFIrj2C';

var connection = mysql.createConnection({
	host : "localhost",
	port : 3306,
	user : "mynah",
	password : "mynah",
	database : "mynah_test"
});

var httpsServer;
var httpServer;

// https verification options
var options = {
	key : fs.readFileSync('server.key'),
	cert : fs.readFileSync('server.crt')
};

// http port
var port = 13337;
// https port
var sslport = 13337;

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
		console.log('post request!');
		console.log(req.url);
		
		if(req.url === '/recording'){
			var device_id;
			var rec_flag;
			
			var rec_file_name;
			console.log('recording upload start');
			
			var form = new formidable.IncomingForm();
			form.keepExtensions = true;
			form.uploadDir ='./recording';
			
			form
			    .on('error', function(err) {
			    	console.log('file receive error! ' + form.uploadDir + "/" + rec_file_name);
			    	
			    	/*
			    	var err_del_query = "DELETE FROM recording_info WHERE device_id = \'" + device_id + "\' ORDER BY rec_seq DESC LIMIT 1;"
				
					console.log('err_del_query : ' + err_del_query);
					
					var del_query = connection.query(err_del_query, function (err) {
						if(err){
							var jobj = basic_jobj;
							jobj.messagetype = "send_tts";
							jobj.result = "SEND_TTS_ERROR";
							console.log(JSON.stringify(jobj));
							res.end((JSON.stringify(jobj)));
							//throw err;
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = "send_tts";
							jobj.result = "SEND_TTS_FAIL";
							console.log(JSON.stringify(jobj));
							res.end((JSON.stringify(jobj)));
						}
					});
			    	*/
			    	
			        throw err;
			    })

			    .on('field', function(field, value) {
			        //receive form fields here
			    	
			    	if(field === 'device_id'){
			    		device_id = value;
			    		console.log('device_id : ' + device_id);
			    	}
			    	if(field === 'rec_flag'){
			    		rec_flag = value;
			    		console.log('rec_flag : ' + rec_flag);
			    	}
			    })

			    /* this is where the renaming happens */
			    .on ('fileBegin', function(name, file){
			        //rename the incoming file to the file's name
			    	var date = new Date();
			    	date.setHours(date.getHours() + 9);
			    	var date_time = date.toISOString().replace(/T/, '_').replace(/\..+/, '').replace(':', '').replace(':', '');
			    	rec_file_name = device_id + "_" + date_time + "_" + file.name;
			        file.path = form.uploadDir + "/" + rec_file_name;
			        console.log('file_name : ' + rec_file_name);
			    })

			    .on('file', function(field, file) {
			        //On file received
			    })

			    .on('progress', function(bytesReceived, bytesExpected) {
			        //self.emit('progess', bytesReceived, bytesExpected)

			        var percent = (bytesReceived / bytesExpected * 100) | 0;
			        process.stdout.write('Uploading: %' + percent + '\r\n');
			    })

			    .on('end', function() {
			    	
			    	var sql_query = "INSERT INTO recording_info"
			    		+ " (user_id, rec_flag, path, reg_date) "
						+ "VALUES("
						+ " (SELECT user_id from user_info where device_id = \'"+device_id+"\' LIMIT 1)" 
						+ ", " + rec_flag
						+ ", \'" + rec_file_name
						+ "\', now());";
					
					console.log('send_tts query : ' + sql_query);
					
					var query = connection.query(sql_query, function (err) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = "send_tts";
							jobj.result = "SEND_TTS_FAIL";
							console.log(JSON.stringify(jobj));
							res.end((JSON.stringify(jobj)));
							//throw err;
						}
						else{
							
							/*
							var rec_file_del_query = "SELECT * FROM recording_info WHERE rec_flag = " + rec_flag + " AND user_id = \'" + device_id + "\' ORDER BY rec_seq LIMIT 10, 1;";
							console.log('rec_file_del_query');
							*/
							
							var rec_del_query = "DELETE FROM recording_info WHERE rec_seq IN "
									+ "(SELECT rec_seq FROM (SELECT rec_seq FROM recording_info WHERE rec_flag = " + rec_flag + " AND user_id = "
									+ "(SELECT user_id from user_info where device_id = \'"+device_id+"\' LIMIT 1) ORDER BY rec_seq DESC LIMIT 10, 10) x);";
							
							console.log('rec_del_query : ' + rec_del_query);
							
							var del_query = connection.query(rec_del_query, function (err) {
								if(err){
									var jobj = basic_jobj;
									jobj.messagetype = "send_tts";
									jobj.result = "SEND_TTS_FAIL";
									console.log(JSON.stringify(jobj));
									res.end((JSON.stringify(jobj)));
									//throw err;
								}
								else{
									var jobj = basic_jobj;
									jobj.messagetype = "send_tts";
									jobj.result = "SEND_TTS_SUCCESS";
									console.log(JSON.stringify(jobj));
									res.end((JSON.stringify(jobj)));
								}
							});
						}
					});
			    });

			form.parse(req);
		}
		else{
			var chunk = ""; //json 받는 변수
	
			req.on('data', function(data) {
				var decoded_data = new Buffer(data.toString(), 'base64').toString(); // base64 인코딩 된 json을 base64로 다시 디코딩
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
					console.log("login query : " + sql_query);
					
					//id로 먼저 검색, 없을 시 아이디가 없다고 respond
					var is_exist_id = true;
					var query = connection.query(sql_query, function(err, rows, fields) {
						if (err) {
							console.log(chunk.user_id + ' log in error');
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "LOGIN_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
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
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								console.log("id exist");
								console.log("family_id : " + chunk.user_id + " / password : " + chunk.password);
								sql_query = "SELECT * FROM user_info WHERE user_id=\'" + chunk.user_id + "\' and password=\'" + chunk.password + "\';";
								console.log("passwd query : " + sql_query);
								query = connection.query(sql_query, function(err, rows, fields) {
									if (err) {
										console.log(chunk.user_id + ' log in error');
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "LOGIN_ERROR";
										console.log(JSON.stringify(jobj));
										res.end(JSON.stringify(jobj));
										//throw err;
									} else {
										if (rows.length == 0) {
											//id는 있는데 passwd와 함께 검색이 안된다면 비밀번호가 틀린 것;
											console.log(chunk.user_id + ' log in fail passwd');
											var jobj = basic_jobj;
											jobj.messagetype = messagetype;
											jobj.result = "LOGIN_FAIL_PASSWD";
											console.log(JSON.stringify(jobj));
											res.end(JSON.stringify(jobj));
										} else {
											//로그인 성공
											console.log(chunk.user_id + ' log in success');
											var jobj = basic_jobj;
											jobj.messagetype = messagetype;
											jobj.result = "LOGIN_SUCCESS";
											console.log(JSON.stringify(jobj));
											res.end(JSON.stringify(jobj));
										}
									}
								});
							}
						}
					});
				}
				else if(messagetype == 'product_check'){
					var sql_query = "SELECT * FROM product_sub_info WHERE product_id=\'"+ chunk.product_id + "\' LIMIT 1;";
					console.log("product check : " + sql_query);
					
					var query = connection.query(sql_query, function(err, rows, fields) {
						if (err) {
							console.log(chunk.product + 'product check error');
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "PRODUCT_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						} else {
							console.log(rows.length);
							if (rows.length == 0) {
								//id가 없을 때 id가 없다고 메세지 보냄. 
								console.log(chunk.product_id + ' no product id');
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "PRODUCT_NOT_EXIST";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								console.log("id exist");
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "PRODUCT_EXIST//"+rows[0].mac_address+"//"+rows[0].uuid;
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
						}
					});
				}
				else if(messagetype == 'get_uuid'){
					var sql_query = "SELECT psi.mac_address, psi.uuid FROM user_info ui, product_sub_info psi WHERE ui.device_id = \'" + chunk.device_id + "\' AND ui.product_id = psi.product_id LIMIT 1;";
					console.log("get_uuid : " + sql_query);
					
					var query = connection.query(sql_query, function(err, rows, fields) {
						if (err) {
							console.log(chunk.product + 'get_uuid error');
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_UUID_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						} else {
							console.log(rows.length);
							if (rows.length == 0) {
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_UUID_FAIL";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								console.log("uuid exist");
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_UUID_SUCCESS//"+rows[0].mac_address+"//"+rows[0].uuid;
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
						}
					});
				}
				else if (messagetype == 'signup') {
					//첫 가족 구성원이 가족의 대표 id를 만든 후의 가입자 생성
					var sql_query = "INSERT INTO user_info values(\'"
							+ chunk.user_id + "\', \'" + chunk.product_id + "\', \'"
							+ chunk.registration_id + "\', \'" + chunk.user_name + "\', "
							+ chunk.gender_flag + ", " + chunk.representative_flag
							+ ", " + chunk.in_home_flag + ", \'"+ chunk.device_id + "\', \'" + chunk.password +"\', now(), 0, 0)";
					console.log('signup query : ' + sql_query);
	
					var query = connection.query(sql_query, function(err) {
						if (err) {
							console.log('err : ' + err);
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "SIGNUP_FAIL";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						} else {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "SIGNUP_SUCCESS";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
						}
					});
					console.log('sign_up OK ' + chunk.user_id);
				} else if (messagetype == 'member_check') {
					//device_id로 회원이엿는지 검사
					var sql_query = "SELECT * FROM user_info WHERE device_id=\'" + chunk.device_id + "\';";
					console.log("id : " + chunk.device_id);
	
					//id로 먼저 검색, 없을 시 아이디가 없다고 respond
					var query = connection.query(sql_query, function(err, rows,
							fields) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "MEMBER_CHECK_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						} else {
							if (rows.length == 0) {
								//회원 아닌거같애
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "IS_NOT_MEMBER";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							} else {
								//회원인듯
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "IS_MEMBER";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
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
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							if(rows.length == 0){
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_USER_INFO_FAIL";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_USER_INFO_SUCCESS";
								
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
					console.log("get_family_inout query : " + sql_query);
					console.log("product id request : "+chunk.product_id);
					
					var query = connection.query(sql_query, function (err, rows, fields) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_FAMILY_INOUT_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							if(rows.length == 0){
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_FAMILY_INOUT_FAIL";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
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
					var query = connection.query(sql_query, function (err, rows1, fields) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "IS_IN_FAMILY_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							if(rows1.length == 0){
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "IS_IN_FAMILY_NO";
								console.log(JSON.stringify(jobj));
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
								
								console.log("in_out_id : " + rows1[0].user_id);
								moving_user_name = rows1[0].user_name;
	
								console.log('inoutsqlquery go');
								var in_out_sql_query = "UPDATE user_info SET inout_time = now(), in_home_flag=" + chunk.is_in_home+" WHERE device_id = \'"+chunk.device_id+"\' ;";
								moving_is_in_home = chunk.is_in_home;
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
												console.log(moving_user_name + " is in home ? : "+moving_is_in_home);
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
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "SET_TEMPERATURE_SUCCESS";
							console.log(JSON.stringify(jobj));
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
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							if(rows.length == 0){
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_TEMPERATURE_FAIL";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
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
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "DELETE_USER_SUCCESS";
							console.log(JSON.stringify(jobj));
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
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							if(rows.length == 0){
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_PRODUCT_SUB_INFO_FAIL";
								console.log(JSON.stringify(jobj));
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
				//get_tts START
				else if (messagetype === 'get_media') {
					//tts 주세요
					
					user_exist_query = "SELECT * FROM user_info WHERE device_id = \'" + chunk.device_id + "\';";
					
					console.log("user_exist_query : " + user_exist_query);
					
					var ue_query = connection.query(user_exist_query, function(err, urows, fields) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_MEDIA_ERROR";
							console.log(JSON.stringify(jobj));
							res.end((JSON.stringify(jobj)));
							//throw err;
						} else {
							console.log('urows length : ' + urows.length);
							if (urows.length === 0) {
								//device_id에 맞는 유저가 1개도 없어
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_MEDIA_FAIL";
								console.log(JSON.stringify(jobj));
								res.writeHead(404, {
									'Content-Type' : 'text/plaind; charset=utf-8'
								});
								res.end((JSON.stringify(jobj)));
							} else {
								sql_query = "SELECT"
									+ "(SELECT path FROM recording_info WHERE rec_flag = 0 AND user_id = (SELECT user_id from user_info where device_id = \'" + chunk.device_id + "\' LIMIT 1) ORDER BY rec_seq DESC LIMIT 1) AS tts_path,"
									+ "(SELECT path FROM recording_info WHERE rec_flag = 1 AND user_id = (SELECT user_id from user_info where device_id = \'" + chunk.device_id + "\' LIMIT 1) ORDER BY rec_seq DESC LIMIT 1) AS rec_path,"
									+ "(SELECT tts FROM user_info WHERE device_id = \'" + chunk.device_id + "\' LIMIT 1) AS tts,"
									+ "(SELECT rec FROM user_info WHERE device_id = \'" + chunk.device_id + "\' LIMIT 1) AS rec"
									+ ";"
								
								console.log("get_media : " + sql_query);
								
								var query = connection.query(sql_query, function(err, rows, fields) {
									if (err) {
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "GET_MEDIA_ERROR";
										console.log(JSON.stringify(jobj));
										res.end((JSON.stringify(jobj)));
										//throw err;
									} else {
										console.log('rows length : ' + rows.length);
										if (rows.length === 0) {
											//tts 1개도 없어
											var jobj = basic_jobj;
											jobj.messagetype = messagetype;
											jobj.result = "GET_MEDIA_FAIL";
											console.log(JSON.stringify(jobj));
											res.writeHead(404, {
												'Content-Type' : 'text/plain; charset=utf-8'
											});
											res.end((JSON.stringify(jobj)));
										} else {
											
											console.log('media exist');
											var jobj = basic_jobj;
											jobj.messagetype = messagetype;
											jobj.result = "GET_MEDIA_SUCCESS";
											
											var ajobj = {
													tts_file_name : "",
													tts_file : "",
													rec_file_name : "",
													rec_file : "",
													opt : ""
												};
											
											ajobj.opt = 'n';
											
											console.log('tts : ' + rows[0].tts);
											console.log('rec : ' + rows[0].rec);
											
											if(rows[0].tts == '0' && rows[0].rec == '0'){
												jobj.attach = ajobj;
												console.log("GET_MEDIA_SUCCESS");
												
												res.end(JSON.stringify(jobj));
												jobj.attach = "";
											}
											if(rows[0].tts == '1' && rows[0].rec == '0'){
												ajobj.opt = 't';
												console.log('tts_path : ' + rows[0].tts_path);
												if(rows[0].tts_path != null){
													var tts_file = "./recording/" + rows[0].tts_path;
													
													fs.readFile(tts_file, function(err, buffer){
														var base64File = new Buffer(buffer, 'binary').toString('base64');
														
														ajobj.tts_file_name = rows[0].tts_path;
														ajobj.tts_file = base64File;
														
														jobj.attach = ajobj;
														console.log("GET_MEDIA_SUCCESS");
														res.end(JSON.stringify(jobj));
														jobj.attach = "";
													});
												}
												else{
													jobj.attach = ajobj;
													console.log("GET_MEDIA_SUCCESS");
													res.end(JSON.stringify(jobj));
													jobj.attach = "";
												}
											}
											if(rows[0].tts == '0' && rows[0].rec == '1'){
												ajobj.opt = 'r';
												if(rows[0].rec_path != null){
													var rec_file = "./recording/" + rows[0].rec_path;
													
													fs.readFile(rec_file, function(err, buffer){
														var base64File = new Buffer(buffer, 'binary').toString('base64');
														
														ajobj.rec_file_name = rows[0].rec_path;
														ajobj.rec_file = base64File;
														
														jobj.attach = ajobj;
														
														//console.log(JSON.stringify(jobj));
														console.log("GET_MEDIA_SUCCESS");
														res.end(JSON.stringify(jobj));
														jobj.attach = "";
													});
												}
												else{
													jobj.attach = ajobj;
													console.log("GET_MEDIA_SUCCESS");
													res.end(JSON.stringify(jobj));
													jobj.attach = "";
												}
											}
											if(rows[0].tts == '1' && rows[0].rec == '1'){
												ajobj.opt = 'b';
												console.log('tts_path : ' + rows[0].tts_path);
												console.log('rec_path : ' + rows[0].rec_path);
												if(rows[0].tts_path != null){
													var tts_file = "./recording/" + rows[0].tts_path;
													
													fs.readFile(tts_file, function(err, buffer){
														console.log('tts file read');
														var base64File = new Buffer(buffer, 'binary').toString('base64');
														
														console.log('qqqqq');
														ajobj.tts_file_name = rows[0].tts_path;
														ajobj.tts_file = base64File;
														
														if(rows[0].rec_path != null){
															var rec_file = "./recording/" + rows[0].rec_path;
															
															fs.readFile(rec_file, function(err, buffer){
																var base64File = new Buffer(buffer, 'binary').toString('base64');
																
																ajobj.rec_file_name = rows[0].rec_path;
																ajobj.rec_file = base64File;
																
																jobj.attach = ajobj;
																
																//console.log(JSON.stringify(jobj));
																console.log("GET_MEDIA_SUCCESS");
																res.end(JSON.stringify(jobj));
																jobj.attach = "";
															});
														}
														else{
															jobj.attach = ajobj;
															console.log("GET_MEDIA_SUCCESS");
															res.end(JSON.stringify(jobj));
															jobj.attach = "";
														}
													});
												}
												else{
													if(rows[0].rec_path != null){
														var rec_file = "./recording/" + rows[0].rec_path;
														
														fs.readFile(rec_file, function(err, buffer){
															var base64File = new Buffer(buffer, 'binary').toString('base64');
															
															ajobj.rec_file_name = rows[0].rec_path;
															ajobj.rec_file = base64File;
															
															jobj.attach = ajobj;
															
															//console.log(JSON.stringify(jobj));
															console.log("GET_MEDIA_SUCCESS");
															res.end(JSON.stringify(jobj));
															jobj.attach = "";
														});
													}
													else{
														jobj.attach = ajobj;
														console.log("GET_MEDIA_SUCCESS");
														res.end(JSON.stringify(jobj));
														jobj.attach = "";
													}
												}
											}
											console.log("option : " + ajobj.opt);
										}
									}
								});
							}
						}
					});
					
					basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
				}//get_tts END
				else if(messagetype == 'get_devices'){
					//product id 사람들 device_id 다 받아오기
					var sql_query = "SELECT device_id FROM mynah_test.user_info WHERE product_id = \'"+chunk.product_id+"\';";
					console.log("get_devices query : " + sql_query);
					
					var query = connection.query(sql_query, function (err, rows, fields) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_DEVICES_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							if(rows.length == 0){
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_DEVICES_FAIL";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_DEVICES_SUCCESS";
								
								var users = [];
								for (var i = 0; i < rows.length; i++) {
									console.log(rows[i].device_id);
									users.push(rows[i]);
								}
								
								jobj.attach = rows;
								
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
								jobj.attach = "";
							}
							
						}
					});
					basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
				}
				else if(messagetype == 'update_status'){
					//온도갱신 product_id 받아서
					var sql_query = "UPDATE user_info SET rec = " + chunk.rec + ", tts = " + chunk.tts + " WHERE user_id = \'" + chunk.user_id + "\';";
					console.log('update_status : ' + sql_query);
					
					var query = connection.query(sql_query, function (err) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "UPDATE_STATUS_FAIL";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "UPDATE_STATUS_SUCCESS";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
						}
					});
				}
				else if(messagetype == 'update_weather'){
					//날씨 정보 갱신
					var sql_query = "SELECT * FROM device_sub_info WHERE device_id = \'" + chunk.user_id + "\';";
					console.log('update_weather : ' + sql_query);
					
					/*
					 * -1 : 에러 : erro
					 *  0 : 맑음 : sunn
					 *  1~3 : 흐림 : clou
					 *  4 : 비 : rain
					 *  5~6 : 눈 : snow
					 */
					var query = connection.query(sql_query, function (err, rows, fields) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "UPDATE_WEATHER_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						}
						else{
							var weather;
							if(chunk.weather == 0){
								weather = "sunn";
							}
							else if(1 <= chunk.weather && chunk.weather <= 3){
								weather = "clou";
							}
							else if(chunk.weather == 4){
								weather = "rain";
							}
							else if(5 <= chunk.weather && chunk.weather <= 6){
								weather = "snow";
							}
							else{
								weather = "erro";
							}
							
							if(rows.length == 0){
								var insert_weather_sql_query = "INSERT INTO device_sub_info VALUES('" + chunk.user_id + "', \'" + weather + "\');"
								console.log('insert_weather : ' + insert_weather_sql_query);
								
								var insert_weather_query = connection.query(insert_weather_sql_query, function(err) {
									if (err) {
										console.log('err : ' + err);
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "UPDATE_WEATHER_FAIL";
										console.log(JSON.stringify(jobj));
										res.end(JSON.stringify(jobj));
										//throw err;
									} else {
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "UPDATE_WEATHER_SUCCESS";
										console.log(JSON.stringify(jobj));
										res.end(JSON.stringify(jobj));
									}
								});
								console.log('weather insert OK ' + chunk.user_id);
							}
							else{
								var update_weather_sql_query = "UPDATE device_sub_info SET weather = " + weather + " WHERE device_id = \'" + chunk.user_id + "\';";
								console.log('update_status : ' + sql_query);
								
								var update_weather_query = connection.query(update_weather_sql_query, function (err) {
									if (err) {
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "UPDATE_WEATHER_FAIL";
										console.log(JSON.stringify(jobj));
										res.end(JSON.stringify(jobj));
										//throw err;
									}
									else{
										var jobj = basic_jobj;
										jobj.messagetype = messagetype;
										jobj.result = "UPDATE_WEATHER_SUCCESS";
										console.log(JSON.stringify(jobj));
										res.end(JSON.stringify(jobj));
									}
								});
								console.log('weather update OK ' + chunk.device_id);
							}
							
						}
					});
					basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
				}
				else if(messagetype == 'get_weather'){
					var sql_query = "SELECT device_id, weather FROM device_sub_info WHERE device_id=\'"+ chunk.device_id + "\' LIMIT 1;";
					console.log("get_weather : " + sql_query);
					
					var query = connection.query(sql_query, function(err, rows, fields) {
						if (err) {
							var jobj = basic_jobj;
							jobj.messagetype = messagetype;
							jobj.result = "GET_WEATHER_ERROR";
							console.log(JSON.stringify(jobj));
							res.end(JSON.stringify(jobj));
							//throw err;
						} else {
							if (rows.length == 0) {
								//저장된 날씨 없어 
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_WEATHER_FAIL";
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
							}
							else{
								var jobj = basic_jobj;
								jobj.messagetype = messagetype;
								jobj.result = "GET_WEATHER_SUCCESS";
								
								jobj.attach = rows[0];
								
								console.log(JSON.stringify(jobj));
								res.end(JSON.stringify(jobj));
								console.log('get weather OK ' + chunk.device_id);
								jobj.attach = "";
							}
						}
					});
					basic_jobj.attach = ""; //얕은복사인가.. 초기화가필요하네
				}
				else if(messagetype == 'get_hour'){
					console.log('get_hour');
					
					var date = new Date();
			    	date.setHours(date.getHours());
					
			    	var hour_jobj = {
			    			hour : date.getHours()
			    	};
			    	
			    	var jobj = basic_jobj;
					jobj.messagetype = messagetype;
					jobj.result = "GET_HOUR_SUCCESS";
					
					jobj.attach = hour_jobj;
					
					console.log(JSON.stringify(jobj));
					res.end(JSON.stringify(jobj));
					jobj.attach = "";
				}
				else{
					//그냥 https 리퀘스트였을 때
					res.end('Hi There');
				}
	
			});
			// res.end("한글 ");
			//res.end('Hi There');
		}
	}
	else {
		// 포스트가 아니면 에러
		res.writeHead(200, {
			'Content-Type' : 'text/plain; charset=utf-8'
		});
		console.log('NOT POST REQUEST');
		
		var date = new Date();
		date.setHours(date.getHours());
		var date_time = date.toISOString().replace(/T/, '_').replace(/\..+/, '').replace(':', '').replace(':', '');
		res.write(date_time + "\n");
		res.write(date.getHours() + "\n");
		var hour_jobj = {
    			hour : date.getHours()
    	};
    	
    	var jobj = basic_jobj;
		jobj.messagetype = "get_hour";
		jobj.result = "GET_HOUR_SUCCESS";
		
		jobj.attach = hour_jobj;
		
		console.log(JSON.stringify(jobj));
		res.end(JSON.stringify(jobj));
		jobj.attach = "";
		//res.end('wrong request');
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
	// http request from port 13337
	res.writeHead(200, {
		'Content-Type' : 'text/plain'
	});
	res.end('request');
	var date = new Date();
	date.setHours(date.getHours() + 9);
	console.log("\r\nhttp request arriving!" + date.toUTCString() + "\r\n");

}

httpsServer = https.createServer(options, HttpsEventProcessCallback);

httpsServer.listen(sslport, '211.189.20.165');
connection.connect();
console.log('Mynah Server running at https://211.189.20.165:13337/');

