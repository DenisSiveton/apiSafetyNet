package com.safetynet.apiSafetyNet.repository;

import com.safetynet.apiSafetyNet.model.InputData.Person;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(PersonCRUD.class)
public class PersonCRUDUnitTest {

    @Autowired
    private PersonCRUD personCRUD;


    @AfterEach
    public void endTest() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/dataTestOrigin.json");
        OutputStream output = new FileOutputStream(personCRUD.getPathData());
        IOUtils.copy(input, output);
    }

    @Test
    public void addPerson_Test(){
        //ARRANGE
        Person personToAddTest = new Person("PM","Mazin","address 3","Culver","97451","741-874-6545","pmm@email.com");

        //ACT
        personCRUD.addPerson(personToAddTest);
        ArrayList<Person> personListUpdated = getPersonList();

        //ASSERT
        assertThat(personListUpdated.size()).isEqualTo(4);
        assertThat(personListUpdated.get(3).getFirstName()).isEqualTo(personToAddTest.getFirstName());
    }

    @Test
    public void modifyInfoPerson_Test() {
        //ARRANGE
        Person personToModifyWithUpdatedInfo = new Person("Denis","Siveton","address 4","Culver","97451","741-874-6512","ds@email.com");

        //ACT
        personCRUD.modifyInfoPerson(personToModifyWithUpdatedInfo);
        ArrayList<Person> personListUpdated = getPersonList();

        //ASSERT
        assertThat(personListUpdated.get(0).getAddress()).isEqualTo("address 4");
    }

    @Test
    public void deletePerson()
    {
        //ARRANGE
        Person personToDelete = new Person("Denis","Siveton","address 1","Culver","97451","741-874-6512","ds@email.com");

        //ACT
        personCRUD.deletePerson(personToDelete);
        ArrayList<Person> personListUpdated = getPersonList();

        //ASSERT
        assertThat(personListUpdated.size()).isEqualTo(2);
    }

    @Test
    public void getPersonsFromJSONFile() throws IOException, ParseException {
        //ARRANGE
        JSONParser jsonP = new JSONParser();
        JSONObject json0Test = (JSONObject) jsonP.parse(new FileReader(personCRUD.getPathData()));
        //ACT
        ArrayList<Person> personList = personCRUD.getPersonsFromJSONFile(json0Test);

        //ASSERT
        assertThat(personList.size()).isEqualTo(3);
        assertThat(personList.get(1).getFirstName()).isEqualTo("Laura");
    }

    private ArrayList<Person> getPersonList() {
        JSONParser jsonP = new JSONParser();
        ArrayList<Person> personListJSON =  new ArrayList<>();
        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(personCRUD.getPathData()));

            /* Create a list of Person from JSON file*/
            personListJSON = personCRUD.getPersonsFromJSONFile(jsonO);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return personListJSON;
    }
}
