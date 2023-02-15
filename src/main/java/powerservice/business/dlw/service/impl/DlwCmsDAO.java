/*
 * (@)# DlwCmsDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대명라이프웨이 Cms 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwCms
 */
@Repository
public class DlwCmsDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 대명라이프웨이 Cms 고객정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보 건수
     * @throws Exception
     */
    public int getDlwCmsCsmmCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwCmsCsmmCount", pmParam);
    }

    /**
     * 대명라이프웨이 Cms 고객정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsCsmm(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCmsCsmm", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Cms 신청내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역 건수
     * @throws Exception
     */
    public int getDlwCmsRgsnHstrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwCmsRgsnHstrCount", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Cms 신청내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 신청내역
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsRgsnHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCmsRgsnHstrList", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Cms 출금의뢰내역의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역 건수
     * @throws Exception
     */
    public int getDlwCmsWdrwHstrCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwCmsWdrwHstrCount", pmParam);
    }

    /**
     * 대명라이프웨이 회원별 Cms 출금의뢰내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 출금의뢰내역
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsWdrwHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCmsWdrwHstrList", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 이체정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return 수정결과
     * @throws Exception
     */
    public int updateCmsTranInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateCmsTranInfo", pmParam);
    }

    /**
     * 대명라이프웨이의 Cms 산출중 여부 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 산출 여부 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsWdrwChk(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCmsWdrwChk", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 금일 부가서비스 등록이력 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보 건수
     * @throws Exception
     */
    public int getDlwCmsAplcDtlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwCmsAplcDtlCount", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 금일 부가서비스 등록이력을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsAplcDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCmsAplcDtl", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 해당구좌 CMS회원여부 검사
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    //public List<Map<String, Object>> getDltnFlagCmsMmbr(Map<String, ?> pmParam) throws Exception {
    public String getDltnFlagCmsMmbr(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDltnFlagCmsMmbr", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 부가서비스 등록
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int insertDlwCmsAnxtSrvc(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertDlwCmsAnxtSrvc", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 회원 등록
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int insertDlwCmsMmbr(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertDlwCmsMmbr", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 회원 수정
     *
     * @param pmParam 검색 조건
     * @return 수정 여부
     * @throws Exception
     */
    public int updateDlwCmsMmbr(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateDlwCmsMmbr", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 회원 삭제
     *
     * @param pmParam 검색 조건
     * @return 삭제여부
     * @throws Exception
     */
    public int deleteDlwCmsMmbr(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.deleteDlwCmsMmbr", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 납입방법 수정
     *
     * @param pmParam 검색 조건
     * @return 수정 여부
     * @throws Exception
     */
    public int updateDlwCmsPymtMthd(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateDlwCmsPymtMthd", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 접수방법 수정
     *
     * @param pmParam 검색 조건
     * @return 수정 여부
     * @throws Exception
     */
    public int updateDlwCmsAcpgMthd(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateDlwCmsAcpgMthd", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 대리납여부, 해피콜 등록
     *
     * @param pmParam 검색 조건
     * @return 대리납 여부
     * @throws Exception
     */
    public int updateDlwCmsRprvPymt(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateDlwCmsRprvPymt", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 출금이체 신청내역조회-신청전인 Data 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 출금이체 신청 전 데이터 건수
     * @throws Exception
     */
    public int getDlwWdrwTranDtlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwWdrwTranDtlCount", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 출금이체 신청내역조회-신청전인 데이터를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 출금이체 신청 전 데이터
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwTranDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwWdrwTranDtlList", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 당월 출금의뢰 총액 조회
     *
     * @param pmParam 검색 조건
     * @return CMS 당월 출금의뢰 총액
     * @throws Exception
     */
    public String getDlwWdrwReqDtTtamt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwWdrwReqDtTtamt", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 공통정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCmsComnInfo", pmParam);
    }

    /**
     * 대명라이프웨이 공통정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return  CMS 공통정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwComnInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwComnInfo", pmParam);
    }

    /**
     * 대명라이프웨이 행사고객 여부?를 조회한다. (프로시저)
     *
     * @param pmParam 검색 조건
     * @return  CMS 최종납입회차
     * @throws Exception
     */
    public String getEvntAccntNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getEvntAccntNo", pmParam);
    }

    /**
     * 대명라이프웨이 상품구분 조회 - 결합상품 구분용
     *
     * @param pmParam 검색 조건
     * @return  CMS 최종납입회차
     * @throws Exception
     */
    public String getPrdtDiv(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getPrdtDiv", pmParam);
    }

    /**
     * 대명라이프웨이 납입액 조회
     *
     * @param pmParam 검색 조건
     * @return  CMS 최종납입회차
     * @throws Exception
     */
    public int getInvAmt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getInvAmt", pmParam);
    }

    /**
     * 대명라이프웨이 청구건수 수정,-실청구금액 수정 > 콜센터산출
     *
     * @param pmParam 검색 조건
     * @return  CMS 최종납입회차
     * @throws Exception
     */
    public int updateInvGunsuForEB21Call(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateInvGunsuForEB21Call", pmParam);
    }

    /**
     * 대명라이프웨이 청구건수 수정,-실청구금액 수정
     *
     * @param pmParam 검색 조건
     * @return  CMS 최종납입회차
     * @throws Exception
     */
    public int updateInvGunsuForEB21(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateInvGunsuForEB21", pmParam);
    }

    /**
     * 대명라이프웨이 청구건수 수정,-실청구금액 수정 >> 카드
     *
     * @param pmParam 검색 조건
     * @return  CMS 최종납입회차
     * @throws Exception
     */
    public int updateInvGunsuForCard(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateInvGunsuForCard", pmParam);
    }

    /**
     * 대명라이프웨이 CMS EB21 산출내역 삭제
     *
     * @param pmParam 검색 조건
     * @return 삭제 여부
     * @throws Exception
     */
    public int deleteCmsWdrwReq(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.deleteCmsWdrwReq", pmParam);
    }

    /**
     * 대명라이프웨이 청구테이블에 고객만족센터 데이터 삽입
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int insertCmsWdrwReqByCall(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.insertCmsWdrwReqByCall", pmParam);
    }

    /**
     * 대명라이프웨이 청구여부 업데이트
     *
     * @param pmParam 검색 조건
     * @return 수정 여부
     * @throws Exception
     */
    public int updateWdrwReqYn(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateWdrwReqYn", pmParam);
    }

    /**
     * 대명라이프웨이 결합상품 산출 오류를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCombErrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCombErrList", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 출금이체 임시회원을 산출한다.
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwTempMember(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwTempMember", pmParam);
    }

    /**
     * 대명라이프웨이 카드오류 회원을 산출한다.
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwErrorMember(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwErrorMember", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 출금이체 연체회원을 산출한다.
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwDelayMember(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwDelayMember", pmParam);
    }

    /**
     * 대명라이프웨이 청구 건수 추가, 총 금액 계산
     *
     * @param pmParam 검색 조건
     * @return  청구건수
     * @throws Exception
     */
    public int getInvAmtByInvGunsu(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getInvAmtByInvGunsu", pmParam);
    }

    /**
     * 신규출금회차 가져오기
     *
     * @param pmParam 검색 조건
     * @return  청구건수
     * @throws Exception
     */
    public int getnew_inv_no(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getnew_inv_no", pmParam);
    }


    /**
     * 대명라이프웨이 CMS 출금의뢰신청 내역 등록
     *
     * @param pmParam 검색 조건
     * @return 등록 건수
     * @throws Exception
     */
    public int insertCmsWdrwReq(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertCmsWdrwReq", pmParam);
    }

    /**
     * 대명라이프웨이 Card 출금의뢰신청 내역 등록
     *
     * @param pmParam 검색 조건
     * @return 등록 건수
     * @throws Exception
     */
    public int insertCardWdrwReq(Map<String, ?> pmParam) throws Exception {
       // CommonUtils.printLog(pmParam);
        return insert("DlwCmsMap.insertCardWdrwReq", pmParam);
    }

    /**
     * 대명라이프웨이 출금이체 신청전 조회-구좌에 의한
     *
     * @param pmParam 검색 조건
     * @return 결합상품 산출 오류
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwAddData(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwWdrwAddData", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 구좌별 최종납입회차 조회
     *
     * @param pmParam 검색 조건
     * @return 최종납입회차
     * @throws Exception
     */
    public int getDlwLastPayNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwLastPayNo", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 출금의뢰신청 내역 등록 (콜센터산출
     )
     *
     * @param pmParam 검색 조건
     * @return 등록 건수
     * @throws Exception
     */
    public int insertDlwWdrwCallCenter(Map<String, ?> pmParam) throws Exception {
        //CommonUtils.printLog(pmParam);
        return insert("DlwCmsMap.insertDlwWdrwCallCenter", pmParam);
    }

    /**
     * 대명라이프웨이 출금일에의한 - 출금이체의뢰내역 조회
     *
     * @param pmParam 검색 조건
     * @return 출금이체의뢰내역
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwHstr(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwWdrwHstr", pmParam);
    }

    /**
     * 대명라이프웨이 콜센터 청구취소 복원
     *
     * @param pmParam 검색 조건
     * @return 복원 건수
     * @throws Exception
     */
    public int updateAllWdrwCallCenterTemp(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateAllWdrwCallCenterTemp", pmParam);
    }

    /**
     * 대명라이프웨이 청구취소
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int deleteAllWdrwTemp(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteAllWdrwTemp", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 산출 신청전 조회-이체일에 의한( 산출  )
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getCmsMemberByIchaeDt(Map<String, ?> pmParam) throws Exception {

        if(!"2".equals(pmParam.get("sgubun"))) {
             return update("DlwCmsMap.getCmsMemberByIchaeDt", pmParam);
        } else {
             return update("DlwCmsMap.getCmsMember_yencheByIchaeDt", pmParam);
        }

    }


    public int getCmsMemberByimsiIchaeDt(Map<String, ?> pmParam) throws Exception {
             return update("DlwCmsMap.getCmsMemberByimsiIchaeDt", pmParam);
    }



    /**
     * 대명라이프웨이 Card 산출 신청전 조회-이체일에 의한( 산출  )
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getNiceMemberByIchaeDt(Map<String, ?> pmParam) throws Exception {

        if(!"2".equals(pmParam.get("sgubun"))) {

               return update("DlwCmsMap.getNiceMemberByIchaeDt", pmParam);
       } else {

               return update("DlwCmsMap.getNiceMember_yencheByIchaeDt", pmParam);
       }
    }

    /**
     * 산출된 건 중 고객만족센터 미적용등록 여부
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getCallWdrwReqCheck(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getCallWdrwReqCheck", pmParam);
    }

    /**
     *  CMS, 콜센터 메뉴 모두에서 출금신청했을 경우
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCallDupWdrw(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getCallDupWdrw", pmParam);
    }

    /**
     * 대명라이프웨이 카드 출금이체의뢰 내역 신청상태 변경(대량건)
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int updateCmsAppStateLrqnt(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateCmsAppStateLrqnt", pmParam);
    }

    /**
     *  대명라이프웨이 CMS 결과 파일명 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCmsResultFileNm(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getCmsResultFileNm", pmParam);
    }

    /**
     *  대명라이프웨이 EB22 파일 결과처리 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void getCmsAppIsTransEb22(Map<String, ?> pmParam) throws Exception {
       // CommonUtils.printLog(pmParam);
        selectOne("DlwCmsMap.getCmsAppIsTransEb22", pmParam);
    }

    public void immsiinsert(Map<String, ?> pmParam) throws Exception {

        selectOne("DlwCmsMap.immsiinsert", pmParam);
    }

    /**
     *  대명라이프웨이 Cms 결과 파일 정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getReadFileCms(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getReadFileCms", pmParam);
    }

    /**
     * 대명라이프웨이 Eb22 파일에 해당하는 신청정보 건수
     *
     * @param pmParam 검색 조건
     * @return 신청정보 건수
     * @throws Exception
     */
    public int getEB22WdrwCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getEB22WdrwCount", pmParam);
    }


    public int CmsMemberByIchaeCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.CmsMemberByIchaeCount", pmParam);
    }




    /**
     *  대명라이프웨이 cms 결과 업데이트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> cmsResultProc(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.cmsResultProc", pmParam);
    }

    /**
     *  대명라이프웨이 출금일에의한 - 출금이체의뢰내역 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrByInvDt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getWdrwHstrByInvDt", pmParam);
    }

    /**
     *  대명라이프웨이 출금이체내역조회(통계)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrSttc(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getWdrwHstrSttc", pmParam);
    }

    /**
     *  대명라이프웨이 출금이체내역조회(통계)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrStatsSumByInvDt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getWdrwHstrStatsSumByInvDt", pmParam);
    }

    /**
     *  대명라이프웨이 출금이체내역조회(통계 - 합)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWdrwHstrStatsCombByInvDt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getWdrwHstrStatsCombByInvDt", pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결제 신청전으로 상태값 변경
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int updateWdrwApclCancCard(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateWdrwApclCancCard", pmParam);
    }

    /**
     * 대명라이프웨이 NICE 카드 결제 로그 삭제 - 신청전으로 되돌리기 시
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int deleteNiceCardLog(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteNiceCardLog", pmParam);
    }

    /**
     * 대명라이프웨이 Cms청구 신청전으로 상태값 변경
     *
     * @param pmParam 검색 조건
     * @return 변경 건수
     * @throws Exception
     */
    public int updateWdrwApclCancCms(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateWdrwApclCancCms", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 회원 정보 조회
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    //
    public String getCmsMem(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getCmsMem", pmParam);
    }

    /**
     *  파일의 SyncNo 를 통한 구좌 기본정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntMemInfoBySyncNo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getAccntMemInfoBySyncNo", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 신규/해지신청-기관(은행)
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int insertDlwCmsAppByNomal(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertDlwCmsAppByNomal", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 해당Sync_No
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public String getIsRealCmsInfoByBankCancl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getIsRealCmsInfoByBankCancl", pmParam);
    }

    /**
     *  구좌별 CMS 정보조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCMSInfoByAccnt(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getCMSInfoByAccnt", pmParam);
    }

    /**
     * CMS정보 Idn_no 추출
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public String getNcaIdnNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getNcaIdnNo", pmParam);
    }

    /**
     * 대명라이프웨이 청구여부 업데이트 - 산출테이블에서 삭제시
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int updateWdrwReqYn1(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateWdrwReqYn1", pmParam);
    }


    public int updateWdrwReqYn1all(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateWdrwReqYn1all", pmParam);
    }

    /**
     * 대명라이프웨이 CMS EB21 산출내역 삭제
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteWDRWHistory(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteWDRWHistory", pmParam);
    }

    public int deleteWDRWHistoryall(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteWDRWHistoryall", pmParam);
    }

    public int deletecardWDRWHistoryall(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deletecardWDRWHistoryall", pmParam);
    }

    /**
     * 대명라이프웨이 청구여부 업데이트 - 카드 산출테이블에서 삭제시
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int updateWdrwReqYn2(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateWdrwReqYn2", pmParam);
    }

    /**
     * 대명라이프웨이 EB21 산출내역 삭제 (카드)
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCardWdrw(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteCardWdrw", pmParam);
    }

    /**
     *  구좌번호로 계좌정보조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAccntInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getAccntInfo", pmParam);
    }

    /**
     *  입금조회 간소화
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPaySoftInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPaySoftInfo", pmParam);
    }


    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngBugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPayMngBugaInfo", pmParam);
    }

    /**
     *  입금전표조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPayMngList", pmParam);
    }

    /**
     *  입금전표조회-결합상품할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPayMngDtlList", pmParam);
    }

    /**
     *  입금전표조회-결합상품추가부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPayMngDtl1List", pmParam);
    }

    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 할부금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtlBugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPayMngDtlBugaInfo", pmParam);
    }

    /**
     *  입금등록/조회 - 입금/상품/부가정보 조회 - 결합상품 추가금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayMngDtl1BugaInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPayMngDtl1BugaInfo", pmParam);
    }

    /**
     *  환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getRefundList", pmParam);
    }

    /**
     *  할부원금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getRefundDtlList", pmParam);
    }

    /**
     *  추가부담금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRefundDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getRefundDtl1List", pmParam);
    }

    /**
     *  납입건수에 따른 납입금액 계산
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtByPayCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getPayAmtByPayCnt", pmParam);
    }

    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 할부원금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtlByPayCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getPayAmtDtlByPayCnt", pmParam);
    }

    /**
     *  납입건수에 따른 납입금액 계산 - 결합상품 추가 부담금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getPayAmtDtl1ByPayCnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getPayAmtDtl1ByPayCnt", pmParam);
    }

    /**
     *  가수금 조회 건수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getGasuMngCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getGasuMngCount", pmParam);
    }

    /**
     *  가수금 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGasuMngList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getGasuMngList", pmParam);
    }

    /**
     *  고객만족센터 산출 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteWDRWCall(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteWDRWCall", pmParam);
    }

    /**
     *  입금전표조회-총금액
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getPayTotalList", pmParam);
    }

    /**
     *  입금전표조회-상품정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getProductDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getProductDtl", pmParam);
    }

    /**
     * 회원번호로 상품코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String getProdCdByAccntNo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getProdCdByAccntNo", pmParam);
    }

    /**
     *  가수금 환불 내역조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getGasuDetail(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getGasuDetail", pmParam);
    }

    /**
     *  가수 환불 상세 내역 입금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertGasuDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertGasuDtl", pmParam);
    }

    /**
     *  가수 환불 상세 내역 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateGasuDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateGasuDtl", pmParam);
    }

    /**
     *  가수금 환불 상세 내역 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGasuDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.deleteGasuDtl", pmParam);
    }

    /**
     *  가수 입금
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertGasuMng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertGasuMng", pmParam);
    }

    /**
     *  가수 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateGasuMng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.updateGasuMng", pmParam);
    }

    /**
     *  가수 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteGasuMng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.deleteGasuMng", pmParam);
    }

    /**
     * INV_NO 조회
     *
     * @param pmParam 검색 조건
     * @return  INV_NO
     * @throws Exception
     */
    public List<Map<String, Object>>  getInvNo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getInvNo", pmParam);
    }

    /**
     * INV_NO, SEQ 조회
     *
     * @param pmParam 검색 조건
     * @return {p_inv_no, p_seq}
     * @throws Exception
     */
    public List<Map<String, Object>> getInvNoAndSeq(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getInvNoAndSeq", pmParam);
    }


//    사용안함 - TOBE에서는 4개의 메소드로 변경됨
// 		getCardAndMemberBirth, updateHpCall, insertHpCall, insertHpCallHist
//    /**
//     * 대명라이프웨이 CMS 대리납여부, 해피콜 등록
//     *
//     * @param pmParam 검색 조건
//     * @return 대리납 여부
//     * @throws Exception
//     */
//    public int updateDlwCmsRprvPymt(Map<String, ?> pmParam) throws Exception {
//        return update("DlwCmsMap.updateDlwCmsRprvPymt", pmParam);
//    }

    /**
     * 생일, 해피콜 건수 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */


    public List<Map<String, Object>> getCardAndMemberBirth(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getCardAndMemberBirth", pmParam);
    }
    /**
     * 해피콜 변경
     *
     * @param 해피콜 정보
     * @return 변경건수
     * @throws Exception
     */
    public int updateHpCall(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateHpCall", pmParam);
    }

    /**
     * 해피콜 마스터 등록
     *
     * @param 해피콜 정보
     * @return 등록건수
     * @throws Exception
     */
    public int insertHpCall(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertHpCall", pmParam);
    }

    /**
     * 해피콜 이력 등록
     *
     * @param 해필콜 이력
     * @return 등록건수
     * @throws Exception
     */
    public int insertHpCallHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertHpCallHist", pmParam);
    }

    /**
     * ???
     *
     * @param ???
     * @return 등록건수
     * @throws Exception
     */
    public int insertEB22ErrorInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertEB22ErrorInfo", pmParam);
    }

    /**
     * 청구테이블 상태 업데이트 (오류:03 , 성공:04)
     *
     * @param ???
     * @return 등록건수
     * @throws Exception
     */
    public int updateWrdwReqStat(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.updateWrdwReqStat", pmParam);
    }

    /**
     *  대명라이프웨이 콜센터 산출 중복건 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCallDupList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getCallDupList", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 산출 신청전 조회-이체일에 의한(산출)_기초데이터작업(170523)
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getCmsMemberByIchaeDt_Basic(Map<String, ?> pmParam) throws Exception {

        if(!"2".equals(pmParam.get("sgubun"))) {
             return update("DlwCmsMap.getCmsMemberByIchaeDt_Basic", pmParam);
        } else {
             return update("DlwCmsMap.getCmsMember_yencheByIchaeDt_Basic", pmParam);
        }

    }

    /**
     * 대명라이프웨이 CMS 산출 임시건 등록
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getCmsimsiIchaeDt_Basic(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.getCmsimsiIchaeDt_Basic", pmParam);
    }

    /**
     * 대명라이프웨이 산출 기초데이터 CMS 확정
     *
     * @param pmParam 검색 조건
     * @return 취소 건수
     * @throws Exception
     */
    public int getBasicWdrwCms_Fix(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.getBasicWdrwCms_Fix", pmParam);
    }

    /**
     * 대명라이프웨이 출금일에의한 - 출금이체의뢰내역 조회_기초데이터작업(170523)
     *
     * @param pmParam 검색 조건
     * @return 출금이체의뢰내역
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwWdrwHstr_Basic(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwWdrwHstr_Basic", pmParam);
    }

    /**
     * 대명라이프웨이 CMS 당월 출금의뢰 총액 조회
     *
     * @param pmParam 검색 조건
     * @return CMS 당월 출금의뢰 총액
     * @throws Exception
     */
    public String getDlwWdrwReqDtTtamt_Basic(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCmsMap.getDlwWdrwReqDtTtamt_Basic", pmParam);
    }

    /**
     * CMS 기초데이터 수정
     *
     * @param pmParam 검색 조건
     * @return 수정 여부
     * @throws Exception
     */
    public int updateBasicAmt(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateBasicAmt", pmParam);
    }

    /**
     * 대명라이프웨이 청구여부 업데이트 - 산출테이블에서 삭제시
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int updateWdrwReqYn_Basic(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateWdrwReqYn_Basic", pmParam);
    }

    /**
     * 대명라이프웨이 CMS EB21 산출내역 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCmsBasic(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteCmsBasic", pmParam);
    }

    /**
     * 대명라이프웨이 Card EB21 산출내역 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCardBasic(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteCardBasic", pmParam);
    }

    /**
     * 대명라이프웨이 일괄삭제 CMS EB21 산출내역 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCmsAllBasic(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteCmsAllBasic", pmParam);
    }

    /**
     * 대명라이프웨이 일괄삭제  Card EB21 산출내역 삭제비트 변경
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int deleteCardAllBasic(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCmsMap.deleteCardAllBasic", pmParam);
    }

    /**
     * 대명라이프웨이 CMS EB21 산출내역 기초데이터 삭제
     *
     * @param pmParam 검색 조건
     * @return 삭제 여부
     * @throws Exception
     */
    public int deleteCmsWdrwReq_Basic(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.deleteCmsWdrwReq_Basic", pmParam);
    }

    /**
     * 대명라이프웨이 산출 기초테이블에 고객만족센터 데이터 삽입
     *
     * @param pmParam 검색 조건
     * @return 등록 여부
     * @throws Exception
     */
    public int insertCmsWdrwReqByCall_Basic(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.insertCmsWdrwReqByCall_Basic", pmParam);
    }

    /**
     *  환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getdaRefundList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getdaRefundList", pmParam);
    }

    /**
     *  할부원금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getdaRefundDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getdaRefundDtlList", pmParam);
    }

    /**
     *  추가부담금 환불정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getdaRefundDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getdaRefundDtl1List", pmParam);
    }

	public List<Map<String, Object>> getAcquResultDataMst(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getAcquResultDataMst", pmParam);
	}

	public int insertAcquResultDataMst(Map<String, ?> pmParam) {
		return insert("DlwCmsMap.insertAcquResultDataMst", pmParam);
	}

	public int insertAcquResultDataDtl(Map<String, ?> pmParam) {
		return insert("DlwCmsMap.insertAcquResultDataDtl", pmParam);
	}

	public int getAcquResultDataListTotalCnt(Map<String, ?> pmParam) {
		return selectOne("DlwCmsMap.getAcquResultDataListTotalCnt", pmParam);
	}

	public List<Map<String, Object>> getAcquResultDataList(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getAcquResultDataList", pmParam);
	}

	public List<Map<String, Object>> getAcquResultDataListTotalSummary1(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getAcquResultDataListTotalSummary1", pmParam);
	}
	
	public List<Map<String, Object>> getAcquResultDataListTotalSummary2(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getAcquResultDataListTotalSummary2", pmParam);
	}

	public List<Map<String, Object>> getAcquResultDataListExcel(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getAcquResultDataListExcel", pmParam);
	}
	
	public int getNotAuthResultDataCount(Map<String, ?> pmParam) {
		return selectOne("DlwCmsMap.getNotAuthResultDataCount", pmParam);
	}

	public List<Map<String, Object>> getNotAuthResultDataList(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getNotAuthResultDataList", pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataListTotalSummary1(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getNotAuthResultDataListTotalSummary1", pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataCancelList(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getNotAuthResultDataCancelList", pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataCancelListTotalSummary1(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getNotAuthResultDataCancelListTotalSummary1", pmParam);
	}
	
	public List<Map<String, Object>> getNotAuthResultDataListTotalSummary2(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getNotAuthResultDataListTotalSummary2", pmParam);
	}
	
	public int getAcquInicisResultDataListTotalCnt(Map<String, ?> pmParam) {
		return selectOne("DlwCmsMap.getAcquInicisResultDataListTotalCnt", pmParam);
	}

	public List<Map<String, Object>> getAcquInicisResultDataList(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getAcquInicisResultDataList", pmParam);
	}

	public List<Map<String, Object>> getAcquInicisResultDataListTotalSummary1(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getAcquInicisResultDataListTotalSummary1", pmParam);
	}

	public int updatePurchResultAccntNo(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updatePurchResultAccntNo", pmParam);
    }
	
	public int insertPurchResultMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertPurchResultMst", pmParam);
    }
	
	public int getLoanPurchResultDataTotalCnt(Map<String, ?> pmParam) {
		return selectOne("DlwCmsMap.getLoanPurchResultDataTotalCnt", pmParam);
	}

	public List<Map<String, Object>> getLoanPurchResultDataList(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getLoanPurchResultDataList", pmParam);
	}

	public List<Map<String, Object>> getLoanPurchResultDataListTotalSummary(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getLoanPurchResultDataListTotalSummary", pmParam);
	}

	public int updateLoanPurchResultAccntNoRealTID(Map<String, Object> pmParam) {
		return update("DlwCmsMap.updateLoanPurchResultAccntNoRealTID", pmParam);
	}

	public List<Map<String, Object>> getMemberWdrwResultAuthList(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getMemberWdrwResultAuthList", pmParam);
	}

	public List<Map<String, Object>> getMemberWdrwResultCancelList(Map<String, ?> pmParam) {
		return selectList("DlwCmsMap.getMemberWdrwResultCancelList", pmParam);
	}

	public int updateLoanPurchMemberWdrwResultAuth(Map<String, Object> pmParam) {
		return update("DlwCmsMap.updateLoanPurchMemberWdrwResultAuth", pmParam);
	}
	
	public int updateLoanPurchMemberWdrwResultCancel(Map<String, Object> pmParam) {
		return update("DlwCmsMap.updateLoanPurchMemberWdrwResultCancel", pmParam);
	}
	
    /**
     * CMSEB11데이터조회
     *
     * @param pmParam 검색 조건
     * @return  CMS EB11
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsEb11List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCmsMap.getDlwCmsEb11List", pmParam);
    }
    
    /**
     *  EB11정보 등록 및 CMS결제정보 수정
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public int insertDlwCmsDataInsert(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertDlwCmsDataInsert", pmParam);
    }
    
    /**
     *  EB11 CMS결제정보이력저장
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public int insertDlwCmsHistoryInsert(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCmsMap.insertDlwCmsHistoryInsert", pmParam);
    }
    
    /**
     *  EB11 CMS결제정보변경
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public int updateDlwCmsInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwCmsMap.updateDlwCmsInfo", pmParam);
    }
}