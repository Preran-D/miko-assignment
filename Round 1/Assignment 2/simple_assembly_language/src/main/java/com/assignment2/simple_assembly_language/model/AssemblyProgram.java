package com.assignment2.simple_assembly_language.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class AssemblyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String programText;
    @Lob
    private String result;
}