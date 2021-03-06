package org.tc.perf.work.items;

import static org.tc.perf.util.SharedConstants.HOSTNAME;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.tc.perf.process.ProcessConfig;
import org.tc.perf.util.Configuration;

/**
 * 
 * Work item to stop a terracotta server. Builds the classpath and stops the L2
 * server. Keeps count of servers stopped on a box and keeps on incrementing the
 * port in server name i.e. HOSTNAME-PORT.
 * 
 * @author Himadri Singh
 */

public class StopL2 extends AbstractL2Work {

	private static final long serialVersionUID = 1L;
	private static AtomicInteger serversStopped = new AtomicInteger();

	public StopL2(final Configuration configuration) {
		super(configuration);
	}

	@Override
	protected ProcessConfig getProcessConfig() {
		String TC_INSTALL_DIR = getTcInstallDir();

		ArrayList<String> jvmArgs = new ArrayList<String>(configuration
				.getL2_jvmArgs());
		jvmArgs.add("-Dtc.install-root=" + TC_INSTALL_DIR);

		String serverName = HOSTNAME + "-952" + serversStopped.get();
		ArrayList<String> args = new ArrayList<String>();
		args.add("-f");
		args.add("tc-config.xml");
		args.add("-n");
		args.add(serverName);

		serversStopped.incrementAndGet();

		ProcessConfig config = new ProcessConfig(TC_STOP_MAIN_CLASS);
		config.setClasspath(TC_INSTALL_DIR + TC_CLASSPATH).setArguments(args)
		.setLocation(TC_INSTALL_DIR).setJvmArgs(jvmArgs)
		.setRelativeLogDir("../server/").setJavaHome(
				configuration.getL2_javaHome()).setLogSnippet(
						TC_STOP_LOG).setConsoleLog(serverName + ".log");

		return config;
	}

}
