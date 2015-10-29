package com.github.photobug;

import org.nutz.mvc.Mvcs;

public class Helper {
	public static String getDbPath(String path) {
		return path.replace("${app.root}", Mvcs.getServletContext().getRealPath("/"));
	}
}
