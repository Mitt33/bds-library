package org.but.feec.library.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.library.api.BookAuthView;
import org.but.feec.library.data.BookRepository;
import org.but.feec.library.exceptions.ResourceNotFoundException;

public class AuthService {
    private BookRepository bookRepository;

    public AuthService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private BookAuthView findPersonByEmail(String email) {
        return bookRepository.findMemberByEmail(email);
    }

    public boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        BookAuthView bookAuthView = findPersonByEmail(username);
        if (bookAuthView == null) {
            throw new ResourceNotFoundException("Provided username is not found.");
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bookAuthView.getPassword());
        return result.verified;
    }
}



