
public class BracketParsing {
	StringBuilder originalEqu=null;
	StringBuilder processEqu=null;
	StringBuilder tempEqu=null;
	public static void main(String args[]){
		BracketParsing bp=new BracketParsing();
		bp.computeEquation("10*(x-9)");
		
	}

	private void computeEquation(String string) {
		// TODO Auto-generated method stub
		originalEqu=new StringBuilder(string);
		float y=0;
		for(int x=-2;x<2;x++){
			processEqu=new StringBuilder(originalEqu);
			y=getYValue(processEqu,x);
		}
	}

	private float getYValue(StringBuilder processEqu,int x) {
		// TODO Auto-generated method stub
		//get last index of (
		float yvalue=0;
		int index =processEqu.lastIndexOf("(");
		if(processEqu.lastIndexOf("(")!=-1){
			yvalue=processEquation(index,x);
		}
		return 0;
	}

	private float processEquation(int index,int x) {
		// TODO Auto-generated method stub
		String rightEqu="";
		int leftCut =index;
		int rightCut=0;
		for(int right=index+1;right<processEqu.length();right++){
			if(processEqu.charAt(right)==')'){
				rightCut=right+1;
				break;
			}
			rightEqu=rightEqu + processEqu.charAt(right);
		}
		System.out.println("Core Bracket to be replaced"+rightEqu);
		
		//solve the core bracket and then call getYValue
		float yvalue= getYValue(new StringBuilder(rightEqu),x);
		String bIns=Float.toString(yvalue);
		processEqu.delete(leftCut, rightCut);
		processEqu.insert(leftCut, bIns);
		return getYValue(processEqu, x);
		/*
		if(rightEqu.equals("x") || rightEqu.equals("-x") || rightEqu.equals("+x")){
			if(rightEqu.equals("x") || rightEqu.equals("+x")){
				return x;
			}else {
					return -x;
			}
		}
		boolean isn=isNumber(rightEqu);
		if(isn==true){
			return Float.valueOf(rightEqu);
		}
		System.out.println("Is a Number ? "+isn);
		System.out.println("Core Bracket "+rightEqu);
*/		
	
	}

	private boolean isNumber(String rightEqu) {
		char[] num=rightEqu.toCharArray();
		boolean isn=true;
		int begin=0;
		if(num[0]=='-' || num[0]=='+'){
			begin=1;
		}
		for(int i=begin;i<num.length;i++){
			if(num[i]<'0' || num[i]>'9'){
				isn=false;
				break;
			}
		}
		return isn;
		// TODO Auto-generated method stub
		
	}

	
}
