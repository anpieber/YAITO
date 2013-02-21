package test.test;

import static junit.framework.Assert.assertTrue;
import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.maven;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.BundleContext;

import test.api.UiComponentFactory;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class IntegrationTest {

	@Inject
	private BundleContext bundleContext;

	@Configuration
	public Option[] config() {
		return new Option[] {
				karafDistributionConfiguration()
						.frameworkUrl(
								maven().groupId("org.apache.karaf")
										.artifactId("apache-karaf").type("zip")
										.version("2.3.1-SNAPSHOT"))
						.karafVersion("2.3.0").name("Apache Karaf"),
				CoreOptions.mavenBundle("test", "api", "1.1.0-SNAPSHOT"),
				// CoreOptions.mavenBundle("test", "consumer","1.0.0-SNAPSHOT"),
				CoreOptions.mavenBundle("test", "provider1", "1.0.0-SNAPSHOT"),
				CoreOptions.mavenBundle("test", "provider2", "1.0.0-SNAPSHOT") };
	}

	@Test
	public void test() throws Exception {
		Assert.assertEquals(
				2,
				bundleContext.getServiceReferences(UiComponentFactory.class,
						null).size());
	}
}