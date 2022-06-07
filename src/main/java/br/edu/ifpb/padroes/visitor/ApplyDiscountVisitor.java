package br.edu.ifpb.padroes.visitor;

import br.edu.ifpb.padroes.model.Book;
import br.edu.ifpb.padroes.model.Electronic;
import br.edu.ifpb.padroes.model.Product;
import br.edu.ifpb.padroes.service.impl.ShoppingCartServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class ApplyDiscountVisitor implements Visitor{

    private static final BigDecimal BOOK_DISCOUNT = BigDecimal.valueOf(0.3); // 30 %
    private static final BigDecimal ELECTRONIC_DISCOUNT = BigDecimal.valueOf(0.05); // 5 %

    public BigDecimal applyDiscount(Product... args){
        BigDecimal total = BigDecimal.ZERO;

        for (Product product : args) {
            total.add(product.getPrice()
                             .multiply(product.accept(this))
                             //.multiply(BigDecimal.valueOf(product.getQuantity()))
                    );
        }

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
