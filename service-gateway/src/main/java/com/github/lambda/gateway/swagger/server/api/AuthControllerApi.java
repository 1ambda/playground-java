/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.github.lambda.gateway.swagger.server.api;

import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Api(value = "AuthController", description = "the AuthController API")
public interface AuthControllerApi {

    Logger log = LoggerFactory.getLogger(AuthControllerApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "", nickname = "login", notes = "", response = UserDTO.class, tags={ "auth-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
        @ApiResponse(code = 200, message = "error", response = Failure.class) })
    @RequestMapping(value = "/auth/login",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<UserDTO> login() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"password\" : \"password\",  \"address\" : \"address\",  \"provider\" : \"provider\",  \"roles\" : [ \"roles\", \"roles\" ],  \"name\" : \"name\",  \"email\" : \"email\",  \"username\" : \"username\"}", UserDTO.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthControllerApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "", nickname = "logout", notes = "", tags={ "auth-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 200, message = "error", response = Failure.class) })
    @RequestMapping(value = "/auth/logout",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    default ResponseEntity<Void> logout() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthControllerApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "", nickname = "register", notes = "", response = UserDTO.class, tags={ "auth-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
        @ApiResponse(code = 200, message = "error", response = Failure.class) })
    @RequestMapping(value = "/auth/register",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<UserDTO> register(@ApiParam(value = ""  )  @Valid @RequestBody UserDTO body) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"password\" : \"password\",  \"address\" : \"address\",  \"provider\" : \"provider\",  \"roles\" : [ \"roles\", \"roles\" ],  \"name\" : \"name\",  \"email\" : \"email\",  \"username\" : \"username\"}", UserDTO.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthControllerApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "", nickname = "whoiam", notes = "", response = UserDTO.class, tags={ "auth-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
        @ApiResponse(code = 200, message = "error", response = Failure.class) })
    @RequestMapping(value = "/auth/whoiam",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<UserDTO> whoiam() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"password\" : \"password\",  \"address\" : \"address\",  \"provider\" : \"provider\",  \"roles\" : [ \"roles\", \"roles\" ],  \"name\" : \"name\",  \"email\" : \"email\",  \"username\" : \"username\"}", UserDTO.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuthControllerApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
