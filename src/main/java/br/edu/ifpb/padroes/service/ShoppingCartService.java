package br.edu.ifpb.padroes.service;

import br.edu.ifpb.padroes.model.Product;
import br.edu.ifpb.padroes.exception.NotEnoughProductsInStockException;

import java.math.BigDecimal;
import java.util.Map;

public interface ShoppingCartService {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    BigDecimal getTotalDiscount();

    void checkout() throws NotEnoughProductsInStockException;

    BigDecimal getTotal();
}
