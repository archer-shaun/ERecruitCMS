/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
* <br>
* <br>
* This SOURCE CODE FILE, which has been provided by Quix as part
* of a Quix Creations product for use ONLY by licensed users of the product,
* includes CONFIDENTIAL and PROPRIETARY information of Quix Creations.
* <br>
* USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
* OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
* THE PRODUCT.<br>
* <br>
* </p>
* -----------------------------------------------------------------------------
* <br>
* <br>
* Modification History:
* Date              Developer          Change Description
* 07-May-2015       Jay          
* 
****************************************** *********************************** */
package com.tohours.imo.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;

import org.apache.commons.io.FileUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.alibaba.druid.util.StringUtils;
import com.tohours.imo.bean.Files;
import com.tohours.imo.bean.Peoples;
import com.tohours.imo.bean.Resource;
import com.tohours.imo.bean.Setting;
import com.tohours.imo.bean.SubExcellence;
import com.tohours.imo.bean.Talent;
import com.tohours.imo.bean.TopExcellence;
import com.tohours.imo.bean.User;
import com.tohours.imo.exception.BusinessException;
import com.tohours.imo.util.Constants;
import com.tohours.imo.util.TohoursUtils;
import com.tohours.imo.util.ZipUtils;

@IocBean
// 声明为Ioc容器中的一个Bean
@At("/attract")
// 整个模块的路径前缀
@Ok("json:{locked:'password|salt',ignoreNull:true}")
// 忽略password和salt属性,忽略空属性的json输出
@Fail("http:500")
// 抛出异常的话,就走500页面
 @Filters(@By(type=CheckSession.class, args={Constants.SESSION_USER, "/"})) //
// 检查当前Session是否带me这个属性
public class AttractModule extends BaseModule {
	@At
	public int count() { // 统计用户数的方法,算是个测试点
		return dao.count(User.class);
	}

	@At
	@Filters
	// 覆盖UserModule类的@Filter设置,因为登陆可不能要求是个已经登陆的Session
	public Object login(@Param("name") String name,
			@Param("password") String password, HttpSession session) {
		User user = dao.fetch(User.class,
				Cnd.where("name", "=", name).and("password", "=", password));
		if (user == null) {
			return false;
		} else {
			session.setAttribute(Constants.SESSION_USER, user.getId());
			return true;
		}
	}

	@At
	@Ok(">>:/attract/index.jsp")
	// 跟其他方法不同,这个方法完成后就跳转首页了
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@At
	public Object add(@Param("..") User user) { // 两个点号是按对象属性一一设置
		NutMap re = new NutMap();
		String msg = checkUser(user, true);
		if (msg != null) {
			return re.setv("ok", false).setv("msg", msg);
		}
		user = dao.insert(user);
		return re.setv("ok", true).setv("data", user);
	}

	@At
	public Object update(@Param("..") User user) {
		NutMap re = new NutMap();
		String msg = checkUser(user, false);
		if (msg != null) {
			return re.setv("ok", false).setv("msg", msg);
		}
		user.setName(null);// 不允许更新用户名
		user.setCreateTime(null);// 也不允许更新创建时间
		user.setUpdateTime(new Date());// 设置正确的更新时间
		dao.updateIgnoreNull(user);// 真正更新的其实只有password和salt
		return re.setv("ok", true);
	}

	@At
	public Object delete(@Param("id") int id, @Attr("me") int me) {
		if (me == id) {
			return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
		}
		dao.delete(User.class, id); // 再严谨一些的话,需要判断是否为>0
		return new NutMap().setv("ok", true);
	}

	@At
	public Object query(@Param("name") String name, @Param("..") Pager pager) {
		Cnd cnd = Strings.isBlank(name) ? null : Cnd.where("name", "like", "%"
				+ name + "%");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(User.class, cnd, pager));
		pager.setRecordCount(dao.count(User.class, cnd));
		qr.setPager(pager);
		return qr; // 默认分页是第1页,每页20条
	}

	@At("/")
	@Ok("void")
	public void index() throws IOException {
		HttpServletResponse response = Mvcs.getResp();
		response.sendRedirect("resourceList?type=1");
	}

	@At
	public Object resourceSave(@Param("..") Resource resource)
			throws IOException {
		NutMap re = new NutMap();
		try {
			checkResource(resource);

			String filePaths = resource.getFilePaths();
			String fileNames = resource.getFileNames();
			String contentTypes = resource.getContentTypes();
			String[] arrPath = filePaths.split(",");
			String[] arrName = fileNames.split(",");
			String[] arrTypes = contentTypes.split(",");
			StringBuffer fileTypes = new StringBuffer();

			Date now = new Date();
			if (resource.getId() > 0) {
				String fileIds = resource.getFileIds();
				String[] arrFileIds = fileIds.split(",");
				if (arrFileIds.length != arrPath.length) {
					String[] tmp = arrFileIds;
					arrFileIds = new String[arrPath.length];
					System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
				}
				Resource dbr = dao.fetch(Resource.class, resource.getId());
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					String fileId = arrFileIds[i];
					if (Strings.isNotBlank(fileId)) {
						fileId = fileId.trim();
					}
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}

					if (Strings.isEmpty(fileId)) {
						int id = this.addFile(path, name, contentType);
						arrFileIds[i] = id + "";
					}
					if (i > 0) {
						fileTypes.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
				}
				dbr.setTitle(resource.getTitle());
				dbr.setContent(resource.getContent());
				dbr.setFileCounts(Long.valueOf(arrPath.length));
				dbr.setFileNames(fileNames);
				dbr.setFilePaths(filePaths);
				dbr.setContentTypes(contentTypes);
				dbr.setFileIds(Strings.join(",", arrFileIds));
				dbr.setFileTypes(fileTypes.toString());
				dbr.setUpdateTime(now);
				dao.update(dbr);

			} else {
				StringBuffer fileIds = new StringBuffer();
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}
					if (i > 0) {
						fileTypes.append(",");
						fileIds.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
					int fileId = addFile(path, name, contentType);
					fileIds.append(fileId);
				}
				resource.setCreateTime(now);
				resource.setUpdateTime(now);
				resource.setFileCounts((long) arrPath.length);
				resource.setFileTypes(fileTypes.toString());
				resource.setFileIds(fileIds.toString());
				resource.setDeleteFlag(false);
				resource = dao.insert(resource);
			}
			return re.setv("ok", true);
		} catch (BusinessException e) {
			return re.setv("ok", false).setv("msg", e.getMessage());
		}
	}

	private int addFile(String path, String name, String contentType)
			throws IOException {
		Date now = new Date();
		Files files = new Files();
		files.setName(name);
		files.setPath(path);
		files.setContentType(contentType);
		files.setCreateTime(now);
		files.setUpdateTime(now);
		File file = new File(this.getRealPath(path));
		files.setData(FileUtils.readFileToByteArray(file));
		files = dao.insert(files);
		return files.getId();
	}


	private void checkResource(Resource resource) {
		if (resource == null) {
			throw new BusinessException("对象不能为空！");
		} else {
			if (resource.equals("1")) {
				if (StringUtils.isEmpty(resource.getTitle())) {
					throw new BusinessException("标题不能为空！");
				}
				if (StringUtils.isEmpty(resource.getContent())) {
					throw new BusinessException("内容不能为空！");
				}
				if (StringUtils.isEmpty(resource.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
				if (resource.getType() == null) {
					throw new BusinessException("资源的类型不能为空！");
				}
				if (Constants.isResourceExists(resource.getType()) == false) {
					throw new BusinessException("所传递的资源类型不正确，type="
							+ resource.getType());
				}
			} else if (resource.equals("3")) {
				if (StringUtils.isEmpty(resource.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
			} else if (resource.equals("5")) {
				if (StringUtils.isEmpty(resource.getTitle())) {
					throw new BusinessException("标题不能为空！");
				}
				if (StringUtils.isEmpty(resource.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
			}
		}
	}

	@At
	@Ok("jsp:jsp.attract.resource")
	public Object resource(@Param("type") String type, @Param("id") Integer id) {
		checkType(type);
		Long longType = Long.valueOf(type);
		NutMap re = new NutMap();
		Resource resource = null;
		if (id != null) {
			resource = dao.fetch(Resource.class, id.intValue());
			if (Constants.isTest) {
				System.out.println("id:" + id);
				System.out.println("resource:" + resource);
			}
		} else {
			resource = loadResourceByType(Long.valueOf(type));
		}
		String typeName = Constants.RESOURCE_TYPE.get(longType);
		return re.setv("resource", resource).setv("typeName", typeName);
	}

	private Resource loadResourceByType(Long type) {
		Cnd cnd = Cnd.where("type", "=", type).and("deleteFlag", "=", false);
		int count = dao.count(Resource.class, cnd);
		if (count == 1) {
			List<Resource> list = dao.query(Resource.class, cnd);
			return list.get(0);
		}
		return null;
	}

	private int countResourceByType(Long type) {
		Cnd cnd = Cnd.where("type", "=", type).and("deleteFlag", "=", false);
		return dao.count(Resource.class, cnd);
	}

	@At
	@Ok("jsp:jsp.attract.resource_edit_pre")
	public Object resourceEditPre(@Param("id") int id) {
		Resource resource = dao.fetch(Resource.class, id);
		if (Constants.isTest) {
			System.out.println("id:" + id);
			System.out.println("resource:" + resource);
		}
		return resource;
	}

	private void checkType(String type) {
		if (StringUtils.isEmpty(type)) {
			throw new BusinessException("类型不能为空！");
		}
		try {
			long longType = Long.parseLong(type);
			if (Constants.isResourceExists(longType) == false) {
				throw new BusinessException("资源类型不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("资源类型不正确");
		}
	}

	protected String checkUser(User user, boolean create) {
		if (user == null) {
			return "空对象";
		}
		if (create) {
			if (Strings.isBlank(user.getName())
					|| Strings.isBlank(user.getPassword()))
				return "用户名/密码不能为空";
		} else {
			if (Strings.isBlank(user.getPassword()))
				return "密码不能为空";
		}
		String passwd = user.getPassword().trim();
		if (6 > passwd.length() || passwd.length() > 12) {
			return "密码长度错误";
		}
		user.setPassword(passwd);
		if (create) {
			int count = dao.count(User.class,
					Cnd.where("name", "=", user.getName()));
			if (count != 0) {
				return "用户名已经存在";
			}
		} else {
			if (user.getId() < 1) {
				return "用户Id非法";
			}
		}
		if (user.getName() != null)
			user.setName(user.getName().trim());
		return null;
	}

	@Filters
	@At
	@Fail("jsp:jsp.500")
	public void error() {
		throw new RuntimeException();
	}

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	@Ok("json")
	public Object upload(@Param("file") TempFile tf) {
		NutMap re = new NutMap();
		FieldMeta meta = tf.getMeta();
		String path = copyToServer(tf);
		return re.setv("ok", true).setv("path", path)
				.setv("name", meta.getFileLocalName())
				.setv("contentType", meta.getContentType());
	}

	/**
	 * 将文件拷贝到attract/uplads目录下，并返回路径
	 * 
	 * @param tf
	 * @return
	 */
	private String copyToServer(TempFile tf) {
		FieldMeta fm = tf.getMeta();
		File file = tf.getFile();
		String realPath = getRealPath(Constants.FILE_PATH);
		Calendar calendar = Calendar.getInstance();
		String datePath = calendar.get(Calendar.YEAR) + "/";
		datePath += this.formatNum(calendar.get(Calendar.MONTH) + 1)
				+ this.formatNum(calendar.get(Calendar.DATE));
		String newFileName = TohoursUtils.randomKey(10) + fm.getFileExtension();
		String separator = "/";
		File dirFile = new File(realPath + separator + datePath + separator
				+ newFileName);
		File dirFolder = new File(realPath + separator + datePath + separator);
		if (dirFolder.exists() == false) {
			dirFolder.mkdirs();
		}
		try {
			FileUtils.copyFile(file, dirFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("文件拷贝出现错误！");
		}
		return Constants.FILE_PATH + separator + datePath + separator
				+ newFileName;
	}

	private String formatNum(int n) {

		return n < 10 ? "0" + n : n + "";
	}

	private String getRealPath(String path) {
		ServletContext sc = Mvcs.getServletContext();
		return sc.getRealPath(path);
	}

	@At
	@Ok("void")
	public void resourceList(@Param("type") String type) throws Exception {
		checkType(type);
		NutMap re = new NutMap();
		re.setv("type", type);
		if (countResourceByType(Long.valueOf(type)) == 1) {
			Mvcs.getResp().sendRedirect("resource?type=" + type);
			if (Constants.isTest) {
				System.out.println("debug:countResource is 1");
			}
		} else {
			Mvcs.getReq()
					.getRequestDispatcher(
							"/WEB-INF/jsp/attract/resource_list.jsp")
					.forward(Mvcs.getReq(), Mvcs.getResp());
		}
	}

	@At
	public Object resourceListData(@Param("type") String type,
			@Param("key") String key, @Param("..") Pager pager) {
		NutMap re = new NutMap();
		checkType(type);
		Long longType = Long.valueOf(type);
		Cnd cnd = Cnd.where("type", "=", longType);
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("title", "like", TohoursUtils.addPercent(key));
		}
		cnd.and("deleteFlag", "=", false).orderBy("createTime", "desc");
		if (Constants.isTest) {
			System.out.println("debug:type=" + type);
			System.out.println("debug:cnd=" + cnd);
		}
		QueryResult qr = new QueryResult();
		List<Resource> list = dao.query(Resource.class, cnd, pager);
		qr.setList(list);
		pager.setRecordCount(dao.count(Resource.class, cnd));
		qr.setPager(pager);
		return re.setv("qr", qr).setv("title",
				Constants.RESOURCE_TYPE.get(longType));
	}

	@At
	public Object resourceDelete(@Param("id") int id) {
		NutMap re = new NutMap();
		try {
			Resource resource = dao.fetch(Resource.class, id);
			resource.setDeleteFlag(true);
			dao.update(resource);
			re.setv("success", true);
		} catch (Exception e) {
			re.setv("success", false);
			re.setv("msg", e.getMessage());
		}
		return re;
	}

	@At
	@Ok("void")
	public void resourceFile(@Param("id") int id) throws IOException {
		Files files = dao.fetch(Files.class, id);
		HttpServletResponse response = Mvcs.getResp();
		response.setContentType(files.getContentType());
		response.getOutputStream().write(files.getData());
	}

	@At
	public Object test() {
		NutMap re = new NutMap();
		// System.out.println(this.getRealPath("/"));
		// Files files = new Files();
		// files.setCreateTime(new Date());
		// files.setUpdateTime(new Date());
		// Response response = Http.get("http://www.sina.com/");
		// InputStream is = response.getStream();
		// try {
		// files.setData(IOUtils.toByteArray(is));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// files.setPath("haha");
		// files.setName("test");
		//
		// Resource r = new Resource();
		// r.setContent("content");
		// r.setTitle("title");
		// List<Files> list = new ArrayList<Files>();
		// list.add(files);
		// r.setFileses(list);
		// dao.insertWith(r, "fileses");
		return re.setv("ok", true);
	}

	@At
	@Ok("json")
	public Object zipResource() {
		NutMap map = new NutMap();
		clearUpload();
		map.put("index_background", loadIndexBackground());
		map.put("guide_page", loadGuidePage());
		map.put("ten_objective_elements", loadObjectiveElements());
		map.put("excellence", loadExcellence());
		map.put("ten_aia_elements", loadAiaElements());
		String zipName = "resource.zip";
		zip(Json.toJson(map), zipName);
		return map;
	}

	private void zip(String json, String zipName) {
		try {
			String realPath = this.getRealPath(Constants.FILE_PATH);
			File data = new File(realPath + "/data.json");
			FileUtils.writeStringToFile(data, json, "UTF-8");
			ZipUtils.zip(new File(realPath), zipName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearUpload() {
//		try {
//			String realPath = getRealPath(Constants.FILE_PATH);
//			File file = new File(realPath);
//			if(file.exists() && file.isDirectory()){
//				FileUtils.deleteDirectory(file);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// 友邦十大要素
	private Object loadAiaElements() {
		List<Map<String, String>> rv = new ArrayList<Map<String, String>>();

		Cnd cnd = Cnd.where("type", "=", Constants.RESOURCE_TYPE_AIA_ELEMENTS);
		cnd.and("deleteFlag", "=", false).orderBy("createTime", "desc");
		List<Resource> list = dao.query(Resource.class, cnd);

		for (Resource resource : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", resource.getTitle());
			map.put("content", resource.getFilePaths());
			rv.add(map);
		}
		return rv;
	}

	private Object loadObjectiveElements() {
		Resource resource = this
				.loadResourceByType(Constants.RESOURCE_TYPE_OBJECTIVE_ELEMENTS);
		String ids = resource.getFileIds();
		String[] arrId = ids.split(",");
		String[] arrPath = resource.getFilePaths().split(",");
		List<String> rv = new ArrayList<String>();
		for (int i = 0; i < arrId.length; i++) {
			rv.add(arrPath[i]);
			generateFile(Integer.parseInt(arrId[i]));
		}
		return rv;
	}

	private Object loadGuidePage() {
		Resource resource = this
				.loadResourceByType(Constants.RESOURCE_TYPE_GUIDE);
		String ids = resource.getFileIds();
		String[] arrId = ids.split(",");
		String[] arrPath = resource.getFilePaths().split(",");
		List<String> rv = new ArrayList<String>();
		for (int i = 0; i < arrId.length; i++) {
			rv.add(arrPath[i]);
			generateFile(Integer.parseInt(arrId[i]));
		}
		return rv;
	}

	private String loadIndexBackground() {
		Resource resource = this
				.loadResourceByType(Constants.RESOURCE_TYPE_INDEX);
		generateFile(Integer.parseInt(resource.getFileIds()));// 只有一个
		return resource.getFilePaths();
	}

	private void generateFile(int fileId) {
		if (Constants.isTest) {
			System.out.println(fileId);
		}
		Files files = dao.fetch(Files.class, fileId);
		String path = files.getPath();
		String realPath = this.getRealPath(path);
		String folderPath = realPath.replaceAll("[^/]*$", "");
		File folder = new File(folderPath);
		if (folder.exists() == false) {
			folder.mkdirs();
		}
		File file = new File(realPath);
		if (file.exists() == false) {
			try {
				FileUtils.writeByteArrayToFile(file, files.getData());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Object loadExcellence() {

		List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

		Cnd cnd = Cnd.where("deleteFlag", "=", false);
		cnd.orderBy("createTime", "desc");
		List<TopExcellence> list = dao.query(TopExcellence.class, cnd);
		for (TopExcellence top : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", top.getName());
			map.put("index", loadIndex(top));
			map.put("sub_class", loadSub(top.getId()));
			rv.add(map);
		}
		return rv;
	}

	private Object loadSub(int topId) {

		List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

		Cnd cnd = Cnd.where("deleteFlag", "=", false).and("top_excel_id", "=",
				topId);
		cnd.orderBy("createTime", "desc");
		List<SubExcellence> list = dao.query(SubExcellence.class, cnd);
		for (SubExcellence sub : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", sub.getName());
			map.put("icon", sub.getFilePaths());
			map.put("peoples", loadPeoples(sub.getId()));
			generateFile(Integer.parseInt(sub.getFileIds()));
			rv.add(map);
		}
		return rv;
	}

	private Object loadPeoples(int subId) {

		List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

		Cnd cnd = Cnd.where("deleteFlag", "=", false).and("sub_excel_id", "=",
				subId);
		cnd.orderBy("createTime", "desc");
		List<Peoples> list = dao.query(Peoples.class, cnd);
		for (Peoples p : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", p.getName());
			map.put("join_date", p.getJoinDate());
			map.put("old_job", p.getOldJob());
			map.put("share_word", p.getShareWord());
			map.put("old_mark", Json.fromJson(p.getOldMark()));
			map.put("newMark", Json.fromJson(p.getNewMark()));
			map.put("picture", p.getFilePath());
			try {
				generateFile(Integer.parseInt(p.getFileId()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			rv.add(map);
		}
		return rv;
	}

	private Object loadIndex(TopExcellence top) {
		Map<String, Object> rv = new HashMap<String, Object>();
		String fileIds = top.getFileIds();
		String filePaths = top.getFilePaths();
		String[] arrIds = fileIds.split(",");
		String[] arrPaths = filePaths.split(",");
		List<String> pictures = new ArrayList<String>();
		for (int i = 0; i < arrIds.length; i++) {
			String id = arrIds[i];
			if (rv.get("video") == null) {
				rv.put("video", arrPaths[i]);
			} else {
				pictures.add(arrPaths[i]);
			}
			generateFile(Integer.parseInt(id));
		}
		return rv;
	}

	// 新增的字段

	/**
	 * 新增或者操作
	 * 
	 * @param talent
	 * @return
	 */
	@At("/save")
	@Ok("json")
	@POST
	public Object saveTalent(@Param("..") Talent talent) {
		NutMap re = new NutMap();
		re.setv("success", true).setv("msg", "操作成功");
		Talent tl = null;
		if (!StringUtils.isEmpty(talent.getId() + "")) {
			tl = dao.fetch(Talent.class, talent.getId());
		}
		try {
			if (tl == null) {
				talent.setCreateTime(new Date());
				tl = talent;
				if (!StringUtils.isEmpty(talent.getUserId())) {
					dao.insert(tl);
				} else {
					re.setv("success", false).setv("msg", "user_id为空,操作失败");
				}

			} else {
				tl = talent;
				talent.setUpdateTime(new Date());
				dao.updateIgnoreNull(tl);
			}
		} catch (Exception e) {
			re.setv("success", false).setv("msg", e.getMessage());
		}
		return re;
	}

	/**
	 * 人才资源列表显示
	 * 
	 * @param type
	 *            类型：人才资源
	 * @param key
	 *            搜索：关键字
	 * @param pager
	 *            分页
	 * @return 返回list与pager
	 */
	@At
	public Object talentList(@Param("key") String key, @Param("..") Pager pager) {
		NutMap um = new NutMap();
		Cnd cnd = Cnd.where("1", "=", "1");
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("name", "like", TohoursUtils.addPercent(key));
		}
		cnd.orderBy("createTime", "desc");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(Talent.class, cnd, pager));
		pager.setRecordCount(dao.count(Talent.class, cnd));
		qr.setPager(pager);
		return um.setv("list", qr.getList()).setv("pager", qr.getPager()); // 默认分页是第1页,每页20条
	}

	/**
	 * 通用的分页显示
	 * 
	 * @param dao
	 * @param pageNumber
	 * @param pageSize
	 * @param cas
	 *            实体的class
	 * @return 返回QueryResult对象
	 */
	public QueryResult getListPage(Dao dao, int pageNumber, int pageSize,
			Class<Object> cas) {
		Pager pager = dao.createPager(pageNumber, pageSize);// 创建pager对象
		Criteria cri = Cnd.cri();// 创建查询语句
		cri.getOrderBy().desc("updateTime");// 设置查询条件
		List<Object> list = dao.query(cas, cri, pager);// 得到list列表
		pager.setRecordCount(dao.count(cas));// 设置总的记录数
		return new QueryResult(list, pager);// 返回QueryResult对象
	}

	/**
	 * 得到所有的list集合
	 * 
	 * @param cas
	 * @return
	 */
	public <T> List<T> getList(Class<T> cas) {
		List<T> list = new ArrayList<T>();
		list = dao.query(cas, Cnd.wrap("1=1"));
		return list;
	}

	/**
	 * 个性化
	 * 
	 * @param individuality
	 * @return
	 */
	@At("/saveIdy")
	@Ok("json")
	@POST
	public Object saveIndividuality(@Param("..") Setting setting) {
		NutMap nt = new NutMap();
		nt.addv("success", true).addv("msg", "操作成功");
		Setting idy = null;
		if (!StringUtils.isEmpty(setting.getAgentId() + "")) {
			idy = dao.fetch(Setting.class, setting.getAgentId());
		}
		try {
			idy = dao.fetch(Setting.class, setting.getAgentId());
			if (idy == null) {
				idy = setting;
				idy.setCreateTime(new Date());
				dao.insert(idy);
			} else {
				idy = setting;
				idy.setUpdateTime(new Date());
				dao.updateIgnoreNull(idy);
			}
		} catch (Exception e) {
			nt.addv("success", false).addv("msg", e.getMessage());
		}
		return nt;
	}

	/**
	 * 列表显示
	 * 
	 * @param pager
	 * @return
	 */
	@At
	public Object settingList(@Param("key") String key, @Param("..") Pager pager) {
		NutMap nm = new NutMap();
		Cnd cnd = Cnd.where("1", "=", "1");
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("agentId", "like", TohoursUtils.addPercent(key));
		}
		cnd.orderBy("createTime", "desc");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(Setting.class, cnd, pager));
		pager.setRecordCount(dao.count(Setting.class, cnd));
		qr.setPager(pager);
		return nm.setv("list", qr.getList()).setv("pager", qr.getPager()); // 默认分页是第1页,每页20条
	}

	// 人力资源首页
	@At
	@Ok("jsp:jsp.attract.talent_list")
	public void talentIndex() {
	}

	// 个性化设置首页
	@At
	@Ok("jsp:jsp.attract.setting_list")
	public void settingIndex() {

	}

	// peoples首页
	@At
	@Ok("jsp:jsp.attract.peoples_list")
	public Object peoplesIndex(@Param("sub_excel_id") String sub_excel_id) {
		NutMap nm = new NutMap();
		Mvcs.getReq().setAttribute(
				"top_id",
				dao.fetch(SubExcellence.class, Integer.parseInt(sub_excel_id))
						.getTop_excel_id() + "");
		return nm.setv("sub_excel_id", sub_excel_id).setv("addFlag", "peoples");
	}

	// peoples的列表显示供ajax调用
	@At
	public Object peoplesList(@Param("key") String key,
			@Param("..") Pager pager, @Param("sub_excel_id") String sub_excel_id) {
		NutMap re = new NutMap();
		Cnd cnd = Cnd.where("1", "=", "1");
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("name", "like", TohoursUtils.addPercent(key));
		}
		if (StringUtils.isEmpty(sub_excel_id)) {
			throw new BusinessException("sub_excel_id不能为空");
		} else {
			cnd.and("sub_excel_id", "=", Integer.parseInt(sub_excel_id));
		}
		cnd.and("deleteFlag", "=", false).orderBy("createTime", "desc");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(Peoples.class, cnd, pager));
		pager.setRecordCount(dao.count(Peoples.class, cnd));
		qr.setPager(pager);
		Mvcs.getReq().setAttribute(
				"top_id",
				dao.fetch(SubExcellence.class, Integer.parseInt(sub_excel_id))
						.getTop_excel_id() + "");
		return re.setv("list", qr.getList()).setv("pager", qr.getPager());
	}

	// peoples的新增与修改
	@At
	@Ok("jsp:jsp.attract.peoples")
	public Object peoplesPre(@Param("id") String id,
			@Param("sub_excel_id") String sub_excel_id) {
		NutMap re = new NutMap();
		Peoples peoples = null;
		if (id != null) {
			peoples = dao.fetch(Peoples.class, Integer.parseInt(id));
		}
		return re.setv("peoples", peoples).setv("addFlag", "peoples")
				.setv("sub_excel_id", sub_excel_id);
	}

	// peoples的保存与更新
	@At
	public Object peoplesSave(@Param("..") Peoples peoples,
			@Param("markName") String markName) throws IOException {
		NutMap re = new NutMap();
		Date now = new Date();
		try {
			checkPeoples(peoples);
			String filePath = peoples.getFilePath();
			String fileName = peoples.getFileName();
			String contentType = peoples.getContentType();
			String fileType = TohoursUtils.getFileExt(fileName);
			if (peoples.getId() > 0) {
				String fileId = peoples.getFileId();
				Peoples pl = dao.fetch(Peoples.class, peoples.getId());
				if (Strings.isEmpty(fileId)) {
					this.addFile(filePath, fileName, contentType);
				}
				pl.setContentType(contentType);
				pl.setFileId(fileId);
				pl.setFileName(fileName);
				pl.setFilePath(filePath);
				pl.setFileType(fileType);
				pl.setJoinDate(peoples.getJoinDate());
				pl.setName(peoples.getName());
				// pl.setNewMark(peoples.getNewMark());
				// pl.setOldMark(peoples.getOldMark());
				dealMark(pl, markName, peoples.getOldMark(),
						peoples.getNewMark());
				pl.setOldJob(peoples.getOldJob());
				pl.setShareWord(peoples.getShareWord());
				dao.update(pl);
			} else {
				int fileId = addFile(filePath, fileName, contentType);
				peoples.setDeleteFlag(false);
				peoples.setCreateTime(now);
				peoples.setUpdateTime(now);
				peoples.setFileType(fileType);
				peoples.setFileId(fileId + "");
				dealMark(peoples, markName, peoples.getOldMark(),
						peoples.getNewMark());
				peoples = dao.insert(peoples);
			}
			return re.setv("success", true).setv("id", peoples.getId());
		} catch (BusinessException e) {
			e.printStackTrace();
			return re.setv("success", false).setv("msg", e.getMessage());
		}
	}

	private void dealMark(Peoples pl, String markName, String oldMark,
			String newMark) {
		if (Constants.isTest) {
			System.out.println("debug::" + markName);
		}
		String[] arrMarkNames = markName.split(",");
		String[] arrOldMarks = oldMark.split(",");
		String[] arrNewMarks = newMark.split(",");

		List<Map<String, String>> listNewMark = new ArrayList<Map<String, String>>();
		List<Map<String, String>> listOldMark = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 5; i++) {
			String mn = arrMarkNames[i];
			mn = mn.trim();
			String om = arrOldMarks[i];
			om = om.trim();
			String nm = arrNewMarks[i];
			nm = nm.trim();
			Map<String, String> mapNewMark = new HashMap<String, String>();
			mapNewMark.put("name", mn);
			mapNewMark.put("mark", nm);
			Map<String, String> mapOldMark = new HashMap<String, String>();
			mapOldMark.put("name", mn);
			mapOldMark.put("mark", om);

			listNewMark.add(mapNewMark);
			listOldMark.add(mapOldMark);
		}

		pl.setOldMark(Json.toJson(listOldMark, JsonFormat.compact()));
		pl.setNewMark(Json.toJson(listNewMark, JsonFormat.compact()));
	}

	// peoples保存时的校验
	private void checkPeoples(Peoples peoples) {
		if (peoples == null) {
			throw new BusinessException("对象不能为空！");
		} else {
			if (StringUtils.isEmpty(peoples.getName())) {
				throw new BusinessException("name不能为空");
			}
			if (StringUtils.isEmpty(peoples.getJoinDate() + "")) {
				throw new BusinessException("join_date不能为空！");
			}
			if (StringUtils.isEmpty(peoples.getShareWord())) {
				throw new BusinessException("share_word不能为空！");
			}
			if (StringUtils.isEmpty(peoples.getFilePath())) {
				throw new BusinessException("上传的文件不能为空！");
			}
		}
	}

	@At
	public Object peoplesDelete(@Param("id") String id) {

		NutMap re = new NutMap();
		try {
			Peoples peoples = dao.fetch(Peoples.class, Integer.parseInt(id));
			peoples.setDeleteFlag(true);
			dao.update(peoples);
			re.setv("success", true);
		} catch (Exception e) {
			re.setv("success", false);
			re.setv("msg", e.getMessage());
		}
		return re;
	}

	// topExcellence首页
	@At
	@Ok("jsp:jsp.attract.top_excellence_list")
	public Object topExcellenceIndex() throws IOException {
		NutMap nm = new NutMap();
		return nm.setv("addFlag", "topexcellence");
	}

	@At
	public Object topExcellenceList(@Param("key") String key,
			@Param("..") Pager pager) {
		NutMap nm = new NutMap();
		Cnd cnd = Cnd.where("1", "=", "1");
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("name", "like", TohoursUtils.addPercent(key));
		}
		cnd.and("deleteFlag", "=", false).orderBy("createTime", "desc");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(TopExcellence.class, cnd, pager));
		pager.setRecordCount(dao.count(TopExcellence.class, cnd));
		qr.setPager(pager);
		return nm.setv("qr", qr).setv("addFlag", "topexcellence");
	}

	@At
	public Object topExcellenceSave(@Param("..") TopExcellence topExcellence)
			throws IOException {
		NutMap re = new NutMap();
		try {
			checkTopExcellence(topExcellence);
			String filePaths = topExcellence.getFilePaths();
			String fileNames = topExcellence.getFileNames();
			String contentTypes = topExcellence.getContentTypes();
			String[] arrPath = filePaths.split(",");
			String[] arrName = fileNames.split(",");
			String[] arrTypes = contentTypes.split(",");
			StringBuffer fileTypes = new StringBuffer();
			Date now = new Date();
			if (topExcellence.getId() > 0) {
				String fileIds = topExcellence.getFileIds();
				String[] arrFileIds = fileIds.split(",");
				if (arrFileIds.length != arrPath.length) {
					String[] tmp = arrFileIds;
					arrFileIds = new String[arrPath.length];
					System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
				}
				TopExcellence dbr = dao.fetch(TopExcellence.class,
						topExcellence.getId());
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					String fileId = arrFileIds[i];
					if (Strings.isNotBlank(fileId)) {
						fileId = fileId.trim();
					}
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}

					if (Strings.isEmpty(fileId)) {
						int id = this.addFile(path, name, contentType);
						arrFileIds[i] = id + "";
					}
					if (i > 0) {
						fileTypes.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
				}
				dbr.setContentTypes(contentTypes);
				dbr.setUpdateTime(now);
				dbr.setCreateTime(now);
				dbr.setFileCounts(Long.valueOf(arrPath.length));
				dbr.setFileIds(Strings.join(",", arrFileIds));
				dbr.setFileNames(fileNames);
				dbr.setFilePaths(filePaths);
				dbr.setFileTypes(fileTypes.toString());
				dbr.setName(topExcellence.getName());
				dao.update(dbr);

			} else {
				StringBuffer fileIds = new StringBuffer();
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}
					if (i > 0) {
						fileTypes.append(",");
						fileIds.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
					int fileId = addFile(path, name, contentType);
					fileIds.append(fileId);
				}
				topExcellence.setDeleteFlag(false);
				topExcellence.setUpdateTime(now);
				topExcellence.setFileCounts((long) arrPath.length);
				topExcellence.setFileTypes(fileTypes.toString());
				topExcellence.setFileIds(fileIds.toString());
				topExcellence = dao.insert(topExcellence);
			}
			return re.setv("success", true).setv("id", topExcellence.getId());
		} catch (BusinessException e) {
			return re.setv("success", false).setv("msg", e.getMessage());
		}
	}

	public void checkTopExcellence(TopExcellence topExcellence) {
		if (topExcellence == null) {
			throw new BusinessException("对象不能为空！");
		} else {
			if (StringUtils.isEmpty(topExcellence.getName())) {
				throw new BusinessException("标题不能为空！");
			}
			if (StringUtils.isEmpty(topExcellence.getFilePaths())) {
				throw new BusinessException("上传的文件不能为空！");
			}
		}

	}

	@At
	@Ok("jsp:jsp.attract.top_excellence")
	public Object topExcellencePre(@Param("id") String id) {
		NutMap re = new NutMap();
		TopExcellence topExcellence = null;
		if (id != null) {
			topExcellence = dao
					.fetch(TopExcellence.class, Integer.parseInt(id));
		}
		return re.setv("topExcellence", topExcellence).setv("addFlag",
				"topexcellence");
	}

	@At
	public Object topExcellenceDelete(@Param("id") String id) {
		NutMap re = new NutMap();
		try {
			TopExcellence topExcellence = dao.fetch(TopExcellence.class,
					Integer.parseInt(id));
			topExcellence.setDeleteFlag(true);
			dao.update(topExcellence);
			re.setv("success", true);
		} catch (Exception e) {
			re.setv("success", false);
			re.setv("msg", e.getMessage());
		}
		return re;
	}

	// subExcellence首页
	@At
	@Ok("jsp:jsp.attract.sub_excellence_list")
	public Object subExcellenceIndex(@Param("top_excel_id") String top_excel_id) {
		NutMap nm = new NutMap();
		return nm.setv("top_excel_id", top_excel_id).setv("addFlag",
				"subexcellence");
	}

	@At
	public Object subExcellenceList(@Param("key") String key,
			@Param("..") Pager pager, @Param("top_excel_id") String top_excel_id) {
		NutMap nm = new NutMap();
		Cnd cnd = Cnd.where("1", "=", "1");
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("name", "like", TohoursUtils.addPercent(key));
		}
		if (StringUtils.isEmpty(top_excel_id)) {
			throw new BusinessException("top_excel_id不能为空");
		} else {
			cnd.and("top_excel_id", "=", Integer.parseInt(top_excel_id));
		}
		cnd.and("deleteFlag", "=", false).orderBy("createTime", "desc");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(SubExcellence.class, cnd, pager));
		pager.setRecordCount(dao.count(SubExcellence.class, cnd));
		qr.setPager(pager);
		return nm.setv("qr", qr);
	}

	@At
	public Object subExcellenceSave(@Param("..") SubExcellence subExcellence)
			throws IOException {
		NutMap re = new NutMap();
		try {
			checkSubExcellence(subExcellence);
			String filePaths = subExcellence.getFilePaths();
			String fileNames = subExcellence.getFileNames();
			String contentTypes = subExcellence.getContentTypes();
			String[] arrPath = filePaths.split(",");
			String[] arrName = fileNames.split(",");
			String[] arrTypes = contentTypes.split(",");
			StringBuffer fileTypes = new StringBuffer();
			Date now = new Date();
			if (subExcellence.getId() > 0) {
				String fileIds = subExcellence.getFileIds();
				String[] arrFileIds = fileIds.split(",");
				if (arrFileIds.length != arrPath.length) {
					String[] tmp = arrFileIds;
					arrFileIds = new String[arrPath.length];
					System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
				}
				SubExcellence dbr = dao.fetch(SubExcellence.class,
						subExcellence.getId());
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					String fileId = arrFileIds[i];
					if (Strings.isNotBlank(fileId)) {
						fileId = fileId.trim();
					}
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}

					if (Strings.isEmpty(fileId)) {
						int id = this.addFile(path, name, contentType);
						arrFileIds[i] = id + "";
					}
					if (i > 0) {
						fileTypes.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
				}
				dbr.setContentTypes(contentTypes);
				dbr.setUpdateTime(now);
				dbr.setFileCounts(Long.valueOf(arrPath.length));
				dbr.setFileIds(Strings.join(",", arrFileIds));
				dbr.setFileNames(fileNames);
				dbr.setFilePaths(filePaths);
				dbr.setFileTypes(fileTypes.toString());
				dbr.setName(subExcellence.getName());
				dao.update(dbr);

			} else {
				StringBuffer fileIds = new StringBuffer();
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}
					if (i > 0) {
						fileTypes.append(",");
						fileIds.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
					int fileId = addFile(path, name, contentType);
					fileIds.append(fileId);
				}
				subExcellence.setDeleteFlag(false);
				subExcellence.setCreateTime(now);
				subExcellence.setUpdateTime(now);
				subExcellence.setFileCounts((long) arrPath.length);
				subExcellence.setFileTypes(fileTypes.toString());
				subExcellence.setFileIds(fileIds.toString());
				subExcellence = dao.insert(subExcellence);
			}
			return re.setv("success", true).setv("id", subExcellence.getId());
		} catch (BusinessException e) {
			return re.setv("success", false).setv("msg", e.getMessage());
		}
	}

	public void checkSubExcellence(SubExcellence subExcellence) {
		if (subExcellence == null) {
			throw new BusinessException("对象不能为空！");
		} else {
			if (StringUtils.isEmpty(subExcellence.getName())) {
				throw new BusinessException("标题不能为空！");
			}
			if (StringUtils.isEmpty(subExcellence.getFilePaths())) {
				throw new BusinessException("上传的文件不能为空！");
			}
		}
	}

	@At
	@Ok("jsp:jsp.attract.sub_excellence")
	public Object subExcellencePre(@Param("id") String id,
			@Param("top_excel_id") String top_excel_id) {
		NutMap re = new NutMap();
		SubExcellence subExcellence = null;
		if (id != null) {
			subExcellence = dao
					.fetch(SubExcellence.class, Integer.parseInt(id));
		}
		return re.setv("subExcellence", subExcellence)
				.setv("addFlag", "subexcellence")
				.setv("top_excel_id", top_excel_id);
	}

	@At
	public Object subExcellenceDelete(@Param("id") String id) {
		NutMap re = new NutMap();
		try {
			SubExcellence subExcellence = dao.fetch(SubExcellence.class,
					Integer.parseInt(id));
			subExcellence.setDeleteFlag(true);
			dao.update(subExcellence);
			re.setv("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			re.setv("success", false);
			re.setv("msg", e.getMessage());
		}
		return re;
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("/Users/niejacob/360云盘/MacPro/tohours/quix/WebContent/attract/uploads");
		FileUtils.deleteDirectory(file);
	}
}
