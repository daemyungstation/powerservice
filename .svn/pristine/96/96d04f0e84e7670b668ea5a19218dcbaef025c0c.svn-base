package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwSlipService {

    public List<Map<String, Object>> selectSlipResnList(Map<String, Object> pmParam) throws Exception; //해약전표 조회
    
    public List<Map<String, Object>> selectSlipResnCloseList(Map<String, Object> pmParam) throws Exception; //해약전표 조회
    
    public List<Map<String, Object>> selectSlipResnDetailList(Map<String, Object> pmParam) throws Exception; //해약전표 조회
    
    public List<Map<String, Object>> selectSlipResnDetailCloseList(Map<String, Object> pmParam) throws Exception; //해약전표 조회
    
    public int updateResnSlipClose(Map<String, ?> pmParam) throws Exception;
    
    public int updateResnDetail(Map<String, ?> pmParam) throws Exception;
    
    public int getSlipResnCloseChk(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> selectSlipCardList(Map<String, Object> pmParam) throws Exception; //카드전표 조회
    
    public int getSlipCardCloseChk(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> selectSlipCardDetailList(Map<String, Object> pmParam) throws Exception; //카드전표 조회
    
    public int SlipCardDetailCloseCnt(Map<String, ?> pmParam) throws Exception;
    
    public int SlipCardDetailCnt(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> selectSlipCardDetailCloseList(Map<String, Object> pmParam) throws Exception; //카드전표 조회
    
    public List<Map<String, Object>> selectSlipCardCancelList(Map<String, Object> pmParam) throws Exception; //카드전표 조회

    public int updateCardDetail(Map<String, ?> pmParam) throws Exception;
    
    public int updateCardSlipClose(Map<String, ?> pmParam) throws Exception;
}