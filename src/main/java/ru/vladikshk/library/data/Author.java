package ru.vladikshk.library.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "author")
@NoArgsConstructor
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    private String name;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
