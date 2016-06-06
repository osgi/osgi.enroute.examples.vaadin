package osgi.enroute.examples.vaadin.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import osgi.enroute.examples.vaadin.api.Application;

@Component(name = "osgi.enroute.examples.vaadin.provider")
public class VaadinApplicationHandler {
	final ConcurrentHashMap<String, VaadinOSGiServlet> servlets = new ConcurrentHashMap<>();
	private HttpService http;

	@Reference
	void setHttpService( HttpService http ) {
		this.http = http;
	}

	@Reference
	void addZApplication(Application<?> a, Map<String, Object> map, ServiceReference<?> ref) throws ServletException, NamespaceException {
		try {
			String alias = (String) map.get("alias");
			VaadinOSGiServlet servlet = new VaadinOSGiServlet(a, ref);
			servlets.put(alias, servlet);
			http.registerServlet(alias, servlet, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void removeZApplication(Application<?> a, Map<String, Object> map) {
		String alias = (String) map.get("alias");
		VaadinOSGiServlet app = servlets.remove(alias);
		if (app != null) {
			http.unregister(alias);
		}
	}
}
