package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwCouponInfoService {

    public List<Map<String, Object>> selectCouponInfoList(Map<String, Object> pmParam) throws Exception; //리스트 정보를 검색한다.

    public List<Map<String, Object>> selectCouponStatList(Map<String, Object> pmParam) throws Exception; //쿠폰현황 검색한다.

    public List<Map<String, Object>> selectCouponHist(Map<String, Object> pmParam) throws Exception;	//쿠폰정보 이력을 검색한다.

    public Map<String, Object> selectCouponDetail(Map<String, Object> pmParam) throws Exception; //쿠폰상세 조회

    public Map<String, Object> selectCouponTpSearch(Map<String, Object> pmParam) throws Exception;	//쿠폰종류 별 조회

    public void saveCouponInfoMst(Map<String, Object> pmParam) throws Exception;	//쿠폰정보 MST 등록/수정

    public void saveCouponInfoDtl(Map<String, Object> pmParam) throws Exception;	//쿠폰정보 DTL 등록/수정

    public void deleteCouponInfoMst(DataSetMap srchInDs) throws Exception;	//쿠폰정보 삭제

    public String selectDupCouponNo(Map<String, Object> pmParam) throws Exception;	//쿠폰번호 중복체크

    public void updateCouponInfoDtlUseYn(Map<String, Object> pmParam) throws Exception;	//쿠폰 DTL 사용유무 update

    public void updateCouponInfoMstUseYn(Map<String, Object> pmParam) throws Exception;	//쿠폰 MST 사용유무 update

    public void deleteCouponInfoDtl(Map<String, Object> pmParam) throws Exception;	//쿠폰정보 DTL 삭제

    public String selectKeyCouponDtl(Map<String, Object> pmParam) throws Exception;	//쿠폰정보 DTL 키 select

}