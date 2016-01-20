function mous(obj,event){
		var d = $(obj).attr("id");
		var id = "operator_"+d;
		var value = $("#"+id).val();
		var e = event || window.event;
		$("#dialog").css({display:"block"}).text(value);
		
	}
	function mousout(obj){
		var d = $(obj).attr("id");
		var id = "operator_"+d;
		$("#dialog").css({display:"none"}).text("");
	}
	function mouseMove(event){ 
		ev = event || window.event; 
		var mousePos = getMouseXY(ev); 
		$("#dialog").css({top:mousePos.Y + 200 + 'px'}).css({left:mousePos.X + 200 + 'px'});
	} 
	function getMouseXY(e){
 		var posx=0,posy=0;
 		if(e==null) e=window.event;
 		if(e.pageX || e.pageY){
 			posx=e.pageX; posy=e.pageY;
 		}
	    else if(e.clientX || e.clientY){
 			if(document.documentElement.scrollTop){
 				posx=e.clientX+document.documentElement.scrollLeft;
 				posy=e.clientY+document.documentElement.scrollTop;
 			}
 			else{
 				posx=e.clientX+document.body.scrollLeft;
 				posy=e.clientY+document.body.scrollTop;
 			}
 		}
 		return {
 			X : posx,
 			Y : posy
 		}
	}