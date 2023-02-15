package powerservice.business.web.service;

import java.util.List;
import java.util.Map;

public interface WebShoppingService {
    /* 여기서부터 쇼핑몰 연동 부분입니다. 옮길때 여기부터 합니다. START */

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

    /* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20171222
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금정보 전체 조회
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberBasicAllAmtInfomation(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20171227
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금정보 건수 조회
     * ================================================================
     * */
    public int getTotalCount(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220418
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰집계
     * ================================================================
     * */
    public List<Map<String, Object>> getReadyCashTotalList(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220418
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰집계
     * ================================================================
     * */
    public List<Map<String, Object>> getReadyCashList(Map<String, ?> pmParam) throws Exception;
    /* 여기서부터 쇼핑몰 연동 부분입니다. 옮길때 여기부터 합니다. END */
}