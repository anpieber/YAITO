package test.consumer.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import test.api.DoSomething;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		System.out.println("started");
		ServiceReference<DoSomething> somethingService = context
				.getServiceReference(DoSomething.class);
		if (somethingService == null) {
			System.out.println("nope :-(");
			return;
		}
		DoSomething doSomethingService = context.getService(somethingService);
		System.out.println(doSomethingService.now());
		context.ungetService(somethingService);
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("stoped");
	}

}
