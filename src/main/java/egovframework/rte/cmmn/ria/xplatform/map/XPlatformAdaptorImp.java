package egovframework.rte.cmmn.ria.xplatform.map;

import javax.servlet.http.HttpServletRequest;

import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.tx.HttpPlatformRequest;

import egovframework.rte.cmmn.ria.support.XpfAdaptor;
/**
 *
 * @author yimoc@tobesoft.com
 *
 */
public class XPlatformAdaptorImp extends XpfAdaptor {

    @Override
    public Object converte4In(HttpPlatformRequest httpPlatformRequest,
            HttpServletRequest request) {

        //log.debug("[XPlatformAdaptorImp.java] XPlatformAdaptorImp.convert4In() start");
        XPlatformMapDTO dto = new XPlatformMapDTO();
        PlatformData pfd = httpPlatformRequest.getData();
        //log.debug("[XPlatformAdaptorImp.java] Request Variable & DataSet debug");
        //log.debug("[XPlatformAdaptorImp.java] " + pfd.saveXml());

        dto.setVariableMap(pfd.getVariableList());
        dto.setTranInfoMap(pfd.getDataSetList());
        dto.setInDataSetMap(pfd.getDataSetList());

        return dto;
    }

    @Override
    public Class getModelName() {
        return (new XPlatformMapDTO().getClass());
    }

}
