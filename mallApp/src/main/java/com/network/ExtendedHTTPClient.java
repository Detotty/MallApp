package com.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import android.content.Context;

public class ExtendedHTTPClient extends DefaultHttpClient{

	final Context context;

	public ExtendedHTTPClient(Context context) {
		this.context = context;
	}


	@Override
	protected ClientConnectionManager createClientConnectionManager()   {
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https",getSslSocketFactory(), 443));
		return new SingleClientConnManager(getParams(), registry);
	}


	public SSLSocketFactory getSslSocketFactory()
	{
		KeyStore trustStore;
		SSLSocketFactory sf = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

			trustStore.load(null, null);
			sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sf;
	}

	//	    private SSLSocketFactory newSslSocketFactory() {
	//	        try {
	//	            // Get an instance of the Bouncy Castle KeyStore format
	//	            KeyStore trusted = KeyStore.getInstance("BKS");
	//	            // Get the raw resource, which contains the keystore with
	//	            // your trusted certificates (root and any intermediate certs)
	//	            InputStream in = context.getResources().openRawResource(R.raw.mykeystore);
	//	            try {
	//	                // Initialize the keystore with the provided trusted certificates
	//	                // Also provide the password of the keystore
	//	                trusted.load(in, "mysecret".toCharArray());
	//	            } finally {
	//	                in.close();
	//	            }
	//	            // Pass the keystore to the SSLSocketFactory. The factory is responsible
	//	            // for the verification of the server certificate.
	//	            SSLSocketFactory sf = new SSLSocketFactory(trusted);
	//	            // Hostname verification from certificate
	//	            // http://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html#d4e506
	//	            sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
	//	            return sf;
	//	        } catch (Exception e) {
	//	            throw new AssertionError(e);
	//	        }
	//	    }

	class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}