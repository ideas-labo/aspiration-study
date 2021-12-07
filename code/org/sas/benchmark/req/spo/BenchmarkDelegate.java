package org.sas.benchmark.req.spo;

import java.util.HashMap;

import org.ssase.model.Delegate;

public class BenchmarkDelegate implements Delegate{

	
	private int obj_index = 0;
	
	
	
	public BenchmarkDelegate(int obj_index) {
		super();
		this.obj_index = obj_index;
	}


    public double predict(double[] xValue) {
		
		String v = "";
		for(int i = 0; i < xValue.length; i++) {
			v += v.equals("")? (int)xValue[i] : ":" + (int)xValue[i];
		}
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		// Windows
		processBuilder.command("sudo sh", prefix + "system-interface", v);
		processBuilder.redirectErrorStream(true);
		
		double r = 0.0;

		try {

			Process process = processBuilder.start();
			int exitCode = process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				r =  Double.parseDouble(line);
				break;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		return r;
	}


	@Override
	public double predict2(double[] xValue) {
		String v = "";
		for(int i = 0; i < xValue.length; i++) {
			v += v.equals("")? (int)xValue[i] : ":" + (int)xValue[i];
		}
	
		HashMap<String, Double> map = obj_index == 0? Parser.map1 : Parser.map2;
		//if(map.containsKey(v)) {
		//	System.out.print(map.containsKey(v) + ": " + v + "***\n");
		//}
		
		
		
		if(map.containsKey(v)) {
			double r = map.get(v);
			if(r == 0) {
				return Double.MAX_VALUE;
			}
			// Only needed for certain benchmarks
			if(obj_index == 0) {			
				r = 1.0/r;
			}
			
			return r*100;
		} else {
			//System.out.print("cannot found " +v+"\n");
			return Double.MAX_VALUE;
		}
		
	
	}

}
