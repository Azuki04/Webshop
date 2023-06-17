package ch.web.web_shop;

import ch.web.web_shop.SpringBootDataJpaApplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpringBootDataJpaApplicationTest {

    @Test
    void main_StartsApplication_Successfully() {
        assertDoesNotThrow(() -> {
            SpringBootDataJpaApplication.main(new String[]{});
        });
    }
}
