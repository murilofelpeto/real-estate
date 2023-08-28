package com.felpeto.realestate.domain.person;

import com.felpeto.realestate.domain.person.vo.Contact;
import com.felpeto.realestate.domain.person.vo.Document;
import com.felpeto.realestate.domain.person.vo.Name;
import com.felpeto.realestate.domain.vo.Address;

public record Owner(Name name,
                    Document document,
                    Address address,
                    Contact contact) {

}
