/*
 * (@)# DlwProdServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwProdService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.DateUtil;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.oreilly.servlet.MultipartRequest;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwProd
 */
@Service
public class DlwProdServiceImpl extends EgovAbstractServiceImpl implements DlwProdService {

    private final Logger log = LoggerFactory.getLogger(DlwProdServiceImpl.class);

    @Resource
    public DlwProdDAO dlwProdDAO;

    /**
     * 상품 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwProdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getDlwProdCount(pmParam);
    }

    /**
     * 신규상품등록시 상품코드번호 확인
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwProdNewCODE(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getDlwProdNewCODE(pmParam);
    }

    /**
     * 상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProdList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getDlwProdList(pmParam);
    }

    /**
     * 상품조회_상품상세정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwProdDtpt(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getDlwProdDtpt(pmParam);
    }

    /**
     * 상품조회_상품상세정보를 검색한다.(비정기형 월청구금액)
     *
     * @param pmParam 검색 조건
     * @return 비정기형 월청구금액 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProductDtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getDlwProductDtl(pmParam);
    }

    /**
     * 상품조회_상품상세정보를 검색한다.(멤버쉽카드종류)
     *
     * @param pmParam 검색 조건
     * @return 멤버쉽카드종류
     * @throws Exception
     */
    public List<Map<String, Object>> getOCBCardCodeList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getOCBCardCodeList(pmParam);
    }

    /**
     * 상품상세분류(모델Mst) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getModelMstInfo(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getModelMstInfo(pmParam);
    }

    /**
     * 모델리스트(모델 Dtl) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 모델리스트(모델 Dtl) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getModelDtlInfo(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getModelDtlInfo(pmParam);
    }

    /**
     * 상품종류리스트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품종류리스트 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdKindList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getProdKindList(pmParam);
    }


    /**
     * 발송/반송 관리에서 고유번호+회원명+우대번호 조회
     *
     * @param pmParam 검색 조건
     * @return 상품종류리스트 정보 목록
     * @throws Exception
     */
    public Map<String, Object> getMemProdAccntWithDlv(String psParam) throws Exception {
        return dlwProdDAO.getMemProdAccntWithDlv(psParam);
    }

    /**
     * 신규 상품 등록
     *
     * @param pmParam 검색 조건
     * @return 신규 상품 등록
     * @throws Exception
     */
    public int insertProdNew(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertProdNew(pmParam);
    }

    /**
     * 상품정보 수정
     *
     * @param pmParam 검색 조건
     * @return 상품정보 수정
     * @throws Exception
     */
    public int updateProdChng(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updateProdChng(pmParam);
    }

    /**
     * 상품정보 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품정보 삭제
     * @throws Exception
     */
    public int deleteProd(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.deleteProd(pmParam);
    }



    /**
     * 신규 상품상세 등록
     *
     * @param pmParam 검색 조건
     * @return 신규 상품상세 등록
     * @throws Exception
     */
    public int insertProdDtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertProdDtl(pmParam);
    }


    /**
     * 상품상세 정보 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품상세정보 삭제
     * @throws Exception
     */
    public int deleteProdDtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.deleteProdDtl(pmParam);
    }


    /**
     * 상품별 상세분류 콤보 리스트 목록
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세분류 콤보 리스트 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdDtlList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getProdDtlList(pmParam);
    }

    /**
     * 상품별 상세분류 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int getClassProdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getClassProdCount(pmParam);
    }

    /**
     * 상품별 상세분류 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세분류 리스트 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getClassProdList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getClassProdList(pmParam);
    }

    /**
     * 상품별 상세분류 선택 행 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세분류 선택 행 삭제
     * @throws Exception
     */
    public int getRowProdDel(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getRowProdDel(pmParam);
    }

    /**
     * 상품별 상세 등록
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세 등록
     * @throws Exception
     */
    public int insertProdKindNew(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertProdKindNew(pmParam);
    }

    /**
     * 상품별 모델 분류 기초 코드 리스트 건수
     *
     * @param pmParam 검색 조건
     * @return 상품별 모델 분류 기초 코드 리스트 건수
     * @throws Exception
     */
    public int getProdModelCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getProdModelCount(pmParam);
    }

    /**
     * 상품별 모델 분류 기초 코드 관리 조회
     *
     * @param pmParam 검색 조건
     * @return 상품별 모델 분류 기초 코드 관리 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getProdModelList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getProdModelList(pmParam);
    }

    /**
     * 기간별 상품 의전원가 조회 건수
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 조회 건수
     * @throws Exception
     */
    public int getPeriodProdCostCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getPeriodProdCostCount(pmParam);
    }

    /**
     * 기간별 상품 의전원가 조회
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getPeriodProdCost(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getPeriodProdCost(pmParam);
    }

    /**
     * 기간별 상품 의전원가 등록
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 등록
     * @throws Exception
     */
    public int insertPeriodProdCost(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertPeriodProdCost(pmParam);
    }

    /**
     * 기간별 상품 의전원가 입력가능여부 체크1
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String checkPeriodProdCost1(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.checkPeriodProdCost1(pmParam);
    }

    /**
     * 기간별 상품 의전원가 입력가능여부 체크2
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String checkPeriodProdCost2(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.checkPeriodProdCost2(pmParam);
    }

    /**
     * 기간별 상품 의전원가 입력가능여부 체크3
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String checkPeriodProdCost3(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.checkPeriodProdCost3(pmParam);
    }

    /**
     * 기간별 상품 의전원가 삭제
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 삭제
     * @throws Exception
     */
    public int getRowPeriodProdCostDel(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getRowPeriodProdCostDel(pmParam);
    }

    /**
     * 기간별 상품 의전원가 수정  (** 등록 또는 삭제시 이어서 적용종료일 수정)
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 수정
     * @throws Exception
     */
    public int updatePeriodProdCost(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updatePeriodProdCost(pmParam);
    }

    /**
     * 상품 등록/수정/삭제 -- 추후 수정할 예정
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
  /*  public int saveProd(XPlatformMapDTO xpDto) throws Exception {
        Map <String, Object> mapInVar     = xpDto.getInVariableMap();
        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        int iCudCnt = 0;

        int rowType = -1;
        Map hmParam = null;

        try {
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap dsDetail = (DataSetMap)mapInDs.get("ds_input2");

            Object gubun = (Object)mapInVar.get("p_gubun");

            if (srchInDs != null && srchInDs.size() > 0) {
                    for (int i=0; i<srchInDs.size(); ++i) {
                           hmParam = srchInDs.get(i);
                           ParamUtils.addSaveParam(hmParam);

                           rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                           if (rowType == DataSet.ROW_TYPE_INSERTED) {
                            System.out.println("* * * * 등록 * * *");
                            //상품코드 확인
                         int nTotal = dlwProdDAO.getDlwProdNewCODE(hmParam);

                                if (nTotal < 1) {
                                       iCudCnt += dlwProdDAO.insertProdNew(hmParam);

                                        for (int j=0; j<dsDetail.size(); ++j) {
                                         Map mDetail = dsDetail.get(j);
                                         ParamUtils.addSaveParam(mDetail);
                                         iCudCnt += dlwProdDAO.insertProdDtl(mDetail);  // 상세정보등록
                                            }
                                }else {
                                    szErrorCode = "-1";
                                    szErrorMsg  = "상품코드 중복 오류!!! 상품코드 확인 후 등록해주세요.";

                               }

                           }
                           else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                                                   System.out.println("* * * * 수정 * * *");

                                                   iCudCnt += dlwProdDAO.updateProdChng(hmParam);
                                                   ParamUtils.addSaveParam(hmParam);

                                                   dlwProdDAO.deleteProdDtl(hmParam);  // 삭제

                                                   if (null != dsDetail && dsDetail.size() > 0) {
                                                       for (int j=0; j<dsDetail.size(); ++j) {
                                                           Map mDetail = dsDetail.get(j);

                                                           ParamUtils.addSaveParam(mDetail);

                                                            rowType = ((Integer) mDetail.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                                                           if (rowType != DataSet.ROW_TYPE_DELETED) {
                                                               iCudCnt += dlwProdDAO.insertProdDtl(mDetail);  // 상세정보등록
                                                           }
                                                       }
                                                   }




                           }
                           else if (rowType == DataSet.ROW_TYPE_DELETED) {
                               iCudCnt += dlwProdDAO.deleteProd(hmParam);
                           }



            }
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }*/


    /**
     * 양도양수 리스트 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTransfList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getTransfList(pmParam);
    }


    /**
     * 상품모델구분코드 등록
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int insertProdModelClCd(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertProdModelClCd(pmParam);
    }

    /**
     * 상품모델구분코드 수정
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int updateProdModelClCd(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updateProdModelClCd(pmParam);
    }
    /**
     * 상품모델명관리
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int updateProdtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updateProdtl(pmParam);
    }


    public int updateProd_dtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updateProd_dtl(pmParam);
    }


    /**
     * 상품모델구분코드 삭제
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public void deleteProdModelClCd(Map<String, ?> pmParam) throws Exception {
        dlwProdDAO.deleteProdModelClCd(pmParam);
    }


    /**
     * 상품모델구분코드 삭제(DEL_FG='Y')
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public void deleteFgProdModelClCd(Map<String, Object> pmParam) throws Exception {
        dlwProdDAO.deleteFgProdModelClCd(pmParam);
    }


    /**
     * 상품모델구분코드 코드조회
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public String getModelClCd(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getModelClCd(pmParam);
    }


    /**
     * 결합상품 종류 관리 등록
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int insertProdKind(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertProdKind(pmParam);
    }


    public int insertProdtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertProdtl(pmParam);
    }



    /**
     * 결합상품 종류 관리 삭제
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int deleteProdKind(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.deleteProdKind(pmParam);
    }

    /**
     * 결합상품 종류 관리 삭제 (DEL_FG='Y')
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int deleteFgProdKind(Map<String, Object> pmParam) throws Exception {
        return dlwProdDAO.deleteFgProdKind(pmParam);
    }

    /**
     * 상품별모델리스트 삭제     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */

    public int deleteProdtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.deleteProdtl(pmParam);
    }


    public List<Map<String, Object>> getprodnList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getprodnList(pmParam);
    }

    /**
     * 상품모델구분코드 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 상세정보
     * @throws Exception
     */
    public Map<String, Object> getProdMolelClCd(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getProdMolelClCd(pmParam);
    }



    public List<Map<String, Object>> getProddtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getProddtl(pmParam);
    }
    /**
     * 결합상품 종류를 검색한다
     *
     * @param pmParam 검색 조건
     * @return 결합상품 상세정보
     * @throws Exception
     */
    public List<Map<String, Object>> getProdKind(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getProdKind(pmParam);
    }

    /**
     * 상품 모델명 리스트
     *
     * @param pmParam 검색 조건
     * @return 결합상품 상세정보
     * @throws Exception
     */
    public List<Map<String, Object>> getProdmodeldtl(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getProdmodeldtl(pmParam);
    }



    /**
     * 회원별 녹취 확인 리스트 건수
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트 건수
     * @throws Exception
     */
    public int getRecBeforeCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getRecBeforeCount(pmParam);
    }

    /**
     * 회원별 녹취 확인 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRecBeforeList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getRecBeforeList(pmParam);
    }

    /**
     * 파일업로드 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileUploadList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getFileUploadList(pmParam);
    }

    /**
     * 업로드실패이력 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUploadFailList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getUploadFailList(pmParam);
    }

    public void uploadToNas(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

        String subDir 		= CommonUtils.nvls(request.getParameter("subDir"));
        String upload_cl 	= CommonUtils.nvls(request.getParameter("upload_cl"));
        String qaid 	= CommonUtils.nvls(request.getParameter("qaid"));
        String qaswon 	= CommonUtils.nvls(request.getParameter("empl_no"));
        String tttttt 	= CommonUtils.nvls(request.getParameter("tttt"));



        mResuslt.put("errMsg", "");

        log.info(">> subDir : " + subDir);
        log.info(">> qaid : " + qaid);

        String[] fileClToArr	= { "0001", "0002", "0003", "0004", "0005" , "0006"};

        String successPath 		= "";
        String errorPath 		= "";

        log.info("hsFactoringFileBatchUpload> 2 ");

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows") >= 0) {
            successPath = EgovProperties.getProperty("life.rec.file.windows.successPath");
            errorPath 	= EgovProperties.getProperty("life.rec.file.windows.errorPath");
        } else {
            successPath = EgovProperties.getProperty("life.rec.file.unix.successPath");
            errorPath 	= EgovProperties.getProperty("life.rec.file.unix.errorPath");
        }

        String tempDir = System.getProperty("java.io.tmpdir");

        log.info("---uploadToNas 서비스 - 1");
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);

        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";

        String sCurrDthms 	= DateUtil.getCurrDthms();
        String sYmd 		= sCurrDthms.substring(0,8);

        int cnt				= 0;
        String[] tok		= null;
        String[] fileLayout	= null;
        String fileNm		= "";
        String ext 			= "";
        String accntNo 		= "";
        String phone		= "";
        String cl1 			= "";
        String cl2 			= "";
        String cl3 			= "";
        String tmpStr 		= "";
        String randomYn		= "N";
        String genDm 		= "";
        HashMap<String,String> fileInfo	= null;
        int fileCnt			= 0;

        UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
        String empleNo 		= CommonUtils.nvls(oUserSession.getUserid());
        Map<String, Object> mParam = null;

        try {

            if ("".equals(empleNo)) {
                throw new EgovBizException("업로드 권한이 없습니다.");
            }

            mParam = new HashMap<>();
            mParam.put("emple_no", empleNo);
            String authYn = dlwProdDAO.selectHSFileUploadAuth(mParam);

            log.info("hsFactoringFileBatchUpload> authYn : " + authYn);

            if (!"Y".equals(authYn)) {
                throw new EgovBizException("업로드 권한이 없습니다.");
            }

            // 실제로는 단건만 처리함
            while (enm.hasMoreElements())
            {
                fileCnt++;
                sKey = (String)enm.nextElement();
                log.info("sKey : " + sKey);
                File upfile = multipartRequest.getFile(sKey);

                String sOrigFn = upfile.getName();


                mResuslt.put("file_nm", sOrigFn);
                mResuslt.put("cl", subDir);

                cnt = dlwProdDAO.recFileDupChk(sOrigFn);
                if (cnt > 0) {
                    throw new EgovBizException("이미 등록되어 있는 파일입니다.");
                }

                log.info("---uploadToNas 서비스 - 2");

                log.info("---uploadToNas 서비스 - 3");

                tok = sOrigFn.split("\\.");
                if (tok.length < 2) {
                    throw new EgovBizException("파일명이 잘못되었습니다.(확장자없음)");
                }

                fileNm	= tok[0].trim();
                ext		= tok[1].toLowerCase();

                if (fileNm.indexOf("_") == 0) {
                    fileNm = fileNm.replaceFirst("_", "");
                }

                if ((!"wav".equals(ext)) && (!"ogg".equals(ext)) && (!"mp3".equals(ext))) {
                    throw new EgovBizException("지원하지 않는 파일형식입니다.(허용된 확장자 : wav, ogg, mp3)");
                }

                fileLayout = fileNm.split("_");
                if (fileLayout.length != 3) {
                    throw new EgovBizException("파일명이 잘못되었습니다.");
                }

                log.info("hsFactoringFileBatchUpload> 11 ");

                accntNo = fileLayout[0];
                cl2 = fileLayout[1];
                randomYn = "N";
                tmpStr = fileLayout[2].replaceAll(" ", "");

                log.info("hsFactoringFileBatchUpload> tmpStr : " + tmpStr);

                if (cl2.equals("08")){ // 의전일 경우 마지막 배열값 중분류로 구분

                    cl3 = fileLayout[2];
                    genDm = new SimpleDateFormat("yyyyMMddhhmmss").format(Long.valueOf(upfile.lastModified()));
                }else{
                    if ( CommonUtils.isNumeric(tmpStr) )
                    {
                        log.info("hsFactoringFileBatchUpload> 12 ");
                        if ( CommonUtils.dateValChk(tmpStr) ) {
                            genDm = tmpStr;
                        } else {
                            genDm = new SimpleDateFormat("yyyyMMddhhmmss").format(Long.valueOf(upfile.lastModified()));
                        }
                    }
                    else {
                        throw new EgovBizException("파일명이 잘못되었습니다.");
                    }
                }

                fileInfo = new HashMap<>();
                fileInfo.put("file_cl"		, subDir);
                fileInfo.put("file_nm"		, sOrigFn);
                fileInfo.put("accnt_no"		, accntNo);
                fileInfo.put("cl1"			, cl1);
                fileInfo.put("cl2"			, cl2);
                fileInfo.put("cl3"			, cl3);
                fileInfo.put("phone"		, phone);
                fileInfo.put("random_yn"	, randomYn);
                fileInfo.put("upload_cl"	, String.valueOf(Integer.valueOf(upload_cl)));
                fileInfo.put("gen_dm"		, genDm);
                fileInfo.put("emple_no"		, empleNo);
                fileInfo.put("qaid"		, qaid);
                fileInfo.put("qaswon"		, qaswon);

                int result = 0;

              //  CommonUtils.printLog(fileInfo);


                log.info("hsFactoringFileBatchUpload> 15 ");

                result = dlwProdDAO.insertUploadFile(fileInfo);

                log.info("hsFactoringFileBatchUpload> result : " + result);

                 if (result > 0)
                 {
                     log.info("hsFactoringFileBatchUpload> 16 ");

                     File fileClFolder = new File(successPath + empleNo + "/" + sYmd + "/" + upload_cl);
                     if (!fileClFolder.isDirectory()) {
                         fileClFolder.mkdirs();
                     }

                     log.info("hsFactoringFileBatchUpload> 17 ");

                     String fileSavePath = successPath + empleNo + "/" + sYmd + "/" + upload_cl + "/" + sOrigFn;
                     log.info("파일저장경로> fileSavePath : " + fileSavePath);
                     log.info("upfile.length() : " + upfile.length());
                     log.info("upfile.getAbsolutePath() : " + upfile.getAbsolutePath());

                     FileOutputStream newFileOs = new FileOutputStream(new File(fileSavePath));
                     FileUtils.copyFile(upfile, newFileOs);
                     upfile.delete();
                     newFileOs.close();

                     log.info("hsFactoringFileBatchUpload> 18 ");

                     HashMap<String,String> obj = new HashMap<>();
                     if ("Y".equals(randomYn)) {
                         log.info("hsFactoringFileBatchUpload> 19 ");
                         obj.put("cl", "임의매치");
                         obj.put("file_nm", sOrigFn);
                     }
                     else {
                         log.info("hsFactoringFileBatchUpload> 20 ");
                         obj.put("cl", "성공");
                         obj.put("file_nm", sOrigFn);
                     }
                     log.info("hsFactoringFileBatchUpload> 21 ");
                 } else {
                     log.info("hsFactoringFileBatchUpload> 22 ");
                     dlwProdDAO.insertUploadErrorFile(fileInfo);

                     log.info("hsFactoringFileBatchUpload> 23 ");
                     log.info("hsFactoringFileBatchUpload> 24 ");

                     File fileClFolder = new File(errorPath + empleNo + "/" + sYmd + "/" + upload_cl);
                     if (!fileClFolder.isDirectory()) {
                         fileClFolder.mkdirs();
                     }
                     log.info("hsFactoringFileBatchUpload> 25 ");

                     File newFile = new File(errorPath + empleNo + "/" + sYmd + "/" + upload_cl + "/" + sOrigFn);

                     FileOutputStream newFileOs = new FileOutputStream(newFile);
                     FileUtils.copyFile(upfile, newFileOs);
                     upfile.delete();
                     newFileOs.close();

                     log.info("hsFactoringFileBatchUpload> 26 ");
                     mResuslt.put("file_nm", sOrigFn);
                     mResuslt.put("errMsg", "파일등록오류");

                     log.info("hsFactoringFileBatchUpload> 27 ");
                 }
            }

            if (fileCnt < 1) {
                throw new EgovBizException("업로드된 파일이 없습니다.");
            }

        } catch (FileNotFoundException ex) {

            log.info("hsFactoringFileBatchUpload> 28 ");
            ex.printStackTrace();

            throw ex;
        } catch (IOException ex) {

            log.info("hsFactoringFileBatchUpload> 29 ");
            ex.printStackTrace();

            throw ex;
        }
    }

    /**
     * 업로드 녹취 파일 삭제
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int hsUploadDataDelete(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map<String, Object> mRow = null;

        try {
            String successPath 		= "";
            String fullPath 		= "";

            log.info("hsFactoringFileBatchUpload> 1 ");

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = EgovProperties.getProperty("life.rec.file.windows.successPath");
            } else {
                successPath = EgovProperties.getProperty("life.rec.file.unix.successPath");
            }

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0) {

                log.info("hsFactoringFileBatchUpload> 2 ");

                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);
                    ParamUtils.addSaveParam(mRow);
                    iCudCnt += dlwProdDAO.hsUploadDataDelete(mRow);
                }

                log.info("hsFactoringFileBatchUpload> 3 ");

                for (int i=0; i<listInDs.size(); ++i) {
                    mRow = listInDs.get(i);
                    fullPath = successPath +
                            CommonUtils.nvls((String)mRow.get("reg_man")) + "/" +
                            CommonUtils.nvls((String)mRow.get("reg_dm")) + "/" +
                            CommonUtils.nvls((String)mRow.get("file_cl")) + "/" +
                            CommonUtils.nvls((String)mRow.get("file_nm"));

                    log.info("> (" + i + ") fullPath : " + fullPath);

                    File f = new File(fullPath);
                    if (f.exists()) {
                        f.delete();
                        log.info("hsFactoringFileBatchUpload> 파일삭제함 ");
                    } else {
                        log.info("hsFactoringFileBatchUpload> 파일삭제 못함 ");
                    }
                }

                log.info("hsFactoringFileBatchUpload> 4 ");
            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    public List<Map<String, Object>> getmonitoringlist(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getmonitoringlist(pmParam);
    }

    /**
     * 모니터링녹취삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deletemonitoring_rec(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.deletemonitoring_rec(pmParam);
    }

    /**
     * 모니터링삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deletemonitoring(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.deletemonitoring(pmParam);
    }

    /**
     * 매입코드관리 count
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getDlwpurchaseCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getDlwpurchaseCount(pmParam);
    }

    /**
     * 매입코드관리를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwpurchaseList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getDlwpurchaseList(pmParam);
    }

    /**
     * 매입코드관리를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return 매입코드관리
     * @throws Exception
     */
    public int updatepurchaseChng(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updatepurchaseChng(pmParam);
    }

    /**
     *  매입코드관리를 입력한다.
     *
     * @param pmParam 검색 조건
     * @return 매입코드관리
     * @throws Exception
     */
    public int insertpurchaseNew(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertpurchaseNew(pmParam);
    }


    public void deletepurchase(Map <String, DataSetMap> mapInDs) {

        Map<String, Object> hmParam = new HashMap<String, Object>();

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

           for(int i = 0; i < listInDs.size() ; i++){


                hmParam = listInDs.get(i);
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                dlwProdDAO.deletepurchase(hmParam);          ///// 삭제


             }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 상품모델 매입매출가 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 검색 수를 반환
     * @throws Exception
     */
    public int getClassProdPriceCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwProdDAO.getClassProdPriceCount(pmParam);
    }

    /**
     * 상품모델 매입매출가 매입매출가 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 매입매출가 리스트 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getClassProdPriceList(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.getClassProdPriceList(pmParam);
    }


    /**
     * 상품모델 매입매출가 등록
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 등록
     * @throws Exception
     */
    public int insertProdModelPrice(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.insertProdModelPrice(pmParam);
    }


    /**
     * 상품모델 매입매출가 수정
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 수정
     * @throws Exception
     */
    public int updateProdModelPrice(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updateProdModelPrice(pmParam);
    }

    /**
     * 상품모델 매입매출가 수정2 (**기존 데이터의 종료일은 (입력적용일-1) 로 UPDATE)
     *
     * @param pmParam 검색 조건
     * @return 기존 데이터의 종료일은 (입력적용일-1) 로 UPDATE
     * @throws Exception
     */
    public int updateProdModelPrice2(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.updateProdModelPrice2(pmParam);
    }

    /**
     * 상품모델 매입매출가 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 삭제
     * @throws Exception
     */
    public int deleteProdModelPrice(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.deleteProdModelPrice(pmParam);
    }

    /**
     * 상품모델 매입매출가 입력가능여부 체크1
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String chkProdModelPrice1(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.chkProdModelPrice1(pmParam);
    }

    /**
     * 상품모델 매입매출가 입력가능여부 체크2
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String chkProdModelPrice2(Map<String, ?> pmParam) throws Exception {
        return dlwProdDAO.chkProdModelPrice2(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 수 (마스터)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public int getProductMstListCount(Map<String, ?> pmParam) throws Exception {
		return dlwProdDAO.getProductMstListCount(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 리스트 (마스터)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public List<Map<String, Object>> getProductMstList(Map<String, ?> pmParam) throws Exception {
		return dlwProdDAO.getProductMstList(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 수 (디테일)
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public int getProductNoDataListCount(Map<String, ?> pmParam) throws Exception {
		return dlwProdDAO.getProductNoDataListCount(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 리스트 (디테일)
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public List<Map<String, Object>> getProductNoDataList(Map<String, ?> pmParam) throws Exception {
		return dlwProdDAO.getProductNoDataList(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별 회차 기본정보 
     * 대상 테이블 : LF_DMUSER.PRODUCT_DTL
     * ================================================================
     * */
	public List<Map<String, Object>> getProductDtlList(Map<String, ?> pmParam) throws Exception {
		return dlwProdDAO.getProductDtlList(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 청구금액 입력 
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public int insertProductNoDataList(Map<String, Object> pmParam) {
		return dlwProdDAO.insertProductNoDataList(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 청구금액 삭제 
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public int deleteProductNoDataList(Map<String, Object> pmParam) {
		return dlwProdDAO.deleteProductNoDataList(pmParam);
	}
}
