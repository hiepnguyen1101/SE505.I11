package J2EE.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping(value = "/testAPIGet", method = RequestMethod.GET)
    @ResponseBody
    public String testRestAPIGet(){
        return "[{\"name\":\"sam\",\"age\":\"12\"},{\"name\":\"sri\",\"age\":\"5\"}]";
    }

    @RequestMapping(value = "/testAPIPost", method = RequestMethod.POST)
    @ResponseBody
    public String testRestAPIPost(){
        return "[{\"name\":\"sam\",\"age\":\"12\"},{\"name\":\"sri\",\"age\":\"5\"}]";
    }
}
