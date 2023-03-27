package ru.vladikshk.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.repository.AuthorsRepository;
import ru.vladikshk.library.security.AuthorDetails;
@Service
public class AuthorDetailsService implements UserDetailsService {
    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorDetailsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = authorsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new AuthorDetails(author);
    }
}
