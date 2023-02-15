/*
 GNU Lesser General Public License, http://www.gnu.org/copyleft/lesser.html
 @author  Jan Odvarko, http://odvarko.cz
 @created 2008-06-15
 @updated 2013-04-08
 @link    http://jscolor.com
*/
var jscolor={dir:"../images/editor/jscolor/",bindClass:"color",binding:!0,preloading:!0,clickedElem:null,styleColor:"",actionType:"",excuteFn:null,install:function(){jscolor.addEvent(window,"load",jscolor.init)},init:function(){jscolor.binding&&jscolor.bind();jscolor.preloading&&jscolor.preload()},getDir:function(){if(!jscolor.dir){var c=jscolor.detectDir();jscolor.dir=!1!==c?c:"jscolor/"}return jscolor.dir},detectDir:function(){for(var c=location.href,f=document.getElementsByTagName("base"),b=0;b<
f.length;b+=1)f[b].href&&(c=f[b].href);f=document.getElementsByTagName("script");for(b=0;b<f.length;b+=1)if(f[b].src&&/(^|\/)jscolor\.js([?#].*)?$/i.test(f[b].src))return c=(new jscolor.URI(f[b].src)).toAbsolute(c),c.path=c.path.replace(/[^\/]+$/,""),c.query=null,c.fragment=null,c.toString();return!1},bind:function(){for(var c=new RegExp("(^|\\s)("+jscolor.bindClass+")\\s*(\\{[^}]*\\})?","i"),f=document.getElementsByTagName("div"),b=0;b<f.length;b+=1){var m;if(!f[b].color&&f[b].className&&(m=f[b].className.match(c))){var x=
{};if(m[3])try{x=(new Function("return ("+m[3]+")"))()}catch(G){}f[b].color=new jscolor.color(f[b],x)}}},preload:function(){for(var c in jscolor.imgRequire)jscolor.imgRequire.hasOwnProperty(c)&&jscolor.loadImage(c)},images:{pad:[181,101],sld:[16,101],cross:[15,15],arrow:[7,11]},imgRequire:{},imgLoaded:{},requireImage:function(c){jscolor.imgRequire[c]=!0},loadImage:function(c){jscolor.imgLoaded[c]||(jscolor.imgLoaded[c]=new Image,jscolor.imgLoaded[c].src=jscolor.getDir()+c)},fetchElement:function(c){return"string"===
typeof c?document.getElementById(c):c},addEvent:function(c,f,b){c.addEventListener?c.addEventListener(f,b,!1):c.attachEvent&&c.attachEvent("on"+f,b)},fireEvent:function(c,f){if(c)if(document.createEvent){var b=document.createEvent("HTMLEvents");b.initEvent(f,!0,!0);c.dispatchEvent(b)}else if(document.createEventObject)document.createEventObject();else if(c["on"+f])c["on"+f]()},getElementPos:function(c){var f=c,b=0,m=0;if(f.offsetParent){do b+=f.offsetLeft,m+=f.offsetTop;while(f=f.offsetParent)}for(;(c=
c.parentNode)&&"BODY"!==c.nodeName.toUpperCase();)b-=c.scrollLeft,m-=c.scrollTop;return[b,m]},getElementSize:function(c){return[c.offsetWidth,c.offsetHeight]},getRelMousePos:function(c){var f=0,b=0;c||(c=window.event);"number"===typeof c.offsetX?(f=c.offsetX,b=c.offsetY):"number"===typeof c.layerX&&(f=c.layerX,b=c.layerY);return{x:f,y:b}},getViewPos:function(){return"number"===typeof window.pageYOffset?[window.pageXOffset,window.pageYOffset]:document.body&&(document.body.scrollLeft||document.body.scrollTop)?
[document.body.scrollLeft,document.body.scrollTop]:document.documentElement&&(document.documentElement.scrollLeft||document.documentElement.scrollTop)?[document.documentElement.scrollLeft,document.documentElement.scrollTop]:[0,0]},getViewSize:function(){return"number"===typeof window.innerWidth?[window.innerWidth,window.innerHeight]:document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)?[document.documentElement.clientWidth,document.documentElement.clientHeight]:
document.body&&(document.body.clientWidth||document.body.clientHeight)?[document.body.clientWidth,document.body.clientHeight]:[0,0]},URI:function(c){function f(b){for(var c="";b;)if("../"===b.substr(0,3)||"./"===b.substr(0,2))b=b.replace(/^\.+/,"").substr(1);else if("/./"===b.substr(0,3)||"/."===b)b="/"+b.substr(3);else if("/../"===b.substr(0,4)||"/.."===b)b="/"+b.substr(4),c=c.replace(/\/?[^\/]*$/,"");else if("."===b||".."===b)b="";else{var f=b.match(/^\/?[^\/]*/)[0];b=b.substr(f.length);c+=f}return c}
this.authority=this.scheme=null;this.path="";this.fragment=this.query=null;this.parse=function(b){b=b.match(/^(([A-Za-z][0-9A-Za-z+.-]*)(:))?((\/\/)([^\/?#]*))?([^?#]*)((\?)([^#]*))?((#)(.*))?/);this.scheme=b[3]?b[2]:null;this.authority=b[5]?b[6]:null;this.path=b[7];this.query=b[9]?b[10]:null;this.fragment=b[12]?b[13]:null;return this};this.toString=function(){var b="";null!==this.scheme&&(b=b+this.scheme+":");null!==this.authority&&(b=b+"//"+this.authority);null!==this.path&&(b+=this.path);null!==
this.query&&(b=b+"?"+this.query);null!==this.fragment&&(b=b+"#"+this.fragment);return b};this.toAbsolute=function(b){b=new jscolor.URI(b);var c=new jscolor.URI;if(null===b.scheme)return!1;null!==this.scheme&&this.scheme.toLowerCase()===b.scheme.toLowerCase()&&(this.scheme=null);null!==this.scheme?(c.scheme=this.scheme,c.authority=this.authority,c.path=f(this.path),c.query=this.query):(null!==this.authority?(c.authority=this.authority,c.path=f(this.path),c.query=this.query):(""===this.path?(c.path=
b.path,c.query=null!==this.query?this.query:b.query):("/"===this.path.substr(0,1)?c.path=f(this.path):(c.path=null!==b.authority&&""===b.path?"/"+this.path:b.path.replace(/[^\/]+$/,"")+this.path,c.path=f(c.path)),c.query=this.query),c.authority=b.authority),c.scheme=b.scheme);c.fragment=this.fragment;return c};c&&this.parse(c)},color:function(c,f,b,m,x,G){function z(a,b,c){if(null===a)return[c,c,c];var e=Math.floor(a),d=c*(1-b);a=c*(1-b*(e%2?a-e:1-(a-e)));switch(e){case 6:case 0:return[c,a,d];case 1:return[a,
c,d];case 2:return[d,c,a];case 3:return[d,a,c];case 4:return[a,d,c];case 5:return[c,d,a]}}function K(a,b){if(!jscolor.picker){jscolor.picker={box:document.createElement("div"),boxB:document.createElement("div"),pad:document.createElement("div"),padB:document.createElement("div"),padM:document.createElement("div"),sld:document.createElement("div"),sldB:document.createElement("div"),sldM:document.createElement("div"),btn:document.createElement("div"),btnS:document.createElement("span"),btnT:document.createTextNode(e.pickerCloseText),
btnOk:document.createElement("input"),btnCancle:document.createElement("input"),lbColor:document.createElement("input"),lbColor2:document.createElement("input"),bottom:document.createElement("div")};for(var t=0;t<jscolor.images.sld[1];t+=4){var f=document.createElement("div");f.style.height="4px";f.style.fontSize="1px";f.style.lineHeight="0";jscolor.picker.sld.appendChild(f)}jscolor.picker.sldB.appendChild(jscolor.picker.sld);jscolor.picker.box.appendChild(jscolor.picker.sldB);jscolor.picker.box.appendChild(jscolor.picker.sldM);
jscolor.picker.padB.appendChild(jscolor.picker.pad);jscolor.picker.box.appendChild(jscolor.picker.padB);jscolor.picker.box.appendChild(jscolor.picker.padM);jscolor.picker.btnS.appendChild(jscolor.picker.btnT);jscolor.picker.btn.appendChild(jscolor.picker.btnS);jscolor.picker.box.appendChild(jscolor.picker.btn);jscolor.picker.boxB.appendChild(jscolor.picker.box);jscolor.picker.lbColor2.setAttribute("type","text");jscolor.picker.lbColor2.setAttribute("id","cp_lb_color2");jscolor.picker.lbColor2.setAttribute("readonly",
"readonly");jscolor.picker.lbColor2.style.border="solid 1px #7D7D7D";jscolor.picker.lbColor2.style.width="40px";jscolor.picker.lbColor2.style.height="19px";jscolor.picker.lbColor.setAttribute("type","text");jscolor.picker.lbColor.setAttribute("id","cp_lb_color");jscolor.picker.lbColor.style.border="solid 1px #7D7D7D";jscolor.picker.lbColor.style.width="60px";jscolor.picker.lbColor.style.height="19px";jscolor.picker.btnOk.setAttribute("type","button");jscolor.picker.btnOk.setAttribute("id","cp_btn_ok");
jscolor.picker.btnOk.setAttribute("value","Ok");jscolor.picker.btnOk.style.width="40px";jscolor.picker.btnCancle.setAttribute("type","button");jscolor.picker.btnCancle.setAttribute("id","cp_btn_cancle");jscolor.picker.btnCancle.setAttribute("value","Close");jscolor.picker.btnCancle.style.width="55px";jscolor.picker.bottom.appendChild(jscolor.picker.lbColor2);jscolor.picker.bottom.appendChild(jscolor.picker.lbColor);jscolor.picker.bottom.appendChild(jscolor.picker.btnOk);jscolor.picker.bottom.appendChild(jscolor.picker.btnCancle);
jscolor.picker.boxB.appendChild(jscolor.picker.bottom);jscolor.picker.boxB.setAttribute("id","color_picker")}var d=jscolor.picker;d.box.onmouseup=d.box.onmouseout=function(){c.focus()};d.box.onmousedown=function(){u=!0};d.box.onmousemove=function(a){if(n||p)n&&A(a),p&&B(a),document.selection?document.selection.empty():window.getSelection&&window.getSelection().removeAllRanges(),v()};"ontouchstart"in window&&d.box.addEventListener("touchmove",function(a){var d={offsetX:a.touches[0].pageX-q.X,offsetY:a.touches[0].pageY-
q.Y};if(n||p)n&&A(d),p&&B(d),v();a.stopPropagation();a.preventDefault()},!1);d.padM.onmouseup=d.padM.onmouseout=function(){n&&(n=!1,jscolor.fireEvent(k,"change"))};d.padM.onmousedown=function(a){switch(r){case 0:0===e.hsv[2]&&e.fromHSV(null,null,1);break;case 1:0===e.hsv[1]&&e.fromHSV(null,1,null)}p=!1;n=!0;A(a);v()};"ontouchstart"in window&&d.padM.addEventListener("touchstart",function(a){q={X:a.target.offsetParent.offsetLeft,Y:a.target.offsetParent.offsetTop};this.onmousedown({offsetX:a.touches[0].pageX-
q.X,offsetY:a.touches[0].pageY-q.Y})});d.sldM.onmouseup=d.sldM.onmouseout=function(){p&&(p=!1,jscolor.fireEvent(k,"change"))};d.sldM.onmousedown=function(a){n=!1;p=!0;B(a);v()};"ontouchstart"in window&&d.sldM.addEventListener("touchstart",function(a){q={X:a.target.offsetParent.offsetLeft,Y:a.target.offsetParent.offsetTop};this.onmousedown({offsetX:a.touches[0].pageX-q.X,offsetY:a.touches[0].pageY-q.Y})});t=H(e);d.box.style.width=t[0]+"px";d.box.style.height=t[1]+"px";d.boxB.style.position="absolute";
d.boxB.style.clear="both";d.boxB.style.left=a-6+"px";d.boxB.style.top=b+2+"px";d.boxB.style.zIndex=e.pickerZIndex;d.boxB.style.border=e.pickerBorder+"px solid";d.boxB.style.borderColor=e.pickerBorderColor;d.boxB.style.background=e.pickerFaceColor;d.pad.style.width=jscolor.images.pad[0]+"px";d.pad.style.height=jscolor.images.pad[1]+"px";d.padB.style.position="absolute";d.padB.style.left=e.pickerFace+"px";d.padB.style.top=e.pickerFace+"px";d.padB.style.border=e.pickerInset+"px solid";d.padB.style.borderColor=
e.pickerInsetColor;d.padM.style.position="absolute";d.padM.style.left="0";d.padM.style.top="0";d.padM.style.width=e.pickerFace+2*e.pickerInset+jscolor.images.pad[0]+jscolor.images.arrow[0]+"px";d.padM.style.height=d.box.style.height;d.padM.style.cursor="crosshair";d.sld.style.overflow="hidden";d.sld.style.width=jscolor.images.sld[0]+"px";d.sld.style.height=jscolor.images.sld[1]+"px";d.sldB.style.display=e.slider?"block":"none";d.sldB.style.position="absolute";d.sldB.style.right=e.pickerFace+"px";
d.sldB.style.top=e.pickerFace+"px";d.sldB.style.border=e.pickerInset+"px solid";d.sldB.style.borderColor=e.pickerInsetColor;d.sldM.style.display=e.slider?"block":"none";d.sldM.style.position="absolute";d.sldM.style.right="0";d.sldM.style.top="0";d.sldM.style.width=jscolor.images.sld[0]+jscolor.images.arrow[0]+e.pickerFace+2*e.pickerInset+"px";d.sldM.style.height=d.box.style.height;try{d.sldM.style.cursor="pointer"}catch(l){d.sldM.style.cursor="hand"}d.btn.style.display=e.pickerClosable?"block":"none";
d.btn.style.position="absolute";d.btn.style.left=e.pickerFace+"px";d.btn.style.bottom=e.pickerFace+"px";d.btn.style.padding="0 15px";d.btn.style.height="18px";d.btn.style.border=e.pickerInset+"px solid";(function(){var a=e.pickerInsetColor.split(/\s+/);d.btn.style.borderColor=2>a.length?a[0]:a[1]+" "+a[0]+" "+a[0]+" "+a[1]})();d.btn.style.color=e.pickerButtonColor;d.btn.style.font="12px sans-serif";d.btn.style.textAlign="center";try{d.btn.style.cursor="pointer"}catch(g){d.btn.style.cursor="hand"}d.btn.onmousedown=
function(){e.hidePicker()};d.btnS.style.lineHeight=d.btn.style.height;d.btnOk.style.margin="0 2px 0 4px";d.btnOk.onmousedown=function(){var a=document.getElementById("cp_lb_color").value;if(void 0!==jscolor.styleColor||""!==jscolor.styleColor)jscolor.clickedElem.style[jscolor.styleColor]=a;else{var d=iframeDoc.createElement("input");d.type="hidden";d.value=a;d.id="hidden_color";iframeDoc.body.appendChild(d)}if(void 0!==jscolor.actionType||""!==jscolor.actionType)"font_color"==jscolor.actionType?parent.command_fontColor(parent.DEXTTOP.G_CURREDITOR.ID,
parent.document.getElementById("dext5_design_"+parent.DEXTTOP.G_CURREDITOR.ID),a):"font_bg_color"==jscolor.actionType?parent.command_fontBgColor(parent.DEXTTOP.G_CURREDITOR.ID,parent.document.getElementById("dext5_design_"+parent.DEXTTOP.G_CURREDITOR.ID),a):"doc_bg_color"==jscolor.actionType?parent.command_docBGColor(parent.DEXTTOP.G_CURREDITOR.ID,parent.document.getElementById("dext5_design_"+parent.DEXTTOP.G_CURREDITOR.ID),a):"cell_bg_color"==jscolor.actionType?parent.command_cellBGColor(parent.DEXTTOP.G_CURREDITOR.ID,
parent.document.getElementById("dext5_design_"+parent.DEXTTOP.G_CURREDITOR.ID),a):"line_color"==jscolor.actionType&&jscolor.excuteFn();jscolor.clickedElem=null;jscolor.styleColor="";jscolor.actionType="";jscolor.excuteFn=null;parent.document.body.removeChild(parent.document.getElementById("dext_color_back"));parent.document.body.removeChild(parent.document.getElementById("popupCover"))};d.lbColor2.style.margin="0 2px 0 0";d.bottom.style.margin="-5px 0 3px 11px";d.btnCancle.onmousedown=function(){jscolor.clickedElem=
null;jscolor.styleColor="";jscolor.actionType="";jscolor.excuteFn=null;document.getElementById("color_picker").style.display="none";parent.document.getElementById("popupCover").style.height="135px"};switch(r){case 0:var h="hs.png";break;case 1:h="hv.png"}d.padM.style.backgroundImage="url('"+jscolor.getDir()+"cross.gif')";d.padM.style.backgroundRepeat="no-repeat";d.sldM.style.backgroundImage="url('"+jscolor.getDir()+"arrow.gif')";d.sldM.style.backgroundRepeat="no-repeat";d.pad.style.backgroundImage=
"url('"+jscolor.getDir()+h+"')";d.pad.style.backgroundRepeat="no-repeat";d.pad.style.backgroundPosition="0 0";I();J();jscolor.picker.owner=e;document.getElementsByTagName("body")[0].appendChild(d.boxB)}function H(a){return[2*a.pickerInset+2*a.pickerFace+jscolor.images.pad[0]+(a.slider?2*a.pickerInset+2*jscolor.images.arrow[0]+jscolor.images.sld[0]:0),a.pickerClosable?4*a.pickerInset+3*a.pickerFace+jscolor.images.pad[1]+a.pickerButtonHeight:2*a.pickerInset+2*a.pickerFace+jscolor.images.pad[1]]}function I(){switch(r){case 0:var a=
1;break;case 1:a=2}var c=Math.round(e.hsv[0]/6*(jscolor.images.pad[0]-1)),a=Math.round((1-e.hsv[a])*(jscolor.images.pad[1]-1));jscolor.picker.padM.style.backgroundPosition=e.pickerFace+e.pickerInset+c-Math.floor(jscolor.images.cross[0]/2)+"px "+(e.pickerFace+e.pickerInset+a-Math.floor(jscolor.images.cross[1]/2))+"px";c=jscolor.picker.sld.childNodes;switch(r){case 0:for(var b=z(e.hsv[0],e.hsv[1],1),a=0;a<c.length;a+=1)c[a].style.backgroundColor="rgb("+b[0]*(1-a/c.length)*100+"%,"+b[1]*(1-a/c.length)*
100+"%,"+b[2]*(1-a/c.length)*100+"%)";break;case 1:var f,d=[e.hsv[2],0,0],a=Math.floor(e.hsv[0]),l=a%2?e.hsv[0]-a:1-(e.hsv[0]-a);switch(a){case 6:case 0:b=[0,1,2];break;case 1:b=[1,0,2];break;case 2:b=[2,0,1];break;case 3:b=[2,1,0];break;case 4:b=[1,2,0];break;case 5:b=[0,2,1]}for(a=0;a<c.length;a+=1)f=1-1/(c.length-1)*a,d[1]=d[0]*(1-f*l),d[2]=d[0]*(1-f),c[a].style.backgroundColor="rgb("+100*d[b[0]]+"%,"+100*d[b[1]]+"%,"+100*d[b[2]]+"%)"}}function J(){switch(r){case 0:var a=2;break;case 1:a=1}a=Math.round((1-
e.hsv[a])*(jscolor.images.sld[1]-1));jscolor.picker.sldM.style.backgroundPosition="0 "+(e.pickerFace+e.pickerInset+a-Math.floor(jscolor.images.arrow[1]/2))+"px"}function y(){return jscolor.picker&&jscolor.picker.owner===e}function L(){k!==c&&e.importColor()}function A(a){var c=jscolor.getRelMousePos(a);a=c.x-e.pickerFace-e.pickerInset;c=c.y-e.pickerFace-e.pickerInset;switch(r){case 0:e.fromHSV(6/(jscolor.images.pad[0]-1)*a,1-c/(jscolor.images.pad[1]-1),null,C);break;case 1:e.fromHSV(6/(jscolor.images.pad[0]-
1)*a,null,1-c/(jscolor.images.pad[1]-1),C)}}function B(a){a=jscolor.getRelMousePos(a).y-e.pickerFace-e.pickerInset;switch(r){case 0:e.fromHSV(null,null,1-a/(jscolor.images.sld[1]-1),D);break;case 1:e.fromHSV(null,1-a/(jscolor.images.sld[1]-1),null,D)}}function v(){e.onImmediateChange&&("string"===typeof e.onImmediateChange?new Function(e.onImmediateChange):e.onImmediateChange).call(e)}jscolor.clickedElem=b;jscolor.styleColor=m;jscolor.actionType=x;jscolor.excuteFn=G;this.slider=this.caps=this.hash=
this.adjust=this.required=!0;this.styleElement=this.valueElement=c;this.onImmediateChange=null;this.hsv=[0,0,1];this.rgb=[1,1,1];this.minH=0;this.maxH=6;this.minS=0;this.maxS=1;this.minV=0;this.maxV=1;this.pickerOnfocus=!1;this.pickerMode="HSV";this.pickerPosition="bottom";this.pickerSmartPosition=!1;this.pickerButtonHeight=20;this.pickerClosable=!1;this.pickerCloseText="Close";this.pickerButtonColor="ButtonText";this.pickerFace=10;this.pickerFaceColor="ThreeDFace";this.pickerBorder=1;this.pickerBorderColor=
"ThreeDHighlight ThreeDShadow ThreeDShadow ThreeDHighlight";this.pickerInset=1;this.pickerInsetColor="ThreeDShadow ThreeDHighlight ThreeDHighlight ThreeDShadow";this.pickerZIndex=15E3;for(var E in f)f.hasOwnProperty(E)&&(this[E]=f[E]);this.hidePicker=function(){y()&&(delete jscolor.picker.owner,document.getElementsByTagName("body")[0].removeChild(jscolor.picker.boxB))};this.showPicker=function(){if(!y()){var a=jscolor.getElementPos(c),b=jscolor.getElementSize(c),e=jscolor.getViewPos(),f=jscolor.getViewSize(),
d=H(this),l,g,h;switch(this.pickerPosition.toLowerCase()){case "left":l=1;g=0;h=-1;break;case "right":l=1;g=0;h=1;break;case "top":l=0;g=1;h=-1;break;default:l=0,h=g=1}var k=(b[g]+d[g])/2,a=this.pickerSmartPosition?[-e[l]+a[l]+d[l]>f[l]?-e[l]+a[l]+b[l]/2>f[l]/2&&0<=a[l]+b[l]-d[l]?a[l]+b[l]-d[l]:a[l]:a[l],-e[g]+a[g]+b[g]+d[g]-k+k*h>f[g]?-e[g]+a[g]+b[g]/2>f[g]/2&&0<=a[g]+b[g]-k-k*h?a[g]+b[g]-k-k*h:a[g]+b[g]-k+k*h:0<=a[g]+b[g]-k+k*h?a[g]+b[g]-k+k*h:a[g]+b[g]-k-k*h]:[a[l],a[g]+b[g]-k+k*h];K(a[l],a[g])}};
this.importColor=function(){k?this.adjust?!this.required&&/^\s*$/.test(k.value)?(k.value="",h.style.backgroundImage=h.jscStyle.backgroundImage,h.style.backgroundColor=h.jscStyle.backgroundColor,h.style.color=h.jscStyle.color,this.exportColor(w|F)):this.fromString(k.value)||this.exportColor():this.fromString(k.value,w)||(h.style.backgroundImage=h.jscStyle.backgroundImage,h.style.backgroundColor=h.jscStyle.backgroundColor,h.style.color=h.jscStyle.color,this.exportColor(w|F)):this.exportColor()};this.exportColor=
function(a){if(!(a&w)&&k){var b=this.toString();this.caps&&(b=b.toUpperCase());this.hash&&(b="#"+b);null!=document.getElementById("cp_lb_color")&&(document.getElementById("cp_lb_color").value=b)}a&F||!h||(h.style.backgroundImage="none",null!=document.getElementById("cp_lb_color2")&&(document.getElementById("cp_lb_color2").style.backgroundColor="#"+this.toString()));a&D||!y()||I();a&C||!y()||J()};this.fromHSV=function(a,b,c,e){null!==a&&(a=Math.max(0,this.minH,Math.min(6,this.maxH,a)));null!==b&&(b=
Math.max(0,this.minS,Math.min(1,this.maxS,b)));null!==c&&(c=Math.max(0,this.minV,Math.min(1,this.maxV,c)));this.rgb=z(null===a?this.hsv[0]:this.hsv[0]=a,null===b?this.hsv[1]:this.hsv[1]=b,null===c?this.hsv[2]:this.hsv[2]=c);this.exportColor(e)};this.fromRGB=function(a,b,c,e){null!==a&&(a=Math.max(0,Math.min(1,a)));null!==b&&(b=Math.max(0,Math.min(1,b)));null!==c&&(c=Math.max(0,Math.min(1,c)));a=null===a?this.rgb[0]:a;b=null===b?this.rgb[1]:b;var d=null===c?this.rgb[2]:c,f=Math.min(Math.min(a,b),d);
c=Math.max(Math.max(a,b),d);var g=c-f;0===g?a=[null,0,c]:(a=a===f?3+(d-b)/g:b===f?5+(a-d)/g:1+(b-a)/g,a=[6===a?0:a,g/c,c]);null!==a[0]&&(this.hsv[0]=Math.max(0,this.minH,Math.min(6,this.maxH,a[0])));0!==a[2]&&(this.hsv[1]=null===a[1]?null:Math.max(0,this.minS,Math.min(1,this.maxS,a[1])));this.hsv[2]=null===a[2]?null:Math.max(0,this.minV,Math.min(1,this.maxV,a[2]));a=z(this.hsv[0],this.hsv[1],this.hsv[2]);this.rgb[0]=a[0];this.rgb[1]=a[1];this.rgb[2]=a[2];this.exportColor(e)};this.fromString=function(a,
b){var c="More".match(/^\W*([0-9A-F]{3}([0-9A-F]{3})?)\W*$/i);return c?(6===c[1].length?this.fromRGB(parseInt(c[1].substr(0,2),16)/255,parseInt(c[1].substr(2,2),16)/255,parseInt(c[1].substr(4,2),16)/255,b):this.fromRGB(parseInt(c[1].charAt(0)+c[1].charAt(0),16)/255,parseInt(c[1].charAt(1)+c[1].charAt(1),16)/255,parseInt(c[1].charAt(2)+c[1].charAt(2),16)/255,b),!0):!1};this.toString=function(){return(256|Math.round(255*this.rgb[0])).toString(16).substr(1)+(256|Math.round(255*this.rgb[1])).toString(16).substr(1)+
(256|Math.round(255*this.rgb[2])).toString(16).substr(1)};var e=this,r="hvs"===this.pickerMode.toLowerCase()?1:0,u=!1,k=jscolor.fetchElement(this.valueElement),h=jscolor.fetchElement(this.styleElement),n=!1,p=!1,q={},w=1,F=2,D=4,C=8;jscolor.addEvent(c,"click",function(){e.showPicker()});jscolor.addEvent(c,"blur",function(){u?u=!1:window.setTimeout(function(){u||(k===c&&e.importColor(),e.pickerOnfocus&&e.hidePicker());u=!1},0)});k&&(f=function(){e.fromString(k.value,w);v()},jscolor.addEvent(k,"keyup",
f),jscolor.addEvent(k,"input",f),jscolor.addEvent(k,"blur",L),k.setAttribute("autocomplete","off"));h&&(h.jscStyle={backgroundImage:h.style.backgroundImage,backgroundColor:h.style.backgroundColor,color:h.style.color});switch(r){case 0:jscolor.requireImage("hs.png");break;case 1:jscolor.requireImage("hv.png")}jscolor.requireImage("cross.gif");jscolor.requireImage("arrow.gif");this.importColor()}};jscolor.install();