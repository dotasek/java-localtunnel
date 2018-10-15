package dotasek.localtunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;

import com.google.gson.Gson;

public class Tunnel extends Observable {

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

	private TunnelConnectionModel init() throws IOException {
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
		
		
		if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException("localtunnel server returned an error, please try again");
		}
		
		Gson gson = new Gson();
		TunnelConnectionResponseModel tunnelConnectionResponse = gson.fromJson(in, TunnelConnectionResponseModel.class);
		
		Integer port = tunnelConnectionResponse.port;
		String host = upstream.getHost();
		Integer max_conn = tunnelConnectionResponse.max_conn_count != null ? tunnelConnectionResponse.max_conn_count : 1;
		return new TunnelConnectionModel(
				upstream.getHost(),
				tunnelConnectionResponse.port,
				tunnelConnectionResponse.id,
				tunnelConnectionResponse.url,
				max_conn
				);
	}

	private void establish(TunnelConnectionModel tunnelConnection) {
		
		TunnelCluster tunnelCluster = TunnelCluster.getTunnelCluster(tunnelConnection);
		
		
	}
	
	public void open() throws IOException {
		TunnelConnectionModel tunnelConnection = null;
		tunnelConnection = init();
		establish(tunnelConnection);
	}
	
	public void close() {
		this.closed = true;
	}
	
}
