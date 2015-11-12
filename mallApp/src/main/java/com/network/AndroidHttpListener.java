package com.network;

/**
 * 
 * @author Muhammad Babar Naveed
 *    Date Feb 26, 2013
 *
 */
public interface AndroidHttpListener {

	
	public void dataRecieved(byte data[]);
	public void errorOccured(final String pError);
}
