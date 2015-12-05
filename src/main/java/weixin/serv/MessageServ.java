package weixin.serv;

import org.springframework.stereotype.Service;

import weixin.bean.Message;

@Service
public class MessageServ {

	public Message getSendMessage() {
		Message message = new Message();

		return message;
	}
}
