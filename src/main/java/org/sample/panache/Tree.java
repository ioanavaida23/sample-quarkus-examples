package org.sample.panache;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Tree extends PanacheEntity implements Serializable {

    public Long code;

    public UUID uuid;

    public Tree() {
    }

    public Tree(Long code) {
        this.code = code;
        this.uuid = UUID.randomUUID();
    }

    @JsonbCreator
    public static Tree of(Long code){
        return new Tree(code);
    }

    @OneToMany(
        mappedBy = "tree",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @JsonbTransient
    public List<Fruit> fruits = new ArrayList<>();
 
    public void addFruit(Fruit fruit) {
        fruits.add(fruit);
        fruit.tree = this;
    }
 
    public void removeFruit(Fruit fruit) {
        fruits.remove(fruit);
        fruit.tree = null;
    }
}

