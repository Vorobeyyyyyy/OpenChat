package com.vorobeyyyyyy.openchat.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.vorobeyyyyyy.openchat.service.IpLocationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;

@Service
@Slf4j
public class IpLocationServiceImpl implements IpLocationService {

    @Value("classpath:geolite/GeoLite2-City.mmdb")
    private Resource ipDatabase;

    private DatabaseReader databaseReader;

    @PostConstruct
    public void init() {
        try {
            databaseReader = new DatabaseReader.Builder(ipDatabase.getFile()).build();
        } catch (IOException e) {
            log.error("Error while initializing ip database reader", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public String getLocation(String ip) {
        try {
            CityResponse cityResponse = databaseReader.city(InetAddress.getByName(ip));
            return cityResponse.getCountry().getName() + "/" + cityResponse.getCity().getName();
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
