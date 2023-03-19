package ru.vladikshk.library.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "author")
@NoArgsConstructor
@RequiredArgsConstructor
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    @NonNull
    String name;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    List<Book> books;
}
