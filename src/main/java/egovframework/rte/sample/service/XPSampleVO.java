package egovframework.rte.sample.service;

import java.util.List;

import egovframework.rte.cmmn.ria.xplatform.vo.RowType;

public class XPSampleVO implements RowType{
	/** 아이디 */
    private String id;
    
    /** 이름 */
    private String name;
    
    /** 내용 */
    private String description;
    
    /** 사용여부 */
    private String useYn;
    
    /** 등록자 */
    private String regUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegUser() {
        return regUser;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }
	int rowType;				//Row Type 를 저장하는 변수 
	
	
	

	public void setRowType(int type) {
		rowType = type;
		
	}
	public int getRowType() {
		return rowType;
	}
	
	
	
			
}
