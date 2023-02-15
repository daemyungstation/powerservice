package powerservice.business.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.sys.service.BasVlService;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwMallLinkedServiceImpl extends EgovAbstractServiceImpl implements DlwMallLinkedService {

	@Resource
    public DlwMallLinkedDAO dlwMallLinkedDAO;
	
	@Resource
    public DlwMallLFLinkedDAO dlwMallLFLinkedDAO;
	
	@Resource
    public BasVlService basVlService;
	
	private final Logger log = LoggerFactory.getLogger(DlwMallLinkedServiceImpl.class);
	
	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 조회
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberBasicList(Map<String, ?> pmParam)throws Exception {
		return dlwMallLinkedDAO.getMemberBasicList(pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 사용/취소금액 조회
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberBasicListUseAmt(Map<String, ?> pmParam) throws Exception {
		return dlwMallLinkedDAO.getMemberBasicListUseAmt(pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
	public int updateResnMemberState(Map<String, ?> pmParam) throws Exception {
		return dlwMallLinkedDAO.updateResnMemberState(pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
	public int updateLFResnMemberState(Map<String, ?> pmParam) throws Exception {
		//dlwMallLinkedDAO
		//dlwMallLFLinkedDAO
		int cnt1 = 0;
		int cnt2 = 0;
		int maxId = 0;
		int chk = 0;
		
		List<Map<String,Object>> lst = new ArrayList<>();
		Map<String, Object> hmParam = new HashMap<String, Object>();
		Map<String, Object> hmParam1 = new HashMap<String, Object>();
		Map<String, Object> hmParam2 = new HashMap<String, Object>();
		Map<String, Object> hmParam3 = new HashMap<String, Object>();
		Map<String, Object> hmParam4 = new HashMap<String, Object>();
		Map<String, Object> hmParam5 = new HashMap<String, Object>();
		
		//CI가 비정상이면 리턴
		chk = dlwMallLFLinkedDAO.getMemberCiChk(pmParam);
		if (chk == 0) {
			return 0;
        }
		
		//2-1
		cnt1 = dlwMallLinkedDAO.getMallMemberCnt(pmParam);
		
		//2-2
		if(cnt1 == 0) {
			//2-2
			dlwMallLFLinkedDAO.deleteMemberShoppng(pmParam);
			
			//2-3
			maxId = dlwMallLinkedDAO.selectMaxMemberid("");
			lst = dlwMallLFLinkedDAO.selectMemberShoppng(pmParam);
			if (lst != null &&lst.size() > 0) {
				hmParam1 = lst.get(0);
            }
	        hmParam1.put("ID", maxId);
			dlwMallLFLinkedDAO.insertMemberShoppng(hmParam1);
			
			//2-4
			dlwMallLFLinkedDAO.updateMemberShopping1(pmParam); 
			
			//2-5
			dlwMallLFLinkedDAO.updateMemberShopping2(pmParam);
			
			//2-6
			lst = dlwMallLFLinkedDAO.selectAllMemberShoppng(pmParam);
			if (lst != null &&lst.size() > 0) {
				hmParam2 = lst.get(0);
            }
			dlwMallLinkedDAO.insertMember(hmParam2);
			
			//2-7
			dlwMallLFLinkedDAO.deleteMemberShoppngHistory(pmParam);
			
			//2-8
			dlwMallLFLinkedDAO.insertMemberShoppngHistory(pmParam);
			
			//2-9
			lst = dlwMallLFLinkedDAO.selectAllMemberShoppngHistory(pmParam);
			if (lst != null &&lst.size() > 0) {
				hmParam3 = lst.get(0);
            }
			dlwMallLinkedDAO.insertMemberHistory(hmParam3);
			
			//2-10
			dlwMallLFLinkedDAO.deleteCashShoppingHistory(pmParam);
			
			//2-11
			dlwMallLFLinkedDAO.insertCashShoppngHistory(pmParam);
			
			//2-12
			lst = dlwMallLFLinkedDAO.selectAllCashShoppngHistory(pmParam);
			if (lst != null &&lst.size() > 0) {
				hmParam4 = lst.get(0);
            }
			dlwMallLinkedDAO.insertCashHistory(hmParam4);
			
			//2-13
			cnt2 = dlwMallLinkedDAO.getNewExistAllFlagCnt(pmParam);
			
			//2-14
			if(cnt2 == 0) {
				lst = dlwMallLFLinkedDAO.selectMember(pmParam);
				if (lst != null &&lst.size() > 0) {
					hmParam5 = lst.get(0);
	            }
				dlwMallLinkedDAO.insertMemberAll(hmParam5);
			}
		}
		
		return 0;
		//return dlwMallLFLinkedDAO.updateLFResnMemberState(pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 적립금 전송
     * ================================================================
     * */
	public int insertMemberBasicAmt(Map<String, ?> pmParam) throws Exception {
		return dlwMallLinkedDAO.insertMemberBasicAmt(pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 행사여부 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getResnMemberStateEvent(Map<String, ?> pmParam) throws Exception {
		return dlwMallLFLinkedDAO.getResnMemberStateEvent(pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 해약여부 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getResnMemberStateRescission(Map<String, ?> pmParam) throws Exception {
		return dlwMallLFLinkedDAO.getResnMemberStateRescission(pmParam);
	}
	
	/**
     * 상품별 종류 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String saveOptSvcList(DataSetMap srchInDs) throws Exception {

        String msg = "error";

        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        int rowType = 0;

        for (int i = 0; i < srchInDs.size(); i++) 
        {
            hmParam = srchInDs.get(i);
            rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            if (rowType == DataSet.ROW_TYPE_UPDATED) 
            {
                if (dlwMallLFLinkedDAO.updateOptSvc(hmParam) > 0)
                {
                	msg = "success";
                }

                if ((!"0001".equals((String)hmParam.get("seq"))) && (!"0002".equals((String)hmParam.get("seq")))) 
                {
                    if (("0014".equals((String)hmParam.get("seq"))) || ("0015".equals((String)hmParam.get("seq")))) 
                    {
                        hmParam.put("optsvc_seq", hmParam.get("seq"));
                        hmParam.put("isu_no", hmParam.get("crt_no"));

                        if (dlwMallLFLinkedDAO.selectCrtNoPerDaySearch(hmParam) == null) 
                        {
                            hmParam.put("isu_no", "000");
                            dlwMallLFLinkedDAO.insertCouponNoPerDayYMS(hmParam);
                        }
                        else if (((String)hmParam.get("crt_no") != null) && (!"".equals((String)hmParam.get("crt_no")))) 
                        {
                            String crtNo = (String)hmParam.get("crt_no");
                            
                            if ((crtNo.length() > 3) || (crtNo.length() < 0)) 
                            {
                                msg = "err_len";
                                break;
                            }
                            dlwMallLFLinkedDAO.updateOtherCrtNoPerDay1(hmParam);
                        }
                    }
                    else if (((String)hmParam.get("crt_no") != null) && (!"".equals((String)hmParam.get("crt_no"))))
                    {
                        hmParam.put("optsvc_seq", hmParam.get("seq"));
                        hmParam.put("isu_no", hmParam.get("crt_no"));

                        if (dlwMallLFLinkedDAO.selectCrtNoSearch(hmParam) == null)
                        {
                        	dlwMallLFLinkedDAO.insertCouponNoYMS(hmParam);
                        } 
                        else 
                        {
                        	dlwMallLFLinkedDAO.updateOtherCrtNo1(hmParam);
                        }
                    }
                }
            }

            if (rowType == DataSet.ROW_TYPE_INSERTED) 
            {
                if (dlwMallLFLinkedDAO.insertOptSvc(hmParam) > 0) msg = "success";
            }
        }
        return msg;
    }
    
    /**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnGubn(Map<String, ?> pmParam) throws Exception {
        return dlwMallLFLinkedDAO.getResnGubn(pmParam);
    }
    
    /**
     * 회원몰 사용금액 (의전, 해약 공용)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getMallUseAmt(String sAccnt_no) throws Exception {
        return dlwMallLinkedDAO.getMallUseAmt(sAccnt_no);
    }    
    
    /* ================================================================
     * 날짜 : 20200128
     * 이름 : 임동진
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수, 해약완료, 유효 로 변경
     * ================================================================
     * */
    public int updateResnMallState(Map<String, ?> pmParam) throws Exception {
        return dlwMallLinkedDAO.updateResnMallState(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20200128
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰 선행 조건 조회
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberBasicYn(Map<String, ?> pmParam) throws Exception {
		return dlwMallLFLinkedDAO.getMemberBasicYn(pmParam);
	}
}
