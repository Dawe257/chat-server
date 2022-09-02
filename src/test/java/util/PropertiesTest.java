package util;

import com.dzhenetl.util.PropertiesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PropertiesTest {

    @Test
    void propertiesReadShouldBeCorrect() {
        Map<String, String> propertiesExpected  = new HashMap<>() {{
            put("port", "23445");
            put("log.path", "file.log");
        }};

        propertiesExpected.forEach((key, expected) -> {
            String actual = PropertiesUtil.get(key);
            Assertions.assertEquals(expected, actual);
        });
    }
}
