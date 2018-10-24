package com.github.lambda.gateway.environment;

import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.swagger.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local"})
@Configuration
public class LocalEnvironment implements Environment {

  @Autowired
  private UserService userService;

  @Value("${application.gateway.test.customer-username}")
  private String testCustomerUsername;

  @Value("${application.gateway.test.customer-password}")
  private String testCustomerPassword;

  @Value("${application.gateway.test.customer-email}")
  private String testCustomerEmail;

  @Override
  public void setup() {
    addTestCustomer();
  }

  public void addTestCustomer() {
    User existing = userService.getUserByUsername(testCustomerUsername);

    if (existing != null) {
      return;
    }

    String name = testCustomerUsername.substring(0, 1).toUpperCase() +
        testCustomerUsername.substring(1);

    UserDTO request = UserDTO.builder()
        .username(testCustomerUsername)
        .password(testCustomerPassword)
        .email(testCustomerUsername + "test@.com")
        .address("Unknown")
        .provider(AuthIdentity.Provider.PASSWORD.value())
        .name(name)
        .build();

    userService.handleAddNewCustomerRequest(request);
  }

}
