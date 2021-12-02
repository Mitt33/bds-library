package org.but.feec.library.services;

import org.but.feec.library.data.PersonRepository;

public class PersonService {
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

}
