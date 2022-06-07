package br.edu.ifpb.padroes.visitor;

import br.edu.ifpb.padroes.model.Book;
import br.edu.ifpb.padroes.model.Electronic;
import br.edu.ifpb.padroes.model.Product;

import java.math.BigDecimal;

public interface Visitor {
    public BigDecimal visitBook(Book book);

    public BigDecimal visitEletronic(Electronic eletronic);

}
