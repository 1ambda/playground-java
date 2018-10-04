package integration;

import com.github.lambda.playground.PlaygroundApplication;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@Profile({"integration"})
@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"management.port=0"},
    classes = PlaygroundApplication.class
)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@DirtiesContext
public abstract class AbstractIntegrationTest {
  private static int ContainerPort = 6379;

  @ClassRule
  public static GenericContainer redis =
      new GenericContainer("redis:4").withExposedPorts(ContainerPort);

  public static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
      // Spring Boot 2.x.
      TestPropertyValues.of(
              "spring.redis.host:" + redis.getContainerIpAddress(),
              "spring.redis.port:" + redis.getMappedPort(ContainerPort))
          .applyTo(ctx);
    }
  }
}
