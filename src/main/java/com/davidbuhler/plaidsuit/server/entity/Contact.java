package com.davidbuhler.plaidsuit.server.entity;

import com.davidbuhler.plaidsuit.shared.validation.MaxLength;
import com.davidbuhler.plaidsuit.shared.validation.MinLength;
import com.davidbuhler.plaidsuit.shared.validation.MsgFrags;
import com.davidbuhler.plaidsuit.shared.Resource;
import com.google.appengine.api.datastore.Key;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.NotNull;

@PersistenceCapable(detachable = "true")
public class Contact
{

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
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Key contactKey;

	public Contact()
	{
		super();
	}

	@Override
	public String toString()
	{
		return "Contact{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", contactKey=" + contactKey +
				'}';
	}

	public Key getContactKey()
	{
		return contactKey;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String value)
	{
		this.lastName = value;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String value)
	{
		this.firstName = value;
	}
}
