package ch.wiss.wiss_quiz.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.wiss_quiz.model.*;
import ch.wiss.wiss_quiz.services.QuestionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path="/question") // This means URL's start with /question (after Application path)
public class QuestionController {

  @Autowired
  private QuestionService questionService;
  
  @PostMapping(path="/{cat_id}") // Map ONLY POST Requests
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public @ResponseBody String addNewQuestion(@PathVariable(value="cat_id") Integer catId, @RequestBody Question question) {    
    if (questionService.addNewQuestion(question, catId) > 9)
    		return "Saved";
      return "Error saving question";
  }
  

  @GetMapping(path="")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public @ResponseBody Iterable<Question> getAllQuestions() {    
    return questionService.findAll();
  } 
    
}