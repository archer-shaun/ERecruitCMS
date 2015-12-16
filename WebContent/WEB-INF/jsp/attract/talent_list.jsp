<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc-top.jsp" %>
<link rel="stylesheet" href="static/css/lightbox.css">
<script type="text/javascript" src="static/js/lightbox.js"></script>
<h2 class="page_title"></h2>
<section class="form">
<form id="queryForm">
		<input type="hidden" name="pageNumber" id="pageNumber" value="1">
		<input type="hidden" name="pageSize" value="10">
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
		<p class="column1"><span>用户</span></p>
		<p class="column2"><span>名字</span></p>
		<p class="column3"><span>性别</span></p>
		<p class="column4"><span>生日</span></p>
	</li>
</ul></section>
<script type="text/javascript">
	function listReload(){
		showLoading('加载中...');
		$.ajax({
			url : 'talentList',
			data : $("#queryForm").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				closeLoading();
				var list = data.list;
				$('.page_title').html("人才资源");
				$('.table_list .cf').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				$('.control').remove();
				var html="";
				for(var i = 0;i<list.length;i++){
					var bean = list[i];
					var userId = bean.userId == null || bean.userId == "undefined" ? "":bean.userId;
					var name = bean.name == null || bean.name == "undefined" ? "":bean.name;
					var sex = bean.sex == null || bean.sex == "undefined" ? "":bean.sex;
					var birthday = bean.birthday == null || bean.birthday == "undefined" ? "":bean.birthday;
					
					html=html+"<li class=\"cf\"><p class=\"column1\"><span>"+userId+"</span></p><p class=\"column2\"><span>"+name+"</span></p>\<p class=\"column3\"><span>"+sex+"</span></p>\<p class=\"column4\"><span>"+birthday+"</span></p>\</li>";
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
	
	function edit(id){
		location.href = 'resource?id=' + id + '&type=' + type;
	}
	
	function del(id){
		showConfirm('你确定要删除这条记录吗？', function(){
			if(id){
				$.ajax({
					url:'resourceDelete',
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