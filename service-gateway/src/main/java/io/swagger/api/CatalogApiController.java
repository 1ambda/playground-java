package io.swagger.api;

import io.swagger.model.CategoryListDTO;
import io.swagger.model.Failure;
import io.swagger.model.ProductDTO;
import io.swagger.model.ProductListDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-15T16:52:04.159+09:00")

@Controller
public class CatalogApiController implements CatalogApi {

    private static final Logger log = LoggerFactory.getLogger(CatalogApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public CatalogApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<CategoryListDTO> findAllCategories() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<CategoryListDTO>(objectMapper.readValue("{  \"items\" : [ {    \"path\" : \"path\",    \"displayName\" : \"displayName\",    \"name\" : \"name\",    \"description\" : \"description\",    \"parentCategoryId\" : 6,    \"id\" : 0  }, {    \"path\" : \"path\",    \"displayName\" : \"displayName\",    \"name\" : \"name\",    \"description\" : \"description\",    \"parentCategoryId\" : 6,    \"id\" : 0  } ]}", CategoryListDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<CategoryListDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<CategoryListDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ProductDTO> findOneProduct(@ApiParam(value = "",required=true) @PathVariable("productId") Long productId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ProductDTO>(objectMapper.readValue("{  \"item\" : {    \"imageId\" : 2,    \"categoryPath\" : \"categoryPath\",    \"imagePath\" : \"imagePath\",    \"categoryDisplayName\" : \"categoryDisplayName\",    \"description\" : \"description\",    \"createdAt\" : 6,    \"price\" : 5,    \"name\" : \"name\",    \"onSale\" : \"onSale\",    \"id\" : 0,    \"imageType\" : \"imageType\",    \"categoryId\" : 5,    \"updatedAt\" : 1  },  \"options\" : [ {    \"price\" : 9,    \"name\" : \"name\",    \"description\" : \"description\",    \"onSale\" : \"onSale\",    \"id\" : 7  }, {    \"price\" : 9,    \"name\" : \"name\",    \"description\" : \"description\",    \"onSale\" : \"onSale\",    \"id\" : 7  } ]}", ProductDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ProductDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ProductDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ProductListDTO> findPaginatedProducts(@ApiParam(value = "",required=true) @PathVariable("productId") Long productId,@ApiParam(value = "",required=true) @PathVariable("page") Long page,@ApiParam(value = "",required=true) @PathVariable("count") Long count) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ProductListDTO>(objectMapper.readValue("{  \"pagination\" : {    \"currentPageOffset\" : 6,    \"itemCountPerPage\" : 0,    \"totalItemCount\" : 1  },  \"items\" : [ {    \"item\" : {      \"imageId\" : 2,      \"categoryPath\" : \"categoryPath\",      \"imagePath\" : \"imagePath\",      \"categoryDisplayName\" : \"categoryDisplayName\",      \"description\" : \"description\",      \"createdAt\" : 6,      \"price\" : 5,      \"name\" : \"name\",      \"onSale\" : \"onSale\",      \"id\" : 0,      \"imageType\" : \"imageType\",      \"categoryId\" : 5,      \"updatedAt\" : 1    },    \"options\" : [ {      \"price\" : 9,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 7    }, {      \"price\" : 9,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 7    } ]  }, {    \"item\" : {      \"imageId\" : 2,      \"categoryPath\" : \"categoryPath\",      \"imagePath\" : \"imagePath\",      \"categoryDisplayName\" : \"categoryDisplayName\",      \"description\" : \"description\",      \"createdAt\" : 6,      \"price\" : 5,      \"name\" : \"name\",      \"onSale\" : \"onSale\",      \"id\" : 0,      \"imageType\" : \"imageType\",      \"categoryId\" : 5,      \"updatedAt\" : 1    },    \"options\" : [ {      \"price\" : 9,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 7    }, {      \"price\" : 9,      \"name\" : \"name\",      \"description\" : \"description\",      \"onSale\" : \"onSale\",      \"id\" : 7    } ]  } ]}", ProductListDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ProductListDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ProductListDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
