package com.example.demo.Service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.example.demo.Model.CartItem;


@Service
public class CartService {

    private Map<String, List<CartItem>> carritos = new HashMap<>();

    public List<CartItem> getCartItems(String nickname) {
        return carritos.computeIfAbsent(nickname, k -> new ArrayList<>());
    }

    public void addItem(String nickname, CartItem item) {
        getCartItems(nickname).add(item);
    }

    public void removeItem(String nickname, int productId) {
    getCartItems(nickname).removeIf(i -> i.getProductId() == productId);
    }


    public void clearCart(String nickname) {
        getCartItems(nickname).clear();
    }
}

