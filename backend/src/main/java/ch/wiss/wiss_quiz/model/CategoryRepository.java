package ch.wiss.wiss_quiz.model;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Component
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}