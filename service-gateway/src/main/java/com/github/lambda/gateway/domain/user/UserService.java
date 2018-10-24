package com.github.lambda.gateway.domain.user;

import com.github.lambda.gateway.domain.cart.CartService;
import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.swagger.model.UserDTO;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class UserService {
  private PasswordEncoder passwordEncoder;
  private UserConverter userConverter;
  private UserActionFacade userActionFacade;
  private UserQueryFacade userQueryFacade;
  private CartService cartService;

  @Autowired
  public UserService(PasswordEncoder passwordEncoder,
                     UserConverter userConverter,
                     UserActionFacade userActionFacade,
                     UserQueryFacade userQueryFacade,
                     CartService cartService) {

    this.passwordEncoder = passwordEncoder;
    this.userConverter = userConverter;
    this.userActionFacade = userActionFacade;
    this.userQueryFacade = userQueryFacade;
    this.cartService = cartService;
  }

  @Transactional
  public User getUserByUsername(String username) {
    User user = userQueryFacade.getUserByUsername(username);

    return user;
  }

  @Transactional
  UserDTO getUserDTOByUsername(String username) {
    User user = userQueryFacade.getUserByUsername(username);
    UserDTO dto = userConverter.convertToUserDTO(user);

    return dto;
  }

  @Transactional(rollbackOn = Exception.class)
  public UserDTO handleAddNewCustomerRequest(UserDTO userDTO) {

    AuthIdentity.Provider provider = AuthIdentity.Provider.valueOf(userDTO.getProvider());
    String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

    Preconditions.checkArgument(AuthIdentity.Provider.PASSWORD.equals(provider),
                                "PASSWORD provider is available for now");
    Preconditions.checkArgument(!StringUtils.isEmpty(encodedPassword),
                                "Invalid password encoding");

    User user = userActionFacade.addNewCustomer(userDTO, provider, encodedPassword);

    // additional logic for a newly created user.
    Long userId = user.getId();
    cartService.createCart(userId);

    // return response
    UserDTO dto = userConverter.convertToUserDTO(user);
    return dto;
  }
}
