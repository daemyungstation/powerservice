package powerservice.core.util;

import powerservice.core.bean.UserSessionCore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParamUtils {
    public static void addPagingParam(Map<String, Object> pmParam) {
        Integer nPage = (Integer) pmParam.get("page");            // 현재 페이지 번호
        Integer nPageSize = (Integer) pmParam.get("pageSize");    // 한페이지에 보여줄 개수

        if (nPage != null && nPageSize != null) {
            pmParam.put("startLine", Integer.toString(nPage * nPageSize - nPageSize + 1));
            pmParam.put("endLine", Integer.toString(nPage * nPageSize + 1));
        }

        // 정렬 설정
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mSortList = (List<Map<String, Object>>) pmParam.get("sort");

        if (mSortList != null && mSortList.size() > 0) {
            Map<String, Object> mSort  = (Map<String, Object>) mSortList.get(0);
            pmParam.put("orderBy", mSort.get("field"));
            pmParam.put("orderDirection", mSort.get("dir"));
        }
    }

    @SuppressWarnings("unchecked")
    public static void addFilterParam(Map<String, Object> pmParam) {
        Map<String, Object> mFilter = (Map<String, Object>) pmParam.get("filter");    // 필터정보

        if (mFilter != null) {
            pmParam.put("logic", mFilter.get("logic"));
            pmParam.put("filters", mFilter.get("filters"));
        }
    }


    public static void addSaveParam(Map<String, Object> pmParam) {
        UserSessionCore oLoginUser = SessionUtils.getLoginUser();

        pmParam.put("rgsr_id", oLoginUser.getUserid());
        pmParam.put("rgsr_nm", oLoginUser.getUsernm());
        pmParam.put("amnd_id", oLoginUser.getUserid());
        pmParam.put("amnd_nm", oLoginUser.getUsernm());
        pmParam.put("cntr_cd", oLoginUser.getCentercd());
    }

    public static void addCenterParam(Map<String, Object> pmParam) {
        UserSessionCore oLoginUser = SessionUtils.getLoginUser();

        pmParam.put("cntr_cd", oLoginUser.getCentercd());
    }

    public static Map<String, Object> getCenterParam() {
        Map<String, Object> mParam = new HashMap<String, Object>();
        UserSessionCore oLoginUser = SessionUtils.getLoginUser();

        if (oLoginUser != null) {
            mParam.put("cntr_cd", oLoginUser.getCentercd());
        }

        return mParam;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> convertDropDownParam(Map<String, Object> param) {
        Map<String, Object> result = getCenterParam();

        if (param.get("filter") != null && ((Map) param.get("filter")).get("filters") != null) {
            List<Map<String, String>> filters  = (List)((Map) param.get("filter")).get("filters");

            if (filters.size() > 0) {
                result.put((String)param.get("key"), filters.get(0).get("value"));
            }
        }

        Set<String> keySet = param.keySet();
        for (String keyName : keySet) {
            if (!(keyName.equals("key") || keyName.equals("filter"))) {
                result.put(keyName, param.get(keyName));
            }
        }

        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> convertAreacdDropDownListParam(Map<String, Object> param) {
        Map<String, Object> result = getCenterParam();
        Map<String, String> filter = (Map<String, String>) ((List) ((Map) param.get("filter")).get("filters")).get(0);

        result.put("parareacd", filter.get("value"));

        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> convertCselTypeDropDownListParam(Map<String, Object> pmParam) {
        Map<String, Object> mResult = getCenterParam();
        Map<String, String> mFilter = (Map<String, String>) ((List) ((Map) pmParam.get("filter")).get("filters")).get(0);

        mResult.put("hgrn_cons_typ_cd", mFilter.get("value"));

        return mResult;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> convertFaqTypeDropDownListParam(Map<String, Object> pmParam) {
        Map<String, Object> mResult = getCenterParam();
        Map<String, String> mFilter = (Map<String, String>) ((List) ((Map) pmParam.get("filter")).get("filters")).get(0);

        mResult.put("parclscd", mFilter.get("value"));

        return mResult;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> convertHrchCdDropDownListParam(Map<String, Object> pmParam) {
        Map<String, Object> mResult = getCenterParam();
        Map<String, String> mFilter = (Map<String, String>) ((List) ((Map) pmParam.get("filter")).get("filters")).get(0);

        mResult.put("hrch_typ_cd", pmParam.get("hrch_typ_cd"));
        mResult.put("hgrn_hrch_cd", mFilter.get("value"));

        return mResult;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, Object> convertEvltTypDropDownListParam(Map<String, Object> pmParam) {
        Map<String, Object> mResult = getCenterParam();
        Map<String, String> mFilter = (Map<String, String>) ((List) ((Map) pmParam.get("filter")).get("filters")).get(0);

        mResult.put("evlt_typ_id", mFilter.get("value"));

        return mResult;
    }
}
