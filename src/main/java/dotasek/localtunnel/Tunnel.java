package dotasek.localtunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import com.google.gson.Gson;

public class Tunnel extends Observable implements Observer {

	Gson gson = new Gson();
	
	private boolean closed;
	
	public boolean isClosed() {
		return closed;
	}
	
	private String host;
	
	public String getHost() {
		return host;
	}
	
	private String subdomain;
	
	private int tunnelCount;
	
	public Tunnel(String host, String subdomain) {
		this.host = host;
		if (this.host == null) {
			this.host = "https://localtunnel.me";
		}
		this.subdomain = subdomain;
		this.closed = false;
	}

	
	

	public void open() throws IOException {
		TunnelConnectionModel tunnelConnection = null;
		tunnelConnection = init();
		System.out.println("connected to tunnel:" + tunnelConnection.name + " " + tunnelConnection.remote_host + " " + tunnelConnection.remote_port);
		establish(tunnelConnection);
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
			//TODO return body message if available.
			throw new IOException("localtunnel server returned an error, please try again");
		}
		
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

	private void establish(TunnelConnectionModel tunnelConnection) throws UnknownHostException, IOException {
		
		TunnelCluster tunnelCluster = TunnelCluster.getTunnelCluster(tunnelConnection);
		tunnelCluster.addObserver(this);
		
		tunnelCount = 0;
		
		for (int i = 0; i < tunnelConnection.max_conn; i++) {
			tunnelCluster.open();
		}
		
	}
	
	private Socket socket;
	
	public void close() {
		this.closed = true;
		closeSocket();
	}


	private void closeSocket() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof TunnelEvent)
		{
			TunnelEvent tunnelEvent = (TunnelEvent) arg1;
			switch (tunnelEvent.type) {
				case OPEN: 
					//once!
					//TODO
					//tunnel.emit(url)
					tunnelCount++;
					this.socket = tunnelEvent.socket;
					if (closed) {
						closeSocket();
					}
					
					System.out.println("tunnel open [total: " + tunnelCount + "]");
					break;
				case ERROR: 
					//TODO
					//self.emit(error)
					break;
				case DEAD: 
					break;
				case REQUEST: 
					break;
			}
		}
		
	}
	
}
