package osgi.enroute.examples.vaadin.addressbook.application;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

@SuppressWarnings( {"rawtypes", "serial", "unchecked"})
public class DTOItem<T> implements Item {
	private static final long serialVersionUID = 1L;
	private final Field[] fields;
	private final Map<String, Property<?>> properties = new HashMap<>();

	private final T value;

	public DTOItem(T t) {
		this.value = t;
		this.fields = t.getClass().getFields();
		Stream.of(this.fields)//
				.filter(f ->! Modifier.isStatic(f.getModifiers()))//
				.forEach(this::toProperty);
		;
	}

	private void toProperty(Field f) {
		Property<?> property = new Property<Object>() {
			boolean readOnly = Modifier.isFinal(f.getModifiers());

			@Override
			public Object getValue() {
				try {
					return f.get(value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public void setValue(Object newValue) throws ReadOnlyException {
				try {
					f.set(value, newValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public Class<?> getType() {
				return f.getType();
			}

			@Override
			public boolean isReadOnly() {
				return readOnly;
			}

			@Override
			public void setReadOnly(boolean newStatus) {
				readOnly = newStatus;
			}
		};
		properties.put(f.getName(), property);
	}

	@Override
	public Property<T> getItemProperty(Object id) {
		return (Property<T>) properties.get(id);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return properties.keySet();
	}

	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

}
