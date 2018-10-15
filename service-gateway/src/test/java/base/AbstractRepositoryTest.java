package base;

import com.github.lambda.gateway.GatewayApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles({"unit"})
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = GatewayApplication.class )
public class AbstractRepositoryTest {

  @Autowired
  protected TestEntityManager entityManager;
}
