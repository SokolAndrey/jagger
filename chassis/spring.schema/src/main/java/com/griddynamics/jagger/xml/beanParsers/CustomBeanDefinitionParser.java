package com.griddynamics.jagger.xml.beanParsers;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kgribov
 * Date: 1/25/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CustomBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser{

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        if (element.hasAttribute(XMLConstants.PARENT)){
            String parent = element.getAttribute(XMLConstants.PARENT);
            if(!parent.isEmpty()) builder.setParentName(parent);
            element.removeAttribute(XMLConstants.PARENT);
        }
        if (element.hasAttribute(XMLConstants.XSI_TYPE)) element.removeAttribute(XMLConstants.XSI_TYPE);
        parseAttributes(element, parserContext, builder);
        parse(element, parserContext, builder);
    }

    protected void parseAttributes(Element element, ParserContext parserContext, BeanDefinitionBuilder builder){
        super.doParse(element, parserContext, builder);
    }

    protected abstract void parse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder);

    public static void setBeanListProperty(String propertyName, boolean merge, Element listParentElement, ParserContext parserContext, BeanDefinition bean){
        if (listParentElement == null){
            return;
        }
        List<Element> list = DomUtils.getChildElements(listParentElement);
        ManagedList result = new ManagedList();
        if (list != null){
            for (Element el : list){
                if (el.hasAttribute(XMLConstants.ATTRIBUTE_REF)){
                    String ref = el.getAttribute(XMLConstants.ATTRIBUTE_REF);
                    if (!ref.isEmpty()){
                        result.add(new RuntimeBeanReference(ref));
                    }else{
                        result.add(parserContext.getDelegate().parsePropertySubElement(el, bean));
                    }
                }else{
                    result.add(parserContext.getDelegate().parsePropertySubElement(el, bean));
                }
            }
        }
        if (merge){
            PropertyValue prop = bean.getPropertyValues().getPropertyValue(propertyName);
            if (prop != null){
                ManagedList origin = (ManagedList)prop.getValue();
                result.addAll(origin);
            }
        }
        bean.getPropertyValues().addPropertyValue(propertyName, result);
    }

    public static void setBeanProperty(String propertyName, Element element, ParserContext parserContext, BeanDefinition bean){
        if (element.hasAttribute(XMLConstants.ATTRIBUTE_REF)){
            String ref = element.getAttribute(XMLConstants.ATTRIBUTE_REF);
            if (!ref.isEmpty()){
                bean.getPropertyValues().add(propertyName, new RuntimeBeanReference(ref));
            }else{
                bean.getPropertyValues().add(propertyName, parserContext.getDelegate().parseCustomElement(element, bean));
            }
        }else{
            bean.getPropertyValues().add(propertyName, parserContext.getDelegate().parseCustomElement(element, bean));
        }
    }

    public static void addConstructorListArg(Element listParentElement, ParserContext parserContext, BeanDefinition bean){
        if (listParentElement == null){
            return;
        }
        List<Element> list = DomUtils.getChildElements(listParentElement);
        ManagedList result = new ManagedList();
        if (list != null){
            for (Element el : list){
                if (el.hasAttribute(XMLConstants.ATTRIBUTE_REF)){
                    String ref = el.getAttribute(XMLConstants.ATTRIBUTE_REF);
                    if (!ref.isEmpty()){
                        result.add(new RuntimeBeanReference(ref));
                    }else{
                        result.add(parserContext.getDelegate().parsePropertySubElement(el, bean));
                    }
                }else{
                    result.add(parserContext.getDelegate().parsePropertySubElement(el, bean));
                }
            }
        }
        bean.getConstructorArgumentValues().addGenericArgumentValue(result);
    }
}
