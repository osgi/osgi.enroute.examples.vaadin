package osgi.enroute.examples.vaadin.addressbook.application;

import java.util.Date;

import org.osgi.dto.DTO;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.

public class Contact extends DTO {

	public Long id;

	public String firstName = "";
	public String lastName = "";
	public String phone = "";
	public String email = "";
	public Date birthDate;
}
