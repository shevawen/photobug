package com.github.photobug;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.dao.util.Daos;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.github.photobug.bean.Photo;
import com.github.photobug.bean.PhotoMeta;

public class PhotoBugSetup implements Setup {

	public void init(NutConfig nc) {
		Dao dao = nc.getIoc().get(Dao.class, "dao");
		Daos.createTablesInPackage(dao , "com.github.photobug.bean", false);
//		
//		Dao dao2 = nc.getIoc().get(Dao.class, "dao2");
//		List<Record> list =dao2.query("t_photo", null, null);
//		for(Record r : list){
//			PhotoMeta m = new PhotoMeta();
//			
//			m.setName(r.getString("name"));
//			m.setCamera(r.getString("camera"));
//			m.setAperture(r.getString("aperture"));
//			m.setIso(r.getString("iso"));
//			m.setExposure(r.getString("exposure"));
//			
//			dao.insert(m);
//			
//			Photo p = new Photo();
//			p.setMetaId(m.getId());
//			p.setAuthor(r.getString("autor"));
//			p.setViews(r.getInt("views"));
//			p.setUserName(r.getString("userName"));
//			p.setLocation(r.getString("url").replace("/uploadfiles/", ""));
//			dao.insert(p);
//		}

	}

	public void destroy(NutConfig nc) {
		//TODO close hsqldb
	}

}
