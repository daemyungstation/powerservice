package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface XlsItemService {

    public int getXlsItemCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getXlsItemList(Map<String, ?> pmParam) throws Exception;

    public int insertXlsItem(Map<String, ?> pmParam) throws Exception;

    public int updateXlsItem(Map<String, ?> pmParam) throws Exception;

    public int deleteXlsItem(Map<String, ?> pmParam) throws Exception;


    public int updateXlsItemPopup(List<Map<String, Object>> mModelList, Map<String, Object> pmParam) throws Exception;

    public int insertXlsItemPopup(Map<String, Object> pmParam) throws Exception;

    public int deleteXlsItemPopup(Map<String, Object> pmParam) throws Exception;


    public List<Map<String, Object>> getXlsItemListGetColumns(Map<String, Object> pmParam, List<Map<String, Object>> pmXlsItemList) throws Exception;

    public List<Map<String, Object>> getXlsItemListForDownload(Map<String, Object> pmParam, List<Map<String, Object>> pmXlsItemList) throws Exception;

}