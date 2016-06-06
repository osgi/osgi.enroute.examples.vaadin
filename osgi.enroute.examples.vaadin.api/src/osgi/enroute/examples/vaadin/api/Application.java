package osgi.enroute.examples.vaadin.api;

import org.osgi.annotation.versioning.ConsumerType;

import com.vaadin.server.UICreateEvent;
import com.vaadin.ui.UI;

/**
 * A service interface for bundles that want to create a Vaadin application.
 * These services should be registered with the {@link #SERVICE_PROPERY_ALIAS}
 * to the path where the application should reside.
 * <p>
 * Normally in Vaadin the instances are created by Vaadin but in this case the
 * application can setup the instances.
 * 
 * @param <T>
 *            the UI type this application supports
 */
@ConsumerType
public interface Application<T extends UI> {
	/**
	 * Service property for the alias
	 */
	String SERVICE_PROPERY_ALIAS = "alias";

	/**
	 * Return the class to be used for the UI. This class can contain the @Theme annotation 
	 * @return the class
	 */
	Class<T> getUIClass();

	/**
	 * Create an instance for a new session. This class must extend the class returned from {@link #getUIClass()}
	 * 
	 * @param event the create event
	 * @return
	 */
	T getInstance(UICreateEvent event);
}
