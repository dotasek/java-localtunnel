package dotasek.localtunnel.test;

import java.util.Observable;

import org.junit.Test;

import dotasek.localtunnel.Tunnel;

public class TunnelTest  {
	 @Test
	    public void test1() throws Exception {
		 Tunnel tunnel = new Tunnel(null, null);
		 tunnel.open();
		 
		// {"id":"unlucky-dragon-42","port":38570,"max_conn_count":10,"url":"https://unlucky-dragon-42.localtunnel.me"}

	 }
}
