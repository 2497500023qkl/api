package com.example.jdbc.link;

import com.example.jdbc.Database.order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@Component
public class Run implements Runnable {

    @Autowired
    JD jd;
    public static Run run;

    @PostConstruct
    public void init() {
        run = this;
        run.jd = this.jd;
    }

    @Override
    public void run() {
        Jedis jedis = new Jedis();
        for (; ; ) {
            order O = new order();
            try {
                String pop = jedis.rpop("pop");
                if (pop == null) {
                    Thread.sleep(1000);
                } else {
                    O.setUid(Integer.parseInt(pop.split("/")[0]));
                    O.setSid(Integer.parseInt(pop.split("/")[1].split("\\.")[0]));
                    O.setPop(Integer.parseInt(pop.split("/")[1].split("\\.")[1]));
                    run.jd.deposit(O);
                    System.out.println("asd");
                }
            } catch (Exception e) {
            }

        }
    }
}
