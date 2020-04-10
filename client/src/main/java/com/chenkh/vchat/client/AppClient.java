package com.chenkh.vchat.client;


import com.chenkh.vchat.client.net.IClient;
import com.chenkh.vchat.client.net.code.JsonDecoder;
import com.chenkh.vchat.client.net.code.JsonEncoder;

public class AppClient {





	public static void main(String[] args) {
		IClient aioClient = IClient.createAioClient(JsonEncoder::new, JsonDecoder::new);
		ITray tray=new VTray();
		IContext context = new ClientContext(aioClient,tray);
		context.launchUi();



	}

}
