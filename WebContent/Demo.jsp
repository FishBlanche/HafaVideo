<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <% 
			String contextPath = request.getContextPath();
			String urlPath =request.getServerName()+":"+request.getServerPort()+contextPath+"/";
       %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"> 

    <head>
        <title></title>
        <meta name="google" value="notranslate" />         
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <style type="text/css" media="screen"> 
            html, body  { height:100%; }
            body { margin:0; padding:0; overflow:auto; text-align:center; 
                   background-color: #ffffff; }   
            object:focus { outline:none; }
            #flashContent { display:none; }  
        </style>
        <link rel="stylesheet" type="text/css" href="history/history.css" />
        <script type="text/javascript" src="history/history.js"></script>
        <script type="text/javascript" src="history/jquery-1.8.3.js"></script> 
        <script type="text/javascript" src="swfobject.js"></script>
        <script type="text/javascript">
            var swfVersionStr = "13.0.0";
            var xiSwfUrlStr = "expressInstall.swf";
            var flashvars = {};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            var attributes = {};
            attributes.id = "Video";
            attributes.name = "Video";
            attributes.align = "middle";
            swfobject.embedSWF(
                "Video.swf", "flashContent", 
                "100%", "100%", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
            
        </script>
    </head>
    <body onload="init()">
        <div id="flashContent">
            <p>
                To view this page ensure that Adobe Flash Player version 
                13.0.0 or greater is installed.  
            </p>
        </div>
        <div id="videoflase" style="padding-top:20%;display:none">
        
        </div>
        
        <noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="Video">
                <param name="movie" value="Video.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <object type="application/x-shockwave-flash" data="Video.swf" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
                    <p> 
                        Either scripts and active content are not permitted to run or Adobe Flash Player version
                        13.0.0 or greater is not installed.
                    </p>
                </object>
            </object>
        </noscript> 
        <script type="text/javascript" >
        var urlPath='<%=urlPath%>';
        var playurl;
        var DimensionChange = false;
        var ws= null;//by myp
	 	function refreshProgress()
 		{
	 		var interval=setInterval(function () { 
	 				if(document.getElementById("Video").SWFLoad()) { //轮询flash的某个方法即可 
	 					callBack(); //回调函数 
	 					clearInterval(interval); 
	 				} 
	 				}, 500);
	 		
 		}
	 	
		function callBack() { 
    			 ws = new WebSocket("ws://"+urlPath+"/DemoWebSocket");
       	      	 ws.onopen = function(evt) {
       	      		ws.send("Number");
       	        };
       	        //by myp
       	     	ws.onmessage = function(msg) {
       	        	
       	     	   try{console.log("msg"+msg.data);}catch(e){}//print the message 
       	     	   
        		 	var c=msg.data.split("+"); 
        		 	if("Number"==c[0])
        		 	{
        		 		playurl=c[1];
        		 		playVideoFunc(playurl); 
        		 	}
       	     		
 	     		};
 	     		/*lingxing
 	     		ws.onmessage = function(msg) {
        		 	playurl = msg.data;
        		 	 
       	     		playVideoFunc(msg.data); 
 	     		};*/
		} 
         function  init(){
        	 refreshProgress();
         }  
         
         function getstate(value){
        	 /*
        	   if(value=="NetStream.Play.Start"){
        		  setTimeout(function () { 
	 				if(DimensionChange) {
	 					document.getElementById("videoflase").style.display="none";
	 		 			document.getElementById("Video").style.display="";
	 				} else{
	 					
	 					 document.getElementById("Video").style.display="none";
	 	        		 var c2 = document.createElement("font");
	 	        		 c2.setAttribute("size","60px");
	 	        		 c2.innerHTML = playurl+" flow instability 10 second replay";
	 	        		 var c1 = document.getElementById("videoflase");
	 	        		 c1.appendChild(c2);
	 	        		 document.getElementById("videoflase").style.display="";
	 	        		 setTimeout(function () { 
	 	        			window.location.reload();
	 	         		 }, 10000); 
	 					
	 				}
	 				}, 10000);
        		  
        	  }*/
        	  
        	  if(value=="NetStream.Video.DimensionChange"){
        		  DimensionChange = true;
        	  }
        	  
        	  if(value=="NetStream.Play.StreamNotFound"){
        		 document.getElementById("Video").style.display="none";
        		 var c2 = document.createElement("font");
        		 c2.setAttribute("size","60px");
        		 c2.innerHTML = playurl+" Play fail";
        		 var c1 = document.getElementById("videoflase");
        		 c1.appendChild(c2);
        		 document.getElementById("videoflase").style.display="block";
        	 }  
         } 
         
            
        function playVideoFunc(value){
        	
        	document.getElementById("Video").playVideo(value);
        	//window.location.href="https://www.baidu.com/";
        	
         }
        
        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function(){
        	 try{console.log("window onbeforeunload");}catch(e){}//print the message 
        	 ws.close();
        }
        </script>    
   </body>
</html>