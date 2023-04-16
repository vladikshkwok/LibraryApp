--liquibase formatted sql
--changeset vladikshk:insert_initial_data

insert into Author(name) values ('F. Scott Fitzgerald'),
                                ('Gabriel García Márquez'),
                                ('Aldous Huxley'),
                                ('Fyodor Dostoevsky'),
                                ('Jack London'),
                                ('George Orwell');

insert into Book(name, author_id) values ('The Great Gatsby', 1),
                                         ('One Hundred Years of Solitude', 2),
                                         ('Brave New World', 3),
                                         ('Crime and Punishment', 4),
                                         ('The Call of the Wild', 5),
                                         ('Nineteen Eighty-Four', 6),
                                         ('Animal Farm', 6);

insert into Tag(name) values ('dystopia'),
                             ('social criticism'),
                             ('Novel'),
                             ('Fiction');

insert into book_tag values (1, 3), (1, 4),
                            (2, 3), (2, 4),
                            (3, 3), (3, 4),
                            (4, 3), (4, 4),
                            (5, 3), (5, 4),
                            (6, 1), (6, 2),
                            (7, 1), (7, 2), (7, 3);