package it.tcgroup.transactiondemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class CartEntity {

    @Getter
    @Setter
    @Embeddable
    public static class PrimaryKey implements Serializable {
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartSeq")
        @SequenceGenerator(name = "cartSeq", sequenceName = "cart_seq", allocationSize = 1)
        private Long seq;
        private String username;
    }


    @EmbeddedId
    private PrimaryKey primaryKey;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", columnDefinition = "uuid", referencedColumnName = "id")
    private ProductEntity product;

}
