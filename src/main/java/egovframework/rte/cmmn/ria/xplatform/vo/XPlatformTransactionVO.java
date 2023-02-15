package egovframework.rte.cmmn.ria.xplatform.vo;

import java.util.List;

public class XPlatformTransactionVO {
    String strInDataset;    // input DataSet 명
    String strInVOClass;	// input DataSet과 매핑될 Value Object class 명
    String strOutDataset;	// output DataSet 명
    String strOutVOClass;	// output DataSet과 매핑될 Value Object calss 명

    public String getStrInDataset() {
        return strInDataset;
    }
    public void setStrInDataset(String strInDataset) {
        this.strInDataset = strInDataset;
    }
    public String getStrInVOClass() {
        return strInVOClass;
    }
    public void setStrInVOClass(String strInVOClass) {
        this.strInVOClass = strInVOClass;
    }
    public String getStrOutDataset() {
        return strOutDataset;
    }
    public void setStrOutDataset(String strOutDataset) {
        this.strOutDataset = strOutDataset;
    }
    public String getStrOutVOClass() {
        return strOutVOClass;
    }
    public void setStrOutVOClass(String strOutVOClass) {
        this.strOutVOClass = strOutVOClass;
    }
}
