package ru.vladikshk.library.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vladikshk.library.data.Author;

import java.util.Collection;

public class AuthorDetails implements UserDetails {

    private final Author author;

    public AuthorDetails(Author author) {
        this.author = author;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // get roles
    }

    @Override
    public String getPassword() {
        return this.author.getPassword();
    }

    @Override
    public String getUsername() {
        return this.author.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Author getAuthor() {
        return author;
    }
}
