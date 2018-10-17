package base;

import base.storage.TestStorageConfig;
import com.github.lambda.gateway.GatewayApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@ActiveProfiles({"functional"})
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = {
        GatewayApplication.class,
        TestStorageConfig.class,
    }
)
@AutoConfigureMockMvc
public @interface ControllerTest {
}
