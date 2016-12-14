import java.util.ArrayList;

public class EquationParser {

	public static void main(String args[]){
		String equy="x/30";
		EquationParser ep = new EquationParser();
		ep.computeEq(equy);
	}

	private void computeEq(String equy) {
		// TODO Auto-generated method stub
		ArrayList<Character> chars = new ArrayList<Character>();
		for (char c : equy.toCharArray()) {
		  chars.add(c);
		}
		//System.out.println("ArrayList "+chars);
		if(chars.get(0)=='-' || chars.get(0)=='+'){
			chars.add(0, '0');
		}
		int y=0;
		for(int x=-2;x<=3;x++){
			y=getYValue(chars,x);
		}
	}

	private int getYValue(ArrayList<Character> chars, int x) {
		// TODO Auto-generated method stub
		String leftNum="";
		String rightNum="";
		float value=0;
		int leftCut=0;int rightCut=0;
		//if arraylist has division
			//if division is present then divide and check if further calculation is 
		     //needed, if yes then rewrite the arraylist and call getYValue()
		    // the method
		int divindex=chars.indexOf('/');
		if(divindex!=-1){
			leftNum="";rightNum="";
			//left compute
			for(int left=divindex-1;left>=0;left--){
				leftCut=left-1;
				if(chars.get(left)=='/' || chars.get(left)=='*' || chars.get(left)=='+' || chars.get(left)=='-'){
					leftCut=left;
					break;
				}else{
					if(chars.get(left)=='x'){
						 leftNum=Integer.toString(x);
					  }else{
						  leftNum=Character.toString(chars.get(left))+leftNum;
					  }
				}
			}
			//right compute
			for(int right=divindex+1;right<chars.size();right++){
				rightCut=right+1;
				if(right!=divindex+1 && (chars.get(right)=='/' || chars.get(right)=='*' || chars.get(right)=='+' || chars.get(right)=='-')){
					rightCut=right;
					break;
				}else{
					if(chars.get(right)=='x'){
						 rightNum=Integer.toString(x);
					  }else{
						  rightNum=rightNum + Character.toString(chars.get(right));
					  }
				}
			}
			value=Float.valueOf(leftNum)/Float.valueOf(rightNum);
			System.out.println("Left Found "+leftNum);
			System.out.println("Right Found "+rightNum);
			System.out.println("Value calculated "+value);
			System.out.println("Equation "+chars);
			System.out.println("LeftCut "+leftCut);
			System.out.println("rightCut "+rightCut);
			chars.subList(leftCut+1, rightCut).clear();
			
			char temp[]=Float.toString(value).toCharArray();
			
			for(int p=temp.length-1;p>=0;p--){
				//System.out.println("in characters "+temp[p]);	
				chars.add(leftCut+1, temp[p]);	
			}
			System.out.println("New Equation "+chars);
			//check whether it is a number
			if(chars.indexOf('/')==-1 && chars.indexOf('*')==-1 && (chars.indexOf('-')==0 || chars.indexOf('-')==-1) && (chars.indexOf('+')==0 || chars.indexOf('+')==-1)){
				System.out.println("It is a number.");
				//StringBuilder sb = new StringBuilder();
				
			}
		}
		//if array has no division but has multiplicationj
		
		//if array has no division or multiplication but has addition
		
		//if array has no division of multiplication or addition but has substraction
		return 0;
	}
}
