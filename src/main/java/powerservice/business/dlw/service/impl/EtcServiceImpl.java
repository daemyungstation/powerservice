
package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tobesoft.xplatform.data.DataSet;

import powerservice.business.dlw.service.EtcService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 기타관리
 *
 * @author 정출연
 * @version 1.0
 * @date 2016.12.27
 */
@Service
public class EtcServiceImpl extends EgovAbstractServiceImpl implements EtcService {

	private final Logger log = LoggerFactory.getLogger(EtcServiceImpl.class);
	
    @Resource
    public EtcDAO etcDAO;
    
    /**
     * 카드 수료율 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectCardFeeRtMngList(Map<String, Object> pmParam) throws Exception {
    	return etcDAO.selectCardFeeRtMngList(pmParam);
    }
    
    /**
     * 부서별 IP 정보 목록 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectDeptIPInfoList(Map<String, Object> pmParam) throws Exception {
    	return etcDAO.selectDeptIPInfoList(pmParam);
    }
    
    /**
     * 부서별 IP 정보 목록 삭제
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteDeptIPInfoList(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {
            	for (int i=0; i<listInDs.size(); ++i) {            		
            		mRow = listInDs.get(i);
            		chk = CommonUtils.nvls((String)mRow.get("chk"));
            		if ("1".equals(chk)) {
            			ParamUtils.addSaveParam(mRow);
            			etcDAO.deleteDeptIPInfoList(mRow);
            		}
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 카드 수수료율 등록/수정
     * - asis - mergeCardFeeRt
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int saveCardFeeRt(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        int rowType;
        String seq = "";
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
            	mRow = listInDs.get(0);            	
            	
            	rowType = ((Integer) mRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            	seq = CommonUtils.nvls((String)mRow.get("seq")).trim();
            	ParamUtils.addSaveParam(mRow);
            	
            	if (rowType == DataSet.ROW_TYPE_INSERTED) {
            		iCudCnt += etcDAO.insertCardFeeRt(mRow);
            	}
            	else if (rowType == DataSet.ROW_TYPE_UPDATED) {
            		iCudCnt += etcDAO.updateCardFeeRt(mRow);
            	}
            	else if (rowType == DataSet.ROW_TYPE_DELETED) {
            		//
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    

    /**
     * 부서별 IP 정보 목록 저장
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int saveDeptIPInfoList(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        int rowType = -1;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            String chk = "";
            if (listInDs != null) {
            	for (int i=0; i<listInDs.size(); ++i) {            		
            		
            		mRow = listInDs.get(i);
            		
            		rowType = ((Integer) mRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		ParamUtils.addSaveParam(mRow);
            		
            		if (rowType == DataSet.ROW_TYPE_INSERTED) {
            			etcDAO.insertDeptIPInfoList(mRow);
            		} 
            		else if (rowType == DataSet.ROW_TYPE_UPDATED) {
            			etcDAO.updateDeptIPInfoList(mRow);
            		} 
            		else if (rowType == DataSet.ROW_TYPE_DELETED) {
            			etcDAO.deleteDeptIPInfoList(mRow);
            		}
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /** ================================================================
     * 날짜 : 20190725
     * 이름 : 송준빈
     * 추가내용 : 상담대상자관리(NEW) 조회수
     * 대상 테이블 : PS_WILLVI.TB_COUNSEL_TARGET
     * ================================================================
     * */
	public int getCounselTargetCount(Map<String, ?> pmParam) throws Exception {
		return etcDAO.getCounselTargetCount(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190725
     * 이름 : 송준빈
     * 추가내용 : 상담대상자관리(NEW) 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_COUNSEL_TARGET
     * ================================================================
     * */
	public List<Map<String, Object>> getCounselTargetList(Map<String, ?> pmParam) throws Exception {
		return etcDAO.getCounselTargetList(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20200617
     * 이름 : 김주용
     * 추가내용 : 다이렉트상담관리(NEW) 조회수
     * 대상 테이블 : 
     * ================================================================
     * */
	public int getDirectCounselCount(Map<String, ?> pmParam) throws Exception {
		return etcDAO.getDirectCounselCount(pmParam);
	}

	/** ================================================================
     * 날짜 : 20200617
     * 이름 : 김주용
     * 추가내용 : 다이렉트상담관리(NEW) 리스트 조회
     * 대상 테이블 : 
     * ================================================================
     * */
	public List<Map<String, Object>> getDirectCounselList(Map<String, ?> pmParam) throws Exception {
		return etcDAO.getDirectCounselList(pmParam);
	}
	
	/**
     * 다이렉트 DB링크 세션 종료
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateDirectSessionClose(Map<String, ?> pmParam) throws Exception {
        return etcDAO.updateDirectSessionClose(pmParam);
    }
}
