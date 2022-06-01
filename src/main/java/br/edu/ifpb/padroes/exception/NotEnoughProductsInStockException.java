package br.edu.ifpb.padroes.exception;

import br.edu.ifpb.padroes.model.Product;

public class NotEnoughProductsInStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "Sem produtos suficientes em estoque";

    public NotEnoughProductsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughProductsInStockException(Product product) {
        super(String.format("Não há produtos do tipo %s em estoque. Restam apenas %d", product.getName(), product.getQuantity()));
    }

}
