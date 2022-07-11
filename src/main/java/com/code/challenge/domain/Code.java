package com.code.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CODE")
public class Code {

    @Id
    private Long id;

    private String Name;
    private String LastName;

    public Code(Long id) {
        this.id = id;
    }
}
