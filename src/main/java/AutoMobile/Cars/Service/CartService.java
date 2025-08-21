package AutoMobile.Cars.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AutoMobile.Cars.Excrptionfold.CustomRuntimeException;
import AutoMobile.Cars.Model.Cart;
import AutoMobile.Cars.Repository.CartRepository;
import AutoMobile.Cars.Util.DataConverter;
import AutoMobile.Cars.Util.cart.CartItem;
import AutoMobile.Cars.Util.cart.CartRequest;
import AutoMobile.Cars.Util.cart.CartResponse;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    DataConverter dataConverter;

    public CartResponse createCart(CartRequest cartRequest) {

        UUID userId = dataConverter.getCurrentUserId();
        Optional<Cart> exitingCart = cartRepository.findByUserId(userId);
        System.err.println("exitingCart -> " + exitingCart);
        Cart cart = exitingCart.orElseGet(() -> new Cart(userId, new HashMap<>()));
        System.err.println("cart -> " + cart);
        Map<UUID, CartItem> items = cart.getItems();

        for (Entry<UUID, CartItem> key : cartRequest.getItems().entrySet()) {
            UUID carId = key.getKey();
            CartItem newItem = key.getValue();

            // If already exists update quantity
            if (items.containsKey(carId)) {
                CartItem existingItem = items.get(carId);
                existingItem.setQuantity(existingItem.getQuantity() + 1);
            } else {
                // If not exists create new
                items.put(carId, CartItem.builder()
                        .imageUrl(newItem.getImageUrl())
                        .price(newItem.getPrice())
                        .model(newItem.getModel())
                        .quantity(newItem.getQuantity())
                        .build());
            }
            cart.setItems(items);
            cart = cartRepository.save(cart);
        }
        return DataConverter.convertToCartResponse(cart);

    }

    public CartResponse removeCart(UUID carId) {

        UUID userId = dataConverter.getCurrentUserId();
        Optional<Cart> exitingCart = cartRepository.findByUserId(userId);
        Cart cart = exitingCart
                .orElseThrow(() -> new RuntimeException("No cart found for user: " + userId));

        Map<UUID, CartItem> items = cart.getItems();

        if (!items.containsKey(carId)) {
            throw new RuntimeException("No car in cart -> " + carId);
        }

        CartItem existingCartItem = items.get(carId);

        if (existingCartItem.getQuantity() > 1) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() - 1);
            items.put(carId, existingCartItem);
        } else {
            items.remove(carId);
        }

        cart = cartRepository.save(cart);
        return DataConverter.convertToCartResponse(cart);

    }

    public CartResponse getCart() {
        UUID useId = dataConverter.getCurrentUserId();
        Optional<Cart> exitingCart = cartRepository.findByUserId(useId);
        Cart cart = exitingCart.orElseGet(() -> new Cart(useId, new HashMap<>()));
        return DataConverter.convertToCartResponse(cart);
    }

    public List<CartResponse> getAllCart() {
        List<Cart> cart = cartRepository.findAll();
        return cart.stream().map(item -> DataConverter.convertToCartResponse(item)).collect(Collectors.toList());
    }

    public String clearCart() {
        try {
            UUID userId = dataConverter.getCurrentUserId();
            Optional<Cart> cart = cartRepository.findByUserId(userId);
            if (cart.isPresent()) {
                Map<UUID, CartItem> items = cart.get().getItems();
                items.clear();
                System.err.println("items -> " + cart.get().getItems());
                cartRepository.save(cart.get());
                if (items.isEmpty()) {
                    return "Cart cleared";
                }

            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'clearCart'");
        }
        return null;
    }

    public String clearSpecficItem(UUID carId) {
        try {
            UUID userId = dataConverter.getCurrentUserId();
            Optional<Cart> cart = cartRepository.findByUserId(userId);
            if (cart.isPresent()) {
                Map<UUID, CartItem> items = cart.get().getItems();
                if (items.get(carId) != null) {
                    items.remove(carId);
                    System.err.println("items -> " + cart.get().getItems());
                    cartRepository.save(cart.get());
                }else{
                    return "Item not available";
                }
            }
        } catch (Exception e) {
            throw new CustomRuntimeException("Unimplemented method 'clearSpecficItem'");
        }
        return null;
    }
}
