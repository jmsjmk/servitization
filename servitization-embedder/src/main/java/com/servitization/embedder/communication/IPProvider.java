package com.servitization.embedder.communication;

import java.net.InetAddress;

public class IPProvider {

	static {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
			hostname = addr.getHostName();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String ip;

	public static String hostname;

}
