package powerservice.common.util.converter.ajax;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {
    private static ObjectMapper _instance = null;

    private ObjectMapperFactory() {
    }

    public static ObjectMapper getInstance() {
        if(_instance == null) {
            synchronized(ObjectMapper.class) {
                if(_instance == null) {
                    ObjectMapper mapper = new ObjectMapper();

                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

                    _instance = mapper;
                }
            }
        }

        return _instance;
    }
}
