package sg.edu.nus.iss.d13revision.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonFormTest {

    @Test
    void defaultConstructor_createsEmptyForm() {
        PersonForm form = new PersonForm();
        assertNull(form.getFirstName());
        assertNull(form.getLastName());
    }

    @Test
    void setFirstName_setsFirstName() {
        PersonForm form = new PersonForm();
        form.setFirstName("Alice");
        assertEquals("Alice", form.getFirstName());
    }

    @Test
    void setLastName_setsLastName() {
        PersonForm form = new PersonForm();
        form.setLastName("Wonderland");
        assertEquals("Wonderland", form.getLastName());
    }

    @Test
    void setAndGetNameFields_independent() {
        PersonForm form = new PersonForm();
        form.setFirstName("John");
        form.setLastName("Doe");
        assertEquals("John", form.getFirstName());
        assertEquals("Doe", form.getLastName());
    }
}
