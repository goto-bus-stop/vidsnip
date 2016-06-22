package space.vidsnip.model;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import space.vidsnip.model.snip.Snip;
import space.vidsnip.model.user.User;

import javax.sql.DataSource;

/**
 * Created by Marijn on 21/06/2016.
 */

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "relationalEntityManagerFactory",
        transactionManagerRef = "relationalTransactionManager")

public class RelationalConfig {

    @Bean
    PlatformTransactionManager relationalTransactionManager(){
        return new JpaTransactionManager(relationalEntityManagerFactory().getObject());
    }


    @Bean
    LocalContainerEntityManagerFactoryBean relationalEntityManagerFactory(){

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(relationalDataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan(Snip.class.getPackage().getName(), User.class.getPackage().getName());

        return factoryBean;

    }

    @ConfigurationProperties(prefix = "datasource.mysql")
    @Bean
    DataSource relationalDataSource(){
        return DataSourceBuilder
                .create()
                .build();
    }

    @Configuration
    @EnableNeo4jRepositories("space.vidsnip.model.graphuser")
    @EnableTransactionManagement
    @ComponentScan("space.vidsnip.model.graphuser")
    public class PersistenceContext extends Neo4jConfiguration {

        @Override
        public SessionFactory getSessionFactory() {
            return new SessionFactory("space.vidsnip.model.graphuser");
        }
    }
}
