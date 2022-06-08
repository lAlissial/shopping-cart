package br.edu.ifpb.padroes.model;

import org.hibernate.validator.constraints.Length;
import br.edu.ifpb.padroes.visitor.Visitor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("book")

public class Book extends Product {
    @Column(name = "isbn", nullable = true, unique = true)
    @Length(min = 10, message = "*ISBN precisa ter ao menos 10 caracteres")
    private String isbnNumber;

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    @Override
    public BigDecimal accept(Visitor visitor) {
        return visitor.visitBook(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book product = (Book) o;

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
