package test.provider.internal;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import test.api.UiComponentFactoryR2;

public class Provider1IdentitiyFactory implements UiComponentFactoryR2 {

	@Override
	public JComponent createIdentityRepresentation() {
		return new JLabel("Provider 1");
	}

	@Override
	public JComponent createIdentitiyRepresentationButCooler() {
		return new JButton("Provider 1 but cooler");
	}

}
