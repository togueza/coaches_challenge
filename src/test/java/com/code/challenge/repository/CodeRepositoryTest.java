package com.code.challenge.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@Ignore
@DBRider
@DataJpaTest
@RunWith(SpringRunner.class)
@DataSet("datasets/code.yml")
public class CodeRepositoryTest {

    @Autowired
    private CodeRepository codeRepository;

    @Test
    public void whenFindById_shouldReturnTheEntity(){
        assertThat(codeRepository.findById(1L).orElse(null), allOf(
                hasProperty("id", equalTo(1L)),
                hasProperty("hola", equalTo("test")),
                hasProperty("hello", equalTo("test"))
        ));
    }

    @Test
    public void whenFindNothingById_shouldReturnOptionEmpty(){
        assertThat(codeRepository.findById(2L), is(equalTo(Optional.empty())));
    }
}