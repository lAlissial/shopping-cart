package br.edu.ifpb.padroes.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import br.edu.ifpb.padroes.visitor.Visitor;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("electronic")
public class Electronic extends Product {
    @Override
    public BigDecimal accept(Visitor visitor) {
        return visitor.visitEletronic(this);
    }
}
