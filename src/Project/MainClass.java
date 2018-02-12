package Project;

public class MainClass {

	final static int N = 10;
	static char []initTable;
	static char []endTable;
	static int k;
	
	static int DEBUG = 10;

	public static void initGame() {
		initTable = new char[2*N+1]	;
		int i;	
		for(i=0;i<N;i++) {	
			initTable[i]='V';
		}
		initTable[i]='N';
		for(int j=i+1;j<2*N+1;j++) {
			initTable[j]='R';
		}

	}

	public static void endGame() {
		endTable = new char[2*N+1];
		int i;
		for(i=0;i<N;i++) {
			endTable[i]='R';
		}
		endTable[i]='N';
		for(int j=i+1;j<2*N+1;j++) {
			endTable[j]='V';
		}

	}




	public static String printStoneState(char[]t,int stoneindex, int time) {
		String res = "";
		switch(t[stoneindex]) {
		case 'V' : 
			res = "(V_"+stoneindex+"_"+time+" & !R_"+stoneindex+"_"+time+")";
			break;
		case 'R' :
			res = "(!V_"+stoneindex+"_"+time+" & R_"+stoneindex+"_"+time+")";
			break;
		case 'N' :
			res = "(!V_"+stoneindex+"_"+time+" & !R_"+stoneindex+"_"+time+")";
			break;
		default : 
			System.err.print("ERROR printStoneState : "+t[stoneindex]);
			System.exit(-1);
		}

		return res;
	}

	public static void printTableInBooleanForm(char[]t) {
		for(int i=0;i<2*N+1;i++) {
			System.out.print(printStoneState(t,i,k));
			if(i!=2*N) {
				System.out.print(" & ");	
			}
		}
		System.out.println();
	}

	public static String toS(String s, int i, int j) {
		return ""+s+"_"+i+"_"+j;
	}

	public static String move1FromLeft(int j) {

		String res = "(\n";

		for(int i=0;i<j;i++) {
			res+="("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";

			res+=" & ";
			
		}
		
		res +=  "\n"+
				"("+toS("V",j+1,k+1)+" & (("+toS("V",j,k)+" & "+toS("!R",j,k)+") & ("+toS("!V",j+1,k)+" & "+toS("!R",j+1,k)+"))) & \n" + 
				"("+toS("R",j+1,k+1)+" = "+toS("!V",j+1,k+1)+") & \n" + 
				"("+toS("R",j,k+1)+" = "+toS("R",j+1,k)+") & ("+toS("V",j,k+1)+" = "+toS("V",j+1,k)+")\n";

		for(int i=j+2;i<2*N+1;i++) {
			res+="& ("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";
		}


		return res+"\n)";
	}


	public static String move1FromRight(int j) {

		String res = "(\n";

		for(int i=2*N;i>j;i--) {
			res+="("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";

			res+=" & ";
			
		}

		res += "\n"+
				"("+toS("R",j-1,k+1)+" & (("+toS("R",j,k)+" & "+toS("!V",j,k)+") & ("+toS("!R",j-1,k)+" & "+toS("!V",j-1,k)+"))) & \n" + 
				"("+toS("V",j-1,k+1)+" = "+toS("!R",j-1,k+1)+") & \n" + 
				"("+toS("V",j,k+1)+" = "+toS("V",j-1,k)+") & ("+toS("R",j,k+1)+" = "+toS("R",j-1,k)+")\n";

		for(int i=j-2;i>=0;i--) {
			res+="& ("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";
		}


		return res+"\n)";
	}


	public static String move2FromLeft(int j) {

		String res = "(\n";

		for(int i=0;i<j;i++) {
			res+="("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";

			res+=" & ";
			
		}

		res +=  "\n"+
				"("+toS("V",j+2,k+1)+" & (("+toS("V",j,k)+" & "+toS("!R",j,k)+") & ("+toS("!V",j+2,k)+" & "+toS("!R",j+2,k)+"))) & \n" + 
				"("+toS("R",j+2,k+1)+" = "+toS("!V",j+2,k+1)+") & \n" + 
				"("+toS("R",j,k+1)+" = "+toS("R",j+2,k)+") & ("+toS("V",j,k+1)+" = "+toS("V",j+2,k)+") &\n"+
				"("+toS("V",j,k)+" = "+toS("!V",j+1,k)+") & ("+toS("R",j,k)+" = "+toS("!R",j+1,k)+")\n";
		
		
		for(int i=j+3;i<2*N+1;i++) {
			res+="& ("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";
		}

		res+="& ("+toS("V",j+1,k+1)+" = "+toS("V",j+1,k)+") & ("+toS("R",j+1,k+1)+" = "+toS("R",j+1,k)+")";


		return res+"\n)";
	}

	public static String move2FromRight(int j) {

		String res = "(\n";

		for(int i=2*N;i>j;i--) {
			res+="("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";
			res+=" & ";
			
		}

		res += "\n"+
				"("+toS("R",j-2,k+1)+" & (("+toS("R",j,k)+" & "+toS("!V",j,k)+") & ("+toS("!R",j-2,k)+" & "+toS("!V",j-2,k)+"))) & \n" + 
				"("+toS("V",j-2,k+1)+" = "+toS("!R",j-2,k+1)+") & \n" + 
				"("+toS("V",j,k+1)+" = "+toS("V",j-2,k)+") & ("+toS("R",j,k+1)+" = "+toS("R",j-2,k)+") &\n"+
				"("+toS("R",j,k)+" = "+toS("!R",j-1,k)+") & ("+toS("V",j,k)+" = "+toS("!V",j-1,k)+")\n";

		for(int i=j-3;i>=0;i--) {
			res+="& ("+toS("V",i,k+1)+" = "+toS("V",i,k)+") & ("+toS("R",i,k+1)+" = "+toS("R",i,k)+")";
		}

		res+="& ("+toS("V",j-1,k+1)+" = "+toS("V",j-1,k)+") & ("+toS("R",j-1,k+1)+" = "+toS("R",j-1,k)+")";


		return res+"\n)";
	}
	
	public static String mutexFrog() {
		String res = "!(";
		
		for(int i=0;i<2*N+1;i++) {
			res+="("+toS("V",i,k)+" & "+toS("R",i,k)+")";
			
			if(i<2*N) {
				res+=" | ";	
			}
			
		}
		res+=")";
		
		return res;
	}


	public static void execute() {
		printTableInBooleanForm(initTable);
		
		while(k<120) {
			boolean writen = false;
			System.out.println("\n&(\n");
			for(int i=0;i<2*N+1;i++) {
				
				

				if(i>0) {
					if(writen) {
						System.out.println("\n|\n");
					}else {
						writen= true;
					}
					if(DEBUG<5) {
						System.out.println("1 from right "+i);
					}
					
					System.out.println(move1FromRight(i));
					
				}
				

				if(i>1) {
					if(writen) {
						System.out.println("\n|\n");
					}else {
						writen= true;
					}
					if(DEBUG<5) {
						System.out.println("2 from right "+i);
					}
					System.out.println(move2FromRight(i));
				}
				

				if(i<2*N) {
					if(writen) {
						System.out.println("\n|\n ");
					}else {
						writen= true;
					}
					if(DEBUG<5) {
						System.out.println("1 from left "+i);
					}
					System.out.println(move1FromLeft(i));
				}
				
				if(i<2*N-1) {
					if(writen) {
						System.out.println("\n|\n");
					}else {
						writen= true;
					}				
					if(DEBUG<5) {
						System.out.println("2 from left "+i);
					}
					System.out.println(move2FromLeft(i));
				}

			}
			
			
			System.out.println("\n)\n");
			System.out.println("&\n");
			System.out.println(mutexFrog()+"\n");
			k++;
		}
		System.out.println("\n&");
		printTableInBooleanForm(endTable);
		
	}




	public static void main(String[] args) {
		k=0;
		initGame();
		endGame();
		execute();
	}


}
