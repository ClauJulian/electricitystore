package com.claujulian.electricity.entidades;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "articulo")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idArticulo;

    private int nroArticulo;

    @Column(nullable=false)
    private String nombreArticulo;

    @Column(nullable=false)
    private String descripcionArticulo;

    @ManyToOne
    @JoinColumn(name="idFabrica" , nullable = false)
    private Fabrica fabrica;


}
