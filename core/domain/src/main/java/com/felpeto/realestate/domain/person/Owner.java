package com.felpeto.realestate.domain.person;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.domain.person.vo.Contact;
import com.felpeto.realestate.domain.person.vo.Document;
import com.felpeto.realestate.domain.person.vo.Name;
import com.felpeto.realestate.domain.vo.Address;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants(level = PRIVATE)
public record Owner(Name name,
                    Document document,
                    Address address,
                    Contact contact) {

  private static final String MANDATORY_FIELD = "[Owner] | {0} is mandatory";

  public Owner(final Name name,
      final Document document,
      final Address address,
      final Contact contact) {
    this.name = requireNonNull(name, format(MANDATORY_FIELD, Fields.name));
    this.document = requireNonNull(document, format(MANDATORY_FIELD, Fields.document));
    this.address = requireNonNull(address, format(MANDATORY_FIELD, Fields.address));
    this.contact = requireNonNull(contact, format(MANDATORY_FIELD, Fields.contact));
  }
}
