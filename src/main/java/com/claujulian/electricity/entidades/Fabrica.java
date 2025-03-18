package com.claujulian.electricity.entidades;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "fabrica")
public class Fabrica {

     @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idFabrica;

    @Column(nullable=false)
    private String nombreFabrica;

}
