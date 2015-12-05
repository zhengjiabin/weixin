package weixin.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import weixin.bean.Message;
import weixin.serv.MessageServ;

import com.sun.jersey.spi.resource.Singleton;

/**
 * 发送消息控制器
 * @author Administrator
 *
 */
@Controller
@Singleton
@Scope("prototype")
@Path(value = "/sendMessage")
public class SendMessageController {
	@Autowired
	private MessageServ serv;
	
	@POST
    @Path(value = "/sendMessage")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Message sendMessage() {
		Message message = serv.getSendMessage();
        return message;
    }
}
