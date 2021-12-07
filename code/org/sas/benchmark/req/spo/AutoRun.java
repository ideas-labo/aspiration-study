package org.sas.benchmark.req.spo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.femosaa.core.SASAlgorithmAdaptor;
import org.femosaa.seed.FixedSeeder;
import org.ssase.requirement.froas.RequirementPrimitive;
import org.ssase.requirement.froas.RequirementProposition;
import org.ssase.requirement.froas.SimpleRequirementProposition;
import org.ssase.util.Repository;
/**
 * 
 * SS-N u=0.0005
 * DiatomSizeReduction u=0.05
 * Coffee u=0.001
 * 
 * *SS-M m=500, pop=10(50), gen=500, no seed
 * *SS-N m=1500, pop=50, gen=500, all seed
 * *SS-K m=500, pop=50, gen=500, no seed
 * *SS-J m=700, pop=30, gen=500, no seed
 * *Adiac m=700, pop=50, gen=500, no seed (m=1000, pop=30, gen=500, no seed)
 * *DiatomSizeReduction - m=500, pop=60, gen=500, no seed
 * *ShapesAll - m=500, pop=60, gen=500, no seed
 * *xgboost4096 m=300, pop=30, gen=500, no seed
 * 
 * CONEX m=1000, pop=10, gen=500, all seed
 * feature6 m=300, pop=10, gen=500, no seed
 * 
 * 
 * feature8
 * 
 * 
 * LSTM - 
 * @author
 *
 */
public class AutoRun {
	/*
	 * new double[] { 500, 1000, 1500, 2000, 2500 }; new double[] { 0.1, 0.8, 5,
	 * 15, 40}; new double[] { 2000, 3300, 5000, 10000, 17000}; new double[] {
	 * 180, 220, 230, 250, 280}; new double[] { 11, 13, 14.5, 15.5, 18}; new
	 * double[] { 230, 400, 600, 800, 1000};
	 */
	// private static double[] ds = new double[] { 2000, 3300, 5000, 10000,
	// 17000};
	//private static String[] weights = new String[] { "1.0-0.0", "0.0-1.0" };
	//private static String[] single_algs = new String[] { "ga" };
	//private static String[] d_pair = new String[] { "0.1-0.1","0.1-0.1","0.1-0.1" };	
	public static String[] propositions = new String[] { "p0-p0","p0-p1","p1-p0","p0-p2","p2-p0","p0-p3","p3-p0","p1-p1","p2-p2","p3-p3","p1-p2","p2-p1","p1-p3","p3-p1","p2-p3","p3-p2"};
	
	//private static String[] d_pair = new String[] { "0.1-0.1" };
	//private static String[] propositions = new String[] { "p1-p1","p2-p2","p3-p3","p1-p2","p2-p1","p1-p3","p3-p1","p2-p3","p3-p2" };
	public static String[] multi_algs = new String[] { /*"nsgaii", "ibea",*/ "moead" };
	private static String benchmark = "Adiac";

	public static void main(String[] args) {

		// double l = Double.MAX_VALUE/0.001;
		// 3.0,48.89200000000001 3.0,33.305
		// System.out.print(l > 1);

		// if(1==1) return;
		
		/*if(benchmark.equals("SS-N")) {
			Parser.u_threshold = 0.00001;
		} else if(benchmark.equals("DiatomSizeReduction")) {
			Parser.u_threshold = 0.05;
		} else if(benchmark.equals("Coffee")) {
			Parser.u_threshold = 0.001;
		} else if(benchmark.equals("CONEX")) {
			Parser.u_threshold = 0.01;
		} else if(benchmark.equals("Adiac")) {
			Parser.u_threshold = 0.001;
		} else if(benchmark.equals("ShapesAll")) {
			Parser.u_threshold = 0.001;
		} else if(benchmark.equals("Wafer")) {
			Parser.u_threshold = 0.001;
		} else if(benchmark.equals("xgboost4096")) {
			Parser.u_threshold = 0.01;
		} else if(benchmark.equals("LSTM")) {
			Parser.u_threshold = 0.01;
		} else if(benchmark.equals("SS-L")) {
			Parser.u_threshold = 0.01;
		} else if(benchmark.equals("SS-C")) {
			Parser.u_threshold = 0.001;
		} else if(benchmark.equals("SS-O")) {
			Parser.u_threshold = 0.01;
		}*/
		
		
		
		Parser.selected = benchmark;
		Simulator.setup();
		SASAlgorithmAdaptor.isFuzzy = true;
		SASAlgorithmAdaptor.isSeedSolution = false;
		boolean runUnrealistic = true;
		//SASAlgorithmAdaptor.logGenerationOfObjectiveValue = 100;
		
	
		
		String[] d_pair = Parser.d_values;

		for (String alg : multi_algs) {

			for (String p : propositions) {

				for (int i = 0; i <d_pair.length;i++) {
					
					
					
					// p0-p0 only runs once
					if(p.equals("p0-p0") && i == 1) {
						break;
					}
					
					if(!runUnrealistic && p.equals("p0-p0")) {
						break;
					}

					// unrelastic aspiration only applied on case where all objectives have aspiration
					if(p.contains("0") && i == 3) {
						continue;
					}
					
					if(!runUnrealistic && i == 3) {
						continue;
					}
					
					/*if(i != 3) {
						continue;
					}*/

					File f = new File("/Users/"+System.getProperty("user.name")+"/research/monitor/ws-soa/sas");

					try {
						if (f.exists()) {
							delete(f);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					RequirementProposition[] rp = getProposition(p,d_pair[i]);
					
					// Will reset so no need to remove
					Repository.setRequirementProposition("sas-rubis_software-P1", rp[0]);
					Repository.setRequirementProposition("sas-rubis_software-P2", rp[1]);
					
					
					//System.out.print( d_pair[i]+"\n");
					
					run_MOEA(alg, p, d_pair[i]);
				}
			}

		}

	}

	public static void run_MOEA(String alg, String p, String d) {
	
			Simulator.alg = alg;

			Simulator.main_test();
			
			if(p.equals("p0-p0")) {
				d = "0,0";
			}

			File source = new File("/Users/"+System.getProperty("user.name")+"/research/monitor/ws-soa/sas");
			File r = new File(
					"/Users/"+System.getProperty("user.name")+"/research/experiments-data/req-vs-mo/configuration-optimization/"
							+ "/" + benchmark + "/" + alg + "/" + p  + "/" + d  + "/" + "/sas");
			File dest = new File(
					"/Users/"+System.getProperty("user.name")+"/research/experiments-data/req-vs-mo/configuration-optimization/"
							+ "/" + benchmark + "/" + alg + "/" + p  + "/" + d  + "/" + "/sas");

			if (r.exists()) {
				System.out.print("Remove " + r + "\n");
				try {
					delete(r);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (!dest.exists()) {
				dest.mkdirs();
			}

			try {
				copyFolder(source, dest);
				if (source.exists()) {
					System.out.print("Remove " + source + "\n");
					delete(source);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out
					.print("End of "
							+ "/Users/"+System.getProperty("user.name")+"/research/experiments-data/req-vs-mo/configuration-optimization/"
							+ "/" + benchmark + "/" + alg + "/" + p  + "/" + d  + "/" + "/sas" + "\n");

		
		File f = new File("/Users/"+System.getProperty("user.name")+"/research/monitor/ws-soa/sas");

		try {
			if (f.exists()) {
				delete(f);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static RequirementProposition[] getProposition(String p, String d) {
		String p1 = p.split("-")[0];
		String p2 = p.split("-")[1];
		String d1 = d.split(",")[0];
		String d2 = d.split(",")[1];
		
		RequirementProposition[] rp = new RequirementProposition[2];
		
		//System.out.print(d1 + "**\n");
		
		if("p0".equals(p1)) {
			rp[0] = 
					new SimpleRequirementProposition(RequirementPrimitive.AS_GOOD_AS_POSSIBLE);
		} else if("p1".equals(p1)) {
			rp[0] = 
					new SimpleRequirementProposition(new BigDecimal(d1).doubleValue(), RequirementPrimitive.AS_GOOD_AS_POSSIBLE_TO_d);
		} else if("p2".equals(p1)) {
			rp[0] = 
			        new SimpleRequirementProposition(new BigDecimal(d1).doubleValue(), RequirementPrimitive.BETTER_THAN_d);
		} else if("p3".equals(p1)) {
			rp[0] = 
					new SimpleRequirementProposition(new BigDecimal(d1).doubleValue(), RequirementPrimitive.AS_GOOD_AS_POSSIBLE, 
							RequirementPrimitive.BETTER_THAN_d);
		}
		
		
		if("p0".equals(p2)) {
			rp[1] = 
					new SimpleRequirementProposition(RequirementPrimitive.AS_GOOD_AS_POSSIBLE);
		} else if("p1".equals(p2)) {
			rp[1] = 
					new SimpleRequirementProposition(new BigDecimal(d2).doubleValue(), RequirementPrimitive.AS_GOOD_AS_POSSIBLE_TO_d);
		} else if("p2".equals(p2)) {
			rp[1] = 
			        new SimpleRequirementProposition(new BigDecimal(d2).doubleValue(), RequirementPrimitive.BETTER_THAN_d);
		} else if("p3".equals(p2)) {
			rp[1] = 
					new SimpleRequirementProposition(new BigDecimal(d2).doubleValue(), RequirementPrimitive.AS_GOOD_AS_POSSIBLE, 
							RequirementPrimitive.BETTER_THAN_d);
		}
		
		return rp;
		
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to "
						+ dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}

	}

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				// System.out.println("Directory is deleted : "
				// + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					// System.out.println("Directory is deleted : "
					// + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			// System.out.println("File is deleted : " +
			// file.getAbsolutePath());
		}
	}
}
