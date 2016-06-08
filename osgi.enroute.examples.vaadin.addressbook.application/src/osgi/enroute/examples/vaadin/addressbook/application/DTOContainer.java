package osgi.enroute.examples.vaadin.addressbook.application;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;

public class DTOContainer<T> extends IndexedContainer {
	private static final long serialVersionUID = 1L;
	private final Field[] fields;
	private final T defaults;

	public DTOContainer(Class<T> c) {
		this.fields = c.getFields();
		Arrays.sort(this.fields, (a, b) -> a.getName().compareTo(b.getName()));

		T d = null;
		try {
			d = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// that's ok
		}
		defaults = d;

		for (Field f : fields)
			try {
				addContainerProperty(f.getName(), f.getType(), defaults != null ? f.get(defaults) : null);
			} catch (Exception e) {

			}
	}

	public void addAll(List<Contact> all) {
		for (Contact c : all) {
			add(c);
		}
	}

	private Item add(Contact c) {
		Item item = addItem(c.id);
		for (Field f : fields) {
			try {
				@SuppressWarnings("unchecked")
				Property<Object> p = item.getItemProperty(f.getName());
				p.setValue(f.get(c));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}
}
