package br.edu.ifpb.padroes.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("electronic")
public class Electronic extends Product {

}
