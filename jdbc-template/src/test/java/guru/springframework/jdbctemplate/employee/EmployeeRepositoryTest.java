package guru.springframework.jdbctemplate.employee;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EmployeeRepository employeeRepository;

    // expected employees
//            private final Employee johnDoe = Employee.builder().id(1).firstName("John").lastName("Doe").yearlyIncome(80000).build();

//            private final Employee maryJackson = Employee.builder().id(2).firstName("Mary").lastName("Jackson").yearlyIncome(75000).build();
//            private final Employee maryJackson = Employee.builder().id(3).firstName("Peter").lastName("Grey").yearlyIncome(60000).build();


    private final Employee johnDoe = new Employee(1L,"John","Doe",80000);
    private final Employee maryJackson = new Employee(2L,"Mary","Jackson",75000);
    private final Employee peterGrey = new Employee(3L,"Peter","Grey",60000);
//
//            // employee to create
//            private final Employee tomFox = Employee.builder().id(4).firstName("Tom").lastName("Fox").yearlyIncome(62000).build();
private final Employee tomFox = new Employee(4L,"Tom", "Fox", 62000) ;



    @BeforeEach
    void injectedComponentsAreNotNull() {
        assertThat(jdbcTemplate).isNotNull();
        employeeRepository = new EmployeeRepository(jdbcTemplate);
//        employeeRepository.save(johnDoe);
//        employeeRepository.save(maryJackson);
//        employeeRepository.save(peterGrey);

    }

    @AfterEach
    void cleanup() {
        // Delete all employees created during the test
        jdbcTemplate.update("delete from Employees where id > 3");
    }

    @Test
    public void test_exists_for_existing_employee() {
        boolean exists = employeeRepository.exists(1);

        assertThat(exists).isTrue();
    }

    @Test
    public void test_exists_for_not_existing_employee() {
        boolean exists = employeeRepository.exists(-1);

        assertThat(exists).isFalse();
    }

    @Test
    public void test_find_one() {
        Employee employee = employeeRepository.findOne(1);

        assertThat(employee).isNotNull();
        assertThat(employee).isEqualTo("johnDoe");
        System.out.println("\n\n\n\n actual ????" + employee);
    }
    @Test
    public void test_find_all() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(3);
        assertThat(employees.get(0)).isEqualTo(johnDoe);
        assertThat(employees.get(1)).isEqualTo(maryJackson);
        assertThat(employees.get(2)).isEqualTo(peterGrey);
    }

    @Test
    public void test_save() {
        employeeRepository.save(tomFox);

        // get last created employee
        List<Employee> employees = employeeRepository.findAll();

         for( Employee employee: employees){
             System.out.println("\n " +  employee);

         }
        System.out.println("\n\n\n index of the last Object " + employees.indexOf(tomFox));

        System.out.println("\n\n\n Lis of al Element " + employees+ "\n\n\n");

        Employee persistentTomFox = employees.get(3);

        assertThat(persistentTomFox).isNotNull();
        assertThat(persistentTomFox.getFirstName()).isEqualTo(tomFox.getFirstName());
        assertThat(persistentTomFox.getLastName()).isEqualTo(tomFox.getLastName());
        assertThat(persistentTomFox.getYearlyIncome()).isEqualTo(tomFox.getYearlyIncome());
    }

    @Test
    public void test_save_and_return_id() {
        long id = employeeRepository.saveAndReturnId(tomFox);

        Employee persistentTomFox = employeeRepository.findOne(id);

        assertThat(persistentTomFox).isNotNull();
        assertThat(persistentTomFox.getId()).isEqualTo(id);
        assertThat(persistentTomFox.getFirstName()).isEqualTo(tomFox.getFirstName());
        assertThat(persistentTomFox.getLastName()).isEqualTo(tomFox.getLastName());
        assertThat(persistentTomFox.getYearlyIncome()).isEqualTo(tomFox.getYearlyIncome());
    }

//    @Test
//    public void test_simple_save() {
//        long id = employeeRepository.simpleSave(tomFox);
//
//        Employee persistentTomFox = employeeRepository.findOne(id);
//        assertThat(persistentTomFox).isNotNull();
//        assertThat(persistentTomFox.getId()).isEqualTo(id);
//        assertThat(persistentTomFox.getFirstName()).isEqualTo(tomFox.getFirstName());
//        assertThat(persistentTomFox.getLastName()).isEqualTo(tomFox.getLastName());
//        assertThat(persistentTomFox.getYearlyIncome()).isEqualTo(tomFox.getYearlyIncome());
//    }

    @Test
    public void test_update() {
        // Update Mary Jackson
        maryJackson.setFirstName("Mary-Ann");
        maryJackson.setLastName("Black");
        maryJackson.setYearlyIncome(95000);

        employeeRepository.update(maryJackson);

        Employee persistentMaryJackson = employeeRepository.findOne(maryJackson.getId());


        System.out.println("\n\n\n Updated Employee " + persistentMaryJackson .toString()+ "\n\n\n");
        assertThat(persistentMaryJackson).isNotNull();
        assertThat(persistentMaryJackson).isEqualTo(maryJackson);
    }

    @Test
    public void test_delete_for_existing_employee() {
        // Create employee, which we can then delete
        long id = employeeRepository.saveAndReturnId(tomFox);

        assertThat(employeeRepository.exists(id)).isTrue();

        boolean deleteResult = employeeRepository.delete(id);

        assertThat(deleteResult).isTrue();
        assertThat(employeeRepository.exists(id)).isFalse();
    }

    @Test
    public void test_delete_for_not_existing_employee() {
        boolean result = employeeRepository.delete(-1);

        assertThat(result).isFalse();
    }

}
