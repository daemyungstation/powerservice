package powerservice.business.dlw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 행사 재고 관리
 *
 * @author 정출연
 * @date 2016/11/01
 * @프로그램ID EventMonitor
 */
@Repository
public class AddRevenueDao extends EgovAbstractMapper {

    /**
     * DB SqlSession을 설정한다.
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    
    // selectAddSalesCondQry
    
    /**
    * 직영 조회 쿼리 조각 조회
    *
    * @param pmParam 검색 조건
    * @return ???
    * @throws Exception
    */
    public List<Map<String, Object>> selectAddSalesCondQry(Map<String, Object> pmParam) throws Exception {
    	List<Map<String, Object>> lstRet = new ArrayList<>();
    	String comCd = "";
    	
    	StringBuffer totSumQry 	= new StringBuffer();
    	StringBuffer dfBaseQry = new StringBuffer("EVT_MNGR_NM,branch_Nm,MEM_NM,ACCNT_NO,PROD_AMT,DEDUCT_AMT,NEW_CHAN_AMT,ADD_SALES_AMT,AMT,BASIC_COST,PROFIT,MARGIN_RT");
    	StringBuffer htBaseQry = new StringBuffer("담당CP,지부,회원명,회원번호,매출금,공제금액,할인금액,추가매출,금액,기본원가,순이익,마진율(%)");
    	
    	StringBuffer pivotQry1 = new StringBuffer();
    	StringBuffer dfQry1 	= new StringBuffer();
    	StringBuffer htQry1 	= new StringBuffer();
    	StringBuffer add1SumQry1 = new StringBuffer(); // ADD1_1_SUM_QRY
    	StringBuffer add2SumQry1 = new StringBuffer(); // ADD2_1_SUM_QRY
    	
    	StringBuffer pivotQry2 = new StringBuffer();
    	StringBuffer dfQry2 	= new StringBuffer();
    	StringBuffer htQry2 	= new StringBuffer();
    	StringBuffer add1SumQry2 = new StringBuffer(); // ADD1_2_SUM_QRY
    	StringBuffer add2SumQry2 = new StringBuffer(); // ADD2_2_SUM_QRY
    	
    	List<Map<String, Object>> lst1 = selectList("AddRevenueMap.selectAddSalesCondQry1", pmParam);
    	List<Map<String, Object>> lst2 = selectList("AddRevenueMap.selectAddSalesCondQry2", pmParam);
    	
    	/* 추가매출 1 */
    	for (int i=0; i<lst1.size(); ++i) {
    		Map<String, Object> mRow = lst1.get(i);
    		pivotQry1.append(",'"+(String)mRow.get("com_cd")+"'");
    		
    		dfQry1.append(",A"+(String)mRow.get("com_cd"));
    		htQry1.append(","+(String)mRow.get("cd_nm"));
    		
    		add1SumQry1.append(",SUM(nvl('"+(String)mRow.get("com_cd")+"',0)) A" + (String)mRow.get("com_cd"));
    		add2SumQry1.append(",0 A"+(String)mRow.get("com_cd"));    		
    		totSumQry.append(",SUM(A"+(String)mRow.get("com_cd")+") A" + (String)mRow.get("com_cd"));
    	}
    	
    	pivotQry1.delete(0, 1);
    	dfQry1.delete(0, 1);
    	htQry1.delete(0, 1);
    	
    	System.out.println("pivotQry1 : " + pivotQry1);
    	System.out.println("dfQry1 : " + dfQry1);
    	System.out.println("htQry1 : " + htQry1);
    	
    	System.out.println("add1SumQry1 : " + add1SumQry1);
    	System.out.println("add2SumQry1 : " + add2SumQry1);
    	System.out.println("totSumQry : " + totSumQry);
    	
    	/* 추가매출 2 */
    	for (int i=0; i<lst2.size(); ++i) {
    		Map<String, Object> mRow = lst2.get(i);
    		comCd = CommonUtils.nvls((String)mRow.get("com_cd"));
    		pivotQry2.append(",'"+(String)mRow.get("com_cd")+"'");
    		
    		dfQry2.append(",B"+(String)mRow.get("com_cd"));
    		htQry2.append(","+(String)mRow.get("cd_nm"));
    		
    		if ("0002".equals(comCd)) {
    			dfQry2.append(",BXXXX,BYYYY");
        		htQry2.append(",2차장지 공급가액,2차장지 부가세");
    		}
    		
    		add1SumQry2.append(",0 B"+(String)mRow.get("com_cd"));
    		add2SumQry2.append(",SUM(nvl('"+(String)mRow.get("com_cd")+"',0)) B" + (String)mRow.get("com_cd"));    		    		
    		totSumQry.append(",SUM(B"+(String)mRow.get("com_cd")+") B" + (String)mRow.get("com_cd"));
    			
    		if ("0002".equals(comCd)) {
    			add1SumQry2.append(",0 BXXXX,0 BYYYY");
        		add2SumQry2.append(",SUM(nvl(FLOOR(TO_NUMBER('0002') / 1.1), 0)) BXXXX,SUM(TO_NUMBER('0002') - nvl(FLOOR(TO_NUMBER('0002') / 1.1), 0)) BYYYY");    		    		
        		totSumQry.append(",SUM(BXXXX) BXXXX,SUM(BYYYY) BYYYY");
    		}
    	}
    	
    	pivotQry2.delete(0, 1);
    	dfQry2.delete(0, 1);
    	htQry2.delete(0, 1);
    	
    	System.out.println("pivotQry2 : " + pivotQry2);
    	System.out.println("dfQry2 : " + dfQry2);
    	System.out.println("htQry2 : " + htQry2);
    	
    	System.out.println("add1SumQry2 : " + add1SumQry2);
    	System.out.println("add2SumQry2 : " + add2SumQry2);
    	System.out.println("totSumQry : " + totSumQry);
    	
    	
    	System.out.println("--------------- 1");
    	
    	pmParam.put("pivot_qry1",		pivotQry1.toString());
    	pmParam.put("pivot_qry2",		pivotQry2.toString());
    	pmParam.put("tot_sum_qry",		totSumQry.toString());
    	pmParam.put("add1_1_sum_qry",	add1SumQry1.toString());
    	pmParam.put("add2_1_sum_qry",	add2SumQry1.toString());
    	pmParam.put("add1_2_sum_qry",	add1SumQry2.toString());
    	pmParam.put("add2_2_sum_qry",	add2SumQry2.toString());
    	
    	System.out.println("--------------- 2");
    	
    	pmParam.put("df_qry1",			dfQry1.toString());
    	pmParam.put("df_qry2",			dfQry2.toString());
    	pmParam.put("ht_qry1",			htQry1.toString());
    	pmParam.put("ht_qry2",			htQry2.toString());
    	pmParam.put("df_base_qry",		dfBaseQry.toString());
    	pmParam.put("ht_base_qry",		htBaseQry.toString());
    	
    	System.out.println("--------------- 3");
    	
    	lstRet.add(pmParam);
    	return lstRet;
    }
    
     /**
     * ???
     *
     * @param pmParam 검색 조건
     * @return ???
     * @throws Exception
     */
    public List<Map<String, Object>> selectAddSalesCondList(Map<String, Object> pmParam) throws Exception {
        return selectList("AddRevenueMap.selectAddSalesCondList", pmParam);
    }
    
    /**
    * ???
    *
    * @param pmParam 검색 조건
    * @return ???
    * @throws Exception
    */
   public List<Map<String, Object>> selectAddSalesOutCondList(Map<String, Object> pmParam) throws Exception {
       return selectList("AddRevenueMap.selectAddSalesOutCondList", pmParam);
   }

   

   /**
   * 외주 조회 쿼리 조각 조회
   *
   * @param pmParam 검색 조건
   * @return ???
   * @throws Exception
   */
   public List<Map<String, Object>> selectAddSalesOutCondQry(Map<String, Object> pmParam) throws Exception {
   	List<Map<String, Object>> lstRet = new ArrayList<>();
   	String comCd = "";
   	
   	StringBuffer totSumQry 	= new StringBuffer();
   	StringBuffer dfBaseQry = new StringBuffer("EVT_MNGR_NM,branch_Nm,MEM_NM,ACCNT_NO,PROD_AMT,SALES_AMT,AMT,MARGIN");
   	StringBuffer htBaseQry = new StringBuffer("담당CP,지부,회원명,회원번호,가입상품금액,추가매출,원가,마진");
   	
   	StringBuffer pivotQry1 = new StringBuffer();
   	StringBuffer dfQry1 	= new StringBuffer();
   	StringBuffer htQry1 	= new StringBuffer();
   	StringBuffer add1SumQry1 = new StringBuffer(); // ADD1_1_SUM_QRY
   	StringBuffer add2SumQry1 = new StringBuffer(); // ADD2_1_SUM_QRY
   	
   	List<Map<String, Object>> lst1 = selectList("AddRevenueMap.selectAddSalesOutCondQry", pmParam);
   	
   	/* 추가매출 1 */
   	for (int i=0; i<lst1.size(); ++i) {
   		Map<String, Object> mRow = lst1.get(i);
   		pivotQry1.append(",'"+(String)mRow.get("com_cd")+"'");
   		
   		dfQry1.append(",A"+(String)mRow.get("com_cd"));
   		htQry1.append(","+(String)mRow.get("cd_nm"));
   		
   		add1SumQry1.append(",SUM(nvl('"+(String)mRow.get("com_cd")+"',0)) A" + (String)mRow.get("com_cd"));
   		add2SumQry1.append(",0 A"+(String)mRow.get("com_cd"));    		
   		totSumQry.append(",SUM(A"+(String)mRow.get("com_cd")+") A" + (String)mRow.get("com_cd"));
   	}
   	
   	pivotQry1.delete(0, 1);
   	dfQry1.delete(0, 1);
   	htQry1.delete(0, 1);
   	
   	System.out.println("pivotQry1 : " + pivotQry1);
   	System.out.println("dfQry1 : " + dfQry1);
   	System.out.println("htQry1 : " + htQry1);
   	
   	System.out.println("add1SumQry1 : " + add1SumQry1);
   	System.out.println("add2SumQry1 : " + add2SumQry1);
   	System.out.println("totSumQry : " + totSumQry);
   	
   	System.out.println("--------------- 1");
   	
   	pmParam.put("pivot_qry1",		pivotQry1.toString());
   	pmParam.put("tot_sum_qry",		totSumQry.toString());
   	pmParam.put("add1_1_sum_qry",	add1SumQry1.toString());
   	pmParam.put("add2_1_sum_qry",	add2SumQry1.toString());
   	
   	System.out.println("--------------- 2");
   	
   	pmParam.put("df_qry1",			dfQry1.toString());
   	pmParam.put("ht_qry1",			htQry1.toString());
   	pmParam.put("df_base_qry",		dfBaseQry.toString());
   	pmParam.put("ht_base_qry",		htBaseQry.toString());
   	
   	System.out.println("--------------- 3");
   	
   	lstRet.add(pmParam);
   	return lstRet;
   }
}
