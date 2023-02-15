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

import powerservice.business.dlw.service.DlwResnService;
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
public class DlwResnServiceImpl extends EgovAbstractServiceImpl implements DlwResnService {

    @Resource
    public DlwResnDAO dlwResnDAO;

    /**
     * 해약 등록 체크 : 행사 등록 여부 조회
     *
     * @param pmParam 검색 조건
     * @return 행사 등록 여부
     * @throws Exception
     */
    public int getEventChk(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getEventChk(pmParam);
    }

    /**
     * 해약 등록 체크 : 해약 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 해약 처리 유무
     * @throws Exception
     */
    public int getResnChk(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnChk(pmParam);
    }

    /**
     * 해약 등록 체크 : 세무 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 세무 처리 유무
     * @throws Exception
     */
    public int getTaxtChk(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getTaxtChk(pmParam);
    }

    /**
     * 해약 등록 체크 : CMS 처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return CMS 처리 유무
     * @throws Exception
     */
    public int getCmsChk(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getCmsChk(pmParam);
    }

    /**
     * 해약 등록 체크 : 콜센터  처리 유무 검색
     *
     * @param pmParam 검색 조건
     * @return 콜센터 처리 유무
     * @throws Exception
     */
    public int getcallcenterChk(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getcallcenterChk(pmParam);
    }
    /**
     * 해약 등록 체크 : 해약 시 CMS청구내역 파일 중 (WDRW_REQ) 신청전(STAT = '01'),신청중(STAT = '02')인 건수 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCmsReqCnt(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getCmsReqCnt(pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSelectProdCl(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getSelectProdCl(pmParam);
    }

    /**
     * 해약 등록 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getSelectJoinType(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getSelectJoinType(pmParam);
    }

    /**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnGubn(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnGubn(pmParam);
    }

    /**
     * CMS정보 조회
     *
     * @param pmParam 검색 조건
     * @return CMS정보
     * @throws Exception
     */
    public Map<String, Object> getCmsInfo(Map<String, Object> pmParam) throws Exception {

        String sPayMethodCd = dlwResnDAO.getPayMethodCd(pmParam);
        System.out.println("------ sPayMethodCd : " + sPayMethodCd);
        if (null == sPayMethodCd) sPayMethodCd = "";
        pmParam.put("pay_mthd", sPayMethodCd);
        return dlwResnDAO.getCmsInfo(pmParam);
    }

    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getResnCount(pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnList(pmParam);
    }

    /**
     * 해약 상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약 상세 정보
     * @throws Exception
     */
    public Map<String, Object> getResnDtpt(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnDtpt(pmParam);
    }

    /**
     * 해약시 미지급 포인트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 해약시 미지급 포인트
     * @throws Exception
     */
    public String getEmartIPoint(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getEmartIPoint(pmParam);
    }

    /**
     * 가수금 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 가수금 정보
     * @throws Exception
     */
    public String getResnGasuAmt(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnGasuAmt(pmParam);
    }

    /**
     *
     * 해약 송금일 업데이트
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int updateResnSenddt(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.updateResnSenddt(pmParam);
    }

    /**
     * 서류 제출 기한 마감 수정
     * 해약 처리 정보를 수정
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int updateResnProc(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.updateResnProc(pmParam);
    }

    /**
     * 해약 수정
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int updateResn(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.updateResn(pmParam);
    }

    /**
     * 해약 등록
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public int insertResn(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.insertResn(pmParam);
    }

    /**
     * 해약 삭제
     *
     * @param pmParam 검색 조건
     * @return 해약 정보
     * @throws Exception
     */
    public String resnDel(Map<String, ?> pmParam) throws Exception {
        String sNewYn = dlwResnDAO.getResnNewYnChk(pmParam);
        if ("Y".equals(sNewYn) || "E".equals(sNewYn)) {
            dlwResnDAO.resnDel(pmParam);
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
    public List<Map<String, Object>> getResnStat1List(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnStat1List(pmParam);
    }
    public List<Map<String, Object>> getResnStat2List(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnStat2List(pmParam);
    }
    public List<Map<String, Object>> getResnStat3List(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnStat3List(pmParam);
    }

    /**
     * 매출입현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBuyChulList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getBuyChulList(pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAccPurList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getAccPurList(pmParam);
    }

    /**
     * 매입매출 회원검색 수
     *
     * @param pmParam 검색 조건
     * @return 매입매출현황 정보의 검색 건수
     * @throws Exception
     */
    public int getMemPurCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getMemPurCount(pmParam);
    }

    /**
     * 매입매출 합계 검색
     * 임동진
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemPurList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getMemPurList(pmParam);
    }


    /**
     * 매입현황상세 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 매입현황상세 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBuyDtlList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getBuyDtlList(pmParam);
    }

    /**
     * (중간마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getTmpMemPurList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getTmpMemPurList(pmParam);
    }

    /**
     * (최종마감) 회원별매출입 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemPurMgList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getMemPurMgList(pmParam);
    }

    /**
     * (최종마감) 매출입현황 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAccPurMgList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getAccPurMgList(pmParam);
    }


    /**
     * (기본데이터) 회원별매출입 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return 매출입현황 목록
     * @throws Exception
     */
    public int getCntMemPurList(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getCntMemPurList(pmParam);
    }


    /**
     * (중간마감) 회원별매출입 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 카운트 조회
     * @throws Exception
     */
    public int srchCntAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.srchCntAccntPurSale(pmParam);
    }

    /**
     * (중간마감) 회원별매출입 카운트 조회 (** 조건 제외)
     *
     * @param pmParam 검색 조건
     * @return (중간마감) 회원별매출입 카운트 조회 (** 조건 제외)
     * @throws Exception
     */
    public int srchCntAccntPurSale2(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.srchCntAccntPurSale2(pmParam);
    }

    /**
     * (최종마감) 회원별매출입 카운트 조회
     *
     * @param pmParam 검색 조건
     * @return (최종마감) 회원별매출입 카운트 조회
     * @throws Exception
     */
    public int srchCntAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.srchCntAccntPurSaleMg(pmParam);
    }

    /**
     * 매입매출 최종마감일 조회
     *
     * @param pmParam 검색 조건
     * @return 매입매출 최종마감일 조회
     * @throws Exception
     */
    public String srchMgDtAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.srchMgDtAccntPurSaleMg(pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 입력
     *
     * @param pmParam 검색 조건
     * @return 회원별 상품모델 매입매출 입력
     * @throws Exception
     */
    public int saveAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.saveAccntPurSale(pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 수정
     *
     * @param pmParam 검색 조건
     * @return 회원별 상품모델 매입매출 수정
     * @throws Exception
     */
    public int updateAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.updateAccntPurSale(pmParam);
    }

    /**
     * 회원별 상품모델 매입매출 삭제
     *
     * @param pmParam 검색 조건
     * @return 회원별 상품모델 매입매출 삭제
     * @throws Exception
     */
    public int deleteAccntPurSale(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.deleteAccntPurSale(pmParam);
    }

    /**
     * 회원별매출입 최종마감처리
     *
     * @param pmParam 검색 조건
     * @return 회원별매출입 최종마감처리
     * @throws Exception
     */
    public int saveAccntPurSaleMg(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.saveAccntPurSaleMg(pmParam);
    }


    /**
     * 회원별 해약환급금 조회
     *
     * @param pmParam
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectResnAmtAccnt(Map<String, Object> pmParam) throws Exception {
        return dlwResnDAO.selectResnAmtAccnt(pmParam);
    }

    /**
     * ???
     *
     * @param pmParam
     * @return
     * @throws Exception
     */
    public long selectResnAmtSum(Map<String, Object> pmParam) throws Exception {
        return dlwResnDAO.selectResnAmtSum(pmParam);
    }

    /**
     * 회원별 해약환급금 마감
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int callUPResnAmt(XPlatformMapDTO xpDto) throws Exception {

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

                iCudCnt += dlwResnDAO.callUPResnAmt(mRow);
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
    public int deleteResnAmt(XPlatformMapDTO xpDto) throws Exception {

        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

        int iCudCnt = 0;
        Map mRow = null;

        try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {

                mRow = listInDs.get(0);

                ParamUtils.addSaveParam(mRow);

                CommonUtils.printLog(mRow);

                iCudCnt += dlwResnDAO.deleteResnAmt(mRow);

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
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.updateResnMemberState(pmParam);
    }

    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getWdrwReqConfirm(Map pmParam) {
		return dlwResnDAO.getWdrwReqConfirm(pmParam); 
	}

	/* ================================================================
     * 날짜 : 20190710
     * 이름 : 송준빈
     * 추가내용 : 해약등록 엔터 입력시 해당 은행코드 리턴
     * 대상 테이블 : LF_DMUSER.COM_CD
     * ================================================================
     * */
	public List<Map<String, Object>> getCodeBank(String sCdNm) {
		return dlwResnDAO.getCodeBank(sCdNm); 
	}
	
	/**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getResnNewCount(pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnNewList(pmParam);
    }
    
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount2(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getResnNewCount2(pmParam);
    }

    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList2(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnNewList2(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201126
     * 이름 : 김주용
     * 추가내용 : 회원 만기일자 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public String getMangiDate(Map<String, ?> pmParam) throws Exception {
		return dlwResnDAO.getMangiDate(pmParam);
	}
	
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount3(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getResnNewCount3(pmParam);
    }
    
    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList3(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnNewList3(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약등록 회원 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public Map<String, Object> getResnAccntInfo(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnAccntInfo(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 상세 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public Map<String, Object> getResnDetailInfo(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnDetailInfo(pmParam);
    }  
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 전자서명 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public List<Map<String, Object>> getResnSignInfo(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnSignInfo(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 신규 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnInfo(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.insertResnInfo(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 업데이트 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int updateResnInfo(Map<String, ?> pmParam) throws Exception {
    	String strGbn = pmParam.get("update_gbn").toString();
    	if("U".equals(strGbn)){
    		return dlwResnDAO.updateResnInfo(pmParam);
    	} else {
    		return dlwResnDAO.deleteResnInfo(pmParam);    		
    	}
    	
        
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 레디캐시 실시간 조회
     * ================================================================
     * */
    public String getReadyCashRealAmt(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getReadyCashRealAmt(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 청구 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public List<Map<String, Object>> getResnReqInfo(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnReqInfo(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220307
     * 이름 : 임동진
     * 추가내용 : 해약 환불 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnRefund(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.insertResnRefund(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 상담 로그 등록 
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnCounsel(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.insertResnCounsel(pmParam);
    }

    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnNewCount0(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getResnNewCount0(pmParam);
    }
    
    /**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnNewList0(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnNewList0(pmParam);
    }
    	
    /**
     * 해약현황(전자서명) 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnSignCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnDAO.getResnSignCount(pmParam);
    }
    
    /**
     * 해약현황(전자서명) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnSignList(Map<String, ?> pmParam) throws Exception {
        return dlwResnDAO.getResnSignList(pmParam);
    }
}
