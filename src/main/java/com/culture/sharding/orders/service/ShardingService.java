package com.culture.sharding.orders.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class ShardingService {

    AbstractRoutingDataSource abstractRoutingDataSource;


    @Autowired
    public ShardingService( @Qualifier("abstractRoutingDataSource") DataSource dataSource) {
        this.abstractRoutingDataSource = (AbstractRoutingDataSource) dataSource;

    }

    public String getShardingDataSource(String orgoid){

        if("org1".equals(orgoid) || "org2".equals(orgoid) ){
            return "primaryDataSource";
        }
        else {
            return "secondaryDataSource";
        }

    }

    public void createAndUseDataSource(String url, String username, String password) {
        DataSource dataSource = DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();

    }


}
