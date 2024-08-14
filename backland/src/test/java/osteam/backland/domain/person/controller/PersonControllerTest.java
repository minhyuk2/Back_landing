package osteam.backland.domain.person.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import osteam.backland.domain.person.controller.request.PersonCreateRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;

//    @MockBean
//    private PersonController personController;

    @BeforeEach
    public void setup() {

    }

    @Test
    void longNamePersonTest() throws JsonProcessingException {
        String longNamePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        "teamaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        "01012341234"
                ));

        try {
            mock.perform(MockMvcRequestBuilders.post("/person")
                            .contentType("application/json")
                            .content(longNamePerson))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void longPhonePersonTest() throws JsonProcessingException {
        String longPhonePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        "team",
                        "010123412341111111111111111111111"
                ));
        try {
            mock.perform(MockMvcRequestBuilders.post("/person")
                            .contentType("application/json")
                            .content(longPhonePerson))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shortPhonePersonTest() throws JsonProcessingException {
        String shortPhonePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        "team",
                        "010"
                ));
        try {
            mock.perform(MockMvcRequestBuilders.post("/person")
                            .contentType("application/json")
                            .content(shortPhonePerson))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void nullPersonTest() throws JsonProcessingException {
        String nullPerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        null,
                        null
                ));
        try {
            mock.perform(MockMvcRequestBuilders.post("/person")
                            .contentType("application/json")
                            .content(nullPerson))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void blankPersonTest() throws JsonProcessingException {
        String blankPerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        "",
                        ""
                ));
        try {
            mock.perform(MockMvcRequestBuilders.post("/person")
                            .contentType("application/json")
                            .content(blankPerson))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void nullInputTest() throws JsonProcessingException {
        String longPhonePerson = objectMapper
                .writeValueAsString(null);
        try {
            mock.perform(MockMvcRequestBuilders.post("/person")
                            .contentType("application/json")
                            .content(longPhonePerson))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //controller 부분에 이게 구현되어 있지 않기에 우선 주석처리
//    @Test
//    void changeNamePersonTest() throws JsonProcessingException {
//        String originName = "sos";
//        String phone = "01012341234";
//        String changeName = "team";
//
//        String successPerson = objectMapper
//                .writeValueAsString(new PersonCreateRequest(
//                        originName,
//                        phone
//                ));
//
//        String changePerson = objectMapper
//                .writeValueAsString(new PersonCreateRequest(
//                        changeName,
//                        phone
//                ));
//
//    }


}
