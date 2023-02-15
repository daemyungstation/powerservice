package powerservice.common.util;

public class Config {
    private static Config _instance = null;

    private String realPath;

    private Config() {
    }

    public static Config getInstance() {
        if(_instance == null) {
            synchronized(Config.class) {
                if(_instance == null) {
                    _instance = new Config();
                }
            }
        }

        return _instance;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
}
