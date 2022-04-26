package org.sample.panache;

import javax.json.bind.annotation.JsonbCreator;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Fruit extends PanacheEntity {

    public String name;

    @ManyToOne( cascade = CascadeType.ALL)
    public Tree tree;

    public Fruit() {
    }

    public Fruit(String name, Tree tree) {
        this.name = name;
        this.tree = tree;
    }

    @JsonbCreator
    public static Fruit of(String name, Tree tree) {
        return new Fruit(name, tree);
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
