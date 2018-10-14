package base;

import com.github.lambda.playground.PlaygroundApplication;
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
@ContextConfiguration(classes = PlaygroundApplication.class )
public class AbstractRepositoryTest {

  @Autowired
  protected TestEntityManager entityManager;
}
