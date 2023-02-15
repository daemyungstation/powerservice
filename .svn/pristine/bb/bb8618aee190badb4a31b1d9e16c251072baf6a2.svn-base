/*
 * (@)# UserDAO.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */

package powerservice.business.usr.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 사용자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID User
 */
@Repository
public class UserDAO extends EgovAbstractMapper {

    /**
     * 사용자 정보를 입력한다.
     *
     * @param pmParam 사용자 정보
     * @return
     * @throws Exception
     */
    public int insertUser(Map<String, Object> pmParam) throws Exception {
        return insert("UserMap.insertUser", pmParam);
    }

    /**
     * 사용자 정보를 수정한다.
     *
     * @param pmParam 사용자 정보
     * @throws Exception
     */
    public int updateUser(Map<String, Object> pmParam) throws Exception {
        return update("UserMap.updateUser", pmParam);
    }

    /**
     * 비밀번호를 변경한다.
     *
     * @pmParam pmParam 비밀번호
     * @return
     * @throws Exception
     */
    public int updateUserPassword(Map<String, ?> pmParam) throws Exception {
        return update("UserMap.updateUserPassword", pmParam);
    }

    /**
     * 사용자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UserMap.getUserCount", pmParam);
    }

    /**
     * 사용자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("UserMap.getUserList", pmParam);
    }
    
    /**
     * 사용자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("UserMap.getNewTypeUserList", pmParam);
    }

    /**
     * 사용자 이름을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public String getUserNm(Map<String, ?> pmParam) throws Exception {
        Map<String, ?> mParam =  selectOne("UserMap.getUserNm", pmParam);

        String userNm = (String)mParam.get("user_nm");


        return userNm;
    }

    /**
     * 사용자 정보를 검색한다. - 고객 선택 팝업
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserList2(Map<String, ?> pmParam) throws Exception {
        return selectList("UserMap.getUserList2", pmParam);
    }

    /**
     * 사용자 정보를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public Map<String, Object> getUser(Map<String, ?> pmParam) throws Exception {
        return selectOne("UserMap.getUserList", pmParam);
    }

    /**
     * 사용자 상태 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserStatusList(Map<String, ?> pmParam) throws Exception {
        return selectList("UserMap.getUserStatusList", pmParam);
    }

    /**
     * 사용자 상태 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public int mergeChnlStatInfo(Map<String, ?> pmParam) throws Exception {
        return insert("UserMap.mergeChnlStatInfo", pmParam);
    }

    /**
     * 쪽지 수신대상자 목록 조회
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserListForMemo(Map<String, Object> pmParam) throws Exception {
        return selectList("UserMap.getUserListForMemo", pmParam);
    }

    /**
     * 직무관리 사용자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getevalUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UserMap.getevalUserCount", pmParam);
    }

    public int getsearchevalUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UserMap.getsearchevalUserCount", pmParam);
    }


    /**
     * 직무관리 사용자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getevalUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("UserMap.getevalUserList", pmParam);
    }
    public List<Map<String, Object>> getsearchevalUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("UserMap.getsearchevalUserList", pmParam);
    }



    public int getevalUserCount_dtl(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UserMap.getevalUserCount_dtl", pmParam);
    }

    public int insert_eval_admin(Map<String, ?> pmParam) throws Exception {
        return update("UserMap.insert_eval_admin", pmParam);
    }

    public int update_eval_admin(Map<String, ?> pmParam) throws Exception {
        return update("UserMap.update_eval_admin", pmParam);
    }

    public int update_ma_eval_admin(Map<String, ?> pmParam) throws Exception {
        return update("UserMap.update_ma_eval_admin", pmParam);
    }


    public int del_eval_admin(Map<String, ?> pmParam) throws Exception {
        return update("UserMap.del_eval_admin", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200616
     * 이름 : 송준빈
     * 추가내용 : 마지막 로그인 시간 업데이트
     * 대상 테이블 : PS_WILLVI.TB_USER
     * ================================================================
     */
    public int updateLastLoginDttm(Map<String, ?> pmParam) throws Exception {
        return update("UserMap.updateLastLoginDttm", pmParam);
    }

	public List<Map<String, Object>> getInCallCount(Map<String, ?> mSearchParam) {
//		return (Integer) selectOne("UserMap.getsearchevalUserCount", pmParam); 
		
		return selectList("UserMap.getInCallCount", mSearchParam);
	}

	public List<Map<String, Object>> getOutCallCount(Map<String, ?> mSearchParam) {
		return selectList("UserMap.getOutCallCount", mSearchParam);
	}
	
	/**
     * 사용자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getUserFormCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UserMap.getUserFormCount", pmParam);
    }
}











