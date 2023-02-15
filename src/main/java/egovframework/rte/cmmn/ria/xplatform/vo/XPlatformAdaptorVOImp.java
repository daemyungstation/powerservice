package egovframework.rte.cmmn.ria.xplatform.vo;

import javax.servlet.http.HttpServletRequest;

import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.tx.HttpPlatformRequest;

import egovframework.rte.cmmn.ria.support.XpfAdaptor;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public class XPlatformAdaptorVOImp extends XpfAdaptor {

    @Override
    public Object converte4In(HttpPlatformRequest HttpPlatformRequest,
            HttpServletRequest request) {

        //log.debug("[XPlatformAdaptorVOImp.java] converte4In start");
        XPlatformObjectDTO dto = new XPlatformObjectDTO();
        PlatformData pfd = HttpPlatformRequest.getData();
        //log.debug("[XPlatformAdaptorVOImp.java] Request Variable & DataSet debug");
        //log.debug("[XPlatformAdaptorVOImp.java] " + pfd.saveXml());

        dto.setVariableMap(pfd.getVariableList());
        dto.setTranInfoMap(pfd.getDataSetList());
        dto.setInDataSetMap(pfd.getDataSetList());

        return dto;
    }

    @Override
    public Class getModelName() {
        return (new XPlatformObjectDTO().getClass());
    }

}
