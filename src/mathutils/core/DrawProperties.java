package mathutils.core;

import java.awt.Font;

public class DrawProperties {

	private Font defaultFont;
	private Font smallFont;
	private int expressionSpacing;

	public Font getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}

	public Font getSmallFont() {
		return smallFont;
	}

	public void setSmallFont(Font smallFont) {
		this.smallFont = smallFont;
	}

	public int getExpressionSpacing() {
		return expressionSpacing;
	}

	public void setExpressionSpacing(int expressionSpacing) {
		this.expressionSpacing = expressionSpacing;
	}

}
