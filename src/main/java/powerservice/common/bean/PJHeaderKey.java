/**
* @(#) PJHeaderKey.java
*
* @author 박병목
* @version 1.0
* @date 2011. 09. 07
* Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
* http://www.inwoo.co.kr
*
* NOTICE! This software is the confidential and proprietary information of
* Inwoo Tech Inc. You shall not disclose such Confidential Information and
* shall use it only in accordance with the terms of the license agreement you
* entered into with Inwoo Tech Inc.
*/
package powerservice.common.bean;

/**
 * 엑셀 헤더 Key POJO
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박병목
 * @version 1.0
 * @date 2011. 09. 07
 * @프로그램ID 프로그램ID입력
 */
public class PJHeaderKey {

    private String keyName = "";
    private int maxSize = 0; // 최대 사이즈
    private boolean numericOnlyFlag = false;  // 숫자만 가능한지 구별하는 플래그로 Default false
    private boolean passFlag = false; // DB에 인서트할 필요가 없는키인지 구별하는 플래그
    private String[] removeStrings = null; // 데이터에서 제거해야할 문자들
    private String removeString = null; //데이터에서 제거해야할 문자

    /**
     * 기본 생성자로 생성하지 못하도록 막음
     */
    private PJHeaderKey() {

    }

    /**
     *
     * 생성자
     * @param keyName
     * @param maxSize
     * @param removeChararers
     * @param numerticOnlyFlag
     * @param passFlag
     */
    public PJHeaderKey(String keyName, int maxSize, boolean numerticOnlyFlag, boolean passFlag, String[] removeStrings) {
        this.keyName = keyName;
        this.maxSize = maxSize;
        this.numericOnlyFlag = numerticOnlyFlag;
        this.passFlag = passFlag;
        this.removeStrings = removeStrings;
    }

    /**
     *
     * 생성자
     * @param keyName
     * @param maxSize
     * @param removeChararer
     * @param numerticOnlyFlag
     * @param passFlag
     */
    public PJHeaderKey(String keyName, int maxSize, boolean numerticOnlyFlag, boolean passFlag, String removeCharacter) {
        this.keyName = keyName;
        this.maxSize = maxSize;
        this.numericOnlyFlag = numerticOnlyFlag;
        this.passFlag = passFlag;
        this.removeString = removeString;
    }

    /**
     * 생성자
     * @param keyName
     * @param maxSzie
     * @param numerticOnlyFlag
     * @param removeChararers
     */
    public PJHeaderKey(String keyName, int maxSize, boolean numerticOnlyFlag, String[] removeStrings) {
        this.keyName = keyName;
        this.maxSize = maxSize;
        this.numericOnlyFlag = numerticOnlyFlag;
        this.removeStrings = removeStrings;
    }

    /**
     * 생성자
     * @param keyName
     * @param maxSzie
     * @param numerticOnlyFlag
     * @param removeChararers
     */
    public PJHeaderKey(String keyName, int maxSize, boolean numerticOnlyFlag, String removeString) {
        this.keyName = keyName;
        this.maxSize = maxSize;
        this.numericOnlyFlag = numerticOnlyFlag;
        this.removeString = removeString;
    }

    /**
     * 생성자
     *
     * @param keyName 헤더 키이름
     * @param maxSize 최대 사이즈
     * @param numerticOnlyFlag 숫자만 허용할것인지 구별하는 Falg
     * @param passFlag
     */
    public PJHeaderKey(String keyName, int maxSize, boolean numerticOnlyFlag, boolean passFlag) {
        this.keyName = keyName;
        this.maxSize = maxSize;
        this.numericOnlyFlag = numerticOnlyFlag;
        this.passFlag = passFlag;
    }

    /**
     * 키이름과, 사이즈, 숫자형 데이터인지를 입력받는 생성자
     * @param keyName
     * @param maxSize
     * @param numerticOnlyFlag
     */
    public PJHeaderKey(String keyName, int maxSize, boolean numerticOnlyFlag) {
        this.keyName = keyName;
        this.maxSize = maxSize;
        this.numericOnlyFlag = numerticOnlyFlag;
    }

    /**
     * key이름과, 사이즈를 입력받는 생성자
     *
     * @param keyName 헤더 키이름
     * @param maxSize 최대 사이즈
     */
    public PJHeaderKey(String keyName, int maxSize) {
        this.keyName = keyName;
        this.maxSize = maxSize;
    }

    /**
     *
     * 헤더 키이름을 반환한다.
     *
     * @return keyName
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     *
     * 허용 가능한 최대 사이즈를 반환한다.
     *
     * @return maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     *
     * 숫자형 데이터인지 확인하는 Flag를 반환한다.
     *
     * @return numericOnlyFlag
     */
    public boolean getNumericOnlyFlag() {
        return numericOnlyFlag;
    }

    /**
     *
     * DB에 입력할 필요가 없는 Key인지 확인하는 플래그를 반환한다.
     *
     * @return
     */
    public boolean getPassFlag() {
        return passFlag;
    }

    /**
     *
     * 제거해야할 Character들을 반환한다.
     *
     * @return char[]
     */
    public String[] getRemoveStrings() {
        return removeStrings;
    }

    /**
     *
     * 제거해야할 문자를 반환한다.
     *
     * @return
     */
    public String getRemoveString() {
        return removeString;
    }
}
