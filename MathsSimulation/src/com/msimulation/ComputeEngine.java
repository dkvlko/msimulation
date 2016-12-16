package com.msimulation;

public class ComputeEngine {

	StringBuilder equation = new StringBuilder("(x)^");

	public static void main(String args[]) {
		ComputeEngine epn = new ComputeEngine();
		epn.computeEquation();
	}

	private void computeEquation() {
		// TODO Auto-generated method stub
		float yValue = 0;
		// prepare equation for sin , cos , tan ,log
		equation = new StringBuilder(equation.toString().replaceAll("sin", "s"));
		equation = new StringBuilder(equation.toString().replaceAll("cos", "c"));
		equation = new StringBuilder(equation.toString().replaceAll("tan", "t"));
		equation = new StringBuilder(equation.toString().replaceAll("log", "l"));
		equation = new StringBuilder(equation.toString().replaceAll("exp", "e"));
		// System.out.println("new equation " + equation);
		// System.exit(0);
		for (int x = -300; x <= 300; x++) {
			// System.out.println("X Value " + x);
			StringBuilder processEqu = new StringBuilder(equation);
			processEqu = new StringBuilder(processEqu.toString().replaceAll("x", Integer.toString(x)));
			processEqu = new StringBuilder(processEqu.toString().replaceAll("--", "+"));
			yValue = getYValue(processEqu, x);
			// System.out.println("Y Value " + yValue);
		}
	}

	public float getYValue(StringBuilder processEqu, int x) {
		int index = 0;
		float y = 0;

		if (processEqu.length() != 0) {
			index = processEqu.indexOf(")^");
			if (index != -1) {
				y = performNthRoot(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.lastIndexOf("(");
			if (index != -1) {
				y = processBracket(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("s");
			if (index != -1) {
				y = performSin(processEqu, x, index);

			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("c");
			if (index != -1) {
				y = performCos(processEqu, x, index);

			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("t");
			if (index != -1) {
				y = performTan(processEqu, x, index);

			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("l");
			if (index != -1) {
				y = performLog(processEqu, x, index);

			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("e");
			if (index != -1) {
				y = performExp(processEqu, x, index);

			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("^");
			if (index != -1) {
				y = performNthRoot(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("/");
			if (index != -1) {
				y = performDivision(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("*");
			if (index != -1) {
				y = performMultiplication(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("+", 1);
			if (index != -1) {
				y = performAddition(processEqu, x, index);
			}

		}
		if (processEqu.length() != 0) {
			index = processEqu.indexOf("-", 1);
			if (index != -1) {
				y = performSubstraction(processEqu, x, index);
			}

		}

		if (processEqu.length() != 0) {
			y = performNoOperator(processEqu, x);
		}

		return y;
	}

	private float performNthRoot(StringBuilder processEqu, int x, int index) {
		index = index + 1;
		int leftCut = 0;
		int rightCut = 0;
		String leftEqu = "";
		String rightEqu = "";
		// System.out.println("Original equation " + processEqu);
		// move to the left
		for (int left = index - 1; left >= 0; left--) {
			if (processEqu.charAt(left) == '(') {
				leftCut = left;
				leftEqu = processEqu.charAt(left) + leftEqu;
				break;
			} else {
				leftEqu = processEqu.charAt(left) + leftEqu;
			}
		}
		// System.out.println("The left equ is " + leftEqu);
		float lvalue = getYValue(new StringBuilder(leftEqu), x);
		// right value
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '-' || processEqu.charAt(right) == '+'
					|| processEqu.charAt(right) == '*' || processEqu.charAt(right) == '/'
					|| processEqu.charAt(right) == ')')) {
				rightCut = right;
				break;
			} else {
				rightEqu = processEqu.charAt(right) + rightEqu;
			}
		}
		// System.out.println("right Equ " + rightEqu);// as of now we are
		// implementing only pow of
		float rvalue = Float.valueOf(rightEqu); // constants
		float yvalue = (float) Math.pow((double) lvalue, (double) rvalue);
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}
		// System.exit(0);
		processEqu.delete(leftCut, rightCut);
		// System.out.println("the left equ deleted from original " +
		// processEqu);
		String insY = null;
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);
		// System.out.println("For further processing " + processEqu);
		// System.exit(0);
		return getYValue(processEqu, x);
	}

	private float performExp(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-'
					|| processEqu.charAt(right) == '^')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}
		}

		float yvalue = (float) Math.exp((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}

		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}

		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}

		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);

		return getYValue(processEqu, x);

	}

	private float performLog(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-'
					|| processEqu.charAt(right) == '^')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}
		}

		float yvalue = (float) Math.log((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}

		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}

		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}

		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);

		return getYValue(processEqu, x);

	}

	private float performTan(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-'
					|| processEqu.charAt(right) == '^')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}
		}

		float yvalue = (float) Math.tan((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}

		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}

		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}

		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);

		return getYValue(processEqu, x);

	}

	private float performCos(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-'
					|| processEqu.charAt(right) == '^')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}
		}

		float yvalue = (float) Math.cos((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}

		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}

		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}

		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);

		return getYValue(processEqu, x);

	}

	private float performSin(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		int leftCut = index;
		int rightCut = 0;
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;
			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '+' || processEqu.charAt(right) == '-'
					|| processEqu.charAt(right) == '^')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}
		}

		float yvalue = (float) Math.sin((double) Float.valueOf(rightNum));
		if (yvalue < 0.001 && yvalue > 0) {
			yvalue = 0;
		}
		if (yvalue > -0.001 && yvalue < 0) {
			yvalue = 0;
		}

		if (index != 0 && (processEqu.charAt(index - 1) == '-' || processEqu.charAt(index - 1) == '+')) {
			leftCut = index - 1;
			if (processEqu.charAt(index - 1) == '-') {
				yvalue = -yvalue;
			}
		}

		if (yvalue == 0) { // to handle -0
			yvalue = yvalue + 0;
		}

		String insY = "";
		processEqu.delete(leftCut, rightCut);
		if (yvalue >= 0) {
			insY = "+" + Float.toString(yvalue);
		} else {
			insY = Float.toString(yvalue);
		}
		processEqu.insert(leftCut, insY);

		return getYValue(processEqu, x);

	}

	private float performDivision(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		float yvalue = 0;

		// right number
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if no operator is found on the right then
									// increment rightCut to include all
									// characters

			if (right != index + 1 && (processEqu.charAt(right) == '/' || processEqu.charAt(right) == '*'
					|| processEqu.charAt(right) == '-' || processEqu.charAt(right) == '+')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}

		}
		float rightValue = Float.valueOf(rightNum);
		// left number
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;

			if (left != index - 1 && (processEqu.charAt(left) == '/' || processEqu.charAt(left) == '*'
					|| processEqu.charAt(left) == '+' || processEqu.charAt(left) == '-')) {
				leftNum = processEqu.charAt(left) + leftNum;
				break;
			} else {
				leftNum = processEqu.charAt(left) + leftNum;
			}
		}
		float leftValue = Float.valueOf(leftNum);
		/*
		 * if (rightValue == 0) { rightValue = 0.0001f; }
		 */
		yvalue = leftValue / rightValue;

		if (yvalue == 0) {
			yvalue = yvalue + 0;
		}
		if (leftCut == 0 && rightCut == processEqu.length()) {
			processEqu.setLength(0);
			return yvalue;
		} else {
			processEqu.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			processEqu.insert(leftCut, insY);
			return getYValue(processEqu, x);
		}
	}

	private float performMultiplication(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		float yvalue = 0;

		// right number
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if no operator is found on the right then
									// increment rightCut to include all
									// characters
			if (right != index + 1 && (processEqu.charAt(right) == '*' || processEqu.charAt(right) == '-'
					|| processEqu.charAt(right) == '+')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}

		}

		float rightValue = Float.valueOf(rightNum);
		// left number
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;

			if (left != index - 1 && (processEqu.charAt(left) == '*' || processEqu.charAt(left) == '+'
					|| processEqu.charAt(left) == '-')) {
				leftNum = processEqu.charAt(left) + leftNum;
				break;
			} else {
				leftNum = processEqu.charAt(left) + leftNum;
			}
		}

		float leftValue = Float.valueOf(leftNum);
		yvalue = leftValue * rightValue;

		if (yvalue == 0) {
			yvalue = yvalue + 0;
		}
		if (leftCut == 0 && rightCut == processEqu.length()) {
			processEqu.setLength(0);
			return yvalue;
		} else {
			processEqu.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			processEqu.insert(leftCut, insY);
			return getYValue(processEqu, x);
		}
	}

	private float performAddition(StringBuilder processEqu, int x, int index) {

		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		float yvalue = 0;

		// right number
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if no operator is found on the right then
									// increment rightCut to include all
									// characters
			if (right != index + 1 && (processEqu.charAt(right) == '-' || processEqu.charAt(right) == '+')) {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}

		}

		float rightValue = Float.valueOf(rightNum);

		// left number
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;

			if (left != index - 1 && (processEqu.charAt(left) == '+' || processEqu.charAt(left) == '-')) {
				leftNum = processEqu.charAt(left) + leftNum;
				break;
			} else {
				leftNum = processEqu.charAt(left) + leftNum;
			}
		}

		if (leftNum.equals("-")) {
			yvalue = -rightValue;
			// yvalue = -yvalue;

		} else {
			float leftValue = Float.valueOf(leftNum);
			yvalue = leftValue + rightValue;
		}
		if (yvalue == 0) {
			yvalue = yvalue + 0;
		}
		if (leftCut == 0 && rightCut == processEqu.length()) {
			processEqu.setLength(0);
			return yvalue;
		} else {
			processEqu.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			processEqu.insert(leftCut, insY);
			return getYValue(processEqu, x);
		}

	}

	private float performNoOperator(StringBuilder processEqu, int x) {
		// Only sign operator is found in this method
		float yvalue = Float.valueOf(processEqu.toString());
		if (yvalue == 0) {
			yvalue = yvalue + 0;
		}
		// System.out.println("No operator yvalue " + yvalue);
		return yvalue;
	}

	private float performSubstraction(StringBuilder processEqu, int x, int index) {
		String rightNum = "";
		String leftNum = "";
		int leftCut = 0;
		int rightCut = 0;
		float yvalue = 0;

		// right number
		for (int right = index + 1; right < processEqu.length(); right++) {
			rightCut = right + 1;// if no operator is found on the right then
									// increment rightCut to include all
									// characters
			if (right != index + 1 && processEqu.charAt(right) == '-') {
				rightCut = right;
				break;
			} else {
				rightNum = rightNum + processEqu.charAt(right);
			}

		}

		float rightValue = Float.valueOf(rightNum);
		// left number
		for (int left = index - 1; left >= 0; left--) {
			leftCut = left;

			if (left != index - 1 && processEqu.charAt(left) == '-') {
				leftNum = processEqu.charAt(left) + leftNum;
				break;
			} else {
				leftNum = processEqu.charAt(left) + leftNum;
			}
		}
		// System.out.println("LeftNum " + leftNum);
		if (leftNum.equals("-")) {
			yvalue = -rightValue;
			yvalue = -yvalue;

		} else {
			float leftValue = Float.valueOf(leftNum);
			yvalue = leftValue - rightValue;
		}
		if (yvalue == 0) {
			yvalue = yvalue + 0;
		}
		if (leftCut == 0 && rightCut == processEqu.length()) {
			processEqu.setLength(0);
			return yvalue;
		} else {
			processEqu.delete(leftCut, rightCut);
			String insY = null;
			if (yvalue >= 0) {
				insY = "+" + Float.toString(yvalue);
			} else {
				insY = Float.toString(yvalue);
			}
			processEqu.insert(leftCut, insY);
			return getYValue(processEqu, x);
		}

	}

	private float processBracket(StringBuilder processEqu, int x, int index) {
		String rightEqu = "";
		int leftCut = index;
		int rightCut = 0;
		int sign = 1;
		// search for the first occurrence of ')' on the right of '('
		for (int right = index + 1; right < processEqu.length(); right++) {
			if (processEqu.charAt(right) == ')') {
				rightCut = right + 1; // incremented to include the removal of
										// ')'
				break;
			}
			rightEqu = rightEqu + processEqu.charAt(right); // build the
															// equation within
															// the brackets
		}

		float yvalue = getYValue(new StringBuilder(rightEqu), x);

		// handling sign if it is present outside the '(' operator
		if (index > 0 && processEqu.charAt(index - 1) == '-') {
			leftCut = leftCut - 1; // leftCut decremented to include removal of
									// the '-'
			sign = -1;
		}
		if (index > 0 && processEqu.charAt(index - 1) == '+') {
			leftCut = leftCut - 1;
			sign = 1;
		}

		float rvalue = sign * yvalue;
		if (rvalue == 0) {
			rvalue = rvalue + 0;
		}

		String bIns = null;
		if (rvalue >= 0) {
			bIns = "+" + Float.toString(rvalue);
		} else {
			bIns = Float.toString(rvalue); // no need to add sign string for
											// negative values
		}
		processEqu.delete(leftCut, rightCut);
		processEqu.insert(leftCut, bIns);
		return getYValue(processEqu, x);
	}
}
