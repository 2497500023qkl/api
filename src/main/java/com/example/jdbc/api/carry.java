package com.example.jdbc.api;

import com.example.jdbc.link.JD;
import com.example.jdbc.link.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class carry {

    @Autowired
    JD jd;

    @RequestMapping("/carry")
    public String carrycommodity() {
        Run run = new Run();
        Thread t1 = new Thread(run);
        t1.start();
        return "成功";
    }

}
