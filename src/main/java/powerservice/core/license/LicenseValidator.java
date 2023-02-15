/*
 * (@)# LicenseValidator.java
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2015/01/01
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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import powerservice.business.sys.service.LicnBasInfoService;
import powerservice.business.sys.service.SrvrInfoService;
import powerservice.common.util.DateUtil;
import powerservice.core.util.crypto.AES;

/**
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2015/01/01
 * @PROGRAMID : LicenseValidator
 * @DESCRIPTION : 라이선스 관리
 */

@Component
public class LicenseValidator {

    private final String CRYPTO_KEY = "Inwoo.LIC.KEY!#%";	// key size 16 byte
    private static String gsXmlFilePath = "/WEB-INF/config/inwoo/license/inwoo-license.xml";

    @Autowired
    private ServletContext ctx;

    @Resource
    private LicnBasInfoService licnBasInfoService;

    @Resource
    private SrvrInfoService srvrInfoService;

    private static LicenseState gsStatusCode = LicenseState.NOT_FOUND; // 라이선스
    private static License goLicenseBean = null; // 라이선스 파일정보


    /**
     * 라이선스 환경파일 로딩(최초한번)
     * 만약 이미 로딩되었다면 환경파일을 다시 열지 않음
     * @throws Exception
     */
    @PostConstruct
    private void loadLicense() throws Exception {
        try {
            if (goLicenseBean == null) {
                goLicenseBean = (new LicenseLoader()).load(ctx.getRealPath("") + gsXmlFilePath);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        validateLicense();

        // System.out.println(getLicenseMessage());
    }

    /**
     * 제품이 설치된 IP 체크
     */
    private boolean validateIpAddr() {
        try {
            String sLicenseIpAddr = goLicenseBean.getLicenseIpAddr();
            String sServerIpAddr = getServerIpAddr();
            System.out.println("[LicenseValidator.java] LICENSE IP : " + sLicenseIpAddr);
            System.out.println("[LicenseValidator.java] SERVER IP : " + sServerIpAddr);

            String sLocalIpAddr = InetAddress.getLocalHost().getHostAddress();
            System.out.println("[LicenseValidator.java] LOCAL IP : " + sLocalIpAddr);
            // if (!sLicenseIpAddr.equals(LicenseConstant.IP_ANY) && !sLocalIpAddr.equals(sLicenseIpAddr)) {
            if (!sLicenseIpAddr.equals(License.IP_ANY) && !sServerIpAddr.equals(sLicenseIpAddr)) {
                gsStatusCode = LicenseState.INVALID_IP;
                return false;
            }
            return true;
        } catch (UnknownHostException ue) {
            return false;
        }
    }

    /**
     * 제품의 만료기간 체크
     */
    private boolean validateDate() {
        Date dLicenseExpiration = goLicenseBean.getLicenseExpiration();
        Date dToday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);

        if (dLicenseExpiration.getTime() != License.EXPIRATION_NEVER_LONG
                && dToday.after(dLicenseExpiration)) {
            gsStatusCode = LicenseState.EXPIRED;
            return false;
        }

        return true;
    }

    /**
     * 제품이 설치된 사이트 체크
     * @throws Exception
     */
    private boolean validateSite() throws Exception {
        try {
            String sLicenseSite = goLicenseBean.getLicenseSite();
            if (!sLicenseSite.equals(License.SITE_INWOO)) {
                String sBasicValueSite = licnBasInfoService.getSiteName(null);
                if (!sBasicValueSite.equals(sLicenseSite)) {
                    gsStatusCode = LicenseState.INVALID_SITE;
                    return false;
                }
            }
            return true;

        } catch (Exception except) {
            return false;
        }
    }

    /**
     * 라이선스 만기가 되었다면 CODE를 설정하고 true 반환
     */
    public boolean isExpiredLicense() {
        // serviceValidateDate 가 true이면 라이선스 기간이 남아있다는 것임
        if (validateDate()) {
            return false;
        }
        return true;
    }

    /**
     * 라이선스 객체를 반환한다.
     *
     * @return 라이선스 사용자 수
     */
    public License getLicenseBean() {
        return goLicenseBean;
    }

    /**
     * 라이선스의 사용자 수를 반환한다.
     *
     * @return 라이선스 사용자 수
     */
    public int getLicenseUserCount() {
        return goLicenseBean.getLicenseUser();
    }

    /**
     * DB에 등록된 사용자 수를 반환한다.
     *
     * @return DB 등록 사용자 수
     */
    public int getUserCount() {
        int nCount = 0;
        try {
            Integer nCountInteger = licnBasInfoService.getUserCount(null);
            if (nCountInteger != null) {
                nCount = nCountInteger.intValue();
            }
        } catch (Exception except) {
            except.printStackTrace();
        }
        return nCount;
    }

    /**
     * 오늘 로그인한 사용자 수를 반환한다.
     *
     * @return 오늘 로그인 사용자 수
     */
    public int getTodayLoginUserCount(String psUserId) {
        int nCount = 0;
        try {
            Map<String, Object> mParam = new HashMap<String, Object>();
            mParam.put("user_id", psUserId);
            Integer nCountInteger = licnBasInfoService.getTodayLoginUserCount(mParam);
            if (nCountInteger != null) {
                nCount = nCountInteger.intValue();
            }
        } catch (Exception except) {
            except.printStackTrace();
        }
        return nCount;
    }

    /**
     * 라이선스의 센터 수를 반환한다.
     *
     * @return 라이선스 센터 수
     */
    public int getLicenseCenterCount() {
        return goLicenseBean.getLicenseCenter();
    }

    /**
     * DB에 등록된 센터 수를 구한다.
     *
     * @return DB 등록 센터 수
     */
    public int getCenterCount() {
        int nCount = 0;
        try {
            Integer nCountInteger = licnBasInfoService.getCenterCount(null);
            if (nCountInteger != null) {
                nCount = nCountInteger.intValue();
            }
        } catch (Exception except) {
            except.printStackTrace();
        }
        return nCount;
    }

    /**
     * 제품에 대한 라이선스를 체크함(KEP, IP, 만료일, 사이트명순)
     * 이 함수는 SeedLoader를 통해 서버가 로딩될때 한번만 호출되면 됨
     * 여러번 호출할 필요없음
     *
     * @return 라이선스 체크 결과
     * @throws Exception
     */
    public boolean validateLicense() throws Exception {
        String sCurrentData = null;
        String sLicenseData = null;
        int nCurrentCenterCount = 1; // 라이선스 적용 대상 센터 수

        try {
            sCurrentData = encryptData(goLicenseBean.toString());
            sLicenseData = goLicenseBean.getLicenseKey();
            System.out.println(sCurrentData);
            if (sCurrentData.equals(sLicenseData)) {
                if (validateIpAddr() && validateDate() && validateSite()) {
                    nCurrentCenterCount = this.getCenterCount();
                    System.out.println("[LicenseValidator.java] => CENTER 수(DB) " + nCurrentCenterCount + ", CENTER 수(LICENSE) : " + goLicenseBean.getLicenseCenter());
                    // 라이선스 발급 센터수보다 등록된 센터수가 많으면 라이선스 Fail처리
                    if (goLicenseBean.getLicenseCenter() < nCurrentCenterCount) {
                        gsStatusCode = LicenseState.INVALID_CENTER_COUNT;
                        return false;
                    }
                    // License 통과 코드 설정
                    gsStatusCode = LicenseState.OK;
                    System.out.println("[LicenseValidator.java] => LICENSE STATUS : " + gsStatusCode);

                    // License 정보 TB_LICENSEHIST 테이블에 INSERT
                    this.insertLicnBasInfoHstr();

                    return true;
                }
            } else {
                gsStatusCode = LicenseState.INVAID_KEY;
            }
        } catch (GeneralSecurityException except) {
            gsStatusCode = LicenseState.INVAID_KEY;
        } catch (Exception except) {
            gsStatusCode = LicenseState.INVAID_KEY;
        }
        System.out.println("[LicenseValidator.java] => LICENSE STATUS : " + gsStatusCode);
        return false;
    }

    /**
     * 라이선스 기준 정보 이력 등록
     */
    private void insertLicnBasInfoHstr() {
        Map<String, Object> mParam = new HashMap<String, Object> ();
        try {
            Object oSrvrId = "Z";
            Object oWasInfoCntn = "";

            // 서버 정보 조회
            mParam.put("srvr_ip_addr", getServerIpAddr());
            Map<String, Object> mSrvrInfo = srvrInfoService.getSrvrInfo(mParam);
            if (mSrvrInfo != null) {
                oSrvrId = mSrvrInfo.get("srvr_id");
                oWasInfoCntn = mSrvrInfo.get("was_info_cntn");
            }
            // System.out.println("====== srvr_ip_addr [" + getServerIpAddr() + "]");
            // System.out.println("====== srvr_id [" + oSrvrId + "]");
            // System.out.println("====== was_info_cntn [" + oWasInfoCntn + "]");

            Date dLicnIsncDt = goLicenseBean.getLicenseIssuance();
            Date dLicnEndDt = goLicenseBean.getLicenseExpiration();
            String sLicnIsncDt = DateUtil.convertDate(dLicnIsncDt, DateUtil.SDF_YYYYMMDD);
            String sLicnEndDt = License.EXPIRATION_NEVER;
            if (dLicnEndDt.getTime() < License.EXPIRATION_NEVER_LONG) {
                sLicnEndDt = DateUtil.convertDate(dLicnEndDt, DateUtil.SDF_YYYYMMDD);
            }

            int nCntrLicnQnt = goLicenseBean.getLicenseCenter();
            int nCnsrLicnQnt = goLicenseBean.getLicenseUser();
            int nAdmrLicnQnt = goLicenseBean.getLicenseAdmin();
            int nStafLicnQnt = goLicenseBean.getLicenseStaff();

            mParam.put("srvr_id", oSrvrId);
            mParam.put("was_info_cntn", oWasInfoCntn);
            mParam.put("site_info_cntn", goLicenseBean.getLicenseSite());
            mParam.put("srvr_ip_addr", goLicenseBean.getLicenseIpAddr());
            mParam.put("licn_isnc_dt", sLicnIsncDt);
            mParam.put("licn_end_dt", sLicnEndDt);

            mParam.put("ps_gds_nm", goLicenseBean.getLicenseProduct());
            mParam.put("ps_gds_vrsn_nm", goLicenseBean.getLicenseVersion());
            mParam.put("ps_licn_key_cntn", goLicenseBean.getLicenseKey());
            mParam.put("cntr_licn_qnt", nCntrLicnQnt < 99999 ? nCntrLicnQnt : 99999);
            mParam.put("cnsr_licn_qnt", nCnsrLicnQnt < 99999 ? nCnsrLicnQnt : 99999);
            mParam.put("admr_licn_qnt", nAdmrLicnQnt < 99999 ? nAdmrLicnQnt : 99999);
            mParam.put("staf_licn_qnt", nStafLicnQnt < 99999 ? nStafLicnQnt : 99999);

            mParam.put("ib_fnct_yn", goLicenseBean.getLicenseIbModuleYn());
            mParam.put("ob_fnct_yn", goLicenseBean.getLicenseObModuleYn());
            mParam.put("kms_fnct_yn", goLicenseBean.getLicenseKmsModuleYn());
            mParam.put("staf_fnct_yn", goLicenseBean.getLicenseStafModuleYn());
            mParam.put("mo_fnct_yn", goLicenseBean.getLicenseMoModuleYn());
            mParam.put("walb_fnct_yn", goLicenseBean.getLicenseWalbModuleYn());

            licnBasInfoService.insertLicnBasInfoHstr(mParam);
        } catch(Exception except) {
            except.printStackTrace();
        }
    }

    /**
     * ========================================================================
     *
     * COMMON 영역
     *
     * ========================================================================
     */

    /**
     * 서버 로딩이후 하단에 표시할 문자열
     */
    public String getLicenseMessage() {
        String sLicenseSite = goLicenseBean.getLicenseSite();
        String sLicenseProduct = goLicenseBean.getLicenseProduct();
        if (sLicenseSite == null) {
            sLicenseSite = "TargetCop(c)";
        }

        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            return "// ==========================================================================================\n"
                + "// \n"
                + "// This license is legally provided for the product '" + sLicenseProduct + "' \n"
                + "// from Inwoo Tech Inc, KOREA to '"+ sLicenseSite + "' only. "
                + "// Unauthorized reproducing, distribution, installation or using \n"
                + "// of this product or license may result severe civil and criminal panerties, and will be \n"
                + "// prosecuted to the maximum extent possible under the law \n"
                + "// ○ HomePage : http://www.inwoo.co.kr \n"
                + "// ○ TelePhone : ☏ 02-6475-3800 \n"
                + "// \n"
                + "// ==========================================================================================";
        } else {
            return
                  "// ==========================================================================================\n"
                + "// \n"
                + "// 본 인증은 '(주)인우기술'이 '" + sLicenseProduct + "'를(을) 사용자 '" + sLicenseSite+ "'에만 \n"
                + "// 제공하는 법적인 사용 허가인증임으로 인증 받지 않은 다른 사용자들은 본 \n"
                + "// 인증을 이용하여 제품의 설치 및 사용을 할 수 없습니다. \n"
                + "// ○ 홈페이지 : http://www.inwoo.co.kr \n"
                + "// ○ 전화번호 : ☏ 02-6475-3800 \n"
                + "// \n"
                + "// ==========================================================================================";
        }
    }

    /**
     * 현재 라이선스 상태에 대한 코드를 반환, 초기상태 코드는 LICENSE_CODE_DO_NOT_EXIST 임
     */
    public LicenseState getLicenseState() {
        return gsStatusCode;
    }

    /**
     * 라이선스파일에서 문자열을 읽어 들여 복호화 함
     *
     * @return 복호화된 문자열
     * @throws Exception
     */
    /*
    private String decryptData(String psData) throws Exception {
        return AES.decrypt(psData, CRYPTO_KEY);
    }
    */

    /**
     * 라이선스를 생성하기 위해 입력된 문자열을 암호화 함
     *
     * @return 암호화된 문자열
     * @throws GeneralSecurityException
     */
    private String encryptData(String psData) throws Exception {
        return AES.encrypt(psData, CRYPTO_KEY);
    }

    /**
     * 현재 서버의 IP 주소를 가져옵니다.
     *
     * @return 현재 서버 IP 주소
     */
    private String getServerIpAddr() {
        try {
            for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();) {
                NetworkInterface networkInterface = enumeration.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException exception) {}
        return null;
    }
}
