package test.provider.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import test.api.DoSomething;

public class Activator implements BundleActivator {

	ServiceRegistration<DoSomething> registration;

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("starting provider1");
		registration = context.registerService(DoSomething.class,
				new DoSomething() {
					@Override
					public String now() {
						return "what the hell";
					}
				}, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("stoping provider 1");
		registration.unregister();
	}

}
