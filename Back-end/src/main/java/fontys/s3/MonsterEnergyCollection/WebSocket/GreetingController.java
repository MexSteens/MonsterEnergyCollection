package fontys.s3.MonsterEnergyCollection.WebSocket;

import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    private final GreetingImpl greetingImpl;

    public GreetingController(GreetingImpl greetingImpl) {
        this.greetingImpl = greetingImpl;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public CommentEntity greeting(CommentEntity message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return greetingImpl.greeting(message);
    }

}
