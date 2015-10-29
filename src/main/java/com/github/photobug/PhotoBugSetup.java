package com.github.photobug;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.resource.Scans;

public class PhotoBugSetup implements Setup {

	public void init(NutConfig nc) {
		Dao dao = nc.getIoc().get(Dao.class);
		for (Class<?> klass : Scans.me().scanPackage("com.github.photobug.bean")) {
			if (null != klass.getAnnotation(Table.class)){
				if(!dao.exists(klass)){
					dao.create(klass, false);
				}
			}
		}

	}

	public void destroy(NutConfig nc) {
		// TODO Auto-generated method stub

	}

}
