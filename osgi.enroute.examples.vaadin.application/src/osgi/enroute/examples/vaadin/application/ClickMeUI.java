package osgi.enroute.examples.vaadin.application;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This is the Vaadin UI clas. It is derived from the Vaadin Hello World example
 */

@SuppressWarnings("serial")
@Theme("reindeer")
public class ClickMeUI extends UI {
	private static final long serialVersionUID = 1L;
	private ClickMeApp clickMeApp;

	public ClickMeUI() {
	}

	public ClickMeUI(ClickMeApp clickMeApp) {
		this.clickMeApp = clickMeApp;
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me " + clickMeApp.nextInstance());
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				clickMeApp.anotherClick();
				layout.addComponent(new Label("Thank you for clicking number " + clickMeApp.getClicks()));
			}
		});
		layout.addComponent(button);
	}
}
