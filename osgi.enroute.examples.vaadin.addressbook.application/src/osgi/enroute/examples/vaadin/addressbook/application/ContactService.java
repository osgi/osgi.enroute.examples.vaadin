package osgi.enroute.examples.vaadin.addressbook.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osgi.enroute.dto.api.DTOs;

/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
@Component(service = ContactService.class)
public class ContactService {

	private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
	// Create dummy data by randomly combining first and last names
	static String[] fnames = { "Peter", "Alice", "John", "Mike", "Olivia", "Nina", "Alex", "Rita", "Dan", "Umberto",
			"Henrik", "Rene", "Lisa", "Linda", "Timothy", "Daniel", "Brian", "George", "Scott", "Jennifer" };
	static String[] lnames = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore",
			"Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Young", "King",
			"Robinson" };

	@Reference
	DTOs dtos;

	@Activate
	void createDemoService() throws Exception {
		Random r = new Random(0);
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i < 100; i++) {
			Contact contact = new Contact();
			contact.firstName = fnames[r.nextInt(fnames.length)];
			contact.lastName = lnames[r.nextInt(fnames.length)];
			contact.email = contact.firstName.toLowerCase() + "@" + contact.lastName.toLowerCase() + ".com";
			contact.phone = "+ 358 555 " + (100 + r.nextInt(900));
			cal.set(1930 + r.nextInt(70), r.nextInt(11), r.nextInt(28));
			contact.birthDate = cal.getTime();
			save(contact);
		}
	}

	private HashMap<Long, Contact> contacts = new HashMap<>();
	private AtomicLong nextId = new AtomicLong(0);

	public synchronized List<Contact> findAll(String stringFilter)  {
		ArrayList<Contact> arrayList = new ArrayList<>();
		for (Contact contact : contacts.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(dtos.deepCopy(contact));
				}
			} catch (Exception ex) {
				LOGGER.log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Contact>() {

			@Override
			public int compare(Contact o1, Contact o2) {
				return (int) (o2.id - o1.id);
			}
		});
		return arrayList;
	}

	public synchronized long count() {
		return contacts.size();
	}

	public synchronized void delete(Contact value) {
		contacts.remove(value.id);
	}

	public void save(Contact entry) throws Exception {
		synchronized (this) {
			if (entry.id == null) {
				entry.id = nextId.getAndIncrement();
			}
		}
		entry = dtos.deepCopy(entry);
		contacts.put(entry.id, entry);
	}

}
