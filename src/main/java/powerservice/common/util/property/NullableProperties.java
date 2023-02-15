/*
 * (@)# NullableProperties.java
 *
 * @author 김경희
 * @version 1.0
 * @date 2009. 4. 8.
 * Copyright (c) 2013 by Inwoo tech inc, KOREA. All Rights Reserved.
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
package powerservice.common.util.property;

import java.util.Properties;

/**
 * 일반 Properties에 null value를 Setting 할 수 있는 Properties Wapping Class
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 김경희
 * @version 1.0
 * @date 2009. 4. 8.
 * @프로그램ID <<프로그램 아이디>>
 */

public class NullableProperties extends Properties {
    public synchronized Object put(Object key, Object value) {
        if (value == null) {
            return super.put(String.valueOf(key).toLowerCase(), "");
        }
        return super.put(String.valueOf(key).toLowerCase(), value);
    }
}
