package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.AddRevenueService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 추가매출 현황
 *
 * @author 정출연
 * @date 2016/11/01
 * @프로그램ID AddRevenue
 */
@Service
public class AddRevenueServiceImpl extends EgovAbstractServiceImpl implements AddRevenueService {
	
	private final Logger log = LoggerFactory.getLogger(AddRevenueServiceImpl.class);

    @Resource
    public AddRevenueDao addRevenueDao;
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAddSalesCondList(Map<String, Object> pmParam) throws Exception {
    	
    	List<Map<String, Object>> list2 = addRevenueDao.selectAddSalesCondQry(pmParam);
    	
    	Map<String, Object> qry = list2.get(0);    	
    	pmParam.put("pivot_qry1",		(String)qry.get("pivot_qry1"));
    	pmParam.put("pivot_qry2",		(String)qry.get("pivot_qry2"));
    	pmParam.put("tot_sum_qry",		(String)qry.get("tot_sum_qry"));
    	pmParam.put("add1_1_sum_qry",	(String)qry.get("add1_1_sum_qry"));
    	pmParam.put("add2_1_sum_qry",	(String)qry.get("add2_1_sum_qry"));
    	pmParam.put("add1_2_sum_qry",	(String)qry.get("add1_2_sum_qry"));
    	pmParam.put("add2_2_sum_qry",	(String)qry.get("add2_2_sum_qry"));
    	pmParam.put("df_qry1",			(String)qry.get("df_qry1"));
    	pmParam.put("df_qry2",			(String)qry.get("df_qry2"));
    	pmParam.put("ht_qry1",			(String)qry.get("ht_qry1"));
    	pmParam.put("ht_qry2",			(String)qry.get("ht_qry2"));
    	pmParam.put("df_base_qry",		(String)qry.get("df_base_qry"));
    	pmParam.put("ht_base_qry",		(String)qry.get("ht_base_qry"));
    	
    	CommonUtils.printLog(pmParam);
    	
        return addRevenueDao.selectAddSalesCondList(pmParam);
    }
    
    /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAddSalesOutCondList(Map<String, Object> pmParam) throws Exception {
    	
    	List<Map<String, Object>> list2 = addRevenueDao.selectAddSalesOutCondQry(pmParam);
    	
    	Map<String, Object> qry = list2.get(0);    	
    	pmParam.put("pivot_qry1",		(String)qry.get("pivot_qry1"));
    	pmParam.put("pivot_qry2",		(String)qry.get("pivot_qry2"));
    	pmParam.put("tot_sum_qry",		(String)qry.get("tot_sum_qry"));
    	pmParam.put("add1_1_sum_qry",	(String)qry.get("add1_1_sum_qry"));
    	pmParam.put("add2_1_sum_qry",	(String)qry.get("add2_1_sum_qry"));
    	pmParam.put("add1_2_sum_qry",	(String)qry.get("add1_2_sum_qry"));
    	pmParam.put("add2_2_sum_qry",	(String)qry.get("add2_2_sum_qry"));
    	pmParam.put("df_qry1",			(String)qry.get("df_qry1"));
    	pmParam.put("df_qry2",			(String)qry.get("df_qry2"));
    	pmParam.put("ht_qry1",			(String)qry.get("ht_qry1"));
    	pmParam.put("ht_qry2",			(String)qry.get("ht_qry2"));
    	pmParam.put("df_base_qry",		(String)qry.get("df_base_qry"));
    	pmParam.put("ht_base_qry",		(String)qry.get("ht_base_qry"));
    	
    	CommonUtils.printLog(pmParam);
    	
        return addRevenueDao.selectAddSalesOutCondList(pmParam);
    }
    
    
}
