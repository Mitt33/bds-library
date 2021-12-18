package org.but.feec.library.services;

import org.but.feec.library.api.PersonBasicView;
import org.but.feec.library.api.PersonDetailView;
import org.but.feec.library.data.PersonRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;

public class PersonService {
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDetailView getPersonDetailView(Long id) {
        return personRepository.findPersonDetailedView(id);
    }

    public List<PersonBasicView> getPersonsBasicView() {
        return personRepository.getPersonsBasicView();
    }

//    public void createPerson(PersonCreateView personCreateView) {
//        // the following three lines can be written in one code line (only for more clear explanation it is written in three lines
//        char[] originalPassword = personCreateView.getPwd();
//        char[] hashedPassword = hashPassword(originalPassword);
//        personCreateView.setPwd(hashedPassword);
//
//        personRepository.createPerson(personCreateView);
//    }

//    public void editPerson(PersonEditView personEditView) {
//        personRepository.editPerson(personEditView);
//    }

    /**
     * <p>
     * Note: For implementation details see: https://github.com/patrickfav/bcrypt
     * </p>
     *
     * @param password to be hashed
     * @return hashed password
     */
    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }


}
