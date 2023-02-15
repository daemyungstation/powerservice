package powerservice.core.bean;

import java.util.HashMap;
import java.util.Map;

public enum ActionResultType {
	OK(0), ERROR(1), UNKNOWN(99);
	
    private int result;

    private static final Map<Integer, ActionResultType> intToTypeMap = new HashMap<Integer, ActionResultType>();
    static {
        for (ActionResultType type : ActionResultType.values()) {
            intToTypeMap.put(type.value(), type);
        }
    }

    public static ActionResultType fromInt(int i) {
    	ActionResultType type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            return ActionResultType.UNKNOWN;
        return type;
    }

    private ActionResultType(int result) {
        this.result = result;
    }

    public int value() {
        return this.result;
    }
}
