package com.gmail.tsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class SenderThread implements Runnable {
	private MessageProvider messageProvider;
	private String sender;

	public SenderThread(MessageProvider messageProvider, String sender) {
		super();
		this.messageProvider = messageProvider;
		this.sender = sender;
	}

	public SenderThread() {
		super();
	}

	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			Thread thS = Thread.currentThread();
			for (; !thS.isInterrupted();) {
				String text = br.readLine();
				Message message = new Message(text, new Date(), sender);
				messageProvider.sendMessage(message);
			}

		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.println("SenderThread shutdown");
	}

}
