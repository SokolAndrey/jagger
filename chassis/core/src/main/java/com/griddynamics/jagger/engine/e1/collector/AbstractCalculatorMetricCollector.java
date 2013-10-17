package com.griddynamics.jagger.engine.e1.collector;

import com.griddynamics.jagger.coordinator.NodeContext;
import com.griddynamics.jagger.engine.e1.scenario.NodeSideInit;
import com.griddynamics.jagger.storage.KeyValueStorage;
import com.griddynamics.jagger.storage.Namespace;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kgribov
 * Date: 10/10/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCalculatorMetricCollector<R> implements NodeSideInit, Serializable{
    protected String sessionId;
    protected String taskId;
    protected NodeContext nodeContext;

    protected MetricCalculator<R> metricCalculator;
    protected String name;
    protected List<MetricDescriptionEntry> aggregators;

    @Override
    public void init(String sessionId, String taskId, NodeContext nodeContext) {
        this.sessionId = sessionId;
        this.taskId = taskId;
        this.nodeContext = nodeContext;

        KeyValueStorage storage = nodeContext.getService(KeyValueStorage.class);
        storage.put(Namespace.of(
                sessionId, taskId, "metricAggregatorProviders"),
                name,
                aggregators
        );
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetricCalculator<R> getMetricCalculator() {
        return metricCalculator;
    }

    public String getName() {
        return name;
    }

    public void setAggregators(List<MetricDescriptionEntry> aggregators) {
        this.aggregators = aggregators;
    }

    public void setMetricCalculator(MetricCalculator<R> metricCalculator) {
        this.metricCalculator = metricCalculator;
    }

    public List<MetricDescriptionEntry> getAggregators() {
        return aggregators;
    }


    public static final class MetricDescriptionEntry {
        private boolean needPlotData;
        private MetricAggregatorProvider metricAggregatorProvider;

        public MetricDescriptionEntry(MetricAggregatorProvider metricAggregatorProvider, boolean needPlotData) {
            this.needPlotData = needPlotData;
            this.metricAggregatorProvider = metricAggregatorProvider;
        }

        public MetricDescriptionEntry() {
        }

        public boolean isNeedPlotData() {
            return needPlotData;
        }

        public void setNeedPlotData(boolean needPlotData) {
            this.needPlotData = needPlotData;
        }

        public MetricAggregatorProvider getMetricAggregatorProvider() {
            return metricAggregatorProvider;
        }

        public void setMetricAggregatorProvider(MetricAggregatorProvider metricAggregatorProvider) {
            this.metricAggregatorProvider = metricAggregatorProvider;
        }
    }
}
