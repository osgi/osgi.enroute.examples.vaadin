package osgi.enroute.examples.vaadin.addressbook.application;

import org.osgi.service.component.annotations.Component;

import com.vaadin.server.UICreateEvent;

import osgi.enroute.examples.vaadin.api.Application;


@Component(name = "osgi.enroute.examples.vaadin.addressbook", property = "alias=/addr")
public class AddressbookApplication implements Application<AddressbookUI> {


	@Override
	public Class<AddressbookUI> getUIClass() {
		return AddressbookUI.class;
	}

	@Override
	public AddressbookUI getInstance(UICreateEvent event) {
		return new AddressbookUI(this);
	}

}
