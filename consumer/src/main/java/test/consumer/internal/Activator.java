package test.consumer.internal;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import com.google.common.collect.Lists;

import test.api.UiComponentFactory;
import test.api.UiComponentFactoryR2;

public class Activator implements BundleActivator {

	private Main main;
	private ServiceTracker<UiComponentFactory, List<JComponent>> tracker;

	public void start(BundleContext context) throws Exception {
		main = new Main();
		System.out.println("started");
		// FrameworkUtil
		// .createFilter("(&(objectClass=test.api.UiComponentFactory)(bla=blub))")
		tracker = new ServiceTracker<UiComponentFactory, List<JComponent>>(
				context, UiComponentFactory.class, null) {
			@Override
			public List<JComponent> addingService(
					ServiceReference<UiComponentFactory> reference) {
				UiComponentFactory service = context.getService(reference);
				List<JComponent> components = new ArrayList<>();
				JComponent comp1 = service.createIdentityRepresentation();
				components.add(comp1);
				main.addComponent(comp1);
				if (service instanceof UiComponentFactoryR2) {
					JComponent comp2 = ((UiComponentFactoryR2) service)
							.createIdentitiyRepresentationButCooler();
					components.add(comp2);
					main.addComponent(comp2);
				}
				context.ungetService(reference);
				return components;
			}

			@Override
			public void removedService(
					ServiceReference<UiComponentFactory> reference,
					List<JComponent> service) {
				for (JComponent jComponent : service) {
					main.removeComponent(jComponent);
				}
			}
		};
		tracker.open();
		main.setVisible(true);
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("stoped");
		tracker.close();
		main.setVisible(false);
	}

	private class Main extends JFrame {
		public Main() {
			getContentPane().setLayout(
					new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			getContentPane().add(new JLabel("DEFAULT IS ME"));
		}

		public void addComponent(final JComponent component) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					getContentPane().add(component);
					getContentPane().revalidate();
				}
			});
		}

		public void removeComponent(final JComponent component) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					getContentPane().remove(component);
					getContentPane().revalidate();
					getContentPane().repaint();
				}
			});
		}
	}

}
