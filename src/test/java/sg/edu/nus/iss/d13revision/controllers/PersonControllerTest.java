package sg.edu.nus.iss.d13revision.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import sg.edu.nus.iss.d13revision.models.Person;
import sg.edu.nus.iss.d13revision.services.PersonService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService perSvc;

    @Test
    void index_returnsIndexView() throws Exception {
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void index_home_returnsIndexView() throws Exception {
        mockMvc.perform(get("/person/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void index_index_returnsIndexView() throws Exception {
        mockMvc.perform(get("/person/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testRetrieve_returnsJsonList() throws Exception {
        List<Person> persons = Arrays.asList(
                new Person("id1", "Mark", "Zuckerberg"),
                new Person("id2", "Elon", "Musk")
        );
        when(perSvc.getPersons()).thenReturn(persons);

        mockMvc.perform(get("/person/testRetrieve"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].firstName").value("Mark"))
                .andExpect(jsonPath("$[0].lastName").value("Zuckerberg"))
                .andExpect(jsonPath("$[1].firstName").value("Elon"))
                .andExpect(jsonPath("$[1].lastName").value("Musk"));
    }

    @Test
    void personList_returnsListView() throws Exception {
        List<Person> persons = Arrays.asList(
                new Person("id1", "Mark", "Zuckerberg")
        );
        when(perSvc.getPersons()).thenReturn(persons);

        mockMvc.perform(get("/person/personList"))
                .andExpect(status().isOk())
                .andExpect(view().name("personList"))
                .andExpect(model().attributeExists("persons"));
    }

    @Test
    void showAddPersonPage_returnsAddPersonView() throws Exception {
        mockMvc.perform(get("/person/addPerson"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeExists("personForm"));
    }

    @Test
    void savePerson_withValidData_redirects() throws Exception {
        mockMvc.perform(post("/person/addPerson")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/person/personList"));
    }

    @Test
    void savePerson_withEmptyFirstName_returnsFormWithError() throws Exception {
        mockMvc.perform(post("/person/addPerson")
                        .param("firstName", "")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void savePerson_withEmptyLastName_returnsFormWithError() throws Exception {
        mockMvc.perform(post("/person/addPerson")
                        .param("firstName", "John")
                        .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void savePerson_withBothEmpty_returnsFormWithError() throws Exception {
        mockMvc.perform(post("/person/addPerson")
                        .param("firstName", "")
                        .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void savePerson_withNullFirstName_returnsFormWithError() throws Exception {
        mockMvc.perform(post("/person/addPerson")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void personToEdit_returnsEditView() throws Exception {
        mockMvc.perform(post("/person/personToEdit")
                        .param("id", "testId")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(view().name("editPerson"))
                .andExpect(model().attributeExists("per"));
    }

    @Test
    void personEdit_redirects() throws Exception {
        mockMvc.perform(post("/person/personEdit")
                        .param("id", "testId")
                        .param("firstName", "Updated")
                        .param("lastName", "Name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/person/personList"));
    }

    @Test
    void personDelete_redirects() throws Exception {
        mockMvc.perform(post("/person/personDelete")
                        .param("id", "testId")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/person/personList"));
    }
}
