/*
 * (@)# LicnBasInfoController.java
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

package powerservice.business.sys.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.sys.service.LicnBasInfoService;
import powerservice.common.util.DateUtil;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.license.License;
import powerservice.core.license.LicenseValidator;

/**
 * 라이선스 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID LicnBasInfo
 */
@Controller
@RequestMapping(value = "/syst/licn-bas-info")
public class LicnBasInfoController {

    @Autowired
    private LicenseValidator licenseValidator;

	@Resource
	private LicnBasInfoService licnBasInfoService;

    /**
     * 라이선스 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getLicense(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");
    	
        ActionResult oResult = new ActionResult();
        Map<String, Object> mData = new HashMap<String, Object>();

        // 라이선스 로드 정보 조회
        Map<String, Object> mLicense = new HashMap<String, Object>();
        License oLicenseBean = licenseValidator.getLicenseBean();
        if (oLicenseBean != null) {
            Date dLicnIsncDt = oLicenseBean.getLicenseIssuance();
            Date dLicnEndDt = oLicenseBean.getLicenseExpiration();
            String sLicnIsncDt = DateUtil.convertDate(dLicnIsncDt, DateUtil.SDF_YYYYMMDD);
            String sLicnEndDt = License.EXPIRATION_NEVER;
            if (dLicnEndDt.getTime() < License.EXPIRATION_NEVER_LONG) {
                sLicnEndDt = DateUtil.convertDate(dLicnEndDt, DateUtil.SDF_YYYYMMDD);
            }

            mLicense.put("site_info_cntn", oLicenseBean.getLicenseSite());
            mLicense.put("cnsr_licn_qnt", oLicenseBean.gerLicenseUserString());
            mLicense.put("licn_isnc_dt", sLicnIsncDt);
            mLicense.put("licn_end_dt", sLicnEndDt);
            mLicense.put("ps_licn_key_cntn", oLicenseBean.getLicenseKey());
            mLicense.put("ps_gds_nm", oLicenseBean.getLicenseProduct());
            mLicense.put("ps_gds_vrsn_nm", oLicenseBean.getLicenseVersion());
            mLicense.put("ib_fnct_yn", oLicenseBean.getLicenseIbModuleYn());
            mLicense.put("ob_fnct_yn", oLicenseBean.getLicenseObModuleYn());
            mLicense.put("kms_fnct_yn", oLicenseBean.getLicenseKmsModuleYn());
            mLicense.put("staf_fnct_yn", oLicenseBean.getLicenseStafModuleYn());
            mLicense.put("mo_fnct_yn", oLicenseBean.getLicenseMoModuleYn());
            mLicense.put("walb_fnct_yn", oLicenseBean.getLicenseWalbModuleYn());
        }
        mData.put("license", mLicense);


        List<Map<String, Object>> mChartList = licnBasInfoService.getSessInfoHstrMonthlyChartList(pmParam);
        mData.put("chart", mChartList);

        oResult.setData(mData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 라이선스 발급 정보 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/licn-isnc-info-hstr-list")
    public ModelAndView getBasicValueList(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");
    	
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        List<Map<String, Object>> mList = licnBasInfoService.getLicnIsncInfoHstrList(pmParam);
        oData.setList(mList);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    
}