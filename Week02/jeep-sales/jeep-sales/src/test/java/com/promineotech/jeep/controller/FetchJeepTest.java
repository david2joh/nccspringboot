/**
 * 
 */
package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
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

class FetchJeepTest {
  @Autowired
  @Getter
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int serverPort;
  //
  // Here for learning purposes
  //   String getBaseUri() {
  //    return String.format("http://localhost:%d/jeeps", serverPort);
  //  }
//   -- Test routine to see if anything is working at all -- good illustration
//  @Autowired JdbcTemplate jdbcTemplate;
//  @Test
//  void throwawayTest() {
//    int numrows =JdbcTestUtils.countRowsInTable(jdbcTemplate,"customers");
//    System.out.println(numrows);
//  }
//  
  
  @Test
  void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Sport";
    String uri = String.format("http://localhost:%d/jeeps?model=%s&trim=%s",
        serverPort, model , trim);

//      ResponseEntity<Jeep> response =  getRestTemplate().getForEntity(uri, Jeep.class);
//      assertThat(response.getStatusCode() == HttpStatus.OK);

    //Whoa even after reading the Super Type Token blog this was a bit mind bending
    //But to not use an anonymous try this
    ParameterizedTypeReference<List<Jeep>> typeRef =
        new ParameterizedTypeReference<List<Jeep>>() {};

        ResponseEntity<List<Jeep>> response
        = restTemplate.exchange(uri, HttpMethod.GET, null, typeRef);
//
//    ResponseEntity<List<Jeep>> response
//       = restTemplate.exchange(uri, HttpMethod.GET, null,
//           new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode() == HttpStatus.OK);
        
        // Check the actual list is the same as the expected List
        List<Jeep> expected = buildExpected();
        assertThat(response.getBody()).isEqualTo(expected);

  }

  /**
   * @return
   */
  protected List<Jeep> buildExpected() {
    List<Jeep> list = new LinkedList<>();
    // @formatter:off
    list.add(Jeep.builder()
        .modelID(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(2)
        .wheelSize(17)
        .basePrice(new BigDecimal("28475.00"))
        .build()
        );
    
    list.add(Jeep.builder()
        .modelID(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(4)
        .wheelSize(17)
        .basePrice(new BigDecimal("31975.00"))
        .build()
        );

    // @formatter:on
    return list;
  }

}
