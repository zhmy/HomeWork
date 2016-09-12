package com.zmy.library;

/**
 * 唯一id生成器
 * 
 * @author zhaolin02
 * 
 */
public class BdUniqueId {
	private static final int MIN_ID = 1000000;
	private volatile static int sBaseId = 0;
	private int mId = 0;

	private BdUniqueId() {

	}

	public static synchronized BdUniqueId gen() {
		if (sBaseId < MIN_ID) {
			sBaseId = MIN_ID;
		}
		BdUniqueId uniqueId = new BdUniqueId();
		uniqueId.mId = sBaseId;
		sBaseId++;
		return uniqueId;
	}

	public int getId() {
		return mId;
	}
}
