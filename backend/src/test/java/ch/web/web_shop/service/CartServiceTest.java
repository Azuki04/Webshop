package ch.web.web_shop.service;

import static ch.web.web_shop.service.CartService.getTotalCost;
import static org.junit.Assert.assertEquals;

class CartServiceTest {
    /*

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartService cartService;

    @Mock
    private Product product;

    @Mock
    private Cart cart;

    @Mock
    private User user;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        // create User
        user= new User(1,"test", "test@test.com", "password");
        // Create test data
        String title = "Test Product";
        String description = "This is a test product";
        String content = "Test content";
        int price = 100;
        int stock = 10;
        Category category = new Category("Test Category");
        // Create a new product
        product = new Product(title, description, content, price, stock, category, user);
        // create cart
        cart = new Cart(Optional.of(product), 1, Optional.of(user));

        //save
        cartRepository.save(cart);
    }

    @AfterAll
    public void tearDown() {
        cartRepository.deleteAll();
    }


    @Test
    public void testGetTotalCost() {
        List<CartItemDto> cartItems = new ArrayList<>();
        cartItems.add(new CartItemDto(cart));
        cartItems.add(new CartItemDto(cart));
        cartItems.add(new CartItemDto(cart));

        double expectedTotalCost = 100;
        double actualTotalCost = getTotalCost(cartItems);

        assertEquals(expectedTotalCost, actualTotalCost, 0.001);
    }

     */
}
