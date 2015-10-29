package com.github.photobug.module;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.github.photobug.PhotoBugSetup;




@IocBy(type=ComboIocProvider.class,args={"*org.nutz.ioc.loader.json.JsonLoader","ioc/",
	  "*org.nutz.ioc.loader.annotation.AnnotationIocLoader","com.github.photobug.module"})
@Encoding(input="utf8",output="utf8")
@Modules(scanPackage=true)
@Localization("msg")
@Ok("jsp")
@Fail("jsp")
@SetupBy(value=PhotoBugSetup.class)
public class MainModule {

}
