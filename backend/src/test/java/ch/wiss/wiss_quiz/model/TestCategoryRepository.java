package ch.wiss.wiss_quiz.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
public class TestCategoryRepository {
    
    @Autowired TestEntityManager entityManager;

    @Autowired CategoryRepository categoryRepository;

    @Test
    public void successfulSetup(){
        assertNotNull(entityManager);
    }

    @Test
    public void addListCats(){

        ArrayList<Integer> ids = new ArrayList<>();

        for (int i = 1; i < 6; i++ ){
            Category cat = new Category();
            cat.setName("Cat " + i);
            cat = categoryRepository.save(cat);

            assertNotNull(cat, "Must not be null after saving");
            assertTrue(cat.getId() > 0);
            ids.add(cat.getId());
        }

        assertEquals(5, categoryRepository.count());

        for (int i: ids){
            assertEquals("Cat "+(i), categoryRepository.findById(i).get().getName());
        }

    }

}
