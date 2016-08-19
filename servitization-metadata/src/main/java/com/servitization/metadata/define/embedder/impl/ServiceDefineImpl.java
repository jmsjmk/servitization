package com.servitization.metadata.define.embedder.impl;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.DeployModel;
import com.servitization.metadata.define.embedder.ServiceDefine;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

public class ServiceDefineImpl implements ServiceDefine {

	private static final long serialVersionUID = 1L;

	private String name;

	private String version;

	private DeployModel deployModel;

	private List<ChainElementDefine> upChainList;

	private List<ChainElementDefine> downChainList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public DeployModel getDeployModel() {
		return deployModel;
	}

	public void setDeployModel(DeployModel deployModel) {
		this.deployModel = deployModel;
	}

	public List<ChainElementDefine> getUpChainList() {
		return upChainList;
	}

	public void setUpChainList(List<ChainElementDefine> upChainList) {
		this.upChainList = upChainList;
	}

	public List<ChainElementDefine> getDownChainList() {
		return downChainList;
	}

	public void setDownChainList(List<ChainElementDefine> downChainList) {
		this.downChainList = downChainList;
	}

	@Override
	public void serialize(Element root) {
		Element me = root.addElement(this.getClass().getSimpleName());
		if (name != null)
			me.addAttribute("name", name);
		if (version != null)
			me.addAttribute("version", version);
		if (deployModel != null)
			me.addAttribute("deployModel", deployModel.name());

		if (upChainList != null) {
			Element child1 = me.addElement("upChainList");
			for (ChainElementDefine ced : upChainList) {
				ced.serialize(child1);
			}
		}
		if (downChainList != null) {
			Element child2 = me.addElement("downChainList");
			for (ChainElementDefine ced : downChainList) {
				ced.serialize(child2);
			}
		}
	}

	@Override
	public void deserialize(Element root) {
		Element self = root.element(this.getClass().getSimpleName());
		this.name = self.attributeValue("name");
		this.version = self.attributeValue("version");
		String dlyModel = self.attributeValue("deployModel");
		if (dlyModel != null)
			this.deployModel = DeployModel.valueOf(dlyModel);
		Element child1 = self.element("upChainList");
		if (child1 != null) {
			List<Element> e_upChainList = child1.elements();
			if (e_upChainList != null && e_upChainList.size() > 0) {
				upChainList = new ArrayList<>();
				for (Element e : e_upChainList) {
					Class<?> clazz;
					ChainElementDefine ced;
					try {
						clazz = Class.forName(e.getName());
						ced = (ChainElementDefine) (clazz.newInstance());
					} catch (Exception e1) {
						throw new RuntimeException("Deserialize failed!", e1);
					}
					ced.deserialize(e);
					upChainList.add(ced);
				}
			}
		}
		Element child2 = self.element("downChainList");
		if (child2 != null) {
			List<Element> e_downChainList = child2.elements();
			if (e_downChainList != null && e_downChainList.size() > 0) {
				downChainList = new ArrayList<>();
				for (Element e : e_downChainList) {
					Class<?> clazz;
					ChainElementDefine ced;
					try {
						clazz = Class.forName(e.getName());
						ced = (ChainElementDefine) (clazz.newInstance());
					} catch (Exception e1) {
						throw new RuntimeException("Deserialize failed!", e1);
					}
					ced.deserialize(e);
					downChainList.add(ced);
				}
			}
		}
	}
}
