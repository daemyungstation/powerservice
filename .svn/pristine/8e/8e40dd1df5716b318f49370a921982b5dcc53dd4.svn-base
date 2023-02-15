package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwNewTypeConsDAO extends EgovAbstractMapper {
	
	/** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 대명라이프웨이 DB SqlSession을 설정한다.
     * 파라미터 : sqlSession 대명라이프웨이 DB SqlSession
     * ================================================================
     * */
	/*
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    */
    
	/** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 권한 쿼리 구분
     * 대상 테이블 : LF_DMUSER.DATA_AUTH_GRP_DTL
     * ================================================================
     * */
    public String getDataAthrQury(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeConsMap.getDataAthrQury", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 상담이력 탭 조회수
     * 대상 테이블 : PS_WILLVI.TB_CONS
     * ================================================================
     * */
    public int getConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeConsMap.getConsCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 상담이력 탭 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_CONS
     * ================================================================
     * */
    public List<Map<String, Object>> getConsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getConsList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 상담이력 팝업 조회수
     * 대상 테이블 : PS_WILLVI.TB_CONS
     * ================================================================
     * */
    public int getNewTypeConsPopCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeConsMap.getNewTypeConsPopCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 상담이력 팝업 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_CONS
     * ================================================================
     * */
    public List<Map<String, Object>> getNewTypeConsPopList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getNewTypeConsPopList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 캠페인 이력 조회수
     * 대상 테이블 : PS_WILLVI.TB_TRGT_CUST_DTPT, PS_WILLVI.TB_DPMS_RESL_HSTR
     * ================================================================
     * */
    public int getIbDpmsReslHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeConsMap.getIbDpmsReslHstrCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 캠페인 이력 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_TRGT_CUST_DTPT, PS_WILLVI.TB_DPMS_RESL_HSTR
     * ================================================================
     * */
    public List<Map<String, Object>> getIbDpmsReslHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getIbDpmsReslHstrList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 VOC 이력 조회수
     * 대상 테이블 : PS.WILLVI.TB_VOC_DTL
     * ================================================================
     * */
    public int getVocDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeConsMap.getVocDtlCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 VOC 이력 리스트 조회
     * 대상 테이블 : PS.WILLVI.TB_VOC_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getVocDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getVocDtlList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 SMS 이력 조회수
     * 대상 테이블 : PS.WILLVI.TB_CHAT_SNDG_HSTR
     * ================================================================
     * */
    public int getChatSndgHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeConsMap.getChatSndgHstrCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 SMS 이력 리스트 조회
     * 대상 테이블 : PS.WILLVI.TB_CHAT_SNDG_HSTR
     * ================================================================
     * */
    public List<Map<String, Object>> getChatSndgHstrList(Map<String, ?> param) throws Exception {
        return selectList("DlwNewTypeConsMap.getChatSndgHstrList", param);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 Encom 이력 조회수
     * 대상 테이블 : LF_DMUSER.CNSL_MNG, LF_DMUSER.MEM_PROD_ACCNT
     * ================================================================
     * */
    public int getDlwConsCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("DlwNewTypeConsMap.getDlwConsCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 Encom 이력 리스트 조회
     * 대상 테이블 : LF_DMUSER.CNSL_MNG, LF_DMUSER.MEM_PROD_ACCNT
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwConsList(Map<String, ?> pmParam) {
        return selectList("DlwNewTypeConsMap.getDlwConsList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 홈페이지 이력 조회수
     * 대상 테이블 : LF_DMUSER.MB_MEM_CHNG_PTC@DMWEB, LF_DMUSER.MB_MEM_MST@DMWEB
     * ================================================================
     * */
    public int getDlwlifeweyCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("DlwNewTypeConsMap.getDlwlifeweyCount", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 홈페이지 이력 리스트 조회
     * 대상 테이블 : LF_DMUSER.MB_MEM_CHNG_PTC@DMWEB, LF_DMUSER.MB_MEM_MST@DMWEB
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwlifeweyList(Map<String, ?> pmParam) {
        return selectList("DlwNewTypeConsMap.getDlwlifeweyList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 아웃바운드정보 탭
     * 대상 테이블 : PS_WILLVI.TB_TRGT_CUST_DTPT, PS_WILLVI.TB_DPMS_RESL_HSTR
     * ================================================================
     * */
    public List<Map<String, Object>> getNewTypeTrgtCustDtptList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getNewTypeTrgtCustDtptList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 해약정보 탭 (해약정보_상품정보 및 가입일자에 따른 SEQ, 적용일자를 검색한다.)
     * 대상 테이블 : LF_DMUSER.RESNSTD_M
     * ================================================================
     * */
    public Map<String, Object> getDlwResnAmtList(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeConsMap.getDlwResnAmtList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 해약정보 탭 (대명라이프웨이 해약환급금 D 목록을 검색한다.)
     * 대상 테이블 : LF_DMUSER.RESNSTD_D
     * ================================================================
     * */
    public List<Map<String, Object>> getResnAmtDetailList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getResnAmtDetailList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 해약정보 탭 (납입이력(상조부금) 정보를 검색한다.)
     * 대상 테이블 : LF_DMUSER.PAY_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwPayMngList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getDlwPayMngList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 납입이력 탭
     * 대상 테이블 : LF_DMUSER.PAY_MNG, LF_DMUSER.PAY_MNG_DTL, LF_DMUSER.PAY_MNG_DTL1
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwPayMngAllList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getDlwPayMngAllList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 결제정보 탭
     * 대상 테이블 : LF_DMUSER.NEW_CANCL_APP, LF_DMUSER.CARD_NEW_CANCL_APP
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwPymnHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getDlwPymnHstrList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190911
     * 이름 : 송준빈
     * 추가내용 : 회원 기본정보 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwMemProdAccntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getDlwMemProdAccntList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 과거청구이력 탭
     * 대상 테이블 : LF_DMUSER.WDRW_REQ, LF_DMUSER.CARD_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwAskHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeConsMap.getDlwAskHstrList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 회원고객정보 탭 (청구이력)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getMainWdrwLogList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getMainWdrwLogList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 LGUPLUS 선납입정보 탭
     * 대상 테이블 : LF_DMUSER.TB_UPLUS_PREPAY_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getNewTypeLgUplusPrepayList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getNewTypeLgUplusPrepayList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 입금전체현황 탭
     * 대상 테이블 : LF_DMUSER.PAY_TOT_INFO_VIEW
     * ================================================================
     * */
    public List<Map<String, Object>> getNewTypePayTotInfoList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getNewTypePayTotInfoList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190918
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 납입액
     * 대상 테이블 : LF_DMUSER.PRODUCT_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getNewTypeProductDtl(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getNewTypeProductDtl", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190918
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 납입액(NXX상품군)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public List<Map<String, Object>> getNewTypeProductDtlNxx(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getNewTypeProductDtlNxx", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190719
     * 이름 : 송준빈
     * 추가내용 : 고객 부가서비스정보 탭
     * 대상 테이블 :  LF_DMUSER.PROD_OPT_SVC_MST, LF_DMUSER.PROD_OPT_SVC_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getNewTypeProdOptSvcList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getNewTypeProdOptSvcList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200324
     * 이름 : 김주용
     * 추가내용 : 회원고객정보 탭 (청구이력)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_EXCEPTION
     * ================================================================
     * */
    public List<Map<String, Object>> getMainWdrwExceList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeConsMap.getMainWdrwExceList", pmParam);
    }
}