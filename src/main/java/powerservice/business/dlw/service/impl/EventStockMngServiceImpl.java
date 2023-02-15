package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.EventStockMngService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 행사 모니터링 정보 관리
 *
 * @author 정출연
 * @date 2016/11/01
 * @프로그램ID EventMonitor
 */
@Service
public class EventStockMngServiceImpl extends EgovAbstractServiceImpl implements EventStockMngService {
	
	private final Logger log = LoggerFactory.getLogger(EventStockMngServiceImpl.class);

    @Resource
    public EventStockMngDao EventStockMngDao;
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return 모니터링 질문지
     * @throws Exception
     */
    public List<Map<String, Object>> selectOrderMngList(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectOrderMngList(pmParam);
    }
    
    /**
     * 입고취소 - 부분입고취소
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteWhInCncl(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose = 0;
        Map hmParam = null;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		if (null != hmParam.get("in_wh_dt")) {            			
            			chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("in_wh_dt"));            			
            			if (chkStockClose > 0) {
            				return -1;
            			}
            		}
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		ParamUtils.addSaveParam(hmParam);
            		
            		List<Map<String, Object>> whInNoArr = EventStockMngDao.selectSchWhInNo(hmParam);
            		
            		EventStockMngDao.deleteWhInDtl(hmParam);
            		
            		int whInDtlCnt = EventStockMngDao.selectWhInDtlCnt(hmParam);
            		if ((whInDtlCnt == 0) && (whInNoArr.size() > 0)) {
            			for (int k = 0; k < whInNoArr.size(); k++) {
            				Map whInNoHm = (Map)whInNoArr.get(k);

            				whInDtlCnt = EventStockMngDao.selectWhInDtlCntByWhInNo(whInNoHm);
            				if (whInDtlCnt == 0) {
            					ParamUtils.addSaveParam(whInNoHm);
            					EventStockMngDao.deleteWhInMst(whInNoHm);
            				}
            			}
            		}
            		
            		hmParam = listInDs.get(0);
            		EventStockMngDao.updateOrdMstProcStat(hmParam);
            		
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectOrdDtlInfo(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectOrdDtlInfo(pmParam);
    }
    
    /**
     * ???
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeOrderMng(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        
        String ordNo = "";
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap lstDetail = (DataSetMap)mapInDs.get("ds_input2");
            
            log.debug("listInDs.size() : " + listInDs.size());
            log.debug("lstDetail.size() : " + lstDetail.size());
            
            if (listInDs == null || listInDs.size() < 1) {
            	return iCudCnt;
            }
            
            Map hmParam = (Map)listInDs.get(0);
            CommonUtils.printLog(hmParam);
    		ParamUtils.addSaveParam(hmParam);
    		
    		int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
    		log.debug("rowType : " + rowType);
    		
    		ordNo = CommonUtils.nvls((String)hmParam.get("ord_no"));
    		
    		if ("".equals(ordNo))
    		{
    			ordNo = EventStockMngDao.selectNewOrdNo(hmParam);
    			ordNo = ordNo.replaceAll("/", "");
    			hmParam.put("ord_no", ordNo);    			
    			EventStockMngDao.insertOrdMst(hmParam);
    		} 
    		else 
    		{
    			EventStockMngDao.updateOrdMst(hmParam);            			
    			EventStockMngDao.deleteOrdDtl(hmParam);
    		}
    		
    		for (int j = 0; j < lstDetail.size(); j++) {
    			Map mDetail = (Map)lstDetail.get(j);
    			mDetail.put("ord_no", ordNo);
				ParamUtils.addSaveParam(mDetail);
				EventStockMngDao.insertOrdDtl(mDetail);
			}
    		
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * ???
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteOrdMst(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
            	Map hmParam = listInDs.get(0);
        		ParamUtils.addSaveParam(hmParam);
        		iCudCnt += EventStockMngDao.deleteOrdMst(hmParam);
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * ???
     * 
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectGoodsComList(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectGoodsComList(pmParam);
    }
    
    /**
     * 발주서 입고처리
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int insertWhInMst(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        Map hmParam = null;
        
        UserSession sess = (UserSession) SessionUtils.getLoginUser();
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap ds_cond = (DataSetMap)mapInDs.get("ds_input2");
            Map mCond = ds_cond.get(0);
            
            String sMode			= CommonUtils.nvls((String)mCond.get("mode"));
            String sInDt 			= CommonUtils.nvls((String)mCond.get("in_dt"));
            String sInCl 			= CommonUtils.nvls((String)mCond.get("in_cl"));
            String sRptNo 			= CommonUtils.nvls((String)mCond.get("rpt_no"));
            String sPartWarehousing = CommonUtils.nvls((String)mCond.get("part_warehousing"));
            String sEmpleNo 		= sess.getEmployeeid();
            
            String whInNo 	= "";
            int iQtyOfMst 	= 0;
            if (null != mCond.get("qty")) {
            	iQtyOfMst = Integer.parseInt(CommonUtils.nvls((String)mCond.get("qty")));
            }           
            
            if ( listInDs != null && listInDs.size() > 0 ) {
            	int chkStockClose = 0;
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("in_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
            	
            	if ("mst".equals(sMode)) {
                	
                	for (int i=0; i<listInDs.size(); ++i) {
                		hmParam = listInDs.get(i);
                		ParamUtils.addSaveParam(hmParam);
                		
                		hmParam.put("in_dt", sInDt);
                		whInNo = EventStockMngDao.selectNewWhInNo();
                		
                		hmParam.put("wh_in_no", whInNo);
                		hmParam.put("emple_no", sEmpleNo);
                		hmParam.put("in_cl", sInCl);
                		if ( !"".equals(sRptNo) ) {
                			hmParam.put("rpt_no", sRptNo);
                		}
                		
                		iCudCnt += EventStockMngDao.insertWhInMst(hmParam);
                		
                		List lstDetail = EventStockMngDao.selectOrdDtlInfo(hmParam);
                		if (null != lstDetail && lstDetail.size() > 0) {
                			for (int j=0; j<lstDetail.size(); j++) {
                				Map mDtl = (Map)lstDetail.get(j);
                				mDtl.put("wh_in_no", whInNo);
                				ParamUtils.addSaveParam(mDtl);
                				
                				int qty = Integer.parseInt(CommonUtils.stringValueOf(mDtl.get("qty")));
                				int inWhQty = 0;
                				if (null != mDtl.get("in_wh_qty")) {
                					inWhQty = Integer.parseInt(CommonUtils.stringValueOf(mDtl.get("in_wh_qty")));
                				}
                				
                				if (inWhQty > 0) {
                					mDtl.put("qty", String.valueOf(inWhQty));
                				}
                				if (qty > inWhQty) {
                					iCudCnt += EventStockMngDao.insertWhInDtl(mDtl);
                				}
                			}
                		}
                	}
                }
            	
            	if ( "newAll".equals(sMode) || "all".equals(sMode) ) {
                	
            		hmParam = listInDs.get(0);
            		ParamUtils.addSaveParam(hmParam);
            		
            		hmParam.put("in_dt", sInDt);
            		whInNo = EventStockMngDao.selectNewWhInNo();
            		
            		hmParam.put("wh_in_no", whInNo);
            		hmParam.put("emple_no", sEmpleNo);
            		hmParam.put("in_cl", sInCl);
            		if ( !"".equals(sRptNo) ) {
            			hmParam.put("rpt_no", sRptNo);
            		}
            		
            		iCudCnt += EventStockMngDao.insertWhInMst(hmParam);
            		List lstDetail = EventStockMngDao.selectOrdDtlInfo(hmParam);
            		
            		if (null != lstDetail && lstDetail.size() > 0) {
            			for (int j=0; j<lstDetail.size(); j++) {
            				Map mDtl = (Map)lstDetail.get(j);
            				mDtl.put("wh_in_no", whInNo);
            				ParamUtils.addSaveParam(mDtl);
            				
            				int qty = Integer.parseInt(CommonUtils.stringValueOf(mDtl.get("qty")));
            				int inWhQty = 0;
            				if (null != mDtl.get("in_wh_qty")) {
            					inWhQty = Integer.parseInt(CommonUtils.stringValueOf(mDtl.get("in_wh_qty")));
            				}
            				
            				if (inWhQty > 0) {
            					mDtl.put("qty", String.valueOf(qty - inWhQty));
            				}
            				if (qty - inWhQty > 0 ) {
            					iCudCnt += EventStockMngDao.insertWhInDtl(mDtl);
            				}
            			}
            		}
                }
            	else if ( "".equals(sPartWarehousing) )
            	{	
            		hmParam = listInDs.get(0);
            		ParamUtils.addSaveParam(hmParam);
            		hmParam.put("in_dt", sInDt);
            		hmParam.put("qty", String.valueOf(iQtyOfMst));
            		hmParam.put("emple_no", sEmpleNo);
            		hmParam.put("in_cl", sInCl);
            		
            		whInNo = EventStockMngDao.selectNewWhInNo();                		
            		hmParam.put("wh_in_no", whInNo);
            		iCudCnt += EventStockMngDao.insertWhInMst(hmParam);
            		iCudCnt += EventStockMngDao.insertWhInDtl(hmParam);
                }
            	else {
            		hmParam = listInDs.get(0);
            		ParamUtils.addSaveParam(hmParam);
            		hmParam.put("in_dt", sInDt);
            		hmParam.put("qty", String.valueOf(iQtyOfMst));
            		hmParam.put("emple_no", sEmpleNo);
            		hmParam.put("in_cl", sInCl);
            		
            		int qty 		= Integer.parseInt(CommonUtils.stringValueOf(iQtyOfMst));
            		int inWhQty 	= 0;
    				int unitCost 	= 0;    				
    				if (null != hmParam.get("in_wh_qty")) {
    					inWhQty = Integer.parseInt(CommonUtils.stringValueOf(hmParam.get("in_wh_qty")));
    				}
    				if (null != hmParam.get("unit_cost")) {
    					unitCost = Integer.parseInt(CommonUtils.stringValueOf(hmParam.get("unit_cost")));
    				}
    				
    				hmParam.put("amt", String.valueOf(unitCost * qty));
    				hmParam.put("tot_amt", String.valueOf(unitCost * qty));
            		
            		whInNo = EventStockMngDao.selectNewWhInNo();
            		hmParam.put("wh_in_no", whInNo);
            		
            		iCudCnt += EventStockMngDao.insertWhInMst(hmParam);
            		iCudCnt += EventStockMngDao.insertWhInDtl(hmParam);
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		ParamUtils.addSaveParam(hmParam);
            		iCudCnt += EventStockMngDao.updateOrdMstProcStat(hmParam);
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	
    	return iCudCnt;
    }
    
    
    /**
     * ???
     * 
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectInWhList(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectInWhList(pmParam);
    }
    
    /**
     * ???
     * 
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectOutWhList(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectOutWhList(pmParam);
    }
    
    /**
     * ???
     * 
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectWhMvList(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectWhMvList(pmParam);
    }
    
    
    /**
     * 입고내역 등록/수정
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeWhInMst(XPlatformMapDTO xpDto, HashMap mRet) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose = 0;
        int rowType = -1;
        String whInNo = "";
        
        Map mDtl = null;
        Map mMst = null;
        
    	try {
    		DataSetMap dsMst 	= (DataSetMap)mapInDs.get("ds_input2");
    		DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
    		
    		System.out.println("listInDs.size() : " + listInDs.size());
    		System.out.println("dsMst.size() : " + dsMst.size());
    		
    		if (listInDs != null && listInDs.size() > 0) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		mDtl = listInDs.get(i);
            		
            		chkStockClose = EventStockMngDao.checkStockClose((String)mDtl.get("in_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
			}
    		
    		if (dsMst != null && dsMst.size() > 0) {
    			mMst = dsMst.get(0);
            	whInNo = CommonUtils.nvls((String)mMst.get("wh_in_no"));
            	
            	if ( "".equals(whInNo) ) {
            		whInNo = EventStockMngDao.selectNewWhInNo();
            		mMst.put("wh_in_no", whInNo);
                	EventStockMngDao.insertWhInMst(mMst);
            	} else {
            		EventStockMngDao.updateWhInMst(mMst); 
            		EventStockMngDao.deleteWhInDtlByWhInNo(mMst);
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		mDtl = listInDs.get(i);
            		ParamUtils.addSaveParam(mDtl);
            		
            		rowType = ((Integer) mDtl.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		if (rowType == DataSet.ROW_TYPE_INSERTED) {
            			mDtl.put("wh_in_no", whInNo);
            			EventStockMngDao.insertWhInDtl(mDtl);
            		}
            		else if (rowType == DataSet.ROW_TYPE_UPDATED) {
            			if (null == mDtl.get("wh_in_no")) {
            				mDtl.put("wh_in_no", whInNo);
            			}
            			EventStockMngDao.insertWhInDtl(mDtl);
            		} 
            		else if (rowType == DataSet.ROW_TYPE_DELETED) {
            			//
            		}
            	}
    		}
    		
    		mRet.put("wh_in_no", whInNo);
            
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    
    /**
     * 
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteWhInMng(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        Map hmParam = null;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("in_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
            	
            	hmParam = listInDs.get(0);
            	iCudCnt += EventStockMngDao.deleteWhInMst(hmParam);            	
            	iCudCnt += EventStockMngDao.deleteWhInDtlByWhInNo(hmParam);
            	
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * ???
     * 
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectInWhDtlInfo(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectInWhDtlInfo(pmParam);
    }
    
    
    /**
     * 출고내역 등록/수정
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeWhOutMst(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        int rowType = -1;
        
        Map mDtl = null;
		Map mMst = null;

        String whOutNo = "";
        
    	try {
    		DataSetMap dsMst = (DataSetMap)mapInDs.get("ds_input2");
    		DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) {
            	for (int i=0; i<listInDs.size(); ++i) {
            		mDtl = listInDs.get(i);
            		chkStockClose = EventStockMngDao.checkStockClose((String)mDtl.get("out_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
			}
            
            if (dsMst != null && dsMst.size() > 0) {
            	
            	mMst = dsMst.get(0);
            	whOutNo = CommonUtils.nvls((String)mMst.get("wh_out_no"));
            	
            	if ("".equals(whOutNo)) {
            		whOutNo = EventStockMngDao.selectNewWhOutNo();
            		mMst.put("wh_out_no", whOutNo);
            		ParamUtils.addSaveParam(mMst);
            		EventStockMngDao.insertWhOutMst(mMst);
            	} 
            	else {
            		EventStockMngDao.updateWhOutMst(mMst);
            		EventStockMngDao.deleteWhOutDtl(mMst);
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		mDtl = listInDs.get(i);
            		rowType = ((Integer) mDtl.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		if ( rowType != DataSet.ROW_TYPE_DELETED) {
            			
            			if (null == mDtl.get("wh_out_no")) {
                			mDtl.put("wh_out_no", whOutNo);
                		}
            			ParamUtils.addSaveParam(mDtl);
            			EventStockMngDao.insertWhOutDtl(mDtl);
            		}
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteWhOutMng(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        Map hmParam = null;
        int chkStockClose = -1;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {

            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("out_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
                	iCudCnt += EventStockMngDao.deleteWhOutDtl(hmParam);
                	iCudCnt += EventStockMngDao.deleteWhOutMst(hmParam);
            	}
            	
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 창고이동 등록/수정
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeWhMvMng(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        int rowType = -1;
        String whInNo = "";
        String whMvNo = "";
        String inWhCd = "";
        String outWhCd = "";
        String mode = "";
        
        Map mDtl = null;
        Map mMst = null;
        String whOutNo = "";
        
    	try {
            DataSetMap dsMst = (DataSetMap)mapInDs.get("ds_input2");
            DataSetMap dsDtl = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsMst != null) {            	
            	for (int i=0; i<dsMst.size(); ++i) {
            		mDtl = dsMst.get(i);            		
            		chkStockClose = EventStockMngDao.checkStockClose((String)mDtl.get("mv_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
			}
            
            if (dsMst != null && dsMst.size() > 0) {
            	
            	mMst = dsMst.get(0);
            	whMvNo = CommonUtils.nvls((String)mMst.get("wh_mv_no"));
            	whOutNo = CommonUtils.nvls((String)mMst.get("wh_out_no"));
        		whInNo	= CommonUtils.nvls((String)mMst.get("wh_in_no"));        		
        		outWhCd	= CommonUtils.nvls((String)mMst.get("out_wh_cd"));
        		inWhCd	= CommonUtils.nvls((String)mMst.get("in_wh_cd"));
            	
            	mMst.put("out_dt", (String)mMst.get("mv_dt"));
            	mMst.put("in_dt", (String)mMst.get("mv_dt"));
            	
            	log.debug("whOutNo : " + whOutNo);
            	log.debug("whInNo : " + whInNo);
            	
            	mode = CommonUtils.nvls((String)mMst.get("mode"));
            	
            	if ("evtRpt".equals(mode)) {
            		EventStockMngDao.deleteEvtRptWhInDtl(mMst);
            		EventStockMngDao.deleteEvtRptWhMvDtl(mMst);
            		EventStockMngDao.deleteEvtRptWhOutDtl(mMst);

            		EventStockMngDao.deleteEvtRptWhIn(mMst);
            		EventStockMngDao.deleteEvtRptWhMv(mMst);
            		EventStockMngDao.deleteEvtRptWhOut(mMst);

            		whOutNo = EventStockMngDao.selectNewWhOutNo();
            		whInNo = EventStockMngDao.selectNewWhInNo();
            		whMvNo = EventStockMngDao.selectNewWhMvNo();

            		mMst.put("wh_mv_no", whMvNo);
            		mMst.put("wh_out_no", whOutNo);
            		mMst.put("wh_in_no", whInNo);

            		EventStockMngDao.insertWhOutMst(mMst);
            		EventStockMngDao.insertWhInMst(mMst);
            		EventStockMngDao.insertWhMvMst(mMst);
        		}
            	
            	if ("".equals(whMvNo)) {
            		whOutNo = EventStockMngDao.selectNewWhOutNo();
            		whInNo = EventStockMngDao.selectNewWhInNo();
            		whMvNo = EventStockMngDao.selectNewWhMvNo();

            		mMst.put("wh_mv_no", whMvNo);
            		mMst.put("wh_out_no", whOutNo);
            		mMst.put("wh_in_no", whInNo);

            		EventStockMngDao.insertWhOutMst(mMst);
            		EventStockMngDao.insertWhInMst(mMst);
            		EventStockMngDao.insertWhMvMst(mMst);
            	} 
            	else {
            		EventStockMngDao.updateWhInMst(mMst);
            		EventStockMngDao.updateWhOutMst(mMst);
            		EventStockMngDao.updateWhMvMst(mMst);

            		EventStockMngDao.deleteWhMvDtl(mMst);
            		EventStockMngDao.deleteWhOutDtl(mMst);
            		EventStockMngDao.deleteWhInDtl(mMst);
            	}
            	
            	for (int i=0; i<dsDtl.size(); ++i) {
            		mDtl = dsDtl.get(i);
            		ParamUtils.addSaveParam(mDtl);
            		
            		rowType = ((Integer) mDtl.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		if ("evtRpt".equals(mode)) {
            			mDtl.put("wh_out_no", whOutNo);
            			mDtl.put("wh_cd", outWhCd);
            			EventStockMngDao.insertWhOutDtl(mDtl);

            			mDtl.put("wh_in_no", whInNo);
            			mDtl.put("wh_cd", inWhCd);
            			EventStockMngDao.insertWhInDtl(mDtl);

            			mDtl.put("wh_mv_no", whMvNo);
            			EventStockMngDao.insertWhMvDtl(mDtl);
            		}
            		
            		if (rowType != DataSet.ROW_TYPE_DELETED) {
            			mDtl.put("wh_out_no", whOutNo);
            			mDtl.put("wh_cd", outWhCd);
            			EventStockMngDao.insertWhOutDtl(mDtl);

            			mDtl.put("wh_in_no", whInNo);
            			mDtl.put("wh_cd", inWhCd);
            			EventStockMngDao.insertWhInDtl(mDtl);

            			mDtl.put("wh_mv_no", whMvNo);
            			EventStockMngDao.insertWhMvDtl(mDtl);
            		}
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 창고이동 삭제
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteWhMvMng(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        Map hmParam = null;
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("mv_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		iCudCnt += EventStockMngDao.deleteWhMvDtl(hmParam);
            		iCudCnt += EventStockMngDao.deleteWhMvMst(hmParam);

            		iCudCnt += EventStockMngDao.deleteWhOutDtl(hmParam);
            		iCudCnt += EventStockMngDao.deleteWhOutMst(hmParam);

            		iCudCnt += EventStockMngDao.deleteWhInDtl(hmParam);
            		iCudCnt += EventStockMngDao.deleteWhInMst(hmParam);
            	}
            	
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * ???
     * 샘플
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectStockInspectList(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectStockInspectList(pmParam);
    }
    
    /**
     * ???
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int mergeStockInspect(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        int rowType = -1;
        String whInNo = "";
        String whMvNo = "";
        String inWhCd = "";
        String outWhCd = "";
        
        Map hmParam = null;
        String stockInspectNo = "";
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("inspect_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
            	
            	hmParam = listInDs.get(0);
            	rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            	
            	if (rowType == DataSet.ROW_TYPE_INSERTED) {
            		stockInspectNo = EventStockMngDao.selectNewStockInspectNo(hmParam);
            		hmParam.put("stock_inspect_no", stockInspectNo);
            		EventStockMngDao.insertStockInspectMst(hmParam);
            	} 
            	else if (rowType == DataSet.ROW_TYPE_UPDATED) {
            		EventStockMngDao.updateStockInspectMst(hmParam);
            		EventStockMngDao.deleteStockInspectDtl(hmParam);
            		stockInspectNo = (String)hmParam.get("stock_inspect_no");
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		ParamUtils.addSaveParam(hmParam);
            		
            		rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		if (rowType == DataSet.ROW_TYPE_INSERTED) {
            			hmParam.put("inspect_dt", (String)hmParam.get("inspect_dt"));
            			hmParam.put("stock_inspect_no", stockInspectNo);
            			EventStockMngDao.insertStockInspectDtl(hmParam);            			
            		} 
            		else if (rowType == DataSet.ROW_TYPE_UPDATED) {
            			hmParam.put("inspect_dt", (String)hmParam.get("inspect_dt"));
            			hmParam.put("stock_inspect_no", stockInspectNo);
            			EventStockMngDao.insertStockInspectDtl(hmParam);
            		} 
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteStockInspectMst(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        Map hmParam = null;        
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("inspect_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		ParamUtils.addSaveParam(hmParam);
            		
            		int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		if (rowType == DataSet.ROW_TYPE_DELETED) {
            			iCudCnt += EventStockMngDao.deleteStockInspectMst(hmParam);
            			iCudCnt += EventStockMngDao.deleteStockInspectDtl(hmParam);
            		}
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * ???
     * 샘플
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectStockCloseList(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectStockCloseList(pmParam);
    }
    
    /**
     * ???
     * 샘플
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectStockCloseListByWh(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.selectStockCloseListByWh(pmParam);
    }
    
    /**
     * 샘플
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int insertStockClose(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        Map hmParam = null;        
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
            	
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		chkStockClose = EventStockMngDao.checkStockClose((String)hmParam.get("chk_dt"));            		
            		if (chkStockClose > 0) {
            			return -1;
            		}
            	}
            	
            	hmParam = listInDs.get(0);
            	List<Map<String, Object>> list = EventStockMngDao.selectIOWhList(hmParam);
            	
            	for (int i=0; i<list.size(); ++i) {
            		hmParam = list.get(i);
            		ParamUtils.addSaveParam(hmParam);            		
            		iCudCnt += EventStockMngDao.insertStockClose(hmParam);
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 샘플
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int deleteStockClose(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        Map hmParam = null;        
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
            	
            	hmParam = listInDs.get(0);
            	iCudCnt += EventStockMngDao.deleteStockClose(hmParam);
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /****************************************************************************************/
    /* 위쪽으로 작업 */
    /****************************************************************************************/
    
    /**
     * ???
     * 샘플
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> sss(Map<String, Object> pmParam) throws Exception {
        return EventStockMngDao.sss(pmParam);
    }
    
    /**
     * 샘플
     *
     * @param XPlatformMapDTO xpDto
     * @return int
     * @throws Exception
     */
    public int saveMonitorQuestList(XPlatformMapDTO xpDto) throws Exception {
    	
    	Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
        int iCudCnt = 0;
        int chkStockClose;
        Map hmParam = null;        
        
    	try {
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null) {
            	for (int i=0; i<listInDs.size(); ++i) {
            		hmParam = listInDs.get(i);
            		
            		ParamUtils.addSaveParam(hmParam);
            		
            		int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
            		
            		if (rowType == DataSet.ROW_TYPE_INSERTED) {
            			iCudCnt += EventStockMngDao.insertSss(hmParam);
            		} 
            		else if (rowType == DataSet.ROW_TYPE_UPDATED) {
            			iCudCnt += EventStockMngDao.updateSss(hmParam);
            		} 
            		else if (rowType == DataSet.ROW_TYPE_DELETED) {
            			iCudCnt += EventStockMngDao.deleteSss(hmParam);
            		}
            	}
            }
        } catch (Exception e) {
            throw e;
        }
    	return iCudCnt;
    }
    
    /**
     * 행사회원 여부 조회
     * 샘플
     * @param pmParam 검색 조건
     * @return 
     * @throws Exception
     */
    public String getString(Map<String, Object> pmParam) throws Exception {
        return "sss";
    }
    
}
