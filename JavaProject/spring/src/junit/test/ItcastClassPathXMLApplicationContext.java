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
	private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>(); // ��ŵ�XML�е�Bean 
	private Map<String, Object> sigletons = new HashMap<String, Object>();// ���ʵ����Map����

	/**
	 * �����ĳ�ʼ��
	 * @param filename
	 */
	public ItcastClassPathXMLApplicationContext(String filename) {
		this.readXML(filename);  // ��ȡXML �ļ�
		this.instanceBean();     // ʵ����һ��Bean
		this.injectObject();     // ��Bean��ģ��ע��
	}

	/**
	 * ��ȡXML�����ļ�
	 * 
	 * @param filename
	 */
	@SuppressWarnings("unchecked")
	private void readXML(String filename) {
		SAXReader saxReader = new SAXReader(); // ����һ����ȡ��
		Document document = null;
		try {
			URL xmlPath = this.getClass().getClassLoader().getResource(filename); // ȡ�������װ����·���µ� filename��Դ�ļ�
			document = saxReader.read(xmlPath); // ��ȡ�ļ����ݷ��õ��ĵ���
			Map<String, String> nsMap = new HashMap<String, String>();

			// ���������ռ�--ΪXML�ĵ��е� xmlns=beans ��Ϊbeans�������ռ䣬Ϊ��ȡ�ڵ�ʹ��
			nsMap.put("ns", "http://www.springframework.org/schema/beans"); // �����ռ�ǰ׺--ns
			XPath xsub = document.createXPath("//ns:beans/ns:bean"); // ����beans/bean��ѯ·��
			xsub.setNamespaceURIs(nsMap); // ���������ռ�
			List<Element> beans = xsub.selectNodes(document); // ��ȡ�ĵ�������bean�ڵ�
			for (Element element : beans) {
				String id = element.attributeValue("id");
				String clazz = element.attributeValue("class");
				BeanDefinition beanDefine = new BeanDefinition(id, clazz);

				// ģ��ע�빦��
				XPath propertysub = element.createXPath("ns:property");
				propertysub.setNamespaceURIs(nsMap); // Ϊ��ѯ·�����������ռ�
				List<Element> propertys = propertysub.selectNodes(element);
				for (Element property : propertys) {
					String propertyName = property.attributeValue("name");
					String propertyref = property.attributeValue("ref");
					String propertyValue = property.attributeValue("value");
					PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyref, propertyValue);
					beanDefine.getPropertys().add(propertyDefinition);
				}
				beanDefines.add(beanDefine); // �Ѵ�ŵ�JavaBean���뵽������ȥ
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * ʹ�÷��似�������Bean������
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
	 * ���Beanʵ��
	 * @param
	 * @return
	 */
	public Object getBean(String beanName) {
		return this.sigletons.get(beanName);
	}
	
	/**
	 * ΪBean ����ע��ֵ
	 */
	private void injectObject() {
		for (BeanDefinition beanDefinition : beanDefines) {
			Object bean = sigletons.get(beanDefinition.getId()); // ��ȡʵ������Bean
			if (bean != null) {
				try {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors(); // Bean������ ������Ϣ
					for (PropertyDefinition propertyDefinition : beanDefinition.getPropertys()) { // propertyDefinition--�û����������
						for (PropertyDescriptor propertydesc : ps) {
							if (propertyDefinition.getName().equals(propertydesc.getName())) {
								Method setter = propertydesc.getWriteMethod(); // ��ȡ���Ե�setter����,private
								if (setter != null) {
									Object value = null; 
									if (propertyDefinition.getRef()!=null && !"".equals(propertyDefinition.getRef().trim())){
									    value = sigletons.get(propertyDefinition.getRef()); // ���ref��ֵ,����ע��											
									} else{  // ע��Ļ���ֵ
										value = ConvertUtils.convert(propertyDefinition.getValue(), propertydesc.getPropertyType());
									}								
									setter.setAccessible(true); // ��ֹset����Ϊ˽��
									setter.invoke(bean, value); // �����ö���ע�뵽������
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
