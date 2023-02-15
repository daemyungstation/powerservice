/**
 * @(#)License.java
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2015/04/01
 *
 * Copyright (c) 2015 by Inwoo Tech Inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of Inwoo Tech Inc.
 * You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Inwoo Tech Inc.
 *
 */
package powerservice.core.license;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2015/04/01
 * @PROGRAMID : License
 * @DESCRIPTION : 제품 LICENSE 정보
 */

public class License {
    public final static int KEY_COUNT = 17;
    public final static String SITE_INWOO = "InwooTech";
    public final static String IP_ANY = "any";
    public final static String EXPIRATION_NEVER = "never";
    public final static long EXPIRATION_NEVER_LONG = 4102412400000L;
    public final static String USER_UNLIMITED = "unlimited";
    public final static SimpleDateFormat DATE_FORAMT = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


    private String gsProduct        = null; // 제품
    private String gsVersion        = null; // 버전
    private String gsSite           = null; // 제품설치장소
    private Date   gdIssuance       = null; // 제품발급일
    private Date   gdExpiration     = null; // 제품종료일
    private int    gnCenter         = 1;    // 센터수
    private int    gnUser           = 1;    // 사용자수
    private int    gnStaff          = 1;    // 직원수
    private int    gnAdmin          = 1;    // 관리자수
    private String gsIbModuleYn     = null; // IB 모듈 여부
    private String gsObModuleYn     = null; // OB 모듈 여부
    private String gsKmsModuleYn    = null; // KMS 모듈 여부
    private String gsStafModuleYn   = null; // 2차처리 모듈 여부
    private String gsMoModuleYn     = null; // MO 모듈 여부
    private String gsWalbModuleYn   = null; // BOARD 모듈 여부
    private String gsIpAddr         = null; // 제품설치 IP
    private String gsKey            = null; // 암호화 KEY


    /**
     * 생성자
     *
     * @param String psProduct 제품
     * @param String psVersion 버전
     * @param String psSite 제품설치장소
     * @param String gsIssuance 제품발급일
     * @param String psExpiration 제품종료일
     * @param String psCenter 센터수
     * @param String psUser 사용자수
     * @param String psStaff 직원수
     * @param String psAdmin 관리자수
     * @param String psIbModuleYn IB 모듈 여부
     * @param String psObModuleYn OB 모듈 여부
     * @param String psKmsModuleYn KMS 모듈 여부
     * @param String psStafModuleYn 2차처리 모듈 여부
     * @param String psMoModuleYn MO 모듈 여부
     * @param String psWalbModuleYn BOARD 모듈 여부
     * @param String psIpAddr 제품설치IP
     * @param String psKey 암호화KEY
     * @throws ParseException
     */
    public License(String psProduct, String psVersion, String psSite, String gsIssuance, String psExpiration, String psCenter, String psUser, String psStaff, String psAdmin,
            String psIbModuleYn, String psObModuleYn, String psKmsModuleYn, String psStafModuleYn, String psMoModuleYn, String psWalbModuleYn, String psIpAddr, String psKey) throws ParseException {
        gsProduct = psProduct;
        gsVersion = psVersion;
        gsSite = psSite;
        gdIssuance = License.DATE_FORAMT.parse(gsIssuance);
        gdExpiration = psExpiration.equals(License.EXPIRATION_NEVER) ? new Date(License.EXPIRATION_NEVER_LONG) : License.DATE_FORAMT.parse(psExpiration);
        gnCenter = psCenter.equals(License.USER_UNLIMITED) ? Integer.MAX_VALUE : Integer.parseInt(psCenter);
        gnUser = psUser.equals(License.USER_UNLIMITED) ? Integer.MAX_VALUE : Integer.parseInt(psUser);
        gnStaff = psStaff.equals(License.USER_UNLIMITED) ? Integer.MAX_VALUE : Integer.parseInt(psStaff);
        gnAdmin = psAdmin.equals(License.USER_UNLIMITED) ? Integer.MAX_VALUE : Integer.parseInt(psAdmin);
        gsIbModuleYn = psIbModuleYn;
        gsObModuleYn = psObModuleYn;
        gsKmsModuleYn = psKmsModuleYn;
        gsStafModuleYn = psStafModuleYn;
        gsMoModuleYn = psMoModuleYn;
        gsWalbModuleYn = psWalbModuleYn;
        gsIpAddr = psIpAddr;
        gsKey = psKey;
    }

    /**
     * 제품 반환
     *
     * @return String
     */
    public String getLicenseProduct() {
        return gsProduct;
    }

    /**
     * 버전 반환
     *
     * @return String
     */
    public String getLicenseVersion() {
        return gsVersion;
    }

    /**
     * 제품설치장소 반환
     *
     * @return String
     */
    public String getLicenseSite() {
        return gsSite;
    }

    /**
     * 제품발급일 반환
     *
     * @return Date
     */
    public Date getLicenseIssuance() {
        return gdIssuance;
    }

    /**
     * 제품종료일 반환
     *
     * @return Date
     */
    public Date getLicenseExpiration() {
        return gdExpiration;
    }

    /**
     * 센터수 반환
     *
     * @return int
     */
    public int getLicenseCenter() {
        return gnCenter;
    }

    /**
     * 사용자수 반환
     *
     * @return int
     */
    public int getLicenseUser() {
        return gnUser;
    }

    /**
     * 직원수 반환
     *
     * @return int
     */
    public int getLicenseStaff() {
        return gnStaff;
    }

    /**
     * 관리자수 반환
     *
     * @return int
     */
    public int getLicenseAdmin() {
        return gnAdmin;
    }

    /**
     * IB 모듈 여부 반환
     *
     * @return String
     */
    public String getLicenseIbModuleYn() {
        return gsIbModuleYn;
    }

    /**
     * OB 모듈 여부 반환
     *
     * @return String
     */
    public String getLicenseObModuleYn() {
        return gsObModuleYn;
    }

    /**
     * KMS 모듈 여부 반환
     *
     * @return String
     */
    public String getLicenseKmsModuleYn() {
        return gsKmsModuleYn;
    }

    /**
     * 2차처리 모듈 여부 반환
     *
     * @return String
     */
    public String getLicenseStafModuleYn() {
        return gsStafModuleYn;
    }

    /**
     * MO 모듈 여부 반환
     *
     * @return String
     */
    public String getLicenseMoModuleYn() {
        return gsMoModuleYn;
    }

    /**
     * BOARD 모듈 여부 반환
     *
     * @return String
     */
    public String getLicenseWalbModuleYn() {
        return gsWalbModuleYn;
    }

    /**
     * 제품설치 IP 반환
     *
     * @return String
     */
    public String getLicenseIpAddr() {
        return gsIpAddr;
    }

    /**
     * 암호화 KEY 반환
     *
     * @return String
     */
    public String getLicenseKey() {
        return gsKey;
    }


    /**
     * 센터수 반환
     *
     * @return String
     */
    public String gerLicenseCenterString() {
        return (gnCenter == Integer.MAX_VALUE) ? License.USER_UNLIMITED : String.valueOf(gnCenter);
    }

    /**
     * 사용자수 반환
     *
     * @return String
     */
    public String gerLicenseUserString() {
        return (gnUser == Integer.MAX_VALUE) ? License.USER_UNLIMITED : String.valueOf(gnUser);
    }

    /**
     * 직원수 반환
     *
     * @return String
     */
    public String gerLicenseStaffString() {
        return (gnStaff == Integer.MAX_VALUE) ? License.USER_UNLIMITED : String.valueOf(gnStaff);
    }

    /**
     * 관리자수 반환
     *
     * @return String
     */
    public String gerLicenseAdminString() {
        return (gnAdmin == Integer.MAX_VALUE) ? License.USER_UNLIMITED : String.valueOf(gnAdmin);
    }

    /**
     * 제품사용 만료일자 반환
     *
     * @return String
     */
    public String getLicenseExpirationString() {
        if (gdExpiration.getTime() == License.EXPIRATION_NEVER_LONG) {
            return License.EXPIRATION_NEVER;
        } else {
            return License.DATE_FORAMT.format(gdExpiration);
        }
    }


    /**
     * 제품에 대한 라이선스정보를 반환, 이 스트링을 이용하여 키값을 생성하거나
     * 생성된 키값을 이용하여 라이선스파일에 저장된 키값과 비교함
     *
     * @return String
     */
    public String toString() {
        return gsProduct + ";" + gsIpAddr + ";" + gerLicenseUserString() + ";" + getLicenseExpirationString() + ";" + gsSite;
    }
}
