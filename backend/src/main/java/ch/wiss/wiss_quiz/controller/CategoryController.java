package ch.wiss.wiss_quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.wiss_quiz.model.Category;
import ch.wiss.wiss_quiz.services.CategoryService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/category") // This means URL's start with /demo (after Application path)
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @PostMapping(path = "/admin/") // Map ONLY POST Requests
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public int addNewCategory(@Valid @RequestParam String name) {
    int id = categoryService.addNewCategory(name);
    return id;
  }

  @DeleteMapping(path = "/admin/{id}") // Map ONLY POST Requests
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> removeCategory(@Valid @PathVariable Integer id) {
      return ResponseEntity.ok("cat removed");
  }

  @GetMapping(path = "")
  public Iterable<Category> getAllCategories() {
    return categoryService.findAll();
  }

}