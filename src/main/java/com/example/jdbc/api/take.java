package com.example.jdbc.api;

import com.example.jdbc.Database.database;
import com.example.jdbc.link.JD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class take {

    @Autowired
    JD jd;
    @RequestMapping("/takedata")
    public String takedata() {
        Jedis jedis = new Jedis();
        List<database> Database = new ArrayList();
        Database = jd.lookup();
        for (int i = 0; i < Database.size(); i++) {
            if(!jedis.exists(Database.get(i).getName())){
                for (int j = 1; j <= Database.get(i).getNumber(); j++) {
                    jedis.rpush(Database.get(i).getName(), Database.get(i).getId() + "." + j);
                }
            }
        }
        return "成功";
    }
}
