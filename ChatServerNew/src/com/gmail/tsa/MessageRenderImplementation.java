package com.gmail.tsa;

import java.text.SimpleDateFormat;

public class MessageRenderImplementation implements MessageRender {

	@Override
	public void renderMessage(Message message) {
		if (message == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy - hh:mm");
		String text = message.getSender() + " " + sdf.format(message.getDepartmentTime()) + " > " + message.getText();
		System.out.println(text);
	}

}
