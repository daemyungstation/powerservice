/*
 * (@)# XlsItemDAO.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 엑셀 항목 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID XlsItem
 */
@Repository
public class XlsItemDAO extends EgovAbstractMapper {

    /**
     * 엑셀 항목 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 항목 정보의 검색 수
     * @throws Exception
     */
    public int getXlsItemCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("XlsItemMap.getXlsItemCount", pmParam);
    }

    /**
     * 엑셀 항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엑셀 항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getXlsItemList(Map<String, ?> pmParam) throws Exception {
        return selectList("XlsItemMap.getXlsItemList", pmParam);
    }

    /**
     * 엑셀 항목 정보를 등록한다.
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int insertXlsItem(Map<String, ?> pmParam) throws Exception {
        return insert("XlsItemMap.insertXlsItem", pmParam);
    }

    /**
     * 엑셀 항목 정보를 수정한다.
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int updateXlsItem(Map<String, ?> pmParam) throws Exception {
        return update("XlsItemMap.updateXlsItem", pmParam);
    }

    /**
     * 엑셀 항목 정보를 삭제한다.
     *
     * @param pmParam 엑셀 항목 리스트
     * @throws Exception
     */
    public int deleteXlsItem(Map<String, ?> pmParam) throws Exception {
        return delete("XlsItemMap.deleteXlsItem", pmParam);
    }


    /**
     * 엑셀 항목 정보를 수정한다.(팝업)
     *
     * @param mModelList 엑셀 항목 리스트
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int updateXlsItemPopup(Map<String, ?> pmParam) throws Exception {
        return update("XlsItemMap.updateXlsItemPopup", pmParam);
    }

    /**
     * 엑셀 항목 정보를 등록한다.(팝업)
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int insertXlsItemPopup(Map<String, ?> pmParam) throws Exception {
        return update("XlsItemMap.insertXlsItemPopup", pmParam);
    }

    /**
     * 엑셀 항목 정보를 삭제한다.(팝업)
     *
     * @param pmParam 엑셀 항목 정보
     * @throws Exception
     */
    public int deleteXlsItemPopup(Map<String, ?> pmParam) throws Exception {
        return update("XlsItemMap.deleteXlsItemPopup", pmParam);
    }

}
