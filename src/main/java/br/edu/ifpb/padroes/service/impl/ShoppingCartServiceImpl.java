package br.edu.ifpb.padroes.service.impl;

import br.edu.ifpb.padroes.exception.NotEnoughProductsInStockException;
import br.edu.ifpb.padroes.model.Book;
import br.edu.ifpb.padroes.model.Electronic;
import br.edu.ifpb.padroes.model.Product;
import br.edu.ifpb.padroes.repository.ProductRepository;
import br.edu.ifpb.padroes.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


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

    private static final BigDecimal BOOK_DISCOUNT = BigDecimal.valueOf(0.3); // 30 %
    private static final BigDecimal ELECTRONIC_DISCOUNT = BigDecimal.valueOf(0.05); // 5 %

    private Map<Product, Integer> products = new HashMap<>();

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
        return
                products.keySet().stream().filter(Book.class::isInstance).map(product -> product.getPrice().multiply(BOOK_DISCOUNT).multiply(BigDecimal.valueOf(products.get(product)))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO)
                        .add(products.keySet().stream().filter(Electronic.class::isInstance).map(product -> product.getPrice().multiply(ELECTRONIC_DISCOUNT).multiply(BigDecimal.valueOf(products.get(product)))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
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
