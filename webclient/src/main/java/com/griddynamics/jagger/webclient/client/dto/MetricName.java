package com.griddynamics.jagger.webclient.client.dto;

import java.io.Serializable;

/**
 * User: amikryukov
 * Date: 12/17/13
 */
public class MetricName implements Serializable {

    protected String metricName;
    protected String metricDisplayName = null;

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getMetricDisplayName() {
        return metricDisplayName == null ? metricName : metricDisplayName;
    }

    public void setMetricDisplayName(String metricDisplayName) {
        this.metricDisplayName = metricDisplayName;
    }
}
