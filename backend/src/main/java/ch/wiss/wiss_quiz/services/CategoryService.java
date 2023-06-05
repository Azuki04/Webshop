package ch.wiss.wiss_quiz.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.wiss.wiss_quiz.model.Category;
import ch.wiss.wiss_quiz.model.CategoryRepository;
import jakarta.validation.Valid;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    public void removeCategory(int id){
        Optional<Category> cat = categoryRepository.findById(id);
        categoryRepository.delete(cat.get());
    }

    public int addNewCategory(@Valid String name) {

        Category c = new Category();
        c.setName(name);
        c = categoryRepository.save(c);
        return c.getId();
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
}
