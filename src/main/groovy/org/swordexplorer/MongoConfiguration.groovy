package org.swordexplorer

import com.mongodb.Mongo
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.swordexplorer.crossref.db.XRefDb

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackageClasses = [XRefDb])
@PropertySource("classpath:app.properties")
class MongoConfiguration extends AbstractMongoConfiguration {


    @Override
    protected String getDatabaseName() {
        return "demo";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new Mongo();
    }


}