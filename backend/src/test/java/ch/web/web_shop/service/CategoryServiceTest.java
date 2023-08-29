package ch.web.web_shop.service;

import ch.web.web_shop.model.Category;
import ch.web.web_shop.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(categoryRepository,null);
    }

    @Test
    public void testGetAllCategories() {
        // Create sample categories
        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");
        List<Category> categories = Arrays.asList(category1, category2);

        // Mock the category repository's behavior
        when(categoryRepository.findAll()).thenReturn(categories);

        // Call the service method
        Iterable<Category> result = categoryService.getAllCategories();

        // Verify the result
        assertEquals(categories, result);
    }
}
