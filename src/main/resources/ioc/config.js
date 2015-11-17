var config = {
	queryLogFilter : {
		type : "com.alibaba.druid.filter.logging.Log4jFilter",
		fields : {
			// 避免 nutz 在判断 table is exsit 的时候，druid 输出错误 
			statementLogErrorEnabled : false
		}
	},

	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			driverClassName : "org.hsqldb.jdbcDriver",
			url : {
				java:"com.github.photobug.Helper.getPath('jdbc:hsqldb:file:${app.root}WEB-INF/data/db')"
			},
			username : "sa",
			password : "",
			filters : "stat",
			proxyFilters: {
				refer : 'queryLogFilter'
			},
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

	dataSource2 : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			driverClassName : "org.hsqldb.jdbcDriver",
			url : "jdbc:hsqldb:file:~/Downloads/photobug/data/db",
			username : "sa",
			password : "",
			testOnBorrow : false
		}
	},
	dao2 : {
		type : "org.nutz.dao.impl.NutDao",
		fields : {
			dataSource : {
				refer : 'dataSource2'
			}
		}
	},
	dataSource1 : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			driverClassName : "net.sourceforge.jtds.jdbc.Driver",
			url : "jdbc:jtds:sqlserver://172.18.112.113:1433/siteweaver",
			username : "sa",
			password : "p@ssw0rd",
			filters : "stat,log4j",
			testOnBorrow : false
		}
	},
	dao1 : {
		type : "org.nutz.dao.impl.NutDao",
		fields : {
			dataSource : {
				refer : 'dataSource1'
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