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
			<p class="column1" style="width:30%;"><span>标题</span></p>
			<p class="column3" style="width:50%;"><span>资源</span></p>
			<p class="column2" style="width:20%;"><span>编辑</span></p>
		</li>
</ul></section>
<script type="text/javascript">
	function listReload(){
		showLoading('加载中...');
		$.ajax({
			url : 'topExcellenceList',
			data : $("#queryForm").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				closeLoading();
				var list = data.qr.list;
				$('.page_title').html(data.title);
				$('.table_list .cf').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				$('.control').remove();
				for(var i = 0;i<list.length;i++){
					var bean = list[i];
					var fileIds = bean.fileIds.split(',');
					var fileTypes = bean.fileTypes.split(',');
					var contentTypes = bean.contentTypes.split(',');
					var fileNames = bean.fileNames.split(',');
					
					var tr = '';
						tr += '		<li class="cf">';
						tr += '		<p class="column1" style="width:30%;"><span>{0}</span></p>'.format(bean.name);
						tr += '		<p class="column3" style="width:50%;"><span>';
						for(var j = 0;j<bean.fileCounts;j++){
							var isImg = contentTypes[j].indexOf('image') >= 0;
							var icon = isImg ? 'img':'file';
							var light = isImg ?'data-lightbox="example-{0}"'.format(fileIds[j]):'';
							tr += '	<a class="file-icon" href="resourceFile?id={0}" {1} title="{3}"><img src="static/images/{2}-icon.jpg"/></a>'.format(fileIds[j], light, icon, fileNames[j]);
						}
						tr += '		</span></p>';
						tr += '		<p class="column2" style="width:20%;"><span>';
						tr += '			<a class="next" href="javascript:;" title=\"下一个\"onclick="subExcellence(\'{0}\');"></a>'.format(bean.id);
						tr += '			<a class="edit" href="javascript:;" onclick="edit(\'{0}\');"></a>'.format(bean.id);
						tr += '			<a class="delete" href="javascript:void(0);" onclick="del(\'{0}\');"></a>'.format(bean.id);
						tr += '		</span></p>';
						tr += '	</li>';
					
					$('.table_list').append(tr);
				}
				if(list.length == 0){
					$('.table_list').append('<li class="cf" style="text-align:center;font-color:gray;">没有找到数据</li>');
				} else {
					var pager = data.qr.pager;
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
		location.href = 'topExcellencePre?id=' + id;
	}
	function subExcellence(id){
		location.href = 'subExcellenceIndex?top_excel_id=' + id;
	}
	
	function del(id){
		showConfirm('你确定要删除这条记录吗？', function(){
			if(id){
				$.ajax({
					url:'topExcellenceDelete',
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