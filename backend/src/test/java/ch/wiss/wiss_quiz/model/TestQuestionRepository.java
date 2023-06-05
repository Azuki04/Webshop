package ch.wiss.wiss_quiz.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TestQuestionRepository {
    
    @Autowired QuestionRepository sut;
    @Autowired CategoryRepository doc;

    @Test
    public void testSetup(){
        assertNotNull(sut);
        assertNotNull(doc);
    }

    @Test
    public void findQuestionsForCategory(){

        Category cat1 = new Category(); cat1.setName("cat1");
        cat1 = doc.save(cat1);

        for (int i=1; i < 5; i++){
            Question q = new Question();
            q.setCategory(cat1);
            q.setQuestion("question-cat1-"+1);
            sut.save(q);
        }

        Category cat2 = new Category(); cat1.setName("cat2");
        cat2 = doc.save(cat2);

        for (int i=1; i < 5; i++){
            Question q = new Question();
            q.setCategory(cat2);
            q.setQuestion("question-cat2-"+1);
            sut.save(q);
        }

        assertEquals(8, sut.count(), "should be 8 questions in db now...");

        Iterable<Question> qs = sut.findByCategory(cat2);
        
        qs.forEach(q -> {
            System.out.println(q.getQuestion());
            assertTrue(q.getQuestion().contains("question-cat2-"));
        });
        //assertEquals(4, i, "should be 4 questions");

    }


}
