package weixin.mshop.evaluate.controller;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExcelTitle;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import weixin.mshop.evaluate.entity.MshopEvaluateEntity;
import weixin.mshop.evaluate.service.MshopEvaluateServiceI;



/**   
 * @Title: Controller
 * @Description: 多店版评价表
 * @author onlineGenerator
 * @date 2015-11-21 09:40:10
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/mshopEvaluateController")
public class MshopEvaluateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MshopEvaluateController.class);

	@Autowired
	private MshopEvaluateServiceI mshopEvaluateService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 多店版评价表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "mshopEvaluate")
	public ModelAndView mshopEvaluate(HttpServletRequest request) {
		return new ModelAndView("weixin/mshop/evaluate/mshopEvaluateList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(MshopEvaluateEntity mshopEvaluate,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MshopEvaluateEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mshopEvaluate, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.mshopEvaluateService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除多店版评价表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(MshopEvaluateEntity mshopEvaluate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		mshopEvaluate = systemService.getEntity(MshopEvaluateEntity.class, mshopEvaluate.getId());
		message = "多店版评价表删除成功";
		try{
			mshopEvaluateService.delete(mshopEvaluate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "多店版评价表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除多店版评价表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "多店版评价表删除成功";
		try{
			for(String id:ids.split(",")){
				MshopEvaluateEntity mshopEvaluate = systemService.getEntity(MshopEvaluateEntity.class, 
				id
				);
				mshopEvaluateService.delete(mshopEvaluate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "多店版评价表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加多店版评价表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(MshopEvaluateEntity mshopEvaluate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "多店版评价表添加成功";
		try{
			mshopEvaluateService.save(mshopEvaluate);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "多店版评价表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新多店版评价表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(MshopEvaluateEntity mshopEvaluate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "多店版评价表更新成功";
		MshopEvaluateEntity t = mshopEvaluateService.get(MshopEvaluateEntity.class, mshopEvaluate.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(mshopEvaluate, t);
			mshopEvaluateService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "多店版评价表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 多店版评价表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(MshopEvaluateEntity mshopEvaluate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mshopEvaluate.getId())) {
			mshopEvaluate = mshopEvaluateService.getEntity(MshopEvaluateEntity.class, mshopEvaluate.getId());
			req.setAttribute("mshopEvaluatePage", mshopEvaluate);
		}
		return new ModelAndView("weixin/mshop/evaluate/mshopEvaluate-add");
	}
	/**
	 * 多店版评价表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(MshopEvaluateEntity mshopEvaluate, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(mshopEvaluate.getId())) {
			mshopEvaluate = mshopEvaluateService.getEntity(MshopEvaluateEntity.class, mshopEvaluate.getId());
			req.setAttribute("mshopEvaluatePage", mshopEvaluate);
		}
		return new ModelAndView("weixin/mshop/evaluate/mshopEvaluate-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("weixin/mshop/evaluate/mshopEvaluateUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(MshopEvaluateEntity mshopEvaluate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "多店版评价表";
			// 根据浏览器进行转码，使其支持中文文件名
			if (BrowserUtils.isIE(request)) {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(codedFileName,
										"UTF-8") + ".xls");
			} else {
				String newtitle = new String(codedFileName.getBytes("UTF-8"),
						"ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			// 产生工作簿对象
			HSSFWorkbook workbook = null;
			CriteriaQuery cq = new CriteriaQuery(MshopEvaluateEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, mshopEvaluate, request.getParameterMap());
			
			List<MshopEvaluateEntity> mshopEvaluates = this.mshopEvaluateService.getListByCriteriaQuery(cq,false);
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("多店版评价表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), MshopEvaluateEntity.class, mshopEvaluates);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {

			}
		}
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public void exportXlsByT(MshopEvaluateEntity mshopEvaluate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "多店版评价表";
			// 根据浏览器进行转码，使其支持中文文件名
			if (BrowserUtils.isIE(request)) {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(codedFileName,
										"UTF-8") + ".xls");
			} else {
				String newtitle = new String(codedFileName.getBytes("UTF-8"),
						"ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			// 产生工作簿对象
			HSSFWorkbook workbook = null;
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("多店版评价表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), MshopEvaluateEntity.class, null);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {

			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setSecondTitleRows(1);
			params.setNeedSave(true);
			try {
				List<MshopEvaluateEntity> listMshopEvaluateEntitys = 
					(List<MshopEvaluateEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),MshopEvaluateEntity.class,params);
				for (MshopEvaluateEntity mshopEvaluate : listMshopEvaluateEntitys) {
					mshopEvaluateService.save(mshopEvaluate);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
