package br.edu.ifpb.padroes.visitor;

import br.edu.ifpb.padroes.model.Book;
import br.edu.ifpb.padroes.model.Electronic;
import br.edu.ifpb.padroes.model.Product;


import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


public class ApplyDiscountByProductVisitor implements Visitor{

    public BigDecimal desconto;

    private static final BigDecimal BOOK_DISCOUNT = BigDecimal.valueOf(0.3); // 30 %
    private static final BigDecimal ELECTRONIC_DISCOUNT = BigDecimal.valueOf(0.05); // 5 %


    public Consumer<? super BigDecimal> getDesconto() {
        return (Consumer<? super BigDecimal>) desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal applyDiscount(Map<Product, Integer> args){
        return args.keySet()
                .stream()
                .map(product -> product.accept(this)
                        .multiply(BigDecimal.valueOf(args.get(product))))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal visitBook(Book book) {
        return book.getPrice().multiply(Optional.ofNullable(desconto).orElse(BOOK_DISCOUNT));

    }

    @Override
    public BigDecimal visitEletronic(Electronic eletronic) {
        return eletronic.getPrice().multiply(Optional.ofNullable(desconto).orElse(ELECTRONIC_DISCOUNT));
    }
}
