<%@ tag language="java" pageEncoding="UTF-8"%><%@ attribute name="fullname" type="java.lang.String" required="true" description="全路径标签名称"%><%@ attribute name="fullnameid" type="java.lang.String" required="true" description="全路径标签名称id"%><%@ attribute name="filename" type="java.lang.String" required="true" description="课件名标签名称"%><%@ attribute name="fullvalue" type="java.lang.String" required="true" description="全路径标签值"%><%@ attribute name="filevalue" type="java.lang.String" required="true" description="文件名标签值"%><%@ attribute name="id" type="java.lang.String" required="true" description="上传标签id"%><%@ attribute name="itemId" type="java.lang.String" required="true" description="上传主键id"%><%@ attribute name="folder" type="java.lang.String" required="false" description="两个选择项：System(默认,返回绝对路径),Folder(返回项目路径)"%><%@ taglib prefix="j" uri="/WEB-INF/tlds/formtag.tld"%><j:set name="ctx" value="${pageContext.request.contextPath}" /><script type="text/javascript">    var count = 0;    function callMethod(method) {        method();    }    jQuery(function () {        jQuery("#${id}").uploadify({            'buttonClass' : 'btn btn-info btn-small',            'buttonText' : '上传',            'multi' : false,            'simUploadLimit' : 1,            'height':'20px',            'swf' : '${ctx}/resources/uploadify/uploadify.swf',            'uploader' : '${ctx}/ajax/courseware/setUpload;jsessionid=${pageContext.session.id}',            'auto' : true,            'fileTypeExts' : '*.*;',            'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {            	$('#${id}').parent().prev(".kj-bg").children('h6').html('<b class="gif-loading"></b>努力上传中...'+Math.round((bytesUploaded/bytesTotal)*100)+'%');            },            'onUploadComplete' : function(file) {            	setTimeout(hidePro,1500);            },            'onUploadStart' : function(file) {                jQuery("#${id}").uploadify("settings", "formData", {'folder':'${folder}','itemId':'${itemId}','filename':'${filename}'});            },            'onUploadSuccess' : function (file, data, Response) {                if (Response) {                    var objvalue = eval("(" + data + ")");                    var fdPath=objvalue.fdPath;                    //$('#${id}').parent().next(".kj-btns").children(".btn-link").last().attr("href",'${ctx}/'+fdPath);                    $('#${id}').parent().next(".kj-btns").children(".btn-link").last().attr("href",'${ctx}/common/courseware/'+objvalue.attId);                    jQuery("#${fullnameid}").val(fdPath);                    count++;                }            }        });        var cwtit = $('#${id}').parent().prev(".kj-bg").children('h6').text();        function hidePro(){        	    	            $('#${id}').parent().prev(".kj-bg").children('h6').text(cwtit);              $('#${id}').parent().prev(".kj-bg").children('.status').html('<span class="text-warning">已上传</span>');            $('#${id}').parent().next(".kj-btns").children(".btn-link").last().removeAttr("disabled");            $('#${id}').nextAll('button').removeAttr("disabled");            // window.parent.ifraAlert2("上传成功!");        }    });</script><input type="file" class="" name="${id}" id="${id}" /><input type="hidden" id="${fullnameid}" name="${fullname}" value="${fullvalue}" />