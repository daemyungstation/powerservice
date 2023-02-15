/*
 * (@)# MyMenuService.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/09
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */
package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

/**
 * MyMenu 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/09
 * @프로그램ID MyMenu
 */

public interface MyMenuService {

    public int getMyMenuCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getMyMenuList(Map<String, ?> pmParam) throws Exception;

    public String insertMyMenu(Map<String, Object> pmParam) throws Exception;

    public String updateMyMenu(Map<String, Object> pmParam) throws Exception;

    public int deleteMyMenu(Map<String, ?> pmParam) throws Exception;
}
