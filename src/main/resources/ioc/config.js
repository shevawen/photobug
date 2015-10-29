var config = {

	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			driverClassName : "org.hsqldb.jdbcDriver",
			url : {java:"com.github.photobug.Helper.getDbPath('jdbc:hsqldb:file:${app.root}data/db')"},
			username : "sa",
			password : "",
			filters : "stat,log4j",
			testOnBorrow : false
		}
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		fields : {
			dataSource : {
				refer : 'dataSource'
			}
		}
	},
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			paths : [ "config.properties" ]
		}
	},
	json : {
		type : "org.nutz.mvc.view.UTF8JsonView",
		args : [ {
			type : 'org.nutz.json.JsonFormat',
			fields : {
				autoUnicode : true
			}
		} ]
	}
};