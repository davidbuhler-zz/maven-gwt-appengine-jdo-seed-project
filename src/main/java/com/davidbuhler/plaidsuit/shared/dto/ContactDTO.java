package com.davidbuhler.plaidsuit.shared.dto;

import com.davidbuhler.plaidsuit.shared.Resource;
import com.davidbuhler.plaidsuit.shared.validation.MaxLength;
import com.davidbuhler.plaidsuit.shared.validation.MinLength;
import com.davidbuhler.plaidsuit.shared.validation.MsgFrags;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ContactDTO implements Serializable {

    public String id;

    @NotBlank(message = Resource.FIRST_NAME + MsgFrags.REQUIRED)
    @NotEmpty(message = Resource.FIRST_NAME + MsgFrags.REQUIRED)
    @NotNull(message = Resource.FIRST_NAME + MsgFrags.REQUIRED)
    @Length(min = MinLength.FIRST_NAME, max = MaxLength.FIRST_NAME, message = Resource.FIRST_NAME + MsgFrags.INVALID_LENGTH)
    private String firstName;

    @NotBlank(message = Resource.LAST_NAME + MsgFrags.REQUIRED)
    @NotEmpty(message = Resource.LAST_NAME + MsgFrags.REQUIRED)
    @NotNull(message = Resource.LAST_NAME + MsgFrags.REQUIRED)
    @Length(min = MinLength.LAST_NAME, max = MaxLength.LAST_NAME, message = Resource.LAST_NAME + MsgFrags.INVALID_LENGTH)
    private String lastName;

    public ContactDTO() {
        new ContactDTO(null, "", "");
    }

    public ContactDTO(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
