package osgi.enroute.examples.vaadin.application;

import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;

import com.vaadin.server.UICreateEvent;

import osgi.enroute.examples.vaadin.api.Application;

/**
 * A simple example application. This is registered as a service and will create
 * the UI instances for Vaadin.
 * <p>
 * In this application we just keep track of the instances and the number of
 * clicks.
 *
 */
@Component(
	name = "osgi.enroute.examples.vaadin.clickme", 
	property = "alias=/poo")
public class ClickMeApp implements Application<ClickMeUI> {
	AtomicInteger instance = new AtomicInteger();
	AtomicInteger clicks = new AtomicInteger();

	@Override
	public Class<ClickMeUI> getUIClass() {
		return ClickMeUI.class;
	}

	@Override
	public ClickMeUI getInstance(UICreateEvent event) {
		return new ClickMeUI(this);
	}

	public int nextInstance() {
		return instance.getAndIncrement();
	}

	public void anotherClick() {
		clicks.incrementAndGet();
	}

	public int getClicks() {
		return clicks.get();
	}
}
