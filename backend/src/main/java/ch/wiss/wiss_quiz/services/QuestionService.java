package ch.wiss.wiss_quiz.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.wiss.wiss_quiz.model.Answer;
import ch.wiss.wiss_quiz.model.AnswerRepository;
import ch.wiss.wiss_quiz.model.Category;
import ch.wiss.wiss_quiz.model.CategoryRepository;
import ch.wiss.wiss_quiz.model.Question;
import ch.wiss.wiss_quiz.model.QuestionRepository;
import jakarta.transaction.Transactional;

/**
 * logic layer between QuestionController & QuestionRepository
 */
@Service
public class QuestionService {

    Logger log = LoggerFactory.getLogger(QuestionService.class);
    
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AnswerRepository answerRepository;

    private boolean testAnswers(Question question){

        if (question.getAnswers().size()<2){
            log.debug("not enough answers");
            return false;
        }
        if (question.getAnswers().stream().filter(a -> a.getCorrect()).collect(Collectors.toList()).size() < 1){
            log.debug("missing correct answer");
            return false;
        }
        return true;

    }


    @Transactional
    public int addNewQuestion(Question question, int catId){
        log.debug("addNewQuestion called...");

        // test for valid answers
        if (testAnswers(question)==false){
            return -1;
        }

        if (question.getQuestion().trim().length() < 3){
            log.debug("Question text to short");
            return -1;
        }

        Optional<Category> cat = categoryRepository.findById(catId);
        question.setCategory(cat.get());
        // we need to store nested Answer-Objects separately
        List<Answer> answers = List.copyOf(question.getAnswers());
        
        question.setAnswers(null);
        questionRepository.save(question);
        
        // we need to store nested Answer-Objects separately
        answers.forEach(a -> {
            a.setQuestion(question);
            answerRepository.save(a);
        });
        return question.getId();
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

}
