package com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 8/31/16.
 */
@JsonRootName(value = "routes")
public class RoutesWrapper {

    private Map<String, Route> routes;

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @JsonAnyGetter
    public Map<String, Route> getRoutes() {
        return routes;
    }

    @JsonAnySetter
    public void setRoutes(Map<String, Route> routes) {
        this.routes = routes;
    }

}
