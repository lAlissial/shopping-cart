package br.edu.ifpb.padroes.visitor;

import br.edu.ifpb.padroes.model.Book;
import br.edu.ifpb.padroes.model.Electronic;
import br.edu.ifpb.padroes.model.Product;
import br.edu.ifpb.padroes.service.impl.ShoppingCartServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplyDiscountByProductVisitor implements Visitor{

    private static final BigDecimal BOOK_DISCOUNT = BigDecimal.valueOf(0.3); // 30 %
    private static final BigDecimal ELECTRONIC_DISCOUNT = BigDecimal.valueOf(0.05); // 5 %

    public BigDecimal applyDiscount(Map<Product, Integer> args){
        BigDecimal total = BigDecimal.ZERO;
        total = total.add(args.keySet()
                .stream()
                .map(product -> product.getPrice()
                                        .multiply(product.accept(this))
                                        .multiply(BigDecimal.valueOf(args.get(product))))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO));
        return total;
    }


    @Override
    public BigDecimal visitBook(Book book) {
        return BOOK_DISCOUNT;
    }

    @Override
    public BigDecimal visitEletronic(Electronic eletronic) {
        return ELECTRONIC_DISCOUNT;
    }
}
