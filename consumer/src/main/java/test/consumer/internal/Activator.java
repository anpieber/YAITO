package test.consumer.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import test.api.DoSomething;

public class Activator implements BundleActivator {

	ServiceTracker<DoSomething, DoSomething> tracker;

	public void start(BundleContext context) throws Exception {
		System.out.println("started");
		tracker = new ServiceTracker<DoSomething, DoSomething>(context,
				DoSomething.class, null) {
			@Override
			public DoSomething addingService(
					ServiceReference<DoSomething> reference) {
				DoSomething service = super.addingService(reference);
				System.out.println(service.now());
				return service;
			}

			@Override
			public void removedService(ServiceReference<DoSomething> reference,
					DoSomething service) {
				System.out.println("entfernt");
				super.removedService(reference, service);
			}
		};
		tracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("stoped");
		tracker.close();
	}

}
