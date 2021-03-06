<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>评价</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="mshopEvaluateController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${mshopEvaluatePage.id }">
					<input id="createName" name="createName" type="hidden" value="${mshopEvaluatePage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${mshopEvaluatePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${mshopEvaluatePage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${mshopEvaluatePage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								评价星级:
							</label>
						</td>
						<td class="value">
						     	 <input id="level" name="level" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${mshopEvaluatePage.level}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评价星级</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容:
							</label>
						</td>
						<td class="value">
						     	 <input id="content" name="content" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${mshopEvaluatePage.content}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">内容</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								客户ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="idCustomer" name="idCustomer" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${mshopEvaluatePage.idCustomer}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商店ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="idStore" name="idStore" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${mshopEvaluatePage.idStore}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商店ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="idGoods" name="idGoods" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${mshopEvaluatePage.idGoods}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品ID</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/weixin/mshop/evaluate/mshopEvaluate.js"></script>		