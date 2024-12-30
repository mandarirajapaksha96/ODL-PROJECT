package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String profilePicture;
    private long size;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;


}
