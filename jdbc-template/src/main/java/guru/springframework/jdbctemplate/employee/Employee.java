package guru.springframework.jdbctemplate.employee;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Employee {

  private long id;

  public void setId(long id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setYearlyIncome(long yearlyIncome) {
    this.yearlyIncome = yearlyIncome;
  }

  public long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public long getYearlyIncome() {
    return yearlyIncome;
  }

  private String firstName;
  private String lastName;
  private long yearlyIncome;

  public Employee(long id, String firstName, String lastName, long yearlyIncome) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.yearlyIncome = yearlyIncome;
  }

  public Map<String, Object> toMap() {
    Map<String, Object> values = new HashMap<>();
    values.put("first_name", firstName);
    values.put("last_name", lastName);
    values.put("yearly_income", yearlyIncome);

    return values;
  }

}