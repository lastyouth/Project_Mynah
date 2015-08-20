<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::MICE::</title>
<link rel="stylesheet" type="text/css" href="/mice/css/Layout.css"/>

<script language="JavaScript" src="/mice/js/common.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
//레이어 팝업 닫기
	function closeLayer(IdName){
		var pop = parent.document.getElementById(IdName);
		pop.style.display = "none";
	}

function goNoRegitProc (){
	var  latitude = document.form.t1.value;
	var  longitude = document.form.t2.value;
	parent.onSetSelectInfo(latitude, longitude);
	closeLayer('layerPop');

}
  //-->
</script>
<script type = "text/javascript"
 src = "http://maps.googleapis.com/maps/api/js?sensor=true">
</script>
<script type = "text/javascript">

var map;
var infowindow = new google.maps.InfoWindow();
var marker =[];
var geocoder;
var geocodemarker = [];


var GreenIcon = new google.maps.MarkerImage(
   "http://labs.google.com/ridefinder/images/mm_20_green.png",
   new google.maps.Size(12, 20),
   new google.maps.Point(0, 0),
   new google.maps.Point(6, 20));
// 녹색 마커 아이콘을 정의하는 부분

function initialize(){

 var latlng = new google.maps.LatLng(37.566535,126.9779692);
 var myOptions = {
  zoom: 15,
  center:latlng,
  mapTypeId: google.maps.MapTypeId.ROADMAP   
 };
 
 map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
 geocoder =  new google.maps.Geocoder();
 google.maps.event.addListener(map, 'click', codeCoordinate);
        /*아랫글에서 설명한 event를 이용 지도를 'click'하면 codeCoordinate함수를 실행합니다.
           codeCoordinate함수는 클릭한 지점의 좌표를 가지고 주소를 찾는 함수입니다. */
}

function Setmarker(latLng) {
     
  if (marker.length > 0)
        {
  marker[0].setMap(null); 
  }
// marker.length는 marker라는 배열의 원소의 개수입니다.
// 만약 이 개수가 0이 아니라면 marker를 map에 표시되지 않게 합니다.
// 이는 다른 지점을 클릭할 때 기존의 마커를 제거하기 위함입니다.

  marker = [];
  marker.length = 0;
// marker를 빈 배열로 만들고, marker 배열의 개수를 0개로 만들어 marker 배열을 초기화합니
// 다.

   marker.push(new google.maps.Marker({
   position: latLng,
   map: map
  } ));
// marker 배열에 새 marker object를 push 함수로 추가합니다.

}
/*클릭한 지점에 마커를 표시하는 함수입니다.
   그런데 이 함수를 잘 봐야 하는 것이 바로 marker를 생성하지 않고 marker라는 배열 안에 Marker 
   obejct  원소를 계속 추가하고 있습니다. 이는 매번 클릭할 때마다 새로운 마커를 생성하기 위함입니
   다. */

//입력 받은 주소를 지오코딩 요청하고 결과를 마커로 지도에 표시합니다.
function codeAddress(event) {

 if (geocodemarker.length > 0)
 {
  for (var i=0;i<geocodemarker.length ;i++ )
  {
   geocodemarker[i].setMap(null);
  }
  geocodemarker =[];
  geocodemarker.length = 0;
 }
        //이 부분도 위와 같이 주소를 입력할 때 표시되는 marker가 매번 새로 나타나게 하기 위함입니
        //다.

 var address = document.getElementById("addr1").value;
        //아래의 주소 입력창에서 받은 정보를 address 변수에 저장합니다.

        //지오코딩하는 부분입니다.
 geocoder.geocode( {'address': address}, function(results, status) {
  if (status == google.maps.GeocoderStatus.OK)  //Geocoding이 성공적이라면,
  {
   //alert(results.length + "개의 결과를 찾았습니다.");
   //결과의 개수를 표시하는 창을 띄웁니다. alert 함수는 알림창 함수입니다.
   for(var i=0;i<results.length;i++)
   {
    map.setCenter(results[i].geometry.location);
    geocodemarker.push(new google.maps.Marker({
     center: results[i].geometry.location,
     position: results[i].geometry.location,
     icon: GreenIcon,
     map: map
    }));
   } 
                        //결과가 여러 개일 수 있기 때문에 모든 결과를 지도에 marker에 표시합니다.
  }
  else
  { alert("Geocode was not successful for the following reason: " + status); 
  }
 });
}


//클릭 이벤트 발생 시 그 좌표를 주소로 변환하는 함수입니다.
function codeCoordinate(event) {
        
 Setmarker(event.latLng);
        //이벤트 발생 시 그 좌표에 마커를 생성합니다.

        // 좌표를 받아 reverse geocoding(좌표를 주소로 바꾸기)를 실행합니다.
 geocoder.geocode({'latLng' : event.latLng}, function(results, status) {
  if (status == google.maps.GeocoderStatus.OK)  {
   if (results[1])
   {
    infowindow.setContent(results[1].formatted_address);
    infowindow.open(map,marker[0]);
                                //infowindow로 주소를 표시합니다.
    document.form.t1.value =  results[1].geometry.location.lat();
    document.form.t2.value =  results[1].geometry.location.lng();
   }
  }
 });
}
//
 
 
</script>
</head>
<body onload="initialize()">
<form name="form" action="#" onsubmit="codeAddress(); return false;">
      <p>
        <b>국가명 / 주소입력:</b>
        <input type="text" id="addr1" name="address" value="" class="address_input" size="40"  method=post />
        <input type="submit" name="find" value="Search" />
		<br />
		위도 : <input type="text" id="t1" name="t1"><br />
		경도 : <input type="text" id="t2" name="t2"><br />
		<a href="javascript:goNoRegitProc();" style="text-size:10"><b>선택 지역 입력</b></a>
      </p>
</form>
 
    <div id="map_canvas" style="width: 500px; height: 400px">
 <!--[if lt IE 7]>  
<div style='border: 1px solid #F7941D; background: #FEEFDA; text-align: center; clear: both; height: 50px; position: relative;'>  
<div style='font-size: 12px; font-weight: bold; margin-top: 12px;'>최신 브라우저로 지금 업그레이드 해주세요.<br/> IE6 이하 버젼에서는 지원되지 않는 기능이 있습니다.
</div>  
</div>  
<![endif]-->	
	</div>
</body>
</html>                