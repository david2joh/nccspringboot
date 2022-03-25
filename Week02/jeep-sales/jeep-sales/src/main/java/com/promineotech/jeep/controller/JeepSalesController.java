/**
 * 
 */
package com.promineotech.jeep.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * @author D
 *
 */
@RequestMapping("/jeeps")

@OpenAPIDefinition(info = @Info(title = "Jeep Sales Service"), servers = {
    @Server(url = "http://localhost:8080", description = "Local server.")})

    
public interface JeepSalesController {
  @Operation(
      summary = "Returns a List of Jeeps",
      description = "Returns a list of Jeeps , optional params  model , trim",
      responses = {
          @ApiResponse(responseCode = "200", description = "Success, A list of Jeeps",
              content = @Content(
                  mediaType = "application/json" , 
                  schema  = @Schema(implementation = Jeep.class))),
          @ApiResponse(responseCode = "400", description = "Request parameters invlaid",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", description = "No Jeeps found with given model/trim",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "Error 500 ",
              content = @Content(mediaType = "application/json"))},
      parameters = {
          @Parameter(name = "model" , allowEmptyValue = false , description = "The model Name"),
          @Parameter(name = "trim" , allowEmptyValue = false , description = "The vehicle trim level")
      }
)

@GetMapping
@ResponseStatus(code=HttpStatus.OK)
List<Jeep> fetchJeeps(
    @RequestParam JeepModel model,
//    @RequestParam(required = false) String model,
    @RequestParam String trim
    );
}
