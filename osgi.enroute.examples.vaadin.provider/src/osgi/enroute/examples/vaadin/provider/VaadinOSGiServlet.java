package osgi.enroute.examples.vaadin.provider;

import java.util.Properties;

import javax.servlet.Servlet;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DefaultDeploymentConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.UI;

import osgi.enroute.examples.vaadin.api.Application;

@SuppressWarnings("serial")
@Component(name = "osgi.enroute.vaadin.servlet", //
		property = {

				HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/foo", Constants.SERVICE_RANKING
						+ ":Integer=100" }, service = Servlet.class, configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true)
@VaadinServletConfiguration(productionMode = false, ui = UI.class)
public class VaadinOSGiServlet extends VaadinServlet {

	Application<?> application;
	ServiceReference<?> ref;

	public VaadinOSGiServlet(Application<?> application, ServiceReference<?> ref) {
		this.application = application;
		this.ref = ref;
	}

	@Override
	protected DeploymentConfiguration createDeploymentConfiguration(Properties initParameters) {
		initParameters.setProperty(SERVLET_PARAMETER_PRODUCTION_MODE, "false");
		initParameters.setProperty(SERVLET_PARAMETER_UI_PROVIDER, VaadinApplicationUIProvider.class.getName());
		return new DefaultDeploymentConfiguration(getClass(), initParameters);
	}

	class LocalVaadinServletService extends VaadinServletService {

		public LocalVaadinServletService(VaadinServlet servlet, DeploymentConfiguration deploymentConfiguration)
				throws ServiceException {
			super(servlet, deploymentConfiguration);
		}

		Application<?> getApplication() {
			return application;
		}
	}

	@Override
	protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration)
			throws ServiceException {
		try {

			LocalVaadinServletService service = new LocalVaadinServletService(this, deploymentConfiguration);
			service.init();
			service.setClassLoader(new ClassLoader() {
				@Override
				public Class<?> loadClass(String name) throws ClassNotFoundException {
					try {
						try {
							Class<?> loadClass = VaadinOSGiServlet.class.getClassLoader().loadClass(name);
							return loadClass;
						} catch (ClassNotFoundException e) {
							return ref.getBundle().loadClass(name);
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			});

			return service;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
