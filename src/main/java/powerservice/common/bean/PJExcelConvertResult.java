/**
* @(#) PJExcelConvertResult.java
*
* @author 조경태
* @version 1.0
* @date 2008. 01. 07
* Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
* http://www.inwoo.co.kr
*
* NOTICE! This software is the confidential and proprietary information of
* Inwoo Tech Inc. You shall not disclose such Confidential Information and
* shall use it only in accordance with the terms of the license agreement you
* entered into with Inwoo Tech Inc.
*/
package powerservice.common.bean;

import java.util.ArrayList;

/**
 * 엑셀파일에서 데이터를 추출한 결과
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 조경태
 * @version 1.0
 * @date 2008. 01. 07
 * @프로그램ID 프로그램ID입력
 */
public class PJExcelConvertResult {

    private ArrayList failList; // 데이터 추출 실패 리스트
    private ArrayList succeedList; // 데이터 추출 성공 리스트

    private PJExcelConvertResult() {}

    /**
     * 생성자
     * @param failList
     * @param successList
     */
    public PJExcelConvertResult(ArrayList succeedList, ArrayList failList) {
        this.succeedList = succeedList;
        this.failList = failList;
    }

    /**
     *
     * 변환 실패 리스트를 반환한다.
     *
     * @return
     */
    public ArrayList getFailList() {
        return failList;
    }

    /**
     *
     * 변환에 성공한 리스트를 반환한다.
     *
     * @return
     */
    public ArrayList getSucceedList() {
        return succeedList;
    }
}
