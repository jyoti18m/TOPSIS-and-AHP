package sxd;
import java.util.*;
import java.io.*;
	public class testrun extends Ahp {
		/*double inputmatrix[][]=new double[5][4];/*row signifies various mutual fund companies and columns signify the criteria; one row extra for
		identifying forward(1) or backward(0) criterion*/
		Scanner sc= new Scanner(System.in);
		double[][] inputvalues (int nc, int na, String criteria[], String companyname[])
		{
			double in; 	double mat[][]=new double[na][nc];
			//BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			for (int i=0; i<na; i++)
			{
				System.out.println("Enter the criteria values for "+companyname[i]+":");
				for (int j=0; j<nc; j++)
				{
					System.out.println("Enter the value for its "+criteria[j]+":");
					in=sc.nextDouble();
					mat[i][j]=in;
				}
			}	
			return mat;
		}
		void calculatetopsis(double weight[], double inputmatrix[][], String companyname[], int criteriatype[], int na, int nc)
		{
			//super.calculateweight();
			double deno[]=new double[nc], squaredsum=0; 
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<na; j++)
				{
					squaredsum=squaredsum+Math.pow(inputmatrix[j][i],2);
				}
				deno[i]=Math.sqrt(squaredsum);
				squaredsum=0;
			}
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<na; j++)
				{
					inputmatrix[j][i]=(inputmatrix[j][i]/deno[i])*weight[i];//normalized weighted decision matrix
				}
			}
			double idealbest[]=new double [nc]; double idealworst[]=new double[nc]; double max=0,min=0;
			for (int i=0; i<nc; i++)
			{
				 max=inputmatrix[0][i]; min=inputmatrix[0][i];
					for (int j=0; j<na; j++)
					{
							if (inputmatrix[j][i]>max)
								max=inputmatrix[j][i];
							if (inputmatrix[j][i]<min)
								min=inputmatrix[j][i];
					}
					if (criteriatype[i]==0)//backward criteria
					{
						idealbest[i]=min;
						idealworst[i]=max;
					}
					if (criteriatype[i]==1)
					{
						idealbest[i]=max;
						idealworst[i]=min;
					}
				}
			/*Eucledian distance from the ideal best*/
			double eudisbest[]=new double[na]; double eudisworst[]=new double[na]; double sumeudisbest=0.0, sumeudisworst=0.0;
			for (int i=0; i<na; i++)
			{
				for (int j=0; j<nc; j++)
				{
					sumeudisbest+=Math.pow((inputmatrix[i][j]-idealbest[j]),2);
					sumeudisworst+=Math.pow((inputmatrix[i][j]-idealworst[j]),2);
				}
				eudisbest[i]=Math.sqrt(sumeudisbest);
				eudisworst[i]=Math.sqrt(sumeudisworst);
				sumeudisbest=0; sumeudisworst=0;
			
			}
			double permatrix[]=new double[na];
			for (int i=0; i<na; i++)
			{
				permatrix[i]=eudisworst[i]/(eudisbest[i]+eudisworst[i]);
			}
			double temp; int n=permatrix.length; String temp2="";
			for (int i = 0; i < n; i++)
			{
	            for (int j = 0; j < n-i-1; j++)
	            {
	                if (permatrix[j] < permatrix[j+1])
	                {
	                    temp = permatrix[j];
	                    permatrix[j] = permatrix[j+1];
	                    permatrix[j+1] = temp;
	                    temp2 = companyname[j];
	                    companyname[j] = companyname[j+1];
	                    companyname[j+1] = temp2;
	                }
	    }
		}
			System.out.println("==================================");
			for (int i=0; i<na; i++)
			{
				System.out.println("Rank "+(i+1)+"--> "+companyname[i]);
				System.out.println("Performance score: "+permatrix[i]);
				System.out.println("=========================================");
			}
			sc.close();
		}
		public static void main(String arg[])throws IOException
		{
			Scanner sc=new Scanner(System.in);
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			testrun obj=new testrun(); 
			Ahp obj2=new Ahp();
			//System.out.println("Here, we have selected 4 Criteria: Price, Storage space, Camera and Looks");
			System.out.println("Enter the number of criteria you want to assess (Upto 10 for now!)");
			int nocriteria=sc.nextInt();
			double conratio[]=new double[nocriteria]; String criteria[]=new String[nocriteria]; int criteriatype[]=new int[nocriteria];
			for (int i=0; i<nocriteria; i++)
				{
				System.out.println("Enter the CRITERIA number " +(i+1)+ " selected for the assessment");
				criteria[i]=br.readLine();
				System.out.println("Enter 0 if it is a BACKWARD CRITERIA and 1 if it is a FORWARD CRITERIA");
				criteriatype[i]=sc.nextInt();
				}
			System.out.println("Enter the number of Alternative products you want to score based on the criteria mentioned");
			int noalternatives=sc.nextInt();
			String alternatives[]=new String[noalternatives];
			System.out.println("Enter the name of the ALTERNATIVES for the assessment");
			for (int i=0; i<noalternatives; i++)
				alternatives[i]=br.readLine();
			double inputmatrix[][]=new double[noalternatives][nocriteria];
			inputmatrix=obj.inputvalues(nocriteria, noalternatives, criteria,alternatives);
			System.out.println("YOUR DECISION MATRIX LOOKS LIKE THIS--------------------> ");
			System.out.print("             \t   "+criteria[0]);
			for (int i=1; i<nocriteria; i++)		//printing the input matrix
			{
				System.out.print("     \t   "+criteria[i]);
			}
			System.out.println();
			for (int i=0; i<noalternatives; i++)
			{
				System.out.print(alternatives[i]+"    \t   ");
				for (int j=0; j<nocriteria; j++)
				{
					System.out.print(inputmatrix[i][j]+"    \t   ");
				}
				System.out.println();
			}									//printing the input matrix
			conratio=obj2.calculateweight(nocriteria, criteria);
			obj.calculatetopsis(conratio,inputmatrix, alternatives, criteriatype, noalternatives, nocriteria);
			sc.close();
		}	
	}


