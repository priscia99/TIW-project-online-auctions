package it.polimi.tiw.project.utils;

import java.time.ZoneId;

public class TimezoneHelper {
	public static ZoneId getCustomTimezoneID() {
		return ZoneId.of("Etc/GMT+0");
	}
}
