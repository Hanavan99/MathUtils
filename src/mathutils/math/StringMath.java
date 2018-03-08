package mathutils.math;

public class StringMath {

    public static String add(String a, String b) {
	String[] numA = split(a);
	String[] numB = split(b);
	String[] result = new String[] { "", "" };
	int carry = 0;
	if (numA.length > 1 && numB.length > 1) {
	    for (int i = Math.min(numA[1].length(), numB[1].length()) - 1; i >= 0; i--) {
		int subsum = Integer.valueOf(numA[1].charAt(i)) + Integer.valueOf(numB[1].charAt(i)) + carry;
		if (subsum > 9) {
		    carry = subsum / 10;
		    subsum -= 10;
		}

		result[1] += subsum;
	    }
	}
	for (int i = Math.min(numA[0].length(), numB[0].length()) - 1; i >= 0; i--) {
	    int subsum = Integer.valueOf(numA[0].charAt(i)) + Integer.valueOf(numB[0].charAt(i)) + carry;
	    if (subsum > 9) {
		carry = subsum / 10;
		subsum -= 10;
	    }

	    result[0] += String.valueOf(subsum);
	}

	return result[0] + "." + result[1];
    }

    public static String[] split(String a) {
	return a.split("\\.");
    }

}
