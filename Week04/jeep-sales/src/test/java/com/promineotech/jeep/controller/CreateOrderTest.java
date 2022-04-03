/**
 * 
 */
package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;
import lombok.Getter;

/**
 * @author D
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
  "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
  "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
  config=@SqlConfig(encoding = "utf-8")
  )
class CreateOrderTest {
  @Autowired
  @Getter
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int serverPort;
  
  @Test
  void testCreateOrderReturnsSuccess201() {
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    String body = createOrderBody();
    String uri = String.format("http://localhost:%d/orders", serverPort);
    HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);
    ParameterizedTypeReference<Order> typeRef =
        new ParameterizedTypeReference<>() {};
        
    ResponseEntity<Order> response =
        restTemplate.exchange(uri, HttpMethod.POST, bodyEntity, typeRef);
    //Did the POST call succeed?
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
 //   Did the POST call actually create what was requested?
    assertThat(response.getBody()).isNotNull();
    Order order = response.getBody();
    assertThat(order.getCustomer().getCustomerId()).isEqualTo("STERN_TORO");
    assertThat(order.getModel().getModelID()).isEqualTo(JeepModel.GRAND_CHEROKEE);
    assertThat(order.getModel().getTrimLevel()).isEqualTo("Summit");
    assertThat(order.getModel().getNumDoors()).isEqualTo(4);
    assertThat(order.getColor().getColorId()).isEqualTo("EXT_JETSET_BLUE");
    assertThat(order.getEngine().getEngineId()).isEqualTo("6_4_GAS");
    assertThat(order.getTire().getTireId()).isEqualTo("265_GOODYEAR");
    assertThat(order.getOptions()).hasSize(3);
  }
  
  
  public String createOrderBody() {
    // @formatter:off
   String body = "{"
        +"\n"+"  \"customer\":\"STERN_TORO\","
        +"\n"+"  \"model\":\"GRAND_CHEROKEE\","
        +"\n"+"  \"trim\":\"Summit\","
        +"\n"+"  \"doors\":\"4\","
        +"\n"+"  \"color\":\"EXT_JETSET_BLUE\","
        +"\n"+"  \"engine\":\"6_4_GAS\","
        +"\n"+"  \"tire\":\"265_GOODYEAR\","
        +"\n"+"  \"options\":["
        +"\n"+"  \"DOOR_MOPAR_REINFORCE\","
        +"\n"+"  \"EXT_MOPAR_HEAD_LED\","
        +"\n"+"  \"INT_MOPAR_COLR\""
        +"]\n"
        +"}";
   // @formatter:on

   //printing body yields :  formated for JSON in K:V pairs
//    {
//      "customer":"STERN_TORO",
//      "model":"GRAND_CHEROKEE",
//      "trim":"Summit",
//      "doors":"4",
//      "color":"EXT_JETSET_BLUE",
//      "engine":"6_4_GAS",
//      "tire":"265_GOODYEAR",
//      "options":[
//      "DOOR_MOPAR_REINFORCE",
//      "EXT_MOPAR_HEAD_LED",
//      "INT_MOPAR_COLR"]
//    }
    
    return body;
  }
  
}
