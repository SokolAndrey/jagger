package com.griddynamics.jagger.xml.beanParsers.workload.scenario;

import com.griddynamics.jagger.invoker.QueryPoolScenarioFactory;
import com.griddynamics.jagger.xml.beanParsers.CustomBeanDefinitionParser;
import com.griddynamics.jagger.xml.beanParsers.XMLConstants;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: kgribov
 * Date: 1/22/13
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryPoolScenarioDefinitionParser extends CustomBeanDefinitionParser {

    @Override
    protected Class getBeanClass(Element element) {
        return QueryPoolScenarioFactory.class;
    }

    @Override
    protected void parse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {

        //parse invoker
        Element invokerElement = DomUtils.getChildElementByTagName(element, XMLConstants.INVOKER);
        setBeanProperty(XMLConstants.INVOKER_CLAZZ, invokerElement, parserContext, builder.getBeanDefinition());

        //parse balancer
        Element balancerElement = DomUtils.getChildElementByTagName(element, XMLConstants.LOAD_BALANCER);
        BeanDefinition balancerBean = parserContext.getDelegate().parseCustomElement(balancerElement);

        //parse endpointProvider
        Element endpointProviderElement = DomUtils.getChildElementByTagName(element, XMLConstants.ENDPOINT_PROVIDER);
        setBeanProperty(XMLConstants.ENDPOINT_PROVIDER, endpointProviderElement, parserContext, balancerBean);

        //parse queryProvider
        Element queryProviderElement = DomUtils.getChildElementByTagName(element, XMLConstants.QUERY_PROVIDER);
        setBeanProperty(XMLConstants.QUERY_PROVIDER, queryProviderElement, parserContext, balancerBean);

        builder.addPropertyValue(XMLConstants.LOAD_BALANCER, balancerBean);
    }

}
