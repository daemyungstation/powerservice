/*
 * (@)# DlwEvntService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwEvntMngService;
import powerservice.business.dlw.service.DlwEvntReceiptService;
import powerservice.business.dlw.service.DlwEvntService;
import powerservice.business.dlw.web.DlwBasiDataMngController;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.DmWebSenderService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.oreilly.servlet.MultipartRequest;
import com.tobesoft.xplatform.data.DataSet;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 행사 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * @프로그램ID DlwEvnt
 */
@Service
public class DlwEvntMngServiceImpl extends EgovAbstractServiceImpl implements DlwEvntMngService {

    @Resource
    public DlwEvntMngDAO DlwEvntMngDAO;

    //지부 관리자 리스트
    public List<Map<String, Object>> getEvntBranchList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntBranchList(pmParam);
    }
    
  //지부 코드가져오기
    public List<Map<String, Object>> getBranchCode(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getBranchCode(pmParam);
    }
    
    public int insertEvntBranch(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.insertEvntBranch(pmParam);
    }
    
    public int updateEvntBranch(Map<String, ?> pmParam) throws Exception {
        return  DlwEvntMngDAO.updateEvntBranch(pmParam);
    }
    public int deleteEvntBranch(Map<String, ?> pmParam) throws Exception {
        return  DlwEvntMngDAO.deleteEvntBranch(pmParam);
    }
    
    //지부 조회 1건
    public List<Map<String, Object>> selectEnvtBranch(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.selectEnvtBranch(pmParam);
    }
    
    //행사자 관리자 리스트
	public List<Map<String, Object>> getEventManList(Map<String, ?> pmParam) throws Exception {
		return DlwEvntMngDAO.getEventManList(pmParam);
	}
	
	/****   행사자 등록   ****/
    public String insrtEventMan(Map<String, Object> pmParam) throws Exception {

        int dupl_custmrCd = DlwEvntMngDAO.getDuplEvtManaMngCd(pmParam); //거래처 코드 중복 여부 가져옴

        if (dupl_custmrCd > 0) {
                                    return "중복";
                                }
            String rslt = "";
            ParamUtils.addSaveParam(pmParam);
            DlwEvntMngDAO.insrtEventMan(pmParam);
        return "";
    }

    /* 행사자 삭제 */
    public int deleteEventMan(Map<String, ?> pmParam) throws Exception {
        return  DlwEvntMngDAO.deleteEventMan(pmParam);
     }

    /* 행사자 수정 */
    public int updateEventMan(Map<String, Object> pmParam) throws Exception {
        ParamUtils.addSaveParam(pmParam);
        return  DlwEvntMngDAO.updateEventMan(pmParam);

     }

    /* 지부장 등록 건수 조회 */
    public int chkEventManaCnt(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.chkEventManaCnt(pmParam);
    }
    
    //창고 관리 리스트
    public List<Map<String, Object>> getEvntWHList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntWHList(pmParam);
    }
    
    /**
     * 창고 등록/수정/삭제
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int saveEvntWarehouse(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int rowType = -1;
        Map hmParam = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                    for (int i=0; i<listInDs.size(); ++i) {
                        hmParam = listInDs.get(i);
                        ParamUtils.addSaveParam(hmParam);

                        rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                        if (rowType == DataSet.ROW_TYPE_INSERTED) {
                            iCudCnt += DlwEvntMngDAO.insrtEvntWH(hmParam);
                        }
                        else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                            iCudCnt += DlwEvntMngDAO.updateEvntWH(hmParam);
                        }
                        else if (rowType == DataSet.ROW_TYPE_DELETED) {
                            iCudCnt += DlwEvntMngDAO.deleteEvntWH(hmParam);
                        }

                        mOut.put("wh_cd", CommonUtils.nvls((String)hmParam.get("wh_cd")));
                    }
            }
        } catch (Exception e) {
            throw e;
        }
            return iCudCnt;
    }
    
    //제공용품 리스트
    public List<Map<String, Object>> getEvntSPList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntSPList(pmParam);
    }
    
    /**
     * 제공용품 등록/수정/삭제
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int saveEvntSupplies(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int rowType = -1;
        Map hmParam = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                    for (int i=0; i<listInDs.size(); ++i) {
                        hmParam = listInDs.get(i);
                        ParamUtils.addSaveParam(hmParam);
                        
                    	hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        hmParam.put("upd_man", hmParam.get("rgsr_id"));

                        rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                        String bSqncFlag = (String) hmParam.get("sqnc_flag");
                        
                        if (rowType == DataSet.ROW_TYPE_INSERTED) {
                        	if (bSqncFlag != null && "Y".equals(bSqncFlag)) {
                                DlwEvntMngDAO.updateEvntSPSequence(hmParam);
                            }
                            iCudCnt += DlwEvntMngDAO.insrtEvntSP(hmParam);
                        }
                        else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                        	if (bSqncFlag != null && "Y".equals(bSqncFlag)) {
                                DlwEvntMngDAO.updateEvntSPSequence(hmParam);
                            }
                            iCudCnt += DlwEvntMngDAO.updateEvntSP(hmParam);
                        }
                        else if (rowType == DataSet.ROW_TYPE_DELETED) {
                            iCudCnt += DlwEvntMngDAO.deleteEvntSP(hmParam);
                        }

                        mOut.put("wh_cd", CommonUtils.nvls((String)hmParam.get("wh_cd")));
                    }
            }
        } catch (Exception e) {
            throw e;
        }
            return iCudCnt;
    }
    
    //제공용품seq
    public List<Map<String, Object>> getEvntSPmaxSqncList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntSPmaxSqncList(pmParam);
    }
    
    //제공용품 카테고리 리스트
    public List<Map<String, Object>> getEvntSPCtgMstList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntSPCtgMstList(pmParam);
    }
    
    //제공용품 카테고리 리스트
    public List<Map<String, Object>> getEvntSPCtgDtlList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntSPCtgDtlList(pmParam);
    }
    
    //제공용품 카테고리 리스트
    public List<Map<String, Object>> getEvntSPCtgSubList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntSPCtgSubList(pmParam);
    }
    
    //제공용품 카테고리 리스트
    public List<Map<String, Object>> getEvntSPCtgSeqList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntSPCtgSeqList(pmParam);
    }
    
    public int updateEvntSPCtgMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.updateEvntSPCtgMst(pmParam);
    }
    
    public int insertEvntSPCtgMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.insertEvntSPCtgMst(pmParam);
    }
    public int deleteEvntSPCtgMst(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.deleteEvntSPCtgMst(pmParam);
    }
    
    public int updateEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.updateEvntSPCtgDtl(pmParam);
    }
    
    public int insertEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.insertEvntSPCtgDtl(pmParam);
    }
    public int deleteEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.deleteEvntSPCtgDtl(pmParam);
    }
    
    public int insertEvntSPCtgSub(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.insertEvntSPCtgSub(pmParam);
    }
    public int deleteEvntSPCtgSub(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.deleteEvntSPCtgSub(pmParam);
    }
    
    //제공용품 팝업 리스트
    public List<Map<String, Object>> getEvntSPpopList(Map<String, ?> pmParam) throws Exception {
        return DlwEvntMngDAO.getEvntSPpopList(pmParam);
    }
}
