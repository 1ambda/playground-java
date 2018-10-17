package base.storage;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.testcontainers.containers.GenericContainer;

@TestConfiguration
public class TestStorageConfig {
  private static int REDIS_CONTAINER_PORT = 6379;
  private static final String REDIS_IMAGE_VERSION = "redis:4";

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
