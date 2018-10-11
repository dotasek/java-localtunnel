package dotasek.localtunnel;

public class TunnelConnectionModel {
	 public String remote_host;
     public Integer remote_port;
     public String name;
     public String url;
     public Integer max_conn;
     
     public TunnelConnectionModel(
    		 String remote_host,
        	 Integer remote_port, 
        	 String name, 
        	 String url, 
        	 Integer max_conn) {
    	 this.remote_host = remote_host;
    	 this.remote_port = remote_port;
    	 this.name = name;
    	 this.url = url;
    	 this.max_conn = max_conn;
     }
}
