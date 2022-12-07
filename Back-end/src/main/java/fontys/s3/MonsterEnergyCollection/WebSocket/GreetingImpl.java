package fontys.s3.MonsterEnergyCollection.WebSocket;

import fontys.s3.MonsterEnergyCollection.Business.Entity.CommentEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class GreetingImpl {
    public CommentEntity greeting(CommentEntity helloMessage){
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(helloMessage.getName()) + "!");
        return helloMessage;
    };
}
