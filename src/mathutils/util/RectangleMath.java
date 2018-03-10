package mathutils.util;

import java.awt.Rectangle;

public class RectangleMath {

	public static Rectangle findBounds(Rectangle... rs) {
		if (rs.length == 0) {
			return null;
		}
		double minx = rs[0].getMinX();
		double miny = rs[0].getMinY();
		double maxx = rs[0].getMaxX();
		double maxy = rs[0].getMaxY();
		for (Rectangle r : rs) {
			minx = r.getMinX() < minx ? r.getMinX() : minx;
			miny = r.getMinY() < miny ? r.getMinY() : miny;
			maxx = r.getMaxX() > maxx ? r.getMaxX() : maxx;
			maxy = r.getMaxY() > maxy ? r.getMaxY() : maxy;
		}
		return new Rectangle((int) minx, (int) miny, (int) (maxx - minx), (int) (maxy - miny));
	}

}
