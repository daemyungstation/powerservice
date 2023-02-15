package powerservice.core.bean;

public class ActionResult {
    private ActionResultType result;
    private String token;
    private String errMsg;
    private Object data;

    public ActionResult() {
        result = ActionResultType.OK;
    }

    public ActionResultType getResult() {
        return result;
    }

    public void setResult(ActionResultType result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
