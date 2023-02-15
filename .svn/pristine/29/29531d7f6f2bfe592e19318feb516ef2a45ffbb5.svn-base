package powerservice.business.mall.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface DlwMallLinkedService {
	
	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 조회
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberBasicList(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 사용/취소금액 조회
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberBasicListUseAmt(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
    public int updateLFResnMemberState(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 적립금 전송
     * ================================================================
     * */
    public int insertMemberBasicAmt(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 행사여부 확인
     * ================================================================
     * */
    public List<Map<String, Object>> getResnMemberStateEvent(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 해약여부 확인
     * ================================================================
     * */
    public List<Map<String, Object>> getResnMemberStateRescission(Map<String, ?> pmParam) throws Exception;
    
    public String saveOptSvcList(DataSetMap srchInDs) throws Exception;
    
    public int getResnGubn(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 사용/취소금액 조회
     * ================================================================
     * */
    public  int getMallUseAmt(String sAccnt_no) throws Exception;
    
    /* ================================================================
     * 날짜 : 20200128
     * 이름 : 임동진
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수, 해약완료, 유효 로 변경
     * ================================================================
     * */
    public int updateResnMallState(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getMemberBasicYn(Map<String, ?> pmParam) throws Exception;
}
