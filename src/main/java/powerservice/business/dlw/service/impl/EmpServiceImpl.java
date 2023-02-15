
package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.EmpService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 기타관리
 *
 * @author 정출연
 * @version 1.0
 * @date 2016.12.27
 */
@Service
public class EmpServiceImpl extends EgovAbstractServiceImpl implements EmpService {

	private final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);
	
    @Resource
    public EmpDAO empDAO;
    
    /**
     * 사원 등록/수정
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeEmployee(XPlatformMapDTO xpDto, Map<String, Object> mResult) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        String seq = "";
        String sTmp = "";
        String empleNo = "";
        String confNo = "";
        String idnNo = "";
        String appDt = "";
        String deptCd = "";
        String oldDeptCd = "";
        boolean isNew = false;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) {
            	
            	mRow = listInDs.get(0);
            	
            	isNew = false;
            	
            	sTmp = empDAO.selectGrpEmpNoDupChk(mRow);
            	if ("dup".equals(sTmp)) {
            		return -1;
            	}
            	
            	empleNo = CommonUtils.nvls((String)mRow.get("emple_no"));
            	
            	if ("".equals(empleNo)) {
            		empleNo = empDAO.selectCreateEmpNo(mRow);
            		mRow.put("emple_no", empleNo);
            		isNew = true;
            	}
            	
            	mResult.put("emple_no", empleNo);
            	
            	if (isNew) {
            		
            		// confNo = CommonUtils.SHA256(AesCrypto.decrypt(confNo, Constants.aesKey));
            		// mRow.put("conf_no"		, confNo);
            		
            		idnNo = CommonUtils.SHA256((String)mRow.get("idn_no"));
            		
            		// mRow.put("intra_passwd"	, idnNo);
            	}
            	
            	deptCd		= CommonUtils.nvls((String)mRow.get("dept_cd"));
            	oldDeptCd	= CommonUtils.nvls((String)mRow.get("old_dept_cd"));
            	appDt 		= CommonUtils.nvls((String)mRow.get("app_dt"));
            	
            	ParamUtils.addSaveParam(mRow);
            	
            	if (!deptCd.equals(oldDeptCd)) {
            		if ("".equals(appDt)) {
            			appDt = CommonUtils.nvls((String)mRow.get("ent_day"));
            			appDt = appDt.substring(0,6);
            			mRow.put("app_dt", appDt);
            		}
            		empDAO.insertDeptTransHist(mRow);
            	}
            	
            	if (isNew) {
            		iCudCnt += empDAO.insertEmployee(mRow);
            	} else {
            		iCudCnt += empDAO.updateEmployee(mRow);
            	}	
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 카드 수료율 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public String createDataAuthQry(Map<String, Object> pmParam) throws Exception {
    	return empDAO.createDataAuthQry(pmParam);
    }
    
    /**
     * 사원 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectEmpleList(Map<String, Object> pmParam) throws Exception {
    	return empDAO.selectEmpleList(pmParam);
    }
    
    /**
     * selectEmpleMemChangeList
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectEmpleMemChangeList(Map<String, Object> pmParam) throws Exception {
    	return empDAO.selectEmpleMemChangeList(pmParam);
    }
    
    /**
     * 사원퇴사 취소
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int resinCancel(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) {
            	mRow = listInDs.get(0);
            	iCudCnt += empDAO.resinCancel(mRow);
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 담당사원 변경
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */    
    public int insertMstrChgInf(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        
        String sKey = "";
        String[] sArr = null;
		String cnslSeq = "";
		String seq = "";
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap ds_cond 	= (DataSetMap)mapInDs.get("ds_input2");
            
            mRow = ds_cond.get(0);
            String oldEmpleNo 	= CommonUtils.nvls((String)mRow.get("old_emple_no"));
            String newEmpleNo 	= CommonUtils.nvls((String)mRow.get("new_emple_no"));
            String chgDay 		= CommonUtils.nvls((String)mRow.get("chg_day"));
            
            String cnslMngCon = CommonUtils.nvls((String)mRow.get("cnsl_mng_con"));
            String cnslDtlCon = CommonUtils.nvls((String)mRow.get("cnsl_dtl_con"));
            
            log.debug("oldEmpleNo : " + oldEmpleNo);
            log.debug("newEmpleNo : " + newEmpleNo);
            log.debug("chgDay : " + chgDay);            
            
            if ("".equals(oldEmpleNo) || "".equals(newEmpleNo)) {
            	throw new EgovBizException("기존담당자와 변경후 담당자 정보가 없습니다.");
            }
            
            if (listInDs != null && listInDs.size() > 0) {
            	
            	// 담당사원 변경
            	for (int i=0; i<listInDs.size(); ++i) {
            		mRow = listInDs.get(i);
            		
            		String chk = CommonUtils.nvls((String)mRow.get("chk"));
            		if (!"1".equals(chk)) {
            			continue;
            		}
            		
            		ParamUtils.addSaveParam(mRow);
            		mRow.put("work_mstr", (String)mRow.get("rgsr_id"));
            		mRow.put("cl", "3");
            		
            		mRow.put("old_emple_no", oldEmpleNo);
            		mRow.put("new_emple_no", newEmpleNo);
            		mRow.put("chg_day", chgDay);
            		
                    CommonUtils.printLog(mRow);
                    
                    iCudCnt += empDAO.insertMstrChgInf(mRow);
                    iCudCnt += empDAO.updateMemProdAccntEmpleChange(mRow);
            	}
            	
            	// 상담내역 저장
            	for (int i=0; i<listInDs.size(); ++i) {
            		mRow = listInDs.get(i);
            		
            		String chk = CommonUtils.nvls((String)mRow.get("chk"));
            		if (!"1".equals(chk)) {
            			continue;
            		}
            		
            		ParamUtils.addSaveParam(mRow);
            		
            		mRow.put("cnsl_mng_con", cnslMngCon);
            		mRow.put("cnsl_dtl_con", cnslDtlCon);            		
            		mRow.put("gubn", "80");
            		mRow.put("cnsl_cd", "01");
            		
            		iCudCnt += empDAO.insertConsulMng(mRow);
            		
            		sKey 		= CommonUtils.nvls((String)mRow.get("skey"));
            		sArr 		= sKey.split("\\|");            		
            		seq 		= sArr[0];
                    cnslSeq 	= sArr[1];
                    
                    log.debug(">sKey : " + sKey);
                    
                    mRow.put("seq", seq);
                    mRow.put("cnsl_seq", cnslSeq);
                    
                    CommonUtils.printLog(mRow);
                    
                    iCudCnt += empDAO.insertConsulDetail(mRow);
            	}
            	
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    

    /**
     * 퇴사작업
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */    
    public int updateEmployeeResin(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        String empleNo = "";
        Map<String, Object> mParam = new HashMap<>();
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) {
            	
            	mRow = listInDs.get(0);
            	empleNo = CommonUtils.nvls((String)mRow.get("emple_no"));
            	
        		ParamUtils.addSaveParam(mRow);
        		
        		if (!"".equals(empleNo)) {
        			mParam = new HashMap<>();
        			mParam.put("old_emple_no", mRow.get("bef_emple_no"));
        			mParam.put("new_emple_no", mRow.get("emple_no"));
        			mParam.put("chg_day", mRow.get("resin_day"));
        			mParam.put("work_mstr", mRow.get("rgsr_id"));
        			mParam.put("cl", mRow.get("1"));        			
        			mParam.put("rgsr_id", mRow.get("rgsr_id"));  
        			
        			empDAO.insertMstrChgInfResin(mRow);
        		}

        		mParam = new HashMap<>();
        		mParam.put("emple_no"		, mRow.get("emple_no"));
        		mParam.put("bef_emple_cd"	, mRow.get("bef_emple_no"));
        		mParam.put("resin_day"		, mRow.get("resin_day"));
        		mParam.put("alow_yn"		, mRow.get("alow_yn"));
        		mParam.put("rgsr_id", mRow.get("rgsr_id"));        		
        		// 퇴사처리
        		iCudCnt += empDAO.updateEmployeeResin(mParam);
        		
        		if (!"".equals(empleNo)) {
        			// asis : updateMemProdAccntEmple, 메소드명 중복때문에 2로 변경
        			empDAO.updateMemProdAccntEmple2(mParam);
        		}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    
    /**
     * 직위/직책 변경
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */    
    public int insertHistPosnEmp(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        String grpCd = "";
        Map<String, Object> mParam = new HashMap<>();
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) {
            	
            	mRow = listInDs.get(0);
            	grpCd = CommonUtils.nvls((String)mRow.get("grp_cd"));
            	
        		ParamUtils.addSaveParam(mRow);
        		
        		mParam = new HashMap<>();
        		mParam.put("emple_no"		, mRow.get("emple_no"));
        		mParam.put("grp_cd"			, mRow.get("grp_cd"));
        		mParam.put("new_posn_cd"	, mRow.get("new_posn_cd"));
        		mParam.put("app_dt"			, mRow.get("app_dt"));
        		mParam.put("rgsr_id"		, mRow.get("rgsr_id"));
        		
        		if ("06".equals(grpCd)) {
    				iCudCnt += empDAO.updatePosnCd(mRow);
    			} else {
    				iCudCnt += empDAO.updateJobdutyCd(mRow);
    			}
    			
    			if (iCudCnt > 0) {
    				empDAO.insertHistPosnEmp(mRow);
    			}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    
    
    
    
    
    
    
    
    /* 부가서비스 ------------------------------------------------- */
    
    /**
     * selectDiscntPassMstlist
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectDiscntPassMstlist(Map<String, Object> pmParam) throws Exception {
    	return empDAO.selectDiscntPassMstlist(pmParam);
    }
    
    /**
     * 생성된 난수 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectRandNumList(Map<String, Object> pmParam) throws Exception {
    	return empDAO.selectRandNumList(pmParam);
    }

    /**
     * 할인우대권 번호 비고 변경
     * 
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int noteSave(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
    	
        int iCudCnt = 0;
        Map mRow = null;
        String seq = "";
        int rowType;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input2");
            
            if (ds_cond != null && ds_cond.size() > 0) {
            	Map mTmp = ds_cond.get(0);
            	seq = CommonUtils.nvls((String)mTmp.get("seq"));
            }
            
            if (listInDs != null && listInDs.size() > 0) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		
            		mRow = listInDs.get(0);
            		
            		rowType = ((Integer) mRow.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		if (DataSet.ROW_TYPE_UPDATED == rowType) {
            			mRow.put("seq", seq);
            			iCudCnt += empDAO.noteSave(mRow);
            		}
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	
    	return iCudCnt;
    }
    
    
    /**
     * 회원 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectMemberList(Map<String, Object> pmParam) throws Exception {
    	return empDAO.selectMemberList(pmParam);
    }
    
    /**
     * 회원 조회
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectMemberListByEmpleNo(Map<String, Object> pmParam) throws Exception {
    	return empDAO.selectMemberListByEmpleNo(pmParam);
    }
    
    
}
