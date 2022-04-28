package org.sample.panache;

import javax.json.bind.annotation.JsonbCreator;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Fruit extends PanacheEntity {

    public String name;

    public Long price;

    @ManyToOne( cascade = CascadeType.ALL)
    public Tree tree;

    public Fruit() {
    }

    public Fruit(String name, Long price, Tree tree) {
        this.name = name;
        this.tree = tree;
        this.price = price;
    }

    @JsonbCreator
    public static Fruit of(String name, Long price, Tree tree) {
        return new Fruit(name, price, tree);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Fruit))
            return false;
        return id != null && id.equals(((Fruit) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
