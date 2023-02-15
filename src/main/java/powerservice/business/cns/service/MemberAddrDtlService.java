package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface MemberAddrDtlService {

    public String insertMemberAddrDtl(Map<String, ?> pmParam) throws Exception;

    public int updateMemberAddrDtl(Map<String, ?> pmParam) throws Exception;

    public int deleteMemberAddrDtl(Map<String, ?> pmParam) throws Exception;

    public int getMemberAddrDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getMemberAddrDtlList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getMemberAddrDtlDtpt(String psParam) throws Exception;

    public int getMemberAddrDtlHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getMemberAddrDtlHstrList(Map<String, ?> pmParam) throws Exception;

}