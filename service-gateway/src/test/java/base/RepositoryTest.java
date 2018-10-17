package base;

import com.github.lambda.gateway.GatewayApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@ActiveProfiles({"unit"})
@DataJpaTest
@ContextConfiguration(classes = GatewayApplication.class)
@AutoConfigureTestEntityManager
public @interface RepositoryTest {
}
