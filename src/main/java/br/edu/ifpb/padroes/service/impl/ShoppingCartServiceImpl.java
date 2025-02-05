package br.edu.ifpb.padroes.service.impl;

import br.edu.ifpb.padroes.exception.NotEnoughProductsInStockException;
import br.edu.ifpb.padroes.model.Product;
import br.edu.ifpb.padroes.repository.ProductRepository;
import br.edu.ifpb.padroes.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import br.edu.ifpb.padroes.visitor.ApplyDiscountByProductVisitor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Dusan
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;

    private Map<Product, Integer> products = new HashMap<>();

    private BigDecimal getDiscount(Map<Product, Integer> dic, BigDecimal desconto){
        ApplyDiscountByProductVisitor discount = new ApplyDiscountByProductVisitor();
        discount.setDesconto(desconto);
        return discount.applyDiscount(dic);
    }

    private BigDecimal getDiscount(Map<Product, Integer> dic){
        ApplyDiscountByProductVisitor discount = new ApplyDiscountByProductVisitor();
        return discount.applyDiscount(dic);
    }

    @Autowired
    public ShoppingCartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
    @Override
    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public BigDecimal getTotalDiscount() {
        /*Caso use um desconto diferente*/
//        BigDecimal descontoNatal = BigDecimal.valueOf(0.5); ///50%
//        return getDiscount(products, descontoNatal);
        return getDiscount(products);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */
    @Override
    public void checkout() throws NotEnoughProductsInStockException {
        Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // Refresh quantity for every product before checking
            product = productRepository.findOne(entry.getKey().getId());
            if (product.getQuantity() < entry.getValue())
                throw new NotEnoughProductsInStockException(product);
            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
        }
        productRepository.save(products.keySet());
        productRepository.flush();
        products.clear();
    }

    @Override
    public BigDecimal getTotal() {
        return products.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).subtract(getTotalDiscount());
    }
}
