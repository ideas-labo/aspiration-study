package org.sas.benchmark.req.spo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.femosaa.core.EAConfigure;
/**
 * 
 * For comparing pareto and weight
 * 
 * 
 * 0,1,0,0,1,3,0.6,23,3,250,100,4,250,23,40,0,1.4,47.112,7711.976945
 * 1:1:0:1:1:1:1:1:0:1:0:0:0:1:0:0:0:1:1:1:0:1:0:1:0:0:1:1:0:0:1:0:0:1:1:0:1:1:0:1:0:1:1:1:1:1:1:1:1:1:1:0:1:9:5:2:4:2:29
 * 0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:4:8:6:3:5:7:8
 * @author tao
 *
 */
public class Parser {
 
	
	
	
	//public static String[] keepZero = {"BDBCAll","BDBJAll","X264All"};
	// two objectives
	public static LinkedHashMap<String, Double> map1 = new LinkedHashMap<String, Double>();
	public static LinkedHashMap<String, Double> map2 = new LinkedHashMap<String, Double>();
	public static List<String> seeds = new ArrayList<String>();
	public static double u_threshold = 0.0001;
	public static String[] d_values = null;
	public static String selected = "SS-N";
	
	
	
	public static double l1_t = 0.2;
	public static double l2_t = 0.9;

	public static double h1_t = 0.9;
	public static double h2_t = 0.1;
	
	public static double m1_t = 0.5;
	public static double m2_t = 0.5;
	
	public static double ex1_v = 0.5; 
	public static double ex2_v = 0.5;
	
	public static double[] d1;
	public static double[] d2;
	//x264 Best 244.23Worst 821.963
	// sql Best 12.513Worst 16.851
    public static void main( String[] args )
    {
    	
    	/*org.femosaa.util.HV hv = new org.femosaa.util.HV();
    	
    	double[][] f1 = new double[][]{{0.0,1.0}, {0.0,0.0}, {0.0,0.0}};
    	double[][] f2 = new double[][]{{800,800},{700,900}};
    	
    	System.out.print(hv.hypervolume(f2));
    	
    	if(1==1) return;*/
    	
    	map1.clear();
    	map2.clear();
    	seeds.clear();
    	d_values = null;
    	if(selected.equals("CONEX")) {
    		readConex();
    	} else if(selected.equals("LSTM")) {
    		readLSTM();
    	} else {
    		read(selected);
    	}
    	
    	
    }
    
    public static void readLSTM(){
    	// We only need to get rid of the mandatory one or those that do not change at all.
    	ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
    	String[] names = null;
    	double time = 0.0;
    	File fi = new File("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/LSTM/");
    	for(File f : fi.listFiles()) {
    		try {
    			BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/LSTM/"+f.getName()));
    			String line = null; 
    			
    		
    			if(f.getName().endsWith("1.csv")) {

        			int o = 0;
        			
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				//System.out.print( data.length+"**\n");
        				for(int i = 0; i < data.length+3; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length+1) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					if(i == 2 || i == 3 || i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						k++;
        						continue;
        					}
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        					
        					
        					double v = 0.0;
        					if("L1".equals(data[index])) {
        					 v = 0.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						//System.out.print(v+"**\n");
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    				
    			} else if(f.getName().endsWith("2.csv")) {
    				

        			int o = 0;
        		
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				for(int i = 0; i < data.length+2; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					if(i == 3 || i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						k++;
        						continue;
        					}
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        				
        					
        					double v = 0.0;
        					if("L2".equals(data[index])) {
        					 v = 1.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    				
    			} else if(f.getName().endsWith("3.csv")) {
    				
    				int o = 0;
    			
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				for(int i = 0; i < data.length+1; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length-1) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					if(i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						k++;
        						continue;
        					}
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        					
        					
        					double v = 0.0;
        					if("L3".equals(data[index])) {
        					 v = 2.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    				
    			} else if(f.getName().endsWith("4.csv")) {
    				int o = 0;
    			
        			while ((line = reader.readLine()) != null) {
        				
        				if(line.contains("$") || o==0) {
        					String[] dd = line.split(",");
        					names = dd;
        					for(String s : dd) {
        						System.out.print("\"" + s + "\",\n");
        							
        					}
        					o++;
        					continue;
        				}
        				String r = "";
        				String[] data = line.split(",");
        				int k = 0;
        				int index = 0;
        				for(int i = 0; i < data.length; i++) {
        					
        					if(i == 6 || i ==7) {
        						index++;
        						continue;
        					}
        					
        					if(list.size() < data.length-2) {
        						list.add(new ArrayList<Double>());
        					}
        					ArrayList<Double> subList = list.get(k);
        					
        					/*if(i == 4) {
        						
        						if(!subList.contains(0.0)) {
            						subList.add(0.0);
            					}
        						
        						continue;
        					}*/
        					
        					///r += r.equals("")? data[i] : ":" + data[i];
        					
        					
        					
        					double v = 0.0;
        					if("L4".equals(data[index])) {
        					 v = 3.0;
        					} else {
        					 v = Double.parseDouble(data[index].replace("\"[", "").replace("]\"", ""));
        					}
        					
        					if(!subList.contains(v)) {
        						subList.add(v);
        					}
        					k++;
        					index++;
        				}
        				
        				
        			}
    			}
    			
    			
    			
    			
    			reader.close();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    	
		
		
		HashSet<Integer> set = new HashSet<Integer>();
		
		
		for(int i = 0; i < list.size(); i++) {
			ArrayList<Double> subList = list.get(i);
			// means it cannot be changed and has no variability
			if (subList.size() == 1) {
				set.add(i);
			} else {
				double[] d = new double[subList.size()];
				for(int j = 0; j < subList.size(); j++) {
					d[j] = subList.get(j);
				}
				
				
				Arrays.sort(d);
				
				subList.clear();
				for(int j = 0; j < d.length; j++) {
					subList.add((Double)d[j]);
					System.out.print("Oringal index: " + i + "=" + d[j] + "\n");
				}
				
				
			}
		}
		
		names = new String[] {"a","b","c","d","e","f","size","arch","link"};
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			System.out.print("<item name=\""+ names[i] +"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(list.get(i).size()-1)+"\" price_per_unit=\"0.5\"  />\n");
			}
		
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			if(list.get(i).size() <= 2) {
				System.out.print("<feature name=\""+names[i]+"\" type=\"categorical\" optional=\"true\"/>\n");
			} else {
				System.out.print("<feature name=\""+names[i]+"\" type=\"numeric\" range=\"0 "+(list.get(i).size()-1)+"\" gap=\"1\" />\n");
			}
			}
		}
		
		System.out.print("Unchanged ones: " + set.toString() + "\n");
		//if (1==1)return;
		HashSet<String> print_out = new HashSet<String>();
		List<Double> o1 = new ArrayList<Double>();
		List<Double> o2 = new ArrayList<Double>();
		
		
		for(File f : fi.listFiles()) {
			
			
		
    	
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/LSTM/"+f.getName()));
			String line = null; 
			
			
			
			
			int o = 0;
			
			while ((line = reader.readLine()) != null) {
				
				if(line.contains("$") || o==0) {
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				double v1 = 0;
				double v2 = 0;
				
				if(f.getName().endsWith("1.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length + 3; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
    					if(i == 2 || i == 3 || i == 4) {
    						
    						r += ":" + "0";
    						k++;
    						continue;
    					}
				
						
    					if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L1".equals(s)) {
								s = "0";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[3]) ? 0.0 : Double.valueOf(data[3]);
					v2 = "nan".equals(data[4]) ? 0.0 : Double.valueOf(data[4]);
					
				} else if(f.getName().endsWith("2.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length + 2; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
    					if(i == 3 || i == 4) {
    						
    						r += ":" + "0";
    						k++;
    						continue;
    					}
				
						
    					if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L2".equals(s)) {
								s = "1";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[4]) ? 0.0 : Double.valueOf(data[4]);
					v2 = "nan".equals(data[5]) ? 0.0 : Double.valueOf(data[5]);
					
				} else if(f.getName().endsWith("3.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length + 1; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
    					if(i == 4) {
    						
    						r += ":" + "0";
    						k++;
    						continue;
    					}
				
						
    					if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L3".equals(s)) {
								s = "2";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[5]) ? 0.0 : Double.valueOf(data[5]);
					v2 = "nan".equals(data[6]) ? 0.0 : Double.valueOf(data[6]);
				} else if(f.getName().endsWith("4.csv")) {
					int index = 0;
					int k  = 0;
					for(int i = 0; i < data.length; i++) {
						

    					if(i == 6 || i ==7) {
    						index++;
    						continue;
    					}
    					
    					
						
						if(!set.contains(i)) {
							ArrayList<Double> subList = list.get(k);
							String s = data[index].replace("\"[", "").replace("]\"", "");
							if("L4".equals(s)) {
								s = "3";
							}
							int v = subList.indexOf(Double.parseDouble(s));
							r += r.equals("")? v : ":" + v;
						}
						k++;
						index++;
						
					}
					
					v1 = "nan".equals(data[6]) ? 0.0 : Double.valueOf(data[6]);
					v2 = "nan".equals(data[7]) ? 0.0 : Double.valueOf(data[7]);
				}
				
				
				
				
				if(map1.containsKey(r)) {
					System.out.print(line + " : " + r+ ", current "  +map1.get(r) +" duplicate\n");
				}
				seeds.add(r);
				
			
				
				if(v1 < 0) {
					v1 = Math.abs(v1);
				}
				
				if(v2 < 0) {
					v2 = Math.abs(v2);
				}
						
				map1.put(r, v1);
				map2.put(r, v2);
		
				System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and " + map2.get(r) +"\n");
				
				if(!"nan".equals(data[data.length-1])) {
					  //v1 = 1.0/v1;	
					  time += Double.valueOf(data[data.length-1]);
					  o1.add(v1);
					  o2.add(v2);
					  print_out.add("("+v1+","+v2+")");
				}
					
				//if(!"nan".equals(data[data.length-1]))
				  //time += Double.valueOf(data[data.length-1]);
			}
			
			System.out.print(map1.size() + "\n");
			setAspirationLevel(o1,o2);
			//System.out.print("Mean runtime: " + time/map1.size() + "\n");
			//getSeeds();
			
			Collections.sort(o1);
			Collections.sort(o2);
	    	double t1 = Double.MAX_VALUE;
	    	for (double d : o1) {
	    		if(d < t1 && d != o1.get(0) ) {
	    			t1 = d;
	    		}
	    	}
	    	
	    	double t2 = Double.MAX_VALUE;
	    	for (double d : o2) {
	    		if(d < t2 && d != o2.get(0) ) {
	    			t2 = d;
	    		}
	    	}
	    	
	    	/*for (String s : print_out) {
	    		System.out.print(s + "\n");
	    	}*/
	    	System.out.print(o1.get(0) - t1 + o1.get(0) + "\n");
			System.out.print(o2.get(0) - t2 + o2.get(0));
			System.out.print("min1: " + o1.get(0)+ "\n");
			System.out.print("min2: " +  o2.get(0));
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
    	
    }

    public static void readConex(){
    	// We only need to get rid of the mandatory one or those that do not change at all.
    	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    	ArrayList<String> names = new ArrayList<String>();
    	//String[] names = null;
    	double time = 0.0;
    	try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/conex-perf.csv"));
			String line = null; 
			
		
			int o = 0;
			while ((line = reader.readLine()) != null) {
				
				if(o==0) {
					String[] dd = line.split(",");
				
					for(int k = 1; k < dd.length; k++) {
						if(!dd[k].equals("") && !dd[k].equals("performance")) {
							names.add(dd[k]);
							System.out.print("\"" + dd[k] + "\",\n");
						}
						
							
					}
					o++;
					
					
					System.out.print("names " + names.size()+"\n");
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 1; i < data.length - 1; i++) {
					///r += r.equals("")? data[i] : ":" + data[i];
					if(list.size() <= i-1) {
						
						list.add(new ArrayList<String>());
					}
					//System.out.print(data.length + " " + list.size() + "\n");
					ArrayList<String> subList = list.get(i-1);
					if(!subList.contains(data[i])) {
						//System.out.print(data.length + " " + list.size() + "\n");
						subList.add(data[i]);
					}
					
				}
				
				
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HashSet<Integer> set = new HashSet<Integer>();
		
		
		for(int i = 0; i < list.size(); i++) {
			ArrayList<String> subList = list.get(i);
			// means it cannot be changed and has no variability
			if (subList.size() == 1) {
				set.add(i);
			} else {
				/*String[] d = new String[subList.size()];
				for(int j = 0; j < subList.size(); j++) {
					d[j] = subList.get(j);
				}*/
				
				
				//Arrays.sort(d);
				
				//subList.clear();
				for(int j = 0; j < subList.size(); j++) {
					//subList.add(d[j]);
					System.out.print(names.get(i) + " Oringal index: " + i + "=" + subList.get(j) + "\n");
				}
				
				
			}
			//System.out.print(" <feature name=\""+names.get(i)+"\" type=\"numeric\" range=\"0 "+(subList.size()-1)+"\" gap=\"1\"/>\n");
			//System.out.print("<item name=\""+names.get(i)+"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(subList.size()-1)+"\" price_per_unit=\"0.5\"  />\n");
		}
		
		/*for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			System.out.print("<item name=\""+ names[i] +"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(list.get(i).size()-1)+"\" price_per_unit=\"0.5\"  />\n");
			}
		
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			if(list.get(i).size() <= 2) {
				System.out.print("<feature name=\""+names[i]+"\" type=\"categorical\" optional=\"true\"/> />\n");
			} else {
				System.out.print("<feature name=\""+names[i]+"\" type=\"numeric\" range=\"0 "+(list.get(i).size()-1)+"\" gap=\"1\" />\n");
			}
			}
		}*/
		
		System.out.print("Unchanged ones: " + set.toString() + "\n");
		//if (1==1)return;
		
	
    	
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/conex-perf.csv"));
			String line = null; 
			
			int o = 0;
			while ((line = reader.readLine()) != null) {
				
				
				if(o==0) {
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 1; i < data.length - 1; i++) {
					
					if(!set.contains(i)) {
						ArrayList<String> subList = list.get(i-1);
						int v = subList.indexOf(data[i]);
						/*for(String s : subList) {
							System.out.print(s + "**\n");
						}
						System.out.print(data[i] + " : " + " " + subList.contains(data[i])+"\n");*/
						r += r.equals("")? v : ":" + v;
					}
					
					
					
				}
				
				
				if(map1.containsKey(r)) {
					System.out.print(line + " : " + r+ ", current "  +map1.get(r) +" duplicate\n");
				}
				seeds.add(r);
				map1.put(r, Double.valueOf(data[data.length-1]));
		
				System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and "+"\n");
				time += Double.valueOf(data[data.length-1]);
			}
			
			System.out.print(map1.size() + "\n");
			System.out.print("Mean runtime: " + time/map1.size() + "\n");
			//getSeeds();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
		HashSet<String> print_out = new HashSet<String>();
		List<Double> o1 = new ArrayList<Double>();
		List<Double> o2 = new ArrayList<Double>();
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/conex.txt"));
			String line = null; 
			
			int o = 0;
			ArrayList<Integer> new_ids= new ArrayList<Integer>();
			while ((line = reader.readLine()) != null) {
				
				
				if(o==0) {
					o++;
					String[] data = line.split("	");
					
					for (int k = 0; k < names.size(); k++) {
						
						int l = -1;
						for(int i = 0; i < data.length; i++) {
							if(names.get(k).equals(data[i])) {
								
								l = i;
								System.out.print(new_ids.size() + " : " + data[i] + " " + l+"***\n");
							}
						}
						
						if(l == -1) {
							System.out.print(names.get(k) + " has no found\n");
						}
						
						new_ids.add(l);
						//System.out.print(data.length + " " + l+"***\n");
					}
					
					System.out.print(list.size() + " : " + new_ids.size() + " " +"size***\n");
					continue;
				}
				String r = "";
				String[] data = line.split("	");
				
				/*for(String s : data) {
					System.out.print(s+"\n");
				}
				System.out.print("-----\n");
				*/
				for (int k = 0; k < new_ids.size();k++) {
					ArrayList<String> subList = list.get(k);
					//System.out.print(data.length + " : " + names.get(k) + " : " + data[new_ids.get(k)] + "***\n");
					int v = subList.indexOf(convert(data[new_ids.get(k)]));
					r += r.equals("")? v : ":" + v;
				}
				
				if(map2.containsKey(r)) {
					System.out.print(r+ ", current "  +map2.get(r) +" duplicate\n");
				}
				
				if(!"".equals(data[1])) {
					map2.put(r, Double.valueOf(data[1]));
				}
				
				
		
				System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and "+ map2.get(r)+"\n");
			
				if(map1.containsKey(r) && map2.containsKey(r)) {
					double v1 =  map1.get(r);
					double v2 =  map2.get(r);
					  //v1 = 1.0/v1;	
					  //time += Double.valueOf(data[data.length-1]);
					  o1.add(v1);
					  o2.add(v2);
					  print_out.add("("+v1+","+v2+")");
				}
					
				//time += Double.valueOf(data[data.length-1]);
			}
			
			System.out.print(map2.size() + "\n");
			
			int p = 0;
			for (String s : map1.keySet()) {
				if(map2.containsKey(s)) {
					p++;
				}
			}
			System.out.print(p);
			
			
			setAspirationLevel(o1,o2);
			//System.out.print("Mean runtime: " + time/map1.size() + "\n");
			//getSeeds();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static String convert(String s) {
    	if("FALSE".equals(s)) {
    		return "False";
    	}
    	
    	if("TRUE".equals(s)) {
    		return "True";
    	}
    	
    	return s;
    }
    
    public static void read(String name){
    	// We only need to get rid of the mandatory one or those that do not change at all.
    	ArrayList<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
    	String[] names = null;
    	double time = 0.0;
    	try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/"+name+".csv"));
			String line = null; 
			
		
			int o = 0;
			while ((line = reader.readLine()) != null) {
				
				if(line.contains("$") || o==0) {
					String[] dd = line.split(",");
					names = dd;
					for(String s : dd) {
						System.out.print("\"" + s + "\",\n");
							
					}
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 0; i < data.length - 2; i++) {
					///r += r.equals("")? data[i] : ":" + data[i];
					if(list.size() <= i) {
						list.add(new ArrayList<Double>());
					}
					
					ArrayList<Double> subList = list.get(i);
					if(!subList.contains(Double.parseDouble(data[i]))) {
						subList.add(Double.parseDouble(data[i]));
					}
					
				}
				
				
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HashSet<Integer> set = new HashSet<Integer>();
		
		
		for(int i = 0; i < list.size(); i++) {
			ArrayList<Double> subList = list.get(i);
			// means it cannot be changed and has no variability
			if (subList.size() == 1) {
				set.add(i);
			} else {
				double[] d = new double[subList.size()];
				for(int j = 0; j < subList.size(); j++) {
					d[j] = subList.get(j);
				}
				
				
				Arrays.sort(d);
				
				subList.clear();
				for(int j = 0; j < d.length; j++) {
					subList.add((Double)d[j]);
					System.out.print("Oringal index: " + i + "=" + d[j] + "\n");
				}
				
				
			}
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			System.out.print("<item name=\""+ names[i] +"\" provision=\"0\" constraint=\"-1\" differences=\"1\" pre_to_max=\"0.7\" pre_of_max=\"0.1\" min=\"0\" max=\""+(list.get(i).size()-1)+"\" price_per_unit=\"0.5\"  />\n");
			}
		
		}
		
		for(int i = 0; i < list.size(); i++) {
			if(!set.contains(i)) {
			if(list.get(i).size() <= 2) {
				System.out.print("<feature name=\""+names[i]+"\" type=\"categorical\" optional=\"true\"/>\n");
			} else {
				System.out.print("<feature name=\""+names[i]+"\" type=\"numeric\" range=\"0 "+(list.get(i).size()-1)+"\" gap=\"1\" />\n");
			}
			}
		}
		
		System.out.print("Unchanged ones: " + set.toString() + "\n");
		//if (1==1)return;
		
		HashSet<String> print_out = new HashSet<String>();
		List<Double> o1 = new ArrayList<Double>();
		List<Double> o2 = new ArrayList<Double>();
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/public-data/performance/flash-data/Flash-MultiConfig/Data/"+name+".csv"));
			String line = null; 
			int o = 0;
			
			while ((line = reader.readLine()) != null) {
				
				if(line.contains("$") || o==0) {
					o++;
					continue;
				}
				String r = "";
				String[] data = line.split(",");
				
				for(int i = 0; i < data.length - 2; i++) {
					
					if(!set.contains(i)) {
						ArrayList<Double> subList = list.get(i);
						int v = subList.indexOf(Double.parseDouble(data[i]));
						r += r.equals("")? v : ":" + v;
					}
					
					
					
				}
				
				
				if(map1.containsKey(r)) {
					System.out.print(line + " : " + r+ ", current "  +map1.get(r) +" duplicate\n");
				}
				seeds.add(r);
				
				double v1 = "nan".equals(data[data.length-2]) ? 0.0 : Double.valueOf(data[data.length-2]);
				double v2 = "nan".equals(data[data.length-1]) ? 0.0 : Double.valueOf(data[data.length-1]);
				
				
				if(v1 == 0|| v2 == 0) {
					continue;
				}
				
				if(v1 < 0) {
					v1 = Math.abs(v1);
				}
				
				if(v2 < 0) {
					v2 = Math.abs(v2);
				}
						
				map1.put(r, v1);
				map2.put(r, v2);
		
				//System.out.print(/*line + " : " + */r + "=" + map1.get(r)+ " and " + map2.get(r) +"\n");
				System.out.print("("+Math.log10((1.0/map1.get(r)))+ "," + Math.log10(map2.get(r)) +")\n");
				if(!"nan".equals(data[data.length-1])) {
				 // v1 = 1.0/v1;//
				  v1 = -1.0*v1;	
				  time += Double.valueOf(data[data.length-1]);
				  o1.add(v1);
				  o2.add(v2);
				  print_out.add("("+v1+","+v2+")");
				}
				
				
				
			}
			
			System.out.print(map1.size() + "\n");
			System.out.print(print_out.size() + "\n");
			System.out.print("Mean runtime: " + time/map1.size() + "\n");
			
			
			
			//getSeeds();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	setAspirationLevel(o1,o2);
    	

		Collections.sort(o1);
		Collections.sort(o2);
    	
    	double t1 = Double.MAX_VALUE;
    	for (double d : o1) {
    		if(d < t1 && d != o1.get(0) ) {
    			t1 = d;
    		}
    	}
    	
    	double t2 = Double.MAX_VALUE;
    	for (double d : o2) {
    		//System.out.print(d+"\n");
    		if(d < t2 && d != o2.get(0) ) {
    			t2 = d;
    			
    		}
    	}
    	
    	/*for (String s : print_out) {
    		System.out.print(s + "\n");
    	}*/
    	if(o1.get(0) <0 && t1 < 0) {
    		System.out.print((-1.0*(Math.abs(o1.get(0)) - Math.abs(t1) + Math.abs(o1.get(0)))) + "\n");
    	} else {
    	System.out.print(o1.get(0) - t1 + o1.get(0) + "\n");
    	}
		System.out.print(o2.get(0) - t2 + o2.get(0) + "\n");
		
		
		System.out.print("min 1: " + o1.get(0)+ "\n");
		
		System.out.print("min 2: " + o2.get(0) + "\n");
		
	}
    
    //HashMap<Double, List<Double>> map1, HashMap<Double, List<Double>> map2
    public static void setAspirationLevel(List<Double> o1, List<Double> o2) {
    	double[] d1 = new double[o1.size()];
    	double[] d2 = new double[o2.size()];
    	
    	for ( int i = 0; i < o1.size(); i++) {
    		d1[i] = o1.get(i);
    		d2[i] = o2.get(i);
    	}
    	
    	Arrays.sort(d1);
    	Arrays.sort(d2);
    	
    	int l1 = 0;
    	int l2 = 0;
    	
    	/*if("ShapesAll1".equals(selected) || "Wafer1".equals(selected)) {
    	l1 = (int)Math.round(d1.length * 0.1);//0.1
        l2 = (int)Math.round(d2.length * 0.9);//0.9
    	} else {*/
    		l1 = (int)Math.round(d1.length * l1_t);//0.1 0.7 0.1
            l2 = (int)Math.round(d2.length * l2_t);//0.9 0.95 0.3
            // 0.2, 0.9 for SS-M
            // 0.1, 0.9 for SS-N
            // 0.1, 0.9 for SS-K
            // 0.1, 0.9 for SS-J
            // 0.1, 0.3 for ShapesAll and other dnn
            // 0.3, 0.9 for xgboost4096
    	//}
    	
    	int h1 = 0;
    	int h2 = 0;
    	if("feature6".equals(selected)) {
    		 h1 = (int)Math.round(d1.length * 0.8);//0.8
        	 h2 = (int)Math.round(d2.length * 0.2);//0.2
    	} else {
    		 h1 = (int)Math.round(d1.length * h1_t);//0.9 0.95 0.3
        	 h2 = (int)Math.round(d2.length * h2_t);//0.1 0.7 0.1
        	// 0.9, 0.1 for SS-M
            // 0.9, 0.1 for SS-N
        	// 0.9, 0.1 for SS-K
        	// 0.9, 0.1 for SS-J
        	// others follow SS-M
        	// 0.3, 0.1 for ShapesAll and other dnn (0.4, 0.1 for DiatomSizeReduction)
        	  // 0.9, 0.3 for xgboost4096
    	}
    	
    	int m1 = 0;
    	int m2 = 0;
    	/*if("ShapesAll1".equals(selected)) {
    		m1 = (int)Math.round(d1.length * 0.2);//0.5
        	m2 = (int)Math.round(d2.length * 0.2);//0.5
    	} else {*/
    		m1 = (int)Math.round(d1.length * m1_t);//0.5 0.9 0.2
        	m2 = (int)Math.round(d2.length * m2_t);//0.5 0.9 0.2
            // 0.5, 0.5 for SS-M
            // 0.5, 0.5 for SS-N
        	// 0.5, 0.5 for SS-K
        	// 0.5, 0.5 for SS-J
        	// others follow SS-M
        	// 0.2, 0.2 for ShapesAll and other dnn
        	 // 0.5, 0.5 for xgboost4096
    	//}
          	System.out.print("m1: " + m1 + ", m2: " + m1 + "\n");
    	
      	int u1 = (int)Math.round(d1.length * u_threshold);
    	int u2 = (int)Math.round(d2.length * u_threshold);
    	
    	
      	int ex1 = (int)Math.round(d1.length * 0.98);
    	int ex2 = (int)Math.round(d2.length * 0.98);
    	
    	System.out.print("ex1: " + ex1 + ", ex2: " + ex2 + "\n");
    	
    	ex1_v = d1[ex1];
    	ex2_v = d2[ex2];
    	
    	System.out.print("d1: " + d1[0] + ":" + d1[d1.length-1] + "\n");
    	System.out.print("d2: " + d2[0] + ":" + d2[d2.length-1] + "\n");
    	
    	
    	Parser.d1 = new double[] {d1[0], d1[d1.length-1]};
    	Parser.d2 = new double[] {d2[0], d2[d2.length-1]};
    	double[] l = new double[] {d1[l1],d2[l2]};
    	System.out.print(d1[l1]+ "," + d2[l2]  + "\n");
    	double[] h = new double[] {d1[h1],d2[h2]};
    	System.out.print(d1[h1] + "," + d2[h2] + "\n");
    	double[] m = new double[] {d1[m1],d2[m2]};
    	System.out.print(d1[m1] + "," + d2[m2] + "\n");
    	double[] u = null;
    	/*if ("SS-N".equals(selected)) {
    	   u = new double[] {d1[u1] - 0.5*d1[u1],d2[u2] - 0.5*d2[u2]};
    	} else {
    	   u = new double[] {d1[u1],d2[u2]};
    	}*/
    	u = new double[] {d1[u1],d2[u2]};
    	System.out.print(d1[u1] + "," + d2[u2] + "\n");
    	
    	System.out.print("-----------\n");
    	
    	
    	System.out.print(Math.log10(d1[l1])+ "," + Math.log10(d2[l2])  + "\n");
    	System.out.print(Math.log10(d1[h1]) + "," + Math.log10(d2[h2]) + "\n");
    	System.out.print(Math.log10(d1[m1]) + "," + Math.log10(d2[m2]) + "\n");
    	System.out.print(Math.log10(d1[u1]) + "," + Math.log10(d2[u2]) + "\n");
    	
    	int l_count = 0;
    	int h_count = 0;
    	int m_count = 0;
    	int u_count = 0;
    	for ( int i = 0; i < o1.size(); i++) {
    		if(l[0] >= o1.get(i) && l[1] >= o2.get(i)) {
    			l_count++;
    		}
    		if(h[0] >=  o1.get(i) && h[1] >=  o2.get(i)) {
    			h_count++;
    		}
    		if(m[0] >=  o1.get(i) && m[1] >=  o2.get(i)) {
    			m_count++;
    		}
    		if(u[0] >=  o1.get(i) && u[1] >=  o2.get(i)) {
    			u_count++;
    		}
    	}
    	
    	System.out.print("l_count: " + l_count + "\n");
    	System.out.print("h_count: " + h_count + "\n");
    	System.out.print("m_count: " + m_count + "\n");
     	System.out.print("u_count: " + u_count + "\n");
     	
     	
     	d_values = new String[4];
     	d_values[0] = String.valueOf(l[0])+","+String.valueOf(l[1]);
     	d_values[1] = String.valueOf(h[0])+","+String.valueOf(h[1]);
     	d_values[2] = String.valueOf(m[0])+","+String.valueOf(m[1]);    	
     	d_values[3] = String.valueOf(u[0])+","+String.valueOf(u[1]);
     	
     	//System.out.print(	d_values[3]+"\n");
    }
	
    public static void validateUnchanged(){
    	
    	
    }
    
	public static void validate(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/"+System.getProperty("user.name")+"/research/experiments-data/fuzzy-requirement/single-objective-dataset/"+selected+".csv"));
			String line = null; 
			
			int[] store = null;
			int total = 0;
			while ((line = reader.readLine()) != null) {
				
				if(line.startsWith("$")) {
					String[] d = line.split(",");
					for (int i = 0; i < d.length; i++) {
						//System.out.print("\""+d[i].substring(1) + "\",\n");
					}
					
					continue;
				}
				
				String[] data = line.split(",");
				
				if(store == null) {
					store = new int[data.length - 1];
					for(int i = 0; i < store.length; i++) {
						store[i] = 0;
					}
				}
				
				for(int i = 0; i < store.length; i++) {
					
					if(data[i].equals("1")) {
						store[i] += 1;
					} 
				}
				
				total++;
		
			}
			
			String r = "";
			for(int i = 0; i < store.length; i++) {
				
				if(store[i] == total) { 
					r += i + ",";
				}
			}
			
			System.out.print(r);
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static List<String> getSeeds(){
		
		int no = EAConfigure.getInstance().pop_size;
		List<String> list = new ArrayList<String>();
		
		/*for (int i = 0; i < seeds.size(); i++ ) {
			   System.out.print(i+"\n");
			   list.add(seeds.get(i));	
			}*/
		
		
		
		int gap = seeds.size() / no;
		
		for (int i = 0; i < seeds.size(); i=i+gap ) {
		   System.out.print(i+"\n");
		   list.add(seeds.get(i));	
		}
		
		if (list.size() < no) {
			list.add(seeds.get(seeds.size()-1));
		}
		
		if (list.size() > no) {
			list.remove(list.size()-1);
		}
		
		for (int i = 0; i < list.size(); i++ ) {
			System.out.print(list.get(i) + "\n");
		}
		System.out.print(list.size());
		return list;
		
	}
	
	private static void normalize(){
		double max =  17.894581279143072;
		double v = 4.1823277703510335;
		double min = 0;
		
		v = (v - min) / (max - min);
		
		System.out.print((0.3 * v) + 1.2);
		
		/**
		 *17.894581279143072
10.953841910378587
4.819035135705402
4.1823277703510335
1.0097075186941624
		 */
	}
	
	/**
	 * apache=0.08888888888888889;0.36666666666666664;0.6444444444444445;
	 * bdbc=0.011525532255482631;0.11996467982050739;0.37815312640389964;
	 * bdbj=0.025053422739665463;0.15032053643799279;0.5187532237860143;
	 * llvm=0.290950744558992;0.43413516609392905;0.7205040091638032;
	 * x264=0.26962281884538364;0.6158034940015544;0.9619841691577251;
	 * sql=0.11226371599815588;0.45804518211157225;0.6885661595205165;
	 */
	private static void run_normalize(){
		String[] a = new String[]{"13.0", "14.5", "15.5"};
		double max = 16.851;
		
		double min = 12.513;
		
		for (String s : a) {
			
			double v = Double.parseDouble(s);
			v = (v - min) / (max - min);
			
			System.out.print(v+";");
		}
		
	}
	
	
}
