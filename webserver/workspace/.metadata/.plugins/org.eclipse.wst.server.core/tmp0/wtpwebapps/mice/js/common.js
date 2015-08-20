// ***��ũ ���*** //

//�а�Ұ� - �а�Ұ�                 menu1()
//	         �������� �� ������ǥ     menu1sub2()
//           ������ �Ұ�              menu1sub3()
//           ã�ƿ��ô±�             menu1sub4()

//��������ȳ�                        menu2()

//���оȳ�                            menu3()

//Ŀ�´�Ƽ - ��������                 menu4()
//           ��ȸ&���                menu4sub2()
//           �ڷ��                   menu4sub3()
//           Q&A                      menu4sub4()

//��ø�ũ                            menu5()



function link(args)
{ 
	var url=""; 
	switch(args)
	{ 
		case "11":url="index.jsp?mid1=00000001";break; 
		case "12":url="index.jsp?mid1=00000001&amp;mid2=00000002";break; 
		case "13":url="index.jsp?mid1=00000001&amp;mid2=00000003";break; 
		case "14":url="index.jsp?mid1=00000001&amp;mid2=00000004";break; 
		
		case "21":url="index.jsp?mid1=00000002";break; 
		case "22":url="index.jsp?mid1=00000002&mid2=00000006";break; 
		case "23":url="index.jsp?mid1=00000002&mid2=00000007";break; 

		case "31":url="index.jsp?mid1=00000003";break; 
		
		case "41":url="index.jsp?mid1=00000004";break; 
		case "42":url="index.jsp?mid1=00000004&amp;mid2=00000011";break; 
		case "43":url="index.jsp?mid1=00000004&amp;mid2=00000012";break; 
		case "44":url="index.jsp?mid1=00000004&amp;mid2=00000013";break; 
		
		case "51":url="index.jsp?mid1=00000005";break; 
	} 
	top.document.location.href=url 
} 




//Element ID �ҷ�����
function dEI(elementID){
	return document.getElementById(elementID);
}

//Footer select Open
function selectOn(boxID){
	var selectBox = document.getElementById(boxID);
	
	if(selectBox.style.display!="block"){
	selectBox.style.display="block";
	selectBox.onmouseover = function(){selectBox.style.display="block";}
	selectBox.onmouseout = function(){selectBox.style.display="none";}
	}else{
		selectBox.style.display="none";
	}
}

// roundBox Layout
function initLayout(layoutEl,childEl) {
	var layoutId = dEI(layoutEl);
	var contentsId = dEI(childEl);
		
	//create and build div structure
	var bodyTH = document.createElement('div');
	var bodyLV = document.createElement('div');
	var bodyRV = document.createElement('div');
	var bodyBH = document.createElement('div');
	var bodyTL = document.createElement('div');
	var bodyTR = document.createElement('div');
	var bodyBL = document.createElement('div');
	var bodyBR = document.createElement('div');
	bodyTH.className = "bodyTH";
	bodyLV.className = "bodyLV";
	bodyRV.className = "bodyRV";
	bodyBH.className = "bodyBH";
	bodyTL.className = "bodyTL";
	bodyTR.className = "bodyTR";
	bodyBL.className = "bodyBL";
	bodyBR.className = "bodyBR";
	layoutId.appendChild(bodyTH);
	bodyTH.appendChild(bodyLV);
	bodyLV.appendChild(bodyRV);
	bodyRV.appendChild(bodyBH);
	bodyBH.appendChild(bodyTL);
	bodyTL.appendChild(bodyTR);
	bodyTR.appendChild(bodyBL);
	bodyBL.appendChild(bodyBR);
	bodyBR.appendChild(contentsId);
}

//Images Btn_KSS
function BtnOn(imgEl){
	imgEl.src = imgEl.src.replace(".gif", "on.gif");
}
function BtnOut(imgEl){
	imgEl.src = imgEl.src.replace("on.gif", ".gif");
}

// first ����ó�� firstChild(�?Id, �±׳���, ó���� ������ ��ȣ) // �����۹�ȣ�� 0����� ��ȯ
function firstChild(Elid, Etn, Num){
	if(Num==""){Num=0;}
	liEl = dEI(Elid).getElementsByTagName(Etn);
	if (liEl.item(Num)) {
		liEl.item(Num).className += " first-child";
	}
}

// first ����ó�� listFirst(�?Id, �±׳���, ó���� ������ ����) // �����۹�ȣ�� 0����� ��ȯ
function listFirst(Elid, Etn, Num){
	liEl = dEI(Elid).getElementsByTagName(Etn);
	for(i=0; liEl.length>i; i=i+Num){
		liEl.item(i).className += " first";
	}
}

//�˾�����
function openPop(url,idn,intWidth,intHeight,scroll) { 
	window.open(url, idn,"width="+intWidth+", height="+intHeight+",resizable=no,scrollbars="+scroll) ;
}

// Tab Content
function tabCheck(dotabid , tnum){
	var inum=parseInt(tnum)-1;
	var linkTab=dEI(dotabid).getElementsByTagName("a");
	for (i=0;i<linkTab.length;i++) {
		var tabimg = linkTab.item(i).getElementsByTagName("img").item(0);
		var tabContents= dEI(dotabid+(1+i));
		if (i==inum) {
			if(tabContents.style.display!="block"){
			tabimg.src=tabimg.src.replace(".gif", "on.gif");
			tabContents.style.display="block";
			}
		}else{
		tabimg.src=tabimg.src.replace("on.gif", ".gif");
		tabContents.style.display="none";
		}
	}
}

// �̹��� �ѿ���
function imgRollover(imgBoxID){
	var MenuCounts = dEI(imgBoxID).getElementsByTagName("img");
	for (i=0;i<MenuCounts.length;i++) {
		var numImg=MenuCounts.item(i);
		var ImgCheck = numImg.src.substring(numImg.src.length-6,numImg.src.length);
		if (ImgCheck!="on.gif") {
				numImg.onmouseover = function () {
					this.src = this.src.replace(".gif", "on.gif");
				}
				numImg.onmouseout = function () {
					this.src = this.src.replace("on.gif", ".gif");
				}
			}
	}
}

//openDrop Layer
function viewInfo(list,total,num){
var size = total;// list count +1
var id = list;
	for(i=1;i<size;i++){
		var liEI = document.getElementById(id+i);
		if(i==num){
			if(liEI.className == "open"){
				liEI.className = "";
			}else{
				liEI.className = "open";
			}
		}else{
			liEI.className = "";
		}
	}
}

// scrolling layer
function initMoving(target, position, topLimit, btmLimit) {
	if (!target)
		return false;

	var obj = target;
	obj.initTop = position;
	obj.topLimit = topLimit;
	obj.bottomLimit = document.documentElement.scrollHeight - btmLimit;

	obj.style.position = "absolute";
	obj.top = obj.initTop;
	obj.left = obj.initLeft;

	if (typeof(window.pageYOffset) == "number") {
		obj.getTop = function() {
			return window.pageYOffset;
		}
	} else if (typeof(document.documentElement.scrollTop) == "number") {
		obj.getTop = function() {
			return document.documentElement.scrollTop;
		}
	} else {
		obj.getTop = function() {
			return 0;
		}
	}

	if (self.innerHeight) {
		obj.getHeight = function() {
			return self.innerHeight;
		}
	} else if(document.documentElement.clientHeight) {
		obj.getHeight = function() {
			return document.documentElement.clientHeight;
		}
	} else {
		obj.getHeight = function() {
			return 500;
		}
	}

	obj.move = setInterval(function() {
		if (obj.initTop > 0) {
			pos = obj.getTop() + obj.initTop;
		} else {
			pos = obj.getTop() + obj.getHeight() + obj.initTop;
			//pos = obj.getTop() + obj.getHeight() / 2 - 15;
		}

		if (pos > obj.bottomLimit)
			pos = obj.bottomLimit;
		if (pos < obj.topLimit)
			pos = obj.topLimit;

		interval = obj.top - pos;
		obj.top = obj.top - interval / 3;
		obj.style.top = obj.top + "px";
	}, 30)
}
//scrolling layer end

function MediaObject(URL, width, height)
{ 
    document.write('<object ID="VodPlayer" name="VodPlayer" classid="CLSID:22D6F312-B0F6-11D0-94AB-0080C74C7E95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715"  standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject" align="middle" width="'+width+'" height="'+height+'">'); 
    document.write('  <param name="CurrentPosition" value="1">'); 
    document.write('  <param name="SetCurrentEntry" value="1">'); 
    document.write('  <param name="ClickToPlay" value="1">'); 
    document.write('  <param name="AutoSize" value="0">'); 
    document.write('  <param name="AutoStart" value="1">'); 
    document.write('  <param name="ShowControls" value="1">'); 
    document.write('  <param name="ShowAudioControls" value="1">'); 
    document.write('  <param name="ShowDisplay" value="0">'); 
    document.write('  <param name="ShowStatusBar" value="1">'); 
    document.write('  <param name="EnableContextMenu" value="0">'); 
    document.write('  <param name="ShowPositionControls" value="0">'); 
    document.write('  <param name="DisplayBackColor" value="0">'); 
    document.write('  <param name="ShowTracker" value="1">'); 
    document.write('  <param name="ShowCaptioning" value="0">'); 
    document.write('  <param name="AutoRewind" value="0">'); 
    document.write('  <param name="EnableTracker" value="1">'); 
    document.write('  <param name="Volume" value="-1">'); 
    document.write('  <param name="stretchToFit" value="0">'); 
    document.write(' <embed src="'+URL+'" type="video/x-ms-wmv" width="'+width+'" height="'+height+'">');
  //  document.write('  <param name="Filename" value="'+URL+'">'); 
    document.write('</object>'); 
} 
function resizeImageWindow() {
alert("aa");
	imgEl = document.getElementById("photoImage");
	alert(imgEl);
	if (imgEl) {
		propotion = imgEl.offsetWidth / imgEl.offsetHeight;
		if (propotion > 1) {
			if (imgEl.offsetWidth > 500) {
				imgEl.style.width = "500px";
				imgEl.style.height = Math.floor(500 / propotion) + "px";
			}
		} else {
			if (imgEl.offsetHeight > 500) {
				imgEl.style.height = "500px";
				imgEl.style.width = Math.floor(500 * propotion) + "px";
			}
		}
	}
	document.getElementById("photoPopup").style.width = (imgEl.offsetWidth + 12) + "px";
	window.resizeTo(imgEl.offsetWidth + 52, document.getElementById("photoPopup").offsetHeight + 145);
}






	// �˾�â


	function getCookie(strName)
{
	var strArg = new String(strName + "=");	
	var nArgLen, nCookieLen, nEnd;
	var i = 0, j;

	nArgLen    = strArg.length;
	nCookieLen = document.cookie.length;

	if(nCookieLen > 0) 
	{

		while(i < nCookieLen) 
		{

			j = i + nArgLen;

			if(document.cookie.substring(i, j) == strArg)
			{

				nEnd = document.cookie.indexOf (";", j);

				if(nEnd == -1) nEnd = document.cookie.length;

				return unescape(document.cookie.substring(j, nEnd));

			}

			i = document.cookie.indexOf(" ", i) + 1;

			if (i == 0) break;

		}

	}

	return("");

}


function setCookie(strName, strValue, dateExpires, strPath, strDomain, isSecure)
{

	var strCookie;

	if(strName == "") return ;

	strCookie = strName + "=" + escape(strValue) + 

		    ((dateExpires) ? "; expires=" + dateExpires.toGMTString() : "")  + 

	      	    ((strPath)     ? "; path="    + strPath : "") + 

	      	    ((strDomain)   ? "; domain="  + strDomain : "") + 

		    ((isSecure)    ? "; secure" : "");

	document.cookie = strCookie; 

}


function delCookie( name )
{

	var todayDate = new Date();

	todayDate.setDate( todayDate.getDate()-1 );

	document.cookie = name+"=; domain=lg.or.kr; path=/; expires="+todayDate.toGMTString()+";";

}

function SetUrl() 
{

		setCookie("LGURL",tUrl,null,"/","lg.or.kr")			

}



function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

/*
 * Left Menu hidden / show
 */
function leftMenuHandler(obj) {
	var menu = document.getElementById(obj);
	var sub_menu = document.getElementById('sub_'+obj);
	
	if(menu.className == "off") {
		menu.className = "on";
		sub_menu.style.display = "none";
	} else {
		menu.className = "off";
		sub_menu.style.display = "block";
	}
}

/*
 * Left Menu sub menu selection 
 */
function leftMenuSelection(obj, link) {
	var total = 18;
	
	for(var i=1 ; i<=total ; i++) {
		document.getElementById('job_'+i).className = "soff";	
	}
	document.getElementById('job_'+obj).className = "son";
	
	if(link != "") {
		location.href = link;
	}
}

/*
 * üũ�ڽ� ��ü üũ / ����
 */
function allBox() {
	allChecked = true;
	var box = document.all.boxes;

	if ( box.length > 0) {
		for ( i = 0; i < box.length; i++) {
			if ( !box[i].checked) {
				allChecked = false;
				break;
			}
		}

		for ( i = 0; i < box.length; i++) {
			document.all.boxes[i].checked = !allChecked;
		}
		document.all.allbox.checked = !allChecked;
	} else {
		box.checked = document.all.allbox.checked;
	}
}

/*
 * 숫자와 콤마만 입력.
 * <input type="text" onKeyDown="return ufNumChk();" onpaste="return false;">
 */
function ufNumChk()
{
	var code = window.event.keyCode;
	if ((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || (code >= 37 && code <= 40) || code == 8 || code == 9 || code == 13 || code == 46 || code == 110 || code == 190) { 
		return true;
	}
	return false;
}

/*
 * 숫자만 입력
 * <INPUT TYPE="text" NAME="txt1" onkeydown="number_check()">
 */

function onlyNumberChk()
{
    var code  = window.event.keyCode;
    if((code >= 48 && code <=57) || code == 8 || (code >= 37 && code <= 40)) { 
    	return true;
	}
    return false;
}

/*
 * 반올림 계산 (숫자, 소수점 자리)
 */
function roundXL(n, digits) {
	if (digits >= 0) return parseFloat(n.toFixed(digits)); // 소수부 반올림

	digits = Math.pow(10, digits); // 정수부 반올림
	var t = Math.round(n * digits) / digits;

	return parseFloat(t.toFixed(0));
}