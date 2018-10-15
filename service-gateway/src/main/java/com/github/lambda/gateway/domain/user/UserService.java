package com.github.lambda.gateway.domain.user;

import javax.transaction.Transactional;

import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.swagger.model.UserDTO;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
  private PasswordEncoder passwordEncoder;
  private UserFactory userFactory;
  private UserActionFacade userActionFacade;
  private UserQueryFacade userQueryFacade;

  @Autowired
  public UserService(PasswordEncoder passwordEncoder,
                     UserFactory userFactory,
                     UserActionFacade userActionFacade,
                     UserQueryFacade userQueryFacade) {

    this.passwordEncoder = passwordEncoder;
    this.userFactory = userFactory;
    this.userActionFacade = userActionFacade;
    this.userQueryFacade = userQueryFacade;
  }

  @Transactional
  public User getUserByUsername(String username) {
    User user = userQueryFacade.getUserByUsername(username);

    return user;
  }

  @Transactional UserDTO getUserDTOByUsername(String username) {
    User user = userQueryFacade.getUserByUsername(username);
    UserDTO dto = userFactory.convertToUserDTO(user);

    return dto;
  }

  @Transactional(rollbackOn = Exception.class)
  public UserDTO addNewCustomer(UserDTO userDTO) {

    AuthIdentity.Provider provider = AuthIdentity.Provider.valueOf(userDTO.getProvider());
    String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

    Preconditions.checkArgument(AuthIdentity.Provider.PASSWORD.equals(provider),
                                "PASSWORD provider is available for now");
    Preconditions.checkArgument(!StringUtils.isEmpty(encodedPassword),
                                "Invalid password encoding");

    User user = userActionFacade.addNewCustomer(userDTO, provider, encodedPassword);
    UserDTO dto = userFactory.convertToUserDTO(user);

    return dto;
  }
}
