package sxd;
import java.util.*;
public class Ahp{
	Scanner s=new Scanner(System.in);
	double[] calculateweight(final int nc, String criteria[])
	{
		double consistencyratio; double meanweight[]=new double[nc]; final double consistencytab[]= {0.00,0.00,0.58,0.90,1.12,1.24,1.32,1.41,1.45,1.49};
		Scanner s=new Scanner(System.in); 
		System.out.println("List of table values to determine the importance of criteria:\n 1-->Equal Importance\n 2-3-->Moderate Importance\n 4-5-->Strong importance"
				+ "\n 6-7-->Very strong importance \n 8-9-->Extreme importance\n");
			//AHP module
			double ahp[][]=new double[nc][nc]; double in;
			for (int i=0; i<nc; i++)
			{
				for (int j=i+1; j<nc; j++)
				{
						System.out.println("How important is choosing "+criteria[i]+" than "+criteria[j]+" ?\nIf "+criteria[j]+" is important than "+criteria[i]+
								", you can enter the INVERSE values of the degree of your preference in decimal form");
						in=s.nextDouble();
						ahp[i][j]=in;
						ahp[j][i]=1/in;
					}
				ahp[i][i]=1;
				}
			ahp[nc-1][nc-1]=1;
			double tempahp[][]=new double[nc][nc];
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<nc; j++)
				{
					tempahp[i][j]=ahp[i][j];
				}
			}
			 
			double colsum[]=new double[nc]; double sum=0;
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<nc; j++)
				{
					sum+=ahp[j][i];
				}
				colsum[i]=sum;
				sum=0;
			}
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<nc; j++)
				{
					ahp[j][i]=ahp[j][i]/colsum[i];
				}
			}
			double sum2=0.0, weightedsumval[]=new double[nc], weightedratio[]=new double[nc];
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<nc; j++)
				{
					sum2+=ahp[i][j];
				}
				meanweight[i]=sum2/nc;
				sum2=0;
			}
			//Checking consistency----------------------------->>>>
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<nc; j++)
				{
					tempahp[j][i]=tempahp[j][i]*meanweight[i];
				}
			}
			
			double weightedsum=0.0;
			for (int i=0; i<nc; i++)
			{
				for (int j=0; j<nc; j++)
				{
					weightedsum+=tempahp[i][j];
				}
				weightedsumval[i]=weightedsum;
				weightedratio[i]=weightedsumval[i]/meanweight[i];
				weightedsum=0;
			}	
			double sumweightedratio=0.0, lambdamax;
			for (int i=0; i<nc; i++)
			{
				sumweightedratio+=weightedratio[i];
			}
			lambdamax=sumweightedratio/nc;
			double consistencyindex=(lambdamax-nc)/(nc-1);
			System.out.println("Consistency index:" +consistencyindex);
			consistencyratio=consistencyindex/consistencytab[nc-1];
			System.out.println("Consistency ratio: "+consistencyratio );
			if (consistencyratio>=0.1)
			{
				System.out.println("Sorry! Weights supplied by you are inconsistent in the data set (AS CONSISTENCY RATIO IS NOT <10%. Please try again!!");
				System.out.println("======================================================================================================================");
				calculateweight(nc,criteria);
				}
			s.close();
			return meanweight;
	}

}
