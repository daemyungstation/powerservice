/*
 * (@)# DlwResnServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/18
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwNewTypeResnService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 해약 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/18
 * @프로그램ID DlwResn
 */
@Service
public class DlwNewTypeResnServiceImpl extends EgovAbstractServiceImpl implements DlwNewTypeResnService {

    @Resource
    public DlwNewTypeResnDAO dlwNewTypeResnDAO;

    /**
     * 해약 등록 체크 : 행사 등록 여부 조회
     *
     * @param pmParam 검색 조건
     * @return 행사 등록 여부
     * @throws Exception
     */
    public int getNewTypeEventChk(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeEventChk(pmParam);
    }

    /**
     * 해약 등록 체크 : 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 해약 처리 유무
     * @throws Exception
     */
    public int getNewTypeResnChk(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnChk(pmParam);
    }

    /**
     * 해약 등록 체크 : 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 세무 처리 유무
     * @throws Exception
     */
    public int getNewTypeTaxtChk(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeTaxtChk(pmParam);
    }

    /**
     * 해약 등록 체크 : CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return CMS 처리 유무
     * @throws Exception
     */
    public int getNewTypeCmsChk(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeCmsChk(pmParam);
    }

    /**
     * 해약 등록 체크 : 콜센터  처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 콜센터 처리 유무
     * @throws Exception
     */
    public int getNewTypecallcenterChk(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypecallcenterChk(pmParam);
    }
    /**
     * 해약 등록 체크 : 해약 시 CMS청구내역 파일 중 (WDRW_REQ) 신청전(STAT = '01'),신청중(STAT = '02')인 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeCmsReqCnt(pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSelectProdCl(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeSelectProdCl(pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getNewTypeSelectJoinType(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeSelectJoinType(pmParam);
    }

    /**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getNewTypeResnGubn(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnGubn(pmParam);
    }

    /**
     * CMS정보 조회
     *
     * @param pmParam 검색 조건
     * @return CMS정보
     * @throws Exception
     */
    public Map<String, Object> getNewTypeCmsInfo(Map<String, Object> pmParam) throws Exception {

        String sPayMethodCd = dlwNewTypeResnDAO.getNewTypePayMethodCd(pmParam);
        System.out.println("------ sPayMethodCd : " + sPayMethodCd);
        if (null == sPayMethodCd) sPayMethodCd = "";
        pmParam.put("pay_mthd", sPayMethodCd);
        return dlwNewTypeResnDAO.getNewTypeCmsInfo(pmParam);
    }

    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeResnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeResnDAO.getNewTypeResnCount(pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnList(pmParam);
    }

    /**
     * 해약 상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약 상세 정보
     * @throws Exception
     */
    public Map<String, Object> getNewTypeResnDtpt(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnDtpt(pmParam);
    }

    /**
     * 해약시 미지급 포인트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 해약시 미지급 포인트
     * @throws Exception
     */
    public String getNewTypeEmartIPoint(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeEmartIPoint(pmParam);
    }

    /**
     * 가수금 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가수금 정보
     * @throws Exception
     */
    public String getNewTypeResnGasuAmt(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnGasuAmt(pmParam);
    }

    /**
     *
     * 해약 송금일 업데이트
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int updateNewTypeResnSenddt(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.updateNewTypeResnSenddt(pmParam);
    }

    /**
     * 서류 제출 기한 마감 수정
     * 해약 처리 정보를 수정
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int updateNewTypeResnProc(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.updateNewTypeResnProc(pmParam);
    }

    /**
     * 해약 수정
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int updateNewTypeResn(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.updateNewTypeResn(pmParam);
    }

    /**
     * 해약 등록
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int insertNewTypeResn(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.insertNewTypeResn(pmParam);
    }

    /**
     * 해약 삭제
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public String resnNewTypeDel(Map<String, ?> pmParam) throws Exception {
        String sNewYn = dlwNewTypeResnDAO.getNewTypeResnNewYnChk(pmParam);
        if ("Y".equals(sNewYn) || "E".equals(sNewYn)) {
            dlwNewTypeResnDAO.resnNewTypeDel(pmParam);
        }
        return sNewYn;
    }


    /**
     * 해약현황 통계 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 통계 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnStat1List(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnStat1List(pmParam);
    }
    public List<Map<String, Object>> getNewTypeResnStat2List(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnStat2List(pmParam);
    }
    public List<Map<String, Object>> getNewTypeResnStat3List(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnStat3List(pmParam);
    }

    /**
     * 매출입현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBuyChulList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeBuyChulList(pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAccPurList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeAccPurList(pmParam);
    }

    /**
     * 매입매출 회원검색 수
     *
     * @param pmParam 검색 조건
     * @return 매입매출현황 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeMemPurCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeResnDAO.getNewTypeMemPurCount(pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMemPurList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeMemPurList(pmParam);
    }


    /**
     * 매입현황상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매입현황상세 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeBuyDtlList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeBuyDtlList(pmParam);
    }

    /**
     * (중간마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTmpMemPurList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeTmpMemPurList(pmParam);
    }

    /**
     * (최종마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeMemPurMgList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeMemPurMgList(pmParam);
    }

    /**
     * (최종마감) 매출입현황 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeAccPurMgList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeAccPurMgList(pmParam);
    }


    /**
     * (기본데이터) 회원별매출입 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public int getNewTypeCntMemPurList(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeResnDAO.getNewTypeCntMemPurList(pmParam);
    }


    /**
     * (중간마감) 회원별매출입 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 카운트 조회
     * @throws Exception
     */
    public int srchNewTypeCntAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.srchNewTypeCntAccntPurSale(pmParam);
    }

    /**
     * (중간마감) 회원별매출입 카운트 조회 (** 조건 제외)
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 카운트 조회 (** 조건 제외)
     * @throws Exception
     */
    public int srchNewTypeCntAccntPurSale2(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.srchNewTypeCntAccntPurSale2(pmParam);
    }

    /**
     * (최종마감) 회원별매출입 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return (최종마감) 회원별매출입 카운트 조회
     * @throws Exception
     */
    public int srchNewTypeCntAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.srchNewTypeCntAccntPurSaleMg(pmParam);
    }

    /**
     * 매입매출 최종마감일 조회
     *
     * @param pmParam 검색 조건
     * @return 매입매출 최종마감일 조회
     * @throws Exception
     */
    public String srchNewTypeMgDtAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.srchNewTypeMgDtAccntPurSaleMg(pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 입력
     *
     * @param pmParam 검색 조건
     * @return 회원별 상품모델 매입매출 입력
     * @throws Exception
     */
    public int saveNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.saveNewTypeAccntPurSale(pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 수정
     *
     * @param pmParam 검색 조건
     * @return 회원별 상품모델 매입매출 수정
     * @throws Exception
     */
    public int updateNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.updateNewTypeAccntPurSale(pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 삭제
     *
     * @param pmParam 검색 조건
     * @return 회원별 상품모델 매입매출 삭제
     * @throws Exception
     */
    public int deleteNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.deleteNewTypeAccntPurSale(pmParam);
    }

    /**
     * 회원별매출입 최종마감처리
     *
     * @param pmParam 검색 조건
     * @return 회원별매출입 최종마감처리
     * @throws Exception
     */
    public int saveNewTypeAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.saveNewTypeAccntPurSaleMg(pmParam);
    }


    /**
     * 회원별 해약환급금 조회
     *
     * @param pmParam
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectNewTypeResnAmtAccnt(Map<String, Object> pmParam) throws Exception {
        return dlwNewTypeResnDAO.selectNewTypeResnAmtAccnt(pmParam);
    }

    /**
     * ???
     *
     * @param pmParam
     * @return
     * @throws Exception
     */
    public long selectNewTypeResnAmtSum(Map<String, Object> pmParam) throws Exception {
        return dlwNewTypeResnDAO.selectNewTypeResnAmtSum(pmParam);
    }

    /**
     * 회원별 해약환급금 마감
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int callNewTypeUPResnAmt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            System.out.println("------------0");

            if (listInDs != null && listInDs.size() > 0) {

                System.out.println("------------1");

                mRow = listInDs.get(0);
                System.out.println("------------2");

                ParamUtils.addSaveParam(mRow);

                CommonUtils.printLog(mRow);
                System.out.println("------------3");

                iCudCnt += dlwNewTypeResnDAO.callNewTypeUPResnAmt(mRow);
                System.out.println("------------4");

            }
            System.out.println("------------5");
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /**
     * 회원별 해약환급금 마감해지
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteNewTypeResnAmt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {

                mRow = listInDs.get(0);

                ParamUtils.addSaveParam(mRow);

                CommonUtils.printLog(mRow);

                iCudCnt += dlwNewTypeResnDAO.deleteNewTypeResnAmt(mRow);

            }
        } catch (Exception e) {
            throw e;
        }
        return iCudCnt;
    }

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수, 해약완료, 유효 로 변경
     * ================================================================
     * */
    public int updateNewTypeResnMemberState(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.updateNewTypeResnMemberState(pmParam);
    }

    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeWdrwReqConfirm(Map pmParam) {
		return dlwNewTypeResnDAO.getNewTypeWdrwReqConfirm(pmParam); 
	}

	/* ================================================================
     * 날짜 : 20190710
     * 이름 : 송준빈
     * 추가내용 : 해약등록 엔터 입력시 해당 은행코드 리턴
     * 대상 테이블 : LF_DMUSER.COM_CD
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeCodeBank(String sCdNm) {
		return dlwNewTypeResnDAO.getNewTypeCodeBank(sCdNm); 
	}
	
	/**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeResnNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeResnDAO.getNewTypeResnNewCount(pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnNewList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnNewList(pmParam);
    }
    
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeResnNewCount2(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeResnDAO.getNewTypeResnNewCount2(pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeResnNewList2(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeResnDAO.getNewTypeResnNewList2(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201126
     * 이름 : 김주용
     * 추가내용 : 회원 만기일자 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public String getNewTypeMangiDate(Map<String, ?> pmParam) throws Exception {
		return dlwNewTypeResnDAO.getNewTypeMangiDate(pmParam);
	}
}
