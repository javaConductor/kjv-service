package org.swordexplorer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * Created by lee on 6/10/17.
 */
@groovy.util.logging.Commons
@SpringBootApplication
class Main {

    static void main(String[] args) {
        log.error("Starting kjv-service ...")
        SpringApplication.run(Main.class, args);
        log.debug("Started kjv-service")
    }

}