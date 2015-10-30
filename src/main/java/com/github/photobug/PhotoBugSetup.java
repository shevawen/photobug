package com.github.photobug;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class PhotoBugSetup implements Setup {

	public void init(NutConfig nc) {
		Dao dao = nc.getIoc().get(Dao.class);
		Daos.createTablesInPackage(dao , "com.github.photobug.bean", false);

	}

	public void destroy(NutConfig nc) {
		// TODO Auto-generated method stub

	}

}
