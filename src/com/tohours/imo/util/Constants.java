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
package com.tohours.imo.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Constants {
	public static final String SESSION_USER = "currUserObj";
	
	public static final Long RESOURCE_TYPE_INDEX = 1L;//首页资源
	public static final Long RESOURCE_TYPE_TEST = 2L;//测试资源
	public static final Long RESOURCE_TYPE_GUIDE=3L;//引导页资源
	public static final Long RESOURCE_TYPE_OBJECTIVE_ELEMENTS=4L;//十大要素
	public static final Long RESOURCE_TYPE_AIA_ELEMENTS=5L;//友邦十大要素
	
	public static final String FILE_PATH = "/attract/uploads";
	
	public static Map<Long, String> RESOURCE_TYPE = new TreeMap<Long, String>();

	public static String APP_PATH;
	
	static{
		RESOURCE_TYPE.put(RESOURCE_TYPE_INDEX, "首页资源");
		RESOURCE_TYPE.put(RESOURCE_TYPE_TEST, "测试资源");
		RESOURCE_TYPE.put(RESOURCE_TYPE_OBJECTIVE_ELEMENTS, "十大要素");
		RESOURCE_TYPE.put(RESOURCE_TYPE_GUIDE, "引导页资源");
		RESOURCE_TYPE.put(RESOURCE_TYPE_AIA_ELEMENTS, "友邦十大要素");
	}
	
	public static boolean isResourceExists(Long resourceType){
		Set<Long> keys = RESOURCE_TYPE.keySet();
		return keys.contains(resourceType);
	}
	
	public static final Boolean isTest = true;
}

