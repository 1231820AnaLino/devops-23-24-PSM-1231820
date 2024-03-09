package com.greglturnquist.payroll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Joaquim", "Sequeira", "Doctor", 35);
    }

    @Test
    public void testEmployeeConstructorValidArguments() {
        assertNotNull(employee);
    }

    @Test
    public void employeeConstructorInvalidFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(null, "Sequeira", "Doctor", 35);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", "Sequeira", "Doctor", 35);
        });
    }

    @Test
    public void employeeConstructorInvalidLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Joaquim", null, "Doctor", 35);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Joaquim", "", "Doctor", 35);
        });
    }

    @Test
    public void employeeConstructorInvalidDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Joaquim", "Sequeira", null, 35);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Joaquim", "Sequeira", "", 35);
        });
    }

    @Test
    public void employeeConstructorInvalidJobYears() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Joaquim", "Sequeira", "Doctor", -1);
        });
    }

    @Test
    public void employeeEquals() {
        Employee employee1 = new Employee("Joaquim", "Sequeira", "Doctor", 35);
        Employee employee2 = new Employee("Quim", "Sequeira", "Doctor", 35);
        assertTrue(employee.equals(employee1));
        assertFalse(employee.equals(employee2));
    }

    @Test
    void getID() {
        Long idExpected = 123L;
        employee.setId(idExpected);
        assertEquals(idExpected, employee.getId());
    }

    @Test
    void setID() {
        Long idExpected = 124L;
        employee.setId(idExpected);
        assertEquals(idExpected, employee.getId());
    }

    @Test
    void getFirstName() {
        assertEquals("Joaquim", employee.getFirstName());
    }

    @Test
    void setFirstName() {
        employee.setFirstName("Filipe");
        assertEquals("Filipe", employee.getFirstName());
    }


    @Test
    void getLastName() {
        assertEquals("Sequeira", employee.getLastName());
    }

    @Test
    void setLastName() {
        employee.setLastName("Rodrigues");
        assertEquals("Rodrigues", employee.getLastName());
    }

    @Test
    void getDescription() {
        assertEquals("Doctor", employee.getDescription());
    }

    @Test
    void setDescription() {
        employee.setDescription("Engineering");
        assertEquals("Engineering", employee.getDescription());
    }

    @Test
    void getJobYears() {
        assertEquals(35, employee.getJobYears());
    }

    @Test
    void setJobYears() {
        employee.setJobYears(36);
        assertEquals(36, employee.getJobYears());
    }


    @Test
    void testToString() {
        long idExpected = 123;
        employee.setId(idExpected);
        String expected = "Employee{id=123, firstName='Joaquim', lastName='Sequeira', description='Doctor', jobYears='35'}";
        String result = employee.toString();
        assertEquals(expected, result);
    }

    @Test
    void testHashCodeConsistency() {
        Employee employee1 = new Employee("Joaquim", "Sequeira", "Doctor", 35);
        assertEquals(employee.hashCode(), employee1.hashCode());
    }

    @Test
    void testHashCodeInconsistency() {
        Employee employee1 = new Employee("Eduardo", "Lopes", "Engineer", 5);

        assertNotEquals(employee.hashCode(), employee1.hashCode());
    }

}