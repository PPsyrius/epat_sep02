package sg.edu.nus.iss.d13revision.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sg.edu.nus.iss.d13revision.models.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }

    @Test
    void getPersons_initiallyContainsTwoDefaultPersons() {
        List<Person> persons = personService.getPersons();
        assertEquals(2, persons.size());
    }

    @Test
    void getPersons_containsMarkZuckerberg() {
        List<Person> persons = personService.getPersons();
        assertTrue(persons.stream()
                .anyMatch(p -> "Mark".equals(p.getFirstName()) && "Zuckerberg".equals(p.getLastName())));
    }

    @Test
    void getPersons_containsElonMusk() {
        List<Person> persons = personService.getPersons();
        assertTrue(persons.stream()
                .anyMatch(p -> "Elon".equals(p.getFirstName()) && "Musk".equals(p.getLastName())));
    }

    @Test
    void addPerson_increasesListSize() {
        int initialSize = personService.getPersons().size();
        personService.addPerson(new Person("Test", "User"));
        assertEquals(initialSize + 1, personService.getPersons().size());
    }

    @Test
    void addPerson_generatesNewId() {
        personService.addPerson(new Person("Test", "User"));
        List<Person> persons = personService.getPersons();
        Person added = persons.get(persons.size() - 1);
        assertNotNull(added.getId());
        assertEquals(8, added.getId().length());
    }

    @Test
    void addPerson_preservesFirstNameAndLastName() {
        personService.addPerson(new Person("Alice", "Wonderland"));
        List<Person> persons = personService.getPersons();
        Person added = persons.get(persons.size() - 1);
        assertEquals("Alice", added.getFirstName());
        assertEquals("Wonderland", added.getLastName());
    }

    @Test
    void updatePerson_modifiesExistingPerson() {
        List<Person> persons = personService.getPersons();
        Person toUpdate = persons.get(0);
        String originalId = toUpdate.getId();

        Person updatedPerson = new Person(originalId, "Updated", "Name");
        personService.updatePerson(updatedPerson);

        Person found = personService.getPersons().stream()
                .filter(p -> p.getId().equals(originalId))
                .findFirst()
                .orElse(null);

        assertNotNull(found);
        assertEquals("Updated", found.getFirstName());
        assertEquals("Name", found.getLastName());
    }

    @Test
    void updatePerson_preservesId() {
        List<Person> persons = personService.getPersons();
        String originalId = persons.get(0).getId();

        personService.updatePerson(new Person(originalId, "New", "Person"));

        assertTrue(personService.getPersons().stream()
                .anyMatch(p -> p.getId().equals(originalId)));
    }

    @Test
    void removePerson_decreasesListSize() {
        List<Person> persons = personService.getPersons();
        int initialSize = persons.size();
        personService.removePerson(persons.get(0));
        assertEquals(initialSize - 1, personService.getPersons().size());
    }

    @Test
    void removePerson_removesCorrectPerson() {
        List<Person> persons = personService.getPersons();
        Person toRemove = persons.get(0);
        String removedId = toRemove.getId();

        personService.removePerson(toRemove);

        assertFalse(personService.getPersons().stream()
                .anyMatch(p -> p.getId().equals(removedId)));
    }

    @Test
    void getPersons_returnsMutableList() {
        List<Person> persons = personService.getPersons();
        assertNotNull(persons);
    }
}
