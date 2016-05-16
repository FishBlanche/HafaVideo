package com.greenorbs.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 获取spring的
 * 
 * @author shadow
 * 
 */
public class BeanFactoryUtil implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	/**
	 * 设置BeanFactory
	 */
	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.beanFactory = factory;
	}

	/**
	 * 根据beanName名字取得bean
	 * 
	 * @param beanName
	 *            bean的名字
	 * @return 对应的是对象
	 */
	public static <T> T getBean(String beanName) {
		if (null != beanFactory) {
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}

}
