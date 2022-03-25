/**
 * 
 */
package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author D
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jeep implements Comparable<Jeep> {
  private Long modelPk;
  private JeepModel modelID;
  private String trimLevel;
  private int numDoors;
  private int wheelSize;
  private BigDecimal basePrice;
  
  @JsonIgnore
  public Long getModlePK() { return modelPk; }
    
  @Override
  public int compareTo(Jeep that) {
      return Comparator
        .comparing(Jeep::getModelID)
        .thenComparing(Jeep::getTrimLevel)
        .thenComparing(Jeep::getNumDoors)
        .thenComparing(Jeep::getWheelSize)
        .compare(this, that);
  }
}
