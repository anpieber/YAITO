package test.provider.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import test.api.UiComponentFactory;

public class Activator implements BundleActivator {

	ServiceRegistration<UiComponentFactory> registration;

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("starting provider2");
		Dictionary<String, String> prop = new Hashtable<>();
		prop.put("bla", "blub");
		registration = context.registerService(UiComponentFactory.class,
				new UiComponentFactory() {
					@Override
					public JComponent createIdentityRepresentation() {
						return new JLabel("Provider 2");
					}
				}, prop);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("stoping provider 2");
		registration.unregister();
	}

}
