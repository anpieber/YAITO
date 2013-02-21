package test.provider.internal;

import javax.swing.JComponent;
import javax.swing.JLabel;

import test.api.UiComponentFactory;

public class Provider1IdentitiyFactory implements UiComponentFactory {

	@Override
	public JComponent createIdentityRepresentation() {
		return new JLabel("Provider 1");
	}

}
