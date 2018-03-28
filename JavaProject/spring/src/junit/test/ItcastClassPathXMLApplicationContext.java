package junit.test;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.XPath;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author Fall-Leaf
 * 
 */

public class ItcastClassPathXMLApplicationContext {
	private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>(); // 存放的XML中的Bean 
	private Map<String, Object> sigletons = new HashMap<String, Object>();// 存放实例的Map集合

	/**
	 * 容器的初始化
	 * @param filename
	 */
	public ItcastClassPathXMLApplicationContext(String filename) {
		this.readXML(filename);  // 读取XML 文件
		this.instanceBean();     // 实例化一个Bean
		this.injectObject();     // 对Bean的模拟注入
	}

	/**
	 * 读取XML配置文件
	 * 
	 * @param filename
	 */
	@SuppressWarnings("unchecked")
	private void readXML(String filename) {
		SAXReader saxReader = new SAXReader(); // 创建一个读取器
		Document document = null;
		try {
			URL xmlPath = this.getClass().getClassLoader().getResource(filename); // 取得类的类装载器路径下的 filename资源文件
			document = saxReader.read(xmlPath); // 读取文件内容放置到文档中
			Map<String, String> nsMap = new HashMap<String, String>();

			// 加入命名空间--为XML文档中的 xmlns=beans 即为beans的命名空间，为读取节点使用
			nsMap.put("ns", "http://www.springframework.org/schema/beans"); // 命名空间前缀--ns
			XPath xsub = document.createXPath("//ns:beans/ns:bean"); // 创建beans/bean查询路径
			xsub.setNamespaceURIs(nsMap); // 创建命名空间
			List<Element> beans = xsub.selectNodes(document); // 获取文档下所有bean节点
			for (Element element : beans) {
				String id = element.attributeValue("id");
				String clazz = element.attributeValue("class");
				BeanDefinition beanDefine = new BeanDefinition(id, clazz);

				// 模拟注入功能
				XPath propertysub = element.createXPath("ns:property");
				propertysub.setNamespaceURIs(nsMap); // 为查询路径设置命名空间
				List<Element> propertys = propertysub.selectNodes(element);
				for (Element property : propertys) {
					String propertyName = property.attributeValue("name");
					String propertyref = property.attributeValue("ref");
					String propertyValue = property.attributeValue("value");
					PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyref, propertyValue);
					beanDefine.getPropertys().add(propertyDefinition);
				}
				beanDefines.add(beanDefine); // 把存放的JavaBean放入到集合中去
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 使用反射技术，完成Bean事例化
	 */
	private void instanceBean() {
		for (BeanDefinition beanDefinition : beanDefines) {
			try {
				if (beanDefinition.getClassName() != null && !"".equals(beanDefinition.getClassName().trim()))
					sigletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得Bean实例
	 * @param
	 * @return
	 */
	public Object getBean(String beanName) {
		return this.sigletons.get(beanName);
	}
	
	/**
	 * 为Bean 对像注入值
	 */
	private void injectObject() {
		for (BeanDefinition beanDefinition : beanDefines) {
			Object bean = sigletons.get(beanDefinition.getId()); // 获取实例化的Bean
			if (bean != null) {
				try {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors(); // Bean的属性 描述信息
					for (PropertyDefinition propertyDefinition : beanDefinition.getPropertys()) { // propertyDefinition--用户定义的属性
						for (PropertyDescriptor propertydesc : ps) {
							if (propertyDefinition.getName().equals(propertydesc.getName())) {
								Method setter = propertydesc.getWriteMethod(); // 获取属性的setter方法,private
								if (setter != null) {
									Object value = null; 
									if (propertyDefinition.getRef()!=null && !"".equals(propertyDefinition.getRef().trim())){
									    value = sigletons.get(propertyDefinition.getRef()); // 获得ref的值,依赖注入											
									} else{  // 注入的基本值
										value = ConvertUtils.convert(propertyDefinition.getValue(), propertydesc.getPropertyType());
									}								
									setter.setAccessible(true); // 防止set方法为私有
									setter.invoke(bean, value); // 把引用对象注入到属性中
								}
								break;
							}
						}
					}
				 } catch (Exception e) {
			  }
			}
		}

	}
}
