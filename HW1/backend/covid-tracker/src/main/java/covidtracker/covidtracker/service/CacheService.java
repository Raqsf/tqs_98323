package covidtracker.covidtracker.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import covidtracker.covidtracker.cache.Cache;
import covidtracker.covidtracker.model.CountryStats;

@Service
public class CacheService {

    @Autowired
    private Cache<String, CountryStats> cache /* = new Cache(300, 300) */;

    public Map<String, Object> getCacheDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("hits", cache.getHitCount());
        details.put("misses", cache.getMissCount());
        details.put("size", cache.size());
        return details;
    }
    
}
