package org.but.feec.library.services;

import org.but.feec.library.data.PersonRepository;

public class AuthService {
    private PersonRepository personRepository;

    public AuthService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
}
