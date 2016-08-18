package com.servitization.metadata.define.embedder;

import java.util.List;

public interface ChainGroupDefine extends ChainElementDefine {

	public List<ChainElementDefine> getChainElementDefineList();
	
	public int getGroupProcessTimeout();
	
	public int getGroupSize();

	public GroupPolicy getGroupPolicy();
}
