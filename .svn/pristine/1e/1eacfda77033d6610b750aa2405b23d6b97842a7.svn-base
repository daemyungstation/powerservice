/*
   파일로 업로드 할 수 있도록 개발한 부분
   custom_code : 0 -> 파일로 업로드
   custom_code : 1 -> DB에 업로드
   개발자 : 정용재
   개발일자 : 2014.08.27
*/
SetImageAutoFitSize = function(a,b){
    var splitChr = "1"==DEXTTOP.G_CURREDITOR._config.custom_code ? "^^" : "?";
    var c=0,d=0,e="",e=b,f=b.split(splitChr);
    2==f.length&&(e=f[0],f=f[1].split("^"),2==f.length&&(c=f[0],d=f[1]));
    if("1"==DEXTTOP.G_CURREDITOR._config.image_auto_fit&&c>DEXTTOP.G_CURREDITOR._defaultWidth) {
        f=DEXTTOP.G_CURREDITOR._defaultWidth/c;
        c=DEXTTOP.G_CURREDITOR._defaultWidth;
        d=parseInt(f*d);
    } else if("2"==DEXTTOP.G_CURREDITOR._config.image_auto_fit){
        var f=c,g=d;
        if(f>DEXTTOP.G_CURREDITOR._defaultWidth||g>DEXTTOP.G_CURREDITOR._defaultHeight){
            var c=DEXTTOP.G_CURREDITOR._defaultWidth;
            var d=DEXTTOP.G_CURREDITOR._defaultHeight;
            var h=d/c;
            var k=g/f;
            k>h?g>d?c=Math.round(f*d/g):(c=f,d=g):k<h?f>c?d=Math.round(g*c/f):(c=f,d=g):(c=f,d=g);
        }
    }


    // 부모창 fileIds에 저장된 이미지 파일의 아이디를 남긴다.
    if ("1"==DEXTTOP.G_CURREDITOR._config.custom_code) {
        var target = parent.$("#dext_editor").attr("target") == undefined || parent.$("#dext_editor").attr("target") == "" ? "viewModel" : parent.$("#dext_editor").attr("target");
        var objFileIds = eval("parent." + target + ".selectedData().fileIds();");

        var fileid = e;
        fileid = fileid.substring(fileid.indexOf("fileid")+7, fileid.length);
        objFileIds.push(fileid);
    }

    a.src=e;
    0<c&&0<d&&(a.style.width=c+"px",a.style.height=d+"px",a.removeAttribute("width"),a.removeAttribute("height"));
    return a;
};
function postBase64ImageToServer(a,b,c){
    if(G_dext5plugIn)
        try{var d=G_dext5plugIn.getLocalImageFromBase64Image(a,b);0<d.length&&(c.src=d);}catch(e){}
    else if("1"==DEXTTOP.G_CURREDITOR._config.paste_image_base64){
        if(void 0==c||null==c)
            d=_iframeDoc.createElement("img")
          , d.src="data:image/"+b+";base64,"+a
          , d.alt="",a=getFirstRange().range
          , a.deleteContents()
          , a.collapse(!0)
          , a.pasteHtml(d.outerHTML)
          , a.collapse(!1);
    } else {
        var f=DEXTTOP.DEXT5.ajax.postData(DEXTTOP.G_CURREDITOR._config.post_url,"dext=test");
        if(f!=null) {
            d="";
            a=a.replace(/[+]/g,"%2B");
            d=d+("imagedata="+a)+("&savefileext="+b);
            d+="&serverdomain="+DEXTTOP.G_CURREDITOR._config.serverDomain;
            d+="&tosavepathurl="+DEXTTOP.G_CURREDITOR._config.toSavePathURL;
            d+="&savafoldernamerule="+DEXTTOP.G_CURREDITOR._config.saveFolderNameRule;
            d+="&savafilenamerule="+DEXTTOP.G_CURREDITOR._config.saveFileNameRule;
            d+="&proxy_url="+DEXTTOP.G_CURREDITOR._config.proxy_url;
            d+="&image_convert_format="+DEXTTOP.G_CURREDITOR._config.image_convert_format;
            d+="&image_convert_width="+DEXTTOP.G_CURREDITOR._config.image_convert_width;
            d+="&image_convert_height="+DEXTTOP.G_CURREDITOR._config.image_convert_height;
            d+="&uploadtype=image";

            // 대상테이블 입력 2014.11.04 정용재
            var table = parent.$("#dext_editor").attr("table") == undefined || parent.$("#dext_editor").attr("table") == "" ? "" : parent.$("#dext_editor").attr("table");
            if (table == "") {
            alert("대상테이블을 입력하지 않았습니다.\r\n확인해주세요.");
                return
            } else {
                d+="&reltblnm=" + table;
            }

            f=DEXTTOP.DEXT5.ajax.postData("1"==DEXTTOP.G_CURREDITOR._config.custom_code ? "/powerservice/file/imagebrowser/pasteinsert" : DEXTTOP.G_CURREDITOR._config.post_url,d)
            , void 0==c||null==c?(d=_iframeDoc.createElement("img"), d=SetImageAutoFitSize(d,f), a=getFirstRange().range, a.deleteContents(), a.collapse(!0), a.pasteHtml(d.outerHTML), a.collapse(!1)):c=SetImageAutoFitSize(c,f);

        } else {
            var g=document.createElement("form");
            g.method="post";
            g.target="upload_frame";
            a='<input type="hidden" id="imagedata" name="imagedata" value="'+a+'" />'+('<input type="hidden" id="savefileext" name="savefileext" value="'+b+'" />');
            a+='<input type="hidden" id="serverdomain" name="serverdomain" value="'+DEXTTOP.G_CURREDITOR._config.serverDomain+'" />';
            a+='<input type="hidden" id="tosavepathurl" name="tosavepathurl" value="'+DEXTTOP.G_CURREDITOR._config.toSavePathURL+'" />';
            a+='<input type="hidden" id="savafoldernamerule" name="savafoldernamerule" value="'+DEXTTOP.G_CURREDITOR._config.saveFolderNameRule+'" />';
            a+='<input type="hidden" id="savafilenamerule" name="savafilenamerule" value="'+DEXTTOP.G_CURREDITOR._config.saveFileNameRule+'" />';
            a+='<input type="hidden" id="proxy_url" name="proxy_url" value="'+DEXTTOP.G_CURREDITOR._config.proxy_url+'" />';
            a+='<input type="hidden" id="image_convert_format" name="image_convert_format" value="'+DEXTTOP.G_CURREDITOR._config.image_convert_format+'" />';
            a+='<input type="hidden" id="image_convert_width" name="image_convert_width" value="'+DEXTTOP.G_CURREDITOR._config.image_convert_width+'" />';
            a+='<input type="hidden" id="image_convert_height" name="image_convert_height" value="'+DEXTTOP.G_CURREDITOR._config.image_convert_height+'" />';
            a+='<input type="hidden" id="uploadtype" name="uploadtype" value="image" />';

            // 대상테이블 입력 2014.11.04 정용재
            var table = parent.$("#dext_editor").attr("table") == undefined || parent.$("#dext_editor").attr("table") == "" ? "" : parent.$("#dext_editor").attr("table");
            if (table == "") {
            alert("대상테이블을 입력하지 않았습니다.\r\n확인해주세요.");
                return
            } else {
                a+='<input type="hidden" id="reltblnm" name="reltblnm" value="' + table + '" />';
            }

            a+='<iframe id="upload_frame" name="upload_frame" frameborder="0" style="width:0px; height:0px;"></iframe>';
            g.innerHTML=a;
            document.body.appendChild(g);
            g.action= "1"==DEXTTOP.G_CURREDITOR._config.custom_code ? "/powerservice/file/imagebrowser/pasteinsert" : DEXTTOP.G_CURREDITOR._config.post_url;
            g.submit();
            var h=document.getElementById("upload_frame");
            DEXTTOP.DEXT5.util.addEvent(h,"load",function(){
                var a,b;
                try{a=h.contentDocument?h.contentDocument.body:h.contentWindow.document.body;}
                catch(d){a=h.document.body;}
                b="textContent"in a?a.textContent:a.innerText;
                document.body.removeChild(g);
                void 0==c||null==c?(a=_iframeDoc.createElement("img"),a=SetImageAutoFitSize(a,b),b=getFirstRange().range,b.deleteContents(),b.collapse(!0),b.pasteHtml(a.outerHTML),b.collapse(!1)):c=SetImageAutoFitSize(c,b);
            },!0);
        }
    }
};
