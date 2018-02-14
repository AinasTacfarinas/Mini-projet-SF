package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class MainClass {

	final static int N = 3;
	static int K = 0;
	static int k=0;


	static int DEBUG = 10;


	public static String toS(String s, int i, int j) {
		return ""+s+"_"+i+"_"+j;
	}

	public static String initState() {
		String res = "";
		int i=0;
		for(i=0;i<N;i++) {
			res+="("+toS("V",i,0)+" & "+ toS("!R",i,0)+") & ";
		}

		res+="("+toS("!V",i,0)+" & "+ toS("!R",i,0)+")";

		for(int j=i+1;j<2*N+1;j++) {
			res+=" & ("+toS("!V",j,0)+" & "+ toS("R",j,0)+")";
		}
		return res;
	}


	public static String finalState() {
		String res = "";
		int i=0;
		for(i=0;i<N;i++) {
			res+="("+toS("!V",i,k)+" & "+ toS("R",i,k)+") & ";
		}

		res+="("+toS("!V",i,k)+" & "+ toS("!R",i,k)+") ";

		for(int j=i;j<2*N+1;j++) {
			res+="("+toS("V",j,k)+" & "+ toS("!R",j,k)+") & ";
		}
		return res;
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

	public static String printTableInBooleanForm(char[]t) {
		String res= "";
		for(int i=0;i<2*N+1;i++) {
			res+=printStoneState(t,i,k);
			if(i!=2*N) {
				res+= " & ";	
			}
		}

		return res+="\n";
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

	public static String question3_4() {
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

	public static String question5() {
		String res = "!(\n";

		for(int i=0;i<=k;i++) {

			for(int j=i+1;j<=k;j++) {
				res+="(";
				for(int m=0;m<2*N+1;m++) {
					res+="("+toS("V",m,j)+" = "+toS("V",m,i)+") & ("+toS("R",m,j)+" = "+toS("R",m,i)+")";
					if(m<2*N) {
						res+=" & ";
					}
				}
				res+=")";
				if(i<k-1) {
					res+="\n|\n";
				}

			}	


		}
		res+="\n)";

		return res;
	}




	public static int execute() throws Exception {

		String booltocnb = "bool2cnf-20110304/a.out";
		String minisat = "minisat/build/release/bin/minisat";
		String file1 = "test.txt";
		String file2 = "test.cnf";
		String file3 = "test.out";

		String blast="";
		while(!blast.equals("SATISFIABLE")) {
			String res=initState();
			k=0;
			while(k<=K) {			

				boolean writen = false;
				res+="\n&(\n";


				for(int i=0;i<2*N+1;i++) {

					if(i>0) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}
						res+=move1FromRight(i);
					}


					if(i>1) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}
						res+=move2FromRight(i);
					}


					if(i<2*N) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}
						res+=move1FromLeft(i);
					}

					if(i<2*N-1) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}				
						res+=move2FromLeft(i);
					}

				}
				res+="\n)\n&\n"+question3_4()+"\n";
				k++;

			}
			res+="\n&\n"+question5()+"\n\n\n&"+finalState();
			File f;
			FileOutputStream fos;
			f = new File(file1);
			fos = new FileOutputStream(f);
			fos.write(res.getBytes());
			K++;

			Process p = new ProcessBuilder()
					.command(booltocnb)
					.redirectInput(new File(file1))
					.redirectOutput(new File(file2))
					.start();

			boolean finished = p.waitFor(5, TimeUnit.SECONDS);
			if (!finished) {
				p.destroy();
				finished=p.waitFor(5, TimeUnit.SECONDS); // wait for the process to terminate
			}

			Process p2 = new ProcessBuilder()
					.command(minisat)
					.redirectInput(new File(file2))
					.redirectOutput(new File(file3))
					.start();


			boolean finished2 = p2.waitFor(5, TimeUnit.SECONDS);
			if (!finished2) {
				p2.destroy();
				finished2=p.waitFor(5, TimeUnit.SECONDS); // wait for the process to terminate
			}

			BufferedReader bis = new BufferedReader(new FileReader(file3));


			String last="";
			last = bis.readLine();

			while((last = bis.readLine())!=null) {
				blast = last;
			}

			bis.close();
		}

		return K;
	}



	public static String formFromTable(char[] tab, boolean init) {
		String res = "";

		int step = init ? 0 : k;

		for(int i=0;i<tab.length;i++) {
			if(tab[i]=='V') {
				res+="(V_"+i+"_"+step+") & (!R_"+i+"_"+step+")";
			}else {
				if(tab[i]=='R') {
					res+="(!V_"+i+"_"+step+") & (R_"+i+"_"+step+")";
				}else {
					if(tab[i]=='X') {
						res+="(!V_"+i+"_"+step+") & (!R_"+i+"_"+step+")";
					}else {
						System.err.println("question7 - Il faut que les valeurs soient 'V', 'R' ou 'X'");
						System.exit(-1);
					}

				}
			}

			if(i<tab.length-1) {
				res+= " & ";
			}
		}

		return res;

	}

	public static int question7(char[] debut, char[] fin) throws Exception {
		
		if(debut.length != fin.length) {
			System.err.println("question7 - Les tableaux ont des tailles différentes");
			System.exit(-1);
		}

		String booltocnb = "bool2cnf-20110304/a.out";
		String minisat = "minisat/build/release/bin/minisat";
		String file1 = "test.txt";
		String file2 = "test.cnf";
		String file3 = "test.out";

		String blast="";
		int cpt = 0;
		int maj = (debut.length + 2)*debut.length;
		while(!blast.equals("SATISFIABLE") && cpt<maj) {
			String res=formFromTable(debut,true);
			k=0;
			while(k<=K) {			

				boolean writen = false;
				res+="\n&(\n";


				for(int i=0;i<debut.length;i++) {

					if(i>0) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}
						if(DEBUG<5) {
							System.out.println("1 from right "+i);
						}

						res+=move1FromRight(i);

					}


					if(i>1) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}
						if(DEBUG<5) {
							System.out.println("2 from right "+i);
						}
						res+=move2FromRight(i);
					}


					if(i<debut.length-1) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}
						if(DEBUG<5) {
							System.out.println("1 from left "+i);
						}
						res+=move1FromLeft(i);
					}

					if(i<debut.length-2) {
						if(writen) {
							res+="\n|\n";
						}else {
							writen= true;
						}				
						if(DEBUG<5) {
							System.out.println("2 from left "+i);
						}
						res+=move2FromLeft(i);
					}

				}
				res+="\n)\n&\n"+question3_4()+"\n";
				k++;

			}
			res+="\n&\n"+question5()+"\n\n\n&"+formFromTable(fin,false);;
			File f;
			FileOutputStream fos;
			f = new File(file1);
			fos = new FileOutputStream(f);
			fos.write(res.getBytes());
			K++;

			Process p = new ProcessBuilder()
					.command(booltocnb)
					.redirectInput(new File(file1))
					.redirectOutput(new File(file2))
					.start();

			boolean finished = p.waitFor(5, TimeUnit.SECONDS);
			if (!finished) {
				p.destroy();
				finished=p.waitFor(5, TimeUnit.SECONDS); // wait for the process to terminate
			}

			Process p2 = new ProcessBuilder()
					.command(minisat)
					.redirectInput(new File(file2))
					.redirectOutput(new File(file3))
					.start();


			boolean finished2 = p2.waitFor(5, TimeUnit.SECONDS);
			if (!finished2) {
				p2.destroy();
				finished2=p.waitFor(5, TimeUnit.SECONDS); // wait for the process to terminate
			}

			BufferedReader bis = new BufferedReader(new FileReader(file3));


			String last="";
			last = bis.readLine();

			while((last = bis.readLine())!=null) {
				blast = last;
			}

			bis.close();
			cpt++;
		}
		if(blast.equals("SATISFIABLE")) {
			return K;
		}else {
			return -1;
		}

	}



	public static void main(String[] args) throws Exception {
		//System.out.println(execute());
		char []debut = new char[] {'V','R','X','R','V'};
		char []fin = new char[] {'R','R','X','V','V'};
		System.out.println(question7(debut, fin));
	}


}
