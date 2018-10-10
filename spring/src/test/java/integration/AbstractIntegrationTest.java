package integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.lambda.playground.PlaygroundApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@ActiveProfiles({"integration"})
@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {
      PlaygroundApplication.class,
      SwaggerClientApi.class,
      AbstractIntegrationTest.Config.class,
    })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTest {

  private static int REDIS_CONTAINER_PORT = 6379;
  private static final String REDIS_IMAGE_VERSION = "redis:4";

  @LocalServerPort protected int randomServerPort;

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

  @TestConfiguration
  static class Config {

    @Bean
    public GenericContainer redisContainer() {
      GenericContainer redisContainer = new GenericContainer(REDIS_IMAGE_VERSION)
          .withExposedPorts(REDIS_CONTAINER_PORT);
      redisContainer.start();
      return redisContainer;
    }

    @Primary
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
      return new LettuceConnectionFactory(redisContainer().getContainerIpAddress(),
          redisContainer().getFirstMappedPort());
    }

  }
}
