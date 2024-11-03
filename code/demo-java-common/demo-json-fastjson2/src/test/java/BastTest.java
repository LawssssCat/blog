import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.IOUtils;
import org.mockito.internal.util.io.IOUtil;

import java.io.IOException;
import java.io.InputStream;

abstract class BastTest {
    <T> T parseJson(String path, Class<T> clazz) {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(path)) {
            String json = IOUtils.toString(resourceAsStream);
            return JSONObject.parseObject(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
