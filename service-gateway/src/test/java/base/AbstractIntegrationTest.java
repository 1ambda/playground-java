package base;

import base.storage.TestStorageConfig;
import base.swagger.SwaggerClientApi;
import com.github.lambda.gateway.GatewayApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Abstract class for testing the application via end-to-end.
 */
@ActiveProfiles({"integration"})
@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {
        GatewayApplication.class,
        SwaggerClientApi.class,
        TestStorageConfig.class,
    }
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTest {

  @LocalServerPort
  protected int randomServerPort;

  public String getBaseUrl() {
    return new StringBuilder()
        .append("http://localhost:")
        .append(randomServerPort)
        .append("/api")
        .toString();
  }

  public List<String> getRedisKeys(RedisTemplate<String, String> redisTemplate) {

    Set<byte[]> rawKeys = redisTemplate.getConnectionFactory().getConnection().keys("*".getBytes());

    List<String> keys = new ArrayList<>();
    Iterator<byte[]> it = rawKeys.iterator();

    while (it.hasNext()) {
      byte[] data = it.next();
      keys.add(new String(data, 0, data.length));
    }

    return keys;
  }
}
