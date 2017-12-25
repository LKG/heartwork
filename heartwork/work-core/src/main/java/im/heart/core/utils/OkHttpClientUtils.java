package im.heart.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkHttpClientUtils {
	protected static final Logger logger = LoggerFactory.getLogger(OkHttpClientUtils.class);
	public static String DEFAULT_ENCODING = "UTF-8";

	private final static OkHttpClient mOkHttpClient = new OkHttpClient();
	public static class UnSafeTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[] {};
		}
	}
	public static KeyManager[] prepareKeyManager(KeyStore clientKeyStore, String password) {
		try {
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(clientKeyStore, password.toCharArray());
			return keyManagerFactory.getKeyManagers();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String fetchEntityString(String url, Map<String, Object> params) throws IOException {
		return fetchEntityString(url, params, DEFAULT_ENCODING);
	}

	public static String fetchEntityString(String url, Map<String, Object> params, String encoding) throws IOException {
		return fetchResponse(url, params).body().string();
	}

	public static Response fetchResponse(String url, Map<String, Object> params) throws IOException {
		OkHttpClient copy = mOkHttpClient.newBuilder().readTimeout(500, TimeUnit.MILLISECONDS).build();
		Builder builder = new Request.Builder().url(url);
		Request request = null;
		if (params == null) {
			request = builder.build();
		} else {
			request = builder.post(builderFormBody(params)).build();
		}
		Response response = copy.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		}
		return response;
	}

	public static String encodingUrl(String url, Map<String, Object> params, String encoding) throws IOException {
		String httpParam = "";
		if (params != null) {
			for (String key : params.keySet()) {
				if (StringUtils.isBlank(httpParam)) {
					httpParam = "?";
				} else {
					httpParam = httpParam + "&";
				}
				httpParam = httpParam + key + "=" + URLEncoder.encode((String) params.get(key), encoding);
			}
		}
		return url+httpParam;
	}

	public static FormBody builderFormBody(Map<String, Object> params) throws IOException {
		FormBody.Builder formBodyBuilder = new FormBody.Builder();
		if (params != null && !params.isEmpty()) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				Object value = params.get(key);
				if (value != null) {
					formBodyBuilder.add(key, value.toString());
				}
			}
		}
		return formBodyBuilder.build();
	}

	public static byte[] fetchEntity(String url, Map<String, Object> params) throws IOException {
		return fetchResponse(url, params).body().bytes();
	}
}
