<%@page import="com.tohours.imo.bean.TopExcellence"%>
<%@page import="com.tohours.imo.bean.SubExcellence"%>
<%@page import="org.nutz.dao.Dao"%>
<%@page import="org.nutz.lang.util.NutMap"%>
<%@page import="com.alibaba.druid.util.StringUtils"%>
<%@page import="org.apache.poi.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
	<meta id="view" name="viewport" >
	<title></title>
	<link rel="stylesheet" href="static/css/common.css">
	<link rel="stylesheet" href="static/css/form.css">
	<link rel="stylesheet" href="static/css/list.css">
	<script type="text/javascript" src="static/js/html5.js"></script>
	<!--[if lt IE 9]>
	<script src="static/js/css3-mediaqueries.js"></script>
	<![endif]-->
	<script src="static/js/jquery.js"></script>
	<!--[if lt IE 10]>
	<script type="text/javascript" src="static/PIE.js"></script>
	<![endif]-->
	<link href="static/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="static/js/jquery-ui-1.9.2.redmond.min.js"></script>
	<script type="text/javascript" src="static/js/common.js"></script>
	<script type="text/javascript" src="static/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="static/js/tohours.min.js"></script>
	</head>

<body>
	<div id="loadingWindow" title="加载中" style="display:none">
		<div style="text-align:center;padding-top:5px;font-size:12px;"><img src="./static/images/loading.gif" width="15" height="15"/> <span id="myLoad">加载中...</span></div>
	</div>
	<script>
		$(function() {
			$("#loadingWindow").dialog({
				autoOpen:false,
				modal: true,
				resizable: false,
				draggable: false,
				height:50
			});
			$('#loadingWindow').prev().remove();
		});
		function showLoading(msg){
			window._loading = new Date().getTime();
			$('#myLoad').html(msg);
			if($("#loadingWindow").dialog('isOpen') == false){
				$("#loadingWindow").dialog('open');
			}
		}
		function updateLoading(msg){
			$('#myLoad').html(msg);
		}
		function closeLoading(){
			if(window._loading && new Date() - window._loading < 1000){
				setTimeout(function(){
					$("#loadingWindow").dialog('close');
				}, new Date() - window._loading);
			} else {
				$("#loadingWindow").dialog('close');
			}
		}
	</script>
	

	<div id="messageWindow" title="系统提示" style="font-size:12px;" style="display:none">
		<div class="ui-widget" style="display:none" id="messageError">
			<div class="ui-state-error ui-corner-all" style="padding: 10px">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin-right: .3em;"></span> <strong>错误:</strong>
					<span id="myMsg"></span>
				</p>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			$("#messageWindow").dialog({
				autoOpen:false,
				modal: true,
				resizable: false,
				draggable: false,
				buttons: {
					'确定':function(){
						$("#messageWindow").dialog('close');
					}
				}
			});
		});
		function showMessage(msg){
			$('#myMsg').html(msg);
			if($("#messageWindow").dialog('isOpen') == false){
				$('#messageError').show();
				$("#messageWindow").dialog('open');
			}
		}
		function updateMessage(msg){
			$('#myMsg').html(msg);
		}
		function closeMessage(){
			$("#messageWindow").dialog('close');
		}
	</script>


	<div id="confirmWindow" title="系统提示" style="font-size:12px;" style="display:none">
		<div style="font-size:16px;"><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><span id="myConfirm"></span></div>
	</div>
	<script>
		$(function() {
			$("#confirmWindow").dialog({
				autoOpen:false,
				modal: true,
				resizable: false,
				draggable: false,
				buttons: {
					'确定':function(){
						if(window._confirmCallback){
							$("#confirmWindow").dialog('close');
							window._confirmCallback();
							window._confirmCallback = undefined;
						}
					},
					'取消':function(){
						$("#confirmWindow").dialog('close');
					}
				}
			});
		});
		function showConfirm(msg, callback){
			window._confirmCallback = callback;
			$('#myConfirm').html(msg);
			if($("#confirmWindow").dialog('isOpen') == false){
				$("#confirmWindow").dialog('open');
			}
		}
		function closeConfirm(){
			$("#confirmWindow").dialog('close');
		}
	</script>
	

<header class="header"><img class="logo" src="static/images/logo.png"></header>
<nav class="nav"><a class="menu_btn" href="javascript:void(0);">菜单</a>
<% 
	NutMap obj = (NutMap)request.getAttribute("obj"); 
	String _type=request.getParameter("type");
	if("1".equals(_type)){
%>
<a class="add_btn" href="resource?type=${param.type}">新增</a>
<%
	}
	if(StringUtils.isEmpty(_type)){
		if(obj != null){
		String _addFlag=(String)obj.get("addFlag");
		String _top_excel_id=(String)obj.get("top_excel_id");
			if("topexcellence".equals(_addFlag)){
%>
<a class="add_btn" href="topExcellencePre">新增</a>
<%
			}else if("subexcellence".equals(_addFlag)){
				String top_id=obj.getString("top_excel_id");
%>
<a class="add_btn" href="subExcellencePre?top_excel_id=<%=top_id %>">新增</a>
<% 
			}else if("peoples".equals(_addFlag)){
				String sub_id=obj.getString("sub_excel_id");
%>
<a class="add_btn" href="peoplesPre?sub_excel_id=<%=sub_id%>">新增</a>
<%
			}
	}
	}
%>
<a class="sign_out_btn" href="../ContentManager?key=home">返回IMOCN</a>
<ul class="menu cf">
	<li class="list_column list1">
		<ul>
			<li><a href="resourceList?type=1">首页资源</a></li>
			<li><a href="#">面谈对象</a></li>
			<li><a href="resourceList?type=3">引导页资源</a></li>
		</ul>
	</li>
	<li class="list_column">
		<ul>
			<li><a href="resourceList?type=4">十大要素</a></li>
			<li><a href="javascript:;">&nbsp;</a></li>
			<li><a href="topExcellenceIndex">TopExcellence</a></li>
		</ul>
	</li>
	<li class="list_column">
		<ul>
			<li><a href="#">行业分类</a></li>
			<li><a href="/attract/talentIndex">人才资源</a></li>
			<li></li>
		</ul>
	</li>
	<li class="list_column">
		<ul>
			<li><a href="#">优秀营销员</a></li>
			<li><a href="resourceList?type=5">友邦十大要素</a></li>
			<li></li>
		</ul>
	</li>
		<%
	if(obj != null){
		String _addFlag=(String)obj.get("addFlag");
		String _sub_excel_id="";
		String _top_excel_id="";
		
		if(obj.containsKey("sub_excel_id")){
			_sub_excel_id=obj.getString("sub_excel_id");
		}
		if(obj.containsKey("top_excel_id")){
			_top_excel_id=(String)obj.get("top_excel_id");	
		}
		if(StringUtils.isEmpty(_top_excel_id) == false){
	%> 
		<li class="list_column">
			<ul>
				<li><a href="subExcellenceIndex?top_excel_id=<%=_top_excel_id %>">SubExcellence</a></li>
			</ul>
		</li>
	<%
		}
		if(StringUtils.isEmpty(_sub_excel_id) == false){
			_top_excel_id=(String)request.getAttribute("top_id");
	%>
		<li class="list_column">
			<ul>
				<li><a href="subExcellenceIndex?top_excel_id=<%=_top_excel_id %>">SubExcellence</a></li>
				<li><a href="peoplesIndex?sub_excel_id=<%=_sub_excel_id %>">Peoples</a></li>
			</ul>
		</li>
<%	
		}
	}
%>
</ul>
<script type="text/javascript"></script>
</nav>