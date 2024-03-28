package com.culture.sharding;

import com.culture.sharding.orders.repository.DataSourceContextHolder;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.culture.sharding.orders",
        entityManagerFactoryRef = "primaryEntityManagerFactory"
)
public class ShardingConfiguration {


    @Bean
    @Primary
    @Qualifier("abstractRoutingDataSource")
    public DataSource dataSource() {
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {

                String key = DataSourceContextHolder.getDataSourceKey();

                if(Objects.isNull(key)){
                    return "primaryDataSource";
                }
                else {
                    return key;
                }
            }
        };

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("primaryDataSource", primaryDataSource());
        // targetDataSources.put("secondaryDataSource", secondaryDataSource());
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }


    @Bean
    //@Qualifier("primaryDataSource")
    public DataSource primaryDataSource() {

        System.out.println("Fucking god");

        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setName("shard1")
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("no-foo.foo")
                .addScripts("shard1_customer_data.foo")
                .build();
    }

    @Bean
    //@Qualifier("secondaryDataSource")
    public DataSource secondaryDataSource() {

        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setName("shard2")
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("no-foo.foo")
                .addScripts("shard2_customer_data.foo")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.culture.sharding.orders")
                .build();
    }

}


