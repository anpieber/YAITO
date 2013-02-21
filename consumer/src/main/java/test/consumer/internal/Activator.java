package test.consumer.internal;

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

import test.api.UiComponentFactory;

public class Activator implements BundleActivator {

	private Main main;
	private ServiceTracker<UiComponentFactory, JComponent> tracker;

	public void start(BundleContext context) throws Exception {
		main = new Main();
		System.out.println("started");
		tracker = new ServiceTracker<UiComponentFactory, JComponent>(
				context,
				FrameworkUtil
						.createFilter("(&(objectClass=test.api.UiComponentFactory)(bla=blub))"),
				null) {
			@Override
			public JComponent addingService(
					ServiceReference<UiComponentFactory> reference) {
				UiComponentFactory service = context.getService(reference);
				JComponent component = service.createIdentityRepresentation();
				main.addComponent(component);
				context.ungetService(reference);
				return component;
			}

			@Override
			public void removedService(
					ServiceReference<UiComponentFactory> reference,
					JComponent service) {
				main.removeComponent(service);
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
