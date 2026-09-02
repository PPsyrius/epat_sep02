package sg.edu.nus.iss.d13revision.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void noArgConstructor_createsEmptyPerson() {
        Person person = new Person();
        assertNull(person.getId());
        assertNull(person.getFirstName());
        assertNull(person.getLastName());
    }

    @Test
    void threeArgConstructor_setsAllFields() {
        Person person = new Person("abc12345", "John", "Doe");
        assertEquals("abc12345", person.getId());
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
    }

    @Test
    void twoArgConstructor_generatesId() {
        Person person = new Person("Jane", "Smith");
        assertNotNull(person.getId());
        assertEquals(8, person.getId().length());
    }

    @Test
    void twoArgConstructor_setsNameFields() {
        Person person = new Person("Jane", "Smith");
        assertEquals("Jane", person.getFirstName());
        assertEquals("Smith", person.getLastName());
    }

    @Test
    void twoArgConstructor_generatesUniqueId() {
        Person p1 = new Person("A", "B");
        Person p2 = new Person("C", "D");
        assertNotEquals(p1.getId(), p2.getId());
    }

    @Test
    void setId_setsId() {
        Person person = new Person();
        person.setId("xyz99999");
        assertEquals("xyz99999", person.getId());
    }

    @Test
    void setFirstName_setsFirstName() {
        Person person = new Person();
        person.setFirstName("Alice");
        assertEquals("Alice", person.getFirstName());
    }

    @Test
    void setLastName_setsLastName() {
        Person person = new Person();
        person.setLastName("Wonder");
        assertEquals("Wonder", person.getLastName());
    }

    @Test
    void toString_containsAllFields() {
        Person person = new Person("id123", "Test", "Person");
        String str = person.toString();
        assertTrue(str.contains("id123"));
        assertTrue(str.contains("Test"));
        assertTrue(str.contains("Person"));
    }

    @Test
    void toString_containsFieldNameLabels() {
        Person person = new Person("id123", "Test", "Person");
        String str = person.toString();
        assertTrue(str.contains("firstName"));
        assertTrue(str.contains("lastName"));
        assertTrue(str.contains("id"));
    }
}
