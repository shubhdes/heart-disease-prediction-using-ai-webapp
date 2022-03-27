package com.hdp.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader("D:/hdp_test.csv"));
			writer = new BufferedWriter(new FileWriter("D:/hdp_test_out.csv"));
			String line = reader.readLine();
			while (line != null) {
				if (!line.contains("NA")) {
					writer.write(line);
					writer.write("\n");
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
