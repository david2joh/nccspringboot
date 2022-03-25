/**
 * 
 */
package com.promineotech.jeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.promineotech.ComponentScanMarker;

/**
 * @author D
 *
 */
//AH ha !  using scanBasePackageClasses = ComponentScanMarker just looks for the 
// java class/interface ComponentScanMarker and then uses the package
// where that named class is as the base package to start scanning
// Note , this can take an array of strings { ... } and add FILTER conditions
// This xxx.class could be named anything we liked as long as we are consistent.
// This feature introduced in SpringBoot 1.3 and substitutes for ComponentScan if used
//   SpringBoot annotations make my head hurt 
//@SpringBootApplication(scanBasePackageClasses = ComponentScanMarker.class)
@SpringBootApplication
// -- Test @ComponentScan with value alias. Superseded by marker class 
// @ComponentScan("com.promineotech") 
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
public class JeepSales {

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(JeepSales.class, args);
  }

}
