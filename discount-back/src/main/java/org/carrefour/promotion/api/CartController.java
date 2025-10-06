package org.carrefour.promotion.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.carrefour.promotion.api.dto.CartDTO;
import org.carrefour.promotion.api.mapper.CartApiMapper;
import org.carrefour.promotion.application.ApplyDiscountService;
import org.carrefour.promotion.domain.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping( "/api/cart" )
@Tag( name = "Cart",
      description = "Endpoints for cart and discount operations" )
@RequiredArgsConstructor
public class CartController {

    private static final Logger log = LoggerFactory.getLogger( CartController.class );

    private final ApplyDiscountService applyDiscountService;
    private final CartApiMapper        cartMapper;

    @Operation( summary = "Récupérer un panier", description = "Récupère les détails d'un panier par son identifiant")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Panier récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Panier non trouvé")
    })
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable String cartId) {
        log.info("🛒 Récupération du panier '{}'", cartId);
        Cart cart = applyDiscountService.getCart(cartId);
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @Operation(summary = "Appliquer une remise", description = "Applique un code de remise à un panier spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remise appliquée avec succès"),
            @ApiResponse(responseCode = "500", description = "Code de remise invalide ou expiré")
    })
    @PostMapping( "/{cartId}/apply-discount/{discountCode}" )
    public ResponseEntity<CartDTO> applyDiscount(
            @PathVariable String cartId,
            @PathVariable String discountCode
             ) {

        log.info( "🎟 Applying discount '{}' on cart '{}'", discountCode, cartId );

        Cart cart = applyDiscountService.applyDiscount( cartId, discountCode );
        return ResponseEntity.ok( cartMapper.toDto( cart ) );
    }
}
