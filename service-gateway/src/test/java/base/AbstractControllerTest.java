package base;

import com.github.lambda.gateway.GatewayApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles({"functional"})
@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {
      GatewayApplication.class,
      TestRedisConfig.class,
    })
@AutoConfigureMockMvc
public class AbstractControllerTest {

  @Autowired private MockMvc mvc;
}
