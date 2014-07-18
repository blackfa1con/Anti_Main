package com.dotcink.android.antitheft;


import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;


public class PayloadTrustManager implements X509TrustManager, HostnameVerifier {

	public X509Certificate[] getAcceptedIssuers() {
		// no preferred issuers
		return new X509Certificate[0];
	}

	public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
			String authType) {
		// trust everyone
	}

	public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
			String authType) {
		// trust everyone
	}

	public boolean verify(String hostname, SSLSession session) {
		// trust everyone
		return true;
	}


	public static void useFor(URLConnection uc) throws Exception {
		if (uc instanceof HttpsURLConnection) {
			HttpsURLConnection huc = ((HttpsURLConnection) uc);
			PayloadTrustManager ptm = new PayloadTrustManager();
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { ptm },
					new java.security.SecureRandom());
			huc.setSSLSocketFactory(sc.getSocketFactory());
			huc.setHostnameVerifier(ptm);
		}
	}
}
