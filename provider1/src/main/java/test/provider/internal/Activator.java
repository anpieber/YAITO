package test.provider.internal;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import test.api.UiComponentFactory;

public class Activator implements BundleActivator {

	ServiceRegistration<UiComponentFactory> registration;

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("starting provider1");
		registration = context.registerService(UiComponentFactory.class,
				new UiComponentFactory() {
					@Override
					public JComponent createIdentityRepresentation() {
						return new JLabel("Provider 1");
					}
				}, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("stoping provider 1");
		registration.unregister();
	}

}
