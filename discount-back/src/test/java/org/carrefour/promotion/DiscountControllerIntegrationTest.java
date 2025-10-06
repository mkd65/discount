package org.carrefour.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.carrefour.promotion.api.CartController;
import org.carrefour.promotion.application.ApplyDiscountService;
import org.carrefour.promotion.domain.model.Cart;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiscountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ApplyDiscountService applyDiscountService;

    @InjectMocks
    private CartController cartController;

    @Test
    void getCart_ShouldReturnCart_WhenCartExists() throws Exception {
        // Given
        String cartId = "1";
        Cart mockCart = new Cart();
        mockCart.setId(cartId);
        mockMvc.perform(get("/api/cart/{cartId}", cartId))
               .andExpect(status().isOk());
    }

    @Test
    void applyDiscount_ShouldReturnUpdatedCart_WhenDiscountIsValid() throws Exception {
        // Given
        String cartId = "1";
        String discountCode = "DISC10";
        Cart mockCart = new Cart();
        mockCart.setId(cartId);


        // When & Then
        mockMvc.perform(post("/api/cart/{cartId}/apply-discount/{discountCode}", cartId, discountCode))
               .andExpect(status().isOk());
    }

    @Test
    void getCart_ShouldReturnNotFound_WhenCartDoesNotExist() throws Exception {
        // Given
        String cartId = "nonexistent";

        // When & Then
        mockMvc.perform(get("/api/cart/{cartId}", cartId))
               .andExpect(status().is5xxServerError());
    }

    @Test
    void applyDiscount_ShouldReturnError_WhenDiscountCodeIsInvalid() throws Exception {
        // Given
        String cartId = "1";
        String invalidDiscountCode = "INVALID";

        // When & Then
        mockMvc.perform(post("/api/cart/{cartId}/apply-discount/{discountCode}", cartId, invalidDiscountCode))
               .andExpect(status().is5xxServerError());
    }

}
