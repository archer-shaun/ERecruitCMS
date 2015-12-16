<%@page import="org.nutz.lang.util.NutMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc-top.jsp" %>
<link rel="stylesheet" href="static/css/lightbox.css">
<script type="text/javascript" src="static/js/lightbox.js"></script>
<h2 class="page_title"></h2>
<section class="form">
<%
	String sub_excel_id=obj.getString("sub_excel_id");
%>
<form id="queryForm">
		<input type="hidden" name="pageNumber" id="pageNumber" value="1">
		<input type="hidden" name="pageSize" value="10">
		<input type="hidden" name="sub_excel_id" value="<%=sub_excel_id%>">
	<div class="input_box">
		<label class="label">关键字：</label>
		<div class="input_border radius"><input type="text" name="key" id="key"></div>
	</div>
	<div class="input_box btn_box cf">
		<div class="input_border search_border search_border2"><input type="button" id="btnSearch"></div><div class="input_border clear_border"><input type="button" id="btnClear"></div>
	</div>
</form></section>
<section class="table">
<ul class="table_list">
	<li class="cf title">
		<p class="column1"><span>名字</span></p>
		<p class="column2"><span>加入时间</span></p>
		<p class="column3"><span>资源</span></p>
		<p class="column4"><span>编辑</span></p>
	</li>
</ul></section>
<script type="text/javascript">
	function listReload(){
		showLoading('加载中...');
		$.ajax({
			url : 'peoplesList',
			data : $("#queryForm").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				closeLoading();
				var list = data.list;
				$('.page_title').html("peoples");
				$('.table_list .cf').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				$('.control').remove();
				var html="";
				for(var i = 0;i<list.length;i++){
					var bean = list[i];
					var name = bean.name == null || bean.name == "undefined" ? "":bean.name;
					var joinDate = bean.joinDate == null || bean.joinDate == "undefined" ? "":bean.joinDate;
					var fileId = bean.fileId == null || bean.fileId == "undefined" ? "":bean.fileId;
					
					var light = 'data-lightbox="example-{0}"'.format(fileId);
					
					html=html+"<li class=\"cf\"><p class=\"column1\"><span>"+name+"</span></p><p class=\"column2\"><span>"+joinDate+"</span></p>";
					html += '<p class="column3"><span>';
					html += '<a class="file-icon" href="resourceFile?id={0}" {1} title="{3}"><img src="static/images/{2}-icon.jpg"/></a>'.format(bean.fileId, light, "img", bean.fileName);
					html += '</span></p>';
					html += '<p class="column4"><span>';
					html += '	<a class="edit" href="javascript:;" onclick="edit(\'{0}\',\'{1}\');"></a>'.format(bean.id,bean.sub_excel_id);
					html += '	<a class="delete" href="javascript:;" onclick="del(\'{0}\');"></a>'.format(bean.id);
					html += '</span></p>';
					html+="\</li>";
				}
				$('.table_list').append(html);
				if(list.length == 0){
					$('.table_list').append('<li class="cf" style="text-align:center;font-color:gray;">没有找到数据</li>');
				} else {
					var pager = data.pager;
					var foot = '';
					foot += '	<div class="control">';
					foot += '		<span><i>{0}</i>/<i>{1}</i>页</span>'.format(pager.pageNumber, pager.pageCount);
					if(pager.pageNumber > 1){
						foot += '<a href="javascript:;" onclick="go({0})">上一页</a> '.format(pager.pageNumber - 1);
					}
					if(pager.pageNumber < pager.pageCount){
						foot += '<a href="javascript:;" onclick="go({0})">下一页</a>'.format(pager.pageNumber + 1);
					}
					foot += '	</div>';

					$('.table_list').after(foot);
				}
			}
		});
	}
	
	$(function(){
		listReload();
		$('#btnSearch').click(function(){
			$('#pageNumber').val(1);
			listReload();
		});
		$('#btnClear').click(function(){
			$('#key').val('');
			$('#pageNumber').val(1);
			listReload();
		});
	});
	
	function edit(id,sub_excel_id){
		location.href = 'peoplesPre?id=' + id +"&sub_excel_id="+sub_excel_id;
	}
	
	function del(id){
		showConfirm('你确定要删除这条记录吗？', function(){
			if(id){
				$.ajax({
					url:'peoplesDelete',
					data:'id=' + id,
					dataType:'json',
					success: function(json){
						if(json.success){
							listReload();
						} else {
							showMessage(json.msg);
						}
					}
				})
			} else {
				showMessage('所提供的ID不存在！');
			}
		});
	}
	function go(pageNumber){
		$('#pageNumber').val(pageNumber);
		listReload();
	}
</script>
</body>
</html>