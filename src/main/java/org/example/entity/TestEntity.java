package org.example.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "test_entities")
public class TestEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    public TestEntity() {
    }
    
    public TestEntity(String name) {
        this.name = name;
    }
} 