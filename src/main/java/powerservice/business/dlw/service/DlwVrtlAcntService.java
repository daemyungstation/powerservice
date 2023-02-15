package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwVrtlAcntService {

    public int getDlwVrtlAcntClctCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwVrtlAcntCsmm(Map<String, ?> pmParam) throws Exception;

    public int deleteDlwVrtlAcntClct(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getVrtlAccntWdrwReqList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getVrtlAccntTargetList(Map<String, ?> pmParam) throws Exception;

    public int getInvAmt(Map<String, ?> pmParam) throws Exception;

    public int deleteTempVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception;

    public int insertTempVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception;

    public int insertVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception;

    public int deleteNiceVrtlAccntWdrwList(Map<String, ?> pmParam) throws Exception;

    public int updateNiceVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception;

    public int getVrtlAccntInfoCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getVrtlAccntInfo(Map<String, ?> pmParam) throws Exception;

    public int updateVrtlAccntRecoverProc(Map<String, ?> pmParam) throws Exception;

    public int updateVrtlAccntPayApp(Map<String, ?> pmParam) throws Exception;

    public int updateCardYenCheChk(Map<String, ?> pmParam) throws Exception;

    public int updateCmsYenCheChk(Map<String, ?> pmParam) throws Exception;

    public int updateVrtlAccntJoinDt(Map<String, ?> pmParam) throws Exception;

    public int insertPayMngByCMS(Map<String, ?> pmParam) throws Exception;

    public int insertPayMngDtlByCMS(Map<String, ?> pmParam) throws Exception;

    public int insertPayMngDtl1ByCMS(Map<String, ?> pmParam) throws Exception;

    public int saveGasuPayProc(Map<String, ?> pmParam) throws Exception;

    public int saveGasuPayDtlProc(Map<String, Object> pmParam) throws Exception;

    public int saveGasuPayDtl1Proc(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getVrtlAccntPayAppComplete(Map<String, ?> pmParam) throws Exception;

    public String getYenCheByPayGubun(Map<String, ?> pmParam) throws Exception;

    public String getYenCheChk(Map<String, ?> pmParam) throws Exception;

    public String vrtlAcntPayByAdmin(Map<String, Object> pmParam) throws Exception;

    public int insertNiceVacctNoti(Map<String, Object> pmParam) throws Exception;

}