/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.github.lambda.gateway.swagger.server.api;

import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.PaginatedProductDTO;
import com.github.lambda.gateway.swagger.model.ProductDTO;
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

@Api(value = "CatalogController", description = "the CatalogController API")
public interface CatalogControllerApi {

    Logger log = LoggerFactory.getLogger(CatalogControllerApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "", nickname = "findAllCategories", notes = "", response = CategoryListDTO.class, tags={ "catalog-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = CategoryListDTO.class),
        @ApiResponse(code = 200, message = "error", response = Failure.class) })
    @RequestMapping(value = "/catalog/categories",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<CategoryListDTO> findAllCategories() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"items\" : [ {    \"path\" : \"path\",    \"displayName\" : \"displayName\",    \"name\" : \"name\",    \"description\" : \"description\",    \"parentCategoryId\" : 6,    \"id\" : 0  }, {    \"path\" : \"path\",    \"displayName\" : \"displayName\",    \"name\" : \"name\",    \"description\" : \"description\",    \"parentCategoryId\" : 6,    \"id\" : 0  } ]}", CategoryListDTO.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CatalogControllerApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "", nickname = "findOneProduct", notes = "", response = ProductDTO.class, tags={ "catalog-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ProductDTO.class),
        @ApiResponse(code = 200, message = "error", response = Failure.class) })
    @RequestMapping(value = "/catalog/product/{productId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ProductDTO> findOneProduct(@ApiParam(value = "",required=true) @PathVariable("productId") Long productId) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"imageId\" : 2,  \"categoryPath\" : \"categoryPath\",  \"imagePath\" : \"imagePath\",  \"categoryDisplayName\" : \"categoryDisplayName\",  \"description\" : \"description\",  \"createdAt\" : 6,  \"price\" : 5,  \"name\" : \"name\",  \"onSale\" : \"onSale\",  \"id\" : 0,  \"imageType\" : \"imageType\",  \"categoryId\" : 5,  \"updatedAt\" : 1}", ProductDTO.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CatalogControllerApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "", nickname = "findPaginatedProducts", notes = "", response = PaginatedProductDTO.class, tags={ "catalog-controller", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PaginatedProductDTO.class),
        @ApiResponse(code = 200, message = "error", response = Failure.class) })
    @RequestMapping(value = "/catalog/products",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<PaginatedProductDTO> findPaginatedProducts(@ApiParam(value = "") @Valid @RequestParam(value = "page", required = false) Long page,@ApiParam(value = "") @Valid @RequestParam(value = "count", required = false) Long count) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"pagination\" : {    \"currentPageOffset\" : 5,    \"itemCountPerPage\" : 1,    \"totalItemCount\" : 5  },  \"products\" : [ {    \"item\" : {      \"imageId\" : 2,      \"categoryPath\" : \"categoryPath\",      \"imagePath\" : \"imagePath\",      \"categoryDisplayName\" : \"categoryDisplayName\",      \"description\" : \"description\",      \"createdAt\" : 6,      \"price\" : 5,      \"name\" : \"name\",      \"onSale\" : \"onSale\",      \"id\" : 0,      \"imageType\" : \"imageType\",      \"categoryId\" : 5,      \"updatedAt\" : 1    },    \"options\" : [ {      \"price\" : 6,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 0    }, {      \"price\" : 6,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 0    } ]  }, {    \"item\" : {      \"imageId\" : 2,      \"categoryPath\" : \"categoryPath\",      \"imagePath\" : \"imagePath\",      \"categoryDisplayName\" : \"categoryDisplayName\",      \"description\" : \"description\",      \"createdAt\" : 6,      \"price\" : 5,      \"name\" : \"name\",      \"onSale\" : \"onSale\",      \"id\" : 0,      \"imageType\" : \"imageType\",      \"categoryId\" : 5,      \"updatedAt\" : 1    },    \"options\" : [ {      \"price\" : 6,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 0    }, {      \"price\" : 6,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 0    } ]  } ]}", PaginatedProductDTO.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default CatalogControllerApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
