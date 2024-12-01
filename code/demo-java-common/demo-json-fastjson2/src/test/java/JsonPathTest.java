import com.alibaba.fastjson2.JSONPath;
import org.junit.jupiter.api.Test;

public class JsonPathTest extends BastTest {
    @Test
    void test() {
        String json = parseJson("demo.json", String.class);
        Object result = JSONPath.eval(json, "$.data[0][?(@.status == 'valid')]");
        System.out.println("result = " + result);
    }
}
