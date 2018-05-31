package com.sunday.cal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

public class CalServer {
	
	private StringBuffer opStrBuf;
	private String[] opList;
	private int count;
	
	CalServer(){
		this.opList =  new String [10];
		this.count = 0;
		this.opStrBuf = new StringBuffer ("");
	}
	
	
	//execute the corresponding step according to the input of the mouse
	public String processMouse (String cmd) {
		
		if (cmd.equalsIgnoreCase("Backspace")) {						
			if (opList[count-1].equals("square")) {
				opStrBuf.delete(opStrBuf.length()-6,opStrBuf.length());
			}else if (opList[count-1].equals("sqrt")){
				opStrBuf.delete(opStrBuf.length()-4,opStrBuf.length());
			}else {
				opStrBuf.delete(opStrBuf.length()-1,opStrBuf.length());
			}
			count--;
			opList[count]=null;
			return opStrBuf.toString();
		}
		
		if (cmd.equalsIgnoreCase("delete")) {
			clearCurrentContent();
			return opStrBuf.toString();
		}
		
		if (count ==(this.opList.length - 1)) {
			String[] newOpList = new String [this.opList.length*2];
			newOpList =Arrays.copyOf(opList, this.opList.length*2);
			opList = newOpList;
		}
		
		opList[count]=cmd;
		opStrBuf.append(cmd);
		count ++;
		
		if (cmd.equals("=")) {
			return cal ();
		}
			
		return opStrBuf.toString();
		
	}
	
	
	//add the new content with the keyboard inputs
	public String proceeKey (int code) {		
		char temp = (char) code;
		if (count ==(this.opList.length - 1)) {
			String[] newOpList = new String [this.opList.length*2];
			newOpList =Arrays.copyOf(opList, this.opList.length*2);
			opList = newOpList;
		}
		opList[count]= ""+temp;
		opStrBuf.append(temp);
		count ++;
		return opStrBuf.toString();
	}
	

	//sbCal is the expression which will be changed according the operation until the final result
	//sbOrg is to keep the return expression
	private String cal () {
		
		StringBuffer sbOrg = new StringBuffer();
		StringBuffer sbCal = new StringBuffer();
		
		for (int i = 0; i < count-1; i ++) {
			sbOrg.append(opList[i]);
			sbCal.append(opList[i]);
		}
		
		System.out.println("-------------------");
		System.out.println(sbCal.toString());
		
		try {
			sbCal = calSquare(sbCal);		
			sbCal = calSquareRoot(sbCal);
			sbCal = calBracketCon(sbCal);
			sbCal = calBiOpe(sbCal,"*");
			sbCal = calBiOpe(sbCal,"/");
			sbCal = calBiOpe(sbCal,"+");
			sbCal = calBiOpe(sbCal,"-");
		} catch (Exception e) {
			clearCurrentContent();
			return "incorrect expression";
		}
		
		sbOrg.append('=').append(sbCal);
		clearCurrentContent();
		return sbOrg.toString();
	}
	
	private void clearCurrentContent () {
		opList = new String[10];
		opStrBuf.delete(0, opStrBuf.length());
		count = 0;
	}
	
	private StringBuffer calSquare (StringBuffer sbCal) throws Exception {
		int index = sbCal.indexOf("square");
		while ( index != -1) {
			int pos = index-1;
			Double value ; 
			System.out.println("pos"+pos);
			if (!Character.isDigit(sbCal.charAt(pos))) throw new IncorrectFormatException();
			while ((pos >= 0 )&&(Character.isDigit(sbCal.charAt(pos)) || (sbCal.charAt(pos))=='.')) {
				pos --;
			}
			if (pos != 0 ) {
				value = Double.parseDouble(sbCal.substring(pos+1, index));
			}else {
				value = Double.parseDouble(sbCal.substring(0, index));
			}
			System.out.println(value);
			Double res= value*value;
			sbCal.replace(pos+1, index+5,res.toString());
			System.out.println(sbCal);
			index = sbCal.indexOf("square");
		}
		return sbCal;		
	}
	
	private StringBuffer calSquareRoot (StringBuffer sbCal) throws Exception{
			int index = sbCal.lastIndexOf("sqrt");
			while ( index != -1) {
				int pos = index-1;
				Double value ; 
				System.out.println("pos"+pos);
				if (pos!=-1) {
					char temp = sbCal.charAt(pos);
					if (Character.isDigit(temp) || temp == ')' || temp =='e')
						throw new IncorrectFormatException();
				} 
				pos = index+4;
				System.out.println(sbCal.charAt(pos));
				if (!Character.isDigit(sbCal.charAt(pos))) throw new IncorrectFormatException();
				
				while ( (pos < sbCal.length())&&(Character.isDigit(sbCal.charAt(pos)) || (sbCal.charAt(pos))=='.' || (sbCal.charAt(pos))=='e')) {
					pos ++;
				}
				try {
					if (sbCal.charAt(pos-1) == 'e')
						value = Double.parseDouble(sbCal.substring(index + 4, pos-1));
					else
						value = Double.parseDouble(sbCal.substring(index + 4, pos));
				}catch (Exception e) {
					throw new IncorrectFormatException();
				}					
				System.out.println(value);
				Double res= Math.sqrt(value);
				sbCal.replace(index, pos,res.toString());
				System.out.println(sbCal);
				index = sbCal.indexOf("sqrt");
			}
			return sbCal;	
	}
	
	private StringBuffer calBracketCon (StringBuffer sbCal) throws Exception {
		
		int index1 = sbCal.lastIndexOf("(");
		while (index1 != -1) {
			int index2 = sbCal.lastIndexOf(")");
			if (index2 == -1) throw new IncorrectFormatException();
			String subStr = sbCal.substring(index1+1, index2);
			System.out.println("substr: "+subStr);
			StringBuffer subPart = new StringBuffer(subStr);
			subPart = calBiOpe(subPart,"*");
			subPart = calBiOpe(subPart,"/");
			subPart = calBiOpe(subPart,"+");
			subPart = calBiOpe(subPart,"-");
			System.out.println("the content after processed:" + subPart.toString());
			sbCal.replace(index1,index2+1,subPart.toString());
			System.out.println("after the process: "+sbCal.toString());
			index1 = sbCal.lastIndexOf("(");			
		}
		return sbCal;
	}
			
	private StringBuffer calBiOpe (StringBuffer sbCal, String mode) throws Exception{
		int index = sbCal.indexOf(mode);
		while (index != -1) {
			int pos1 = index-1;
			int pos2 = index+1;
			while ( (pos1 >=0)&&(Character.isDigit(sbCal.charAt(pos1)) || (sbCal.charAt(pos1))=='.' || (sbCal.charAt(pos1))=='e')){
				pos1 --;
			}
			while ( (pos2 < sbCal.length())&&(Character.isDigit(sbCal.charAt(pos2)) || (sbCal.charAt(pos2))=='.' || (sbCal.charAt(pos2))=='e')) {
				pos2 ++;
			}
			Double value1,value2;
			try {
				if (sbCal.charAt(index-1) == 'e') {
					value1 = Double.parseDouble(sbCal.substring(pos1+1, index-1));
				}
				else {
					value1 = Double.parseDouble(sbCal.substring(pos1+1, index));
				}
				if (sbCal.charAt(pos2-1) == 'e') {
					value2 = Double.parseDouble(sbCal.substring(index+1, pos2-1));
				}
				else {
					value2 = Double.parseDouble(sbCal.substring(index+1, pos2));
				}			
			}catch (Exception e) {
				throw new IncorrectFormatException();
			}
			BigDecimal v1 = new BigDecimal (value1);
			BigDecimal v2 = new BigDecimal (value2);
			
			if (mode.equalsIgnoreCase("-")) {
				v1 = v1.subtract(v2, MathContext.DECIMAL32);}
			else if (mode.equalsIgnoreCase("+")) {
				v1 = v1.add(v2, MathContext.DECIMAL32);}
			else if (mode.equalsIgnoreCase("*")) {
				v1 = v1.multiply(v2, MathContext.DECIMAL32);}
			else if (mode.equalsIgnoreCase("/")) {
				v1 = v1.divide(v2, MathContext.DECIMAL32);}
			
			sbCal.replace(pos1+1, pos2,v1.toString());
			System.out.println(sbCal.toString());
			index = sbCal.indexOf(mode);
		}
		return sbCal;				
	}

}
