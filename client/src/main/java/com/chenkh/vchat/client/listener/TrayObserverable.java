package com.chenkh.vchat.client.listener;

public interface TrayObserverable {

	public void addObserver(TrayListener listener);

	public void removeObserver(TrayListener listener);

}
