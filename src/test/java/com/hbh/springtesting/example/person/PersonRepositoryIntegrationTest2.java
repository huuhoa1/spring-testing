package com.hbh.springtesting.example.person;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersonRepositoryIntegrationTest2 {

    @Autowired
    private PersonRepository subject; // subject of test SOT

    @AfterEach
    public void tearDown() throws Exception {
        subject.deleteAll();
    }
    // will use H2 DB and will test the Spring Data Framework
    @Test
    public void shouldSaveAndFetchPerson() throws Exception {
        Person peter = new Person("Peter", "Pan");
        subject.save(peter);

        Optional<Person> maybePeter = subject.findByLastName("Pan");

        assertEquals(maybePeter, Optional.of(peter));
    }
}
