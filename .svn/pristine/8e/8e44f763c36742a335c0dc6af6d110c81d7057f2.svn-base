/*
 * (@)# DlwCdService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/03
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCdService;
import powerservice.business.dlw.service.DlwDBConnectionService;
import powerservice.common.util.CommonUtils;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프 코드 관리를 한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCd
 */
@Service
public class DlwDBConnectionServiceImpl extends EgovAbstractServiceImpl implements DlwDBConnectionService {
	
	@Resource
    public DlwDBConnectionADAO dlwDBConnectionADAO;
	
	@Resource
    public DlwDBConnectionBDAO dlwDBConnectionBDAO;

    public int selectDBConnection() throws Exception {

        Map<String, Object> hmParamTypeA = new HashMap<String, Object>();
        
        List<Map<String, Object>> listBatchInfo = dlwDBConnectionADAO.getDlwDual(hmParamTypeA);
        List<Map<String, Object>> listBatchInfo2 = dlwDBConnectionBDAO.getDlwDual(hmParamTypeA);
        
   	 return 0;
    }
}
