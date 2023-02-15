package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DlwAlowProportCalcService {
	
	/** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 회원별수당안분 조회수
     * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
     * ================================================================
     * */
	public int getAlowProportAccntCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 회원별수당안분 조회리스트
     * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowProportAccntList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 상품별수당안분 조회수
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public int getAlowProportProdCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 상품별수당안분 조회리스트
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowProportProdList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 수당안분계산대상 고객업로드
     * 대상 테이블 : LF_DMUSER.TMP_PROPORT_CALC
     * ================================================================
     * */
    public void alowAccntExcelUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception;
}
