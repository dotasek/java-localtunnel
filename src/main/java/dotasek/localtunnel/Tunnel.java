package dotasek.localtunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Tunnel {

	private boolean closed;
	
	public boolean isClosed() {
		return closed;
	}
	
	private String host;
	
	public String getHost() {
		return host;
	}
	
	private String subdomain;
	
	public Tunnel(String host, String subdomain) {
		this.host = host;
		if (this.host == null) {
			this.host = "https://localtunnel.me";
		}
		this.subdomain = subdomain;
		this.closed = false;
	}

	public String init() throws IOException {
		String params = "{ responseType: 'json'}";
		String baseUri = this.host + '/';
		
		String uri = baseUri + (subdomain != null ? subdomain : "?new");
		
		URL upstream =new URL(uri);
		HttpURLConnection connection = (HttpURLConnection)upstream.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		out.write(params);
		out.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		 
		for (String line = in.readLine(); line != null; line = in.readLine())
		{
			System.out.println(line);
		}
		
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			throw new IOException("localtunnel server returned an error, please try again");
		}
		
		
		//TODO continue to port localtunnel from Tunnel.js line 51
		return null;
	}

	public void close() {
		this.closed = true;
	}
	
}
