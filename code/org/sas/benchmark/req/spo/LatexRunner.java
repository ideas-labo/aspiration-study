package org.sas.benchmark.req.spo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.sas.benchmark.pw.Data.Pack;

public class LatexRunner {

	static String prefix = "/Users/"+System.getProperty("user.name")+"/research/potential-publications/w-vs-wo-req/supplementary/materials/";
	static String prefix_output = "/Users/"+System.getProperty("user.name")+"/research/potential-publications/w-vs-wo-req/supplementary/trash/";
	static String prefix_pdf_output = "/Users/"+System.getProperty("user.name")+"/research/potential-publications/w-vs-wo-req/supplementary/pdf/";
	// static String f = "w-vs-p.tex";
	/**
	 * @param args
	 */
	// pdflatex -synctex=1 -interaction=nonstopmode --shell-escape w-vs-p.tex
	public static void run(String f) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		// Windows
		processBuilder.command("/Library/TeX/texbin/pdflatex", "-synctex=1",
				"-interaction=nonstopmode", "--shell-escape",
				"-output-directory=" + prefix_output, prefix + f + ".tex");

		try {

			File fi = new File(prefix_output);
			if(!fi.exists()) {
				fi.mkdir();
			}
			
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			int exitCode = process.waitFor();
			System.out.println("\nExited with error code : " + exitCode);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void copyFolder(String f) throws IOException {

		File src = new File(prefix_output + f + ".pdf");
		File dest = new File(
				prefix_pdf_output
						+ f + ".pdf");
		
		File fif = new File(prefix_pdf_output);
		if(!fif.exists()) {
			fif.mkdir();
		}
		
		if(dest.exists()) {
			dest.delete();
		}
		
		if (src.isDirectory()) {

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
			
			
			File fi = new File(prefix_output);
			delete(fi);
		}

	}

	/*public static void generateFile() {

		String eval = "";
		//String time = "\begin{figure*}[t!]\n" + "\\centering\n";
		for (Pack p : Data.packs) {

			for (int i = 0; i < Data.weights.length; i ++) {
				String s = Data.weights[i];

				if (i % 3 == 0) {
					eval += "\\begin{figure*}[h]\n" + "\\centering\n";
				}
				
				eval +=  "\\begin{subfigure}[h]{0.3\\textwidth}\n" +
					"\\includegraphics[width=\\textwidth]{pdf/{"+Data.nameMap.get(p.getBenchmark())+"="+s+"=eval}.pdf}\n" + 
			      "\\subcaption{\\textsc{"+Data.nameMap.get(p.getBenchmark()) + "}, [" + s.split("-")[0] + "," + s.split("-")[1] + "]" +"}\n" + 
			      "\\end{subfigure}\n" + ((i + 1) % 3 == 0 ? "" :  "~\n");
			   
				if ((i+1) % 3 == 0) {
					eval +=   "\\caption{Convergence under equal number of evaluations for \\textsc{" + Data.nameMap.get(p.getBenchmark()) + "}.}\n" +
					"\\end{figure*}\n";
					//eval +=   "\\caption{Convergence under equal running time for \\textsc{" + Data.nameMap.get(p.getBenchmark()) + "}.}\n" +
					//"\\end{figure*}\n";
				}
			}

		}
		
		//eval +=   "\\caption{Convergence under equal number of evaluations.}\n" +
		//"\\end{figure*}\n";
		
		System.out.print(eval);
		
		//"\\caption{Convergence under equal running time.}\n"
	}*/
	
	public static void main (String[] args) {
		 //generateFile();
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
