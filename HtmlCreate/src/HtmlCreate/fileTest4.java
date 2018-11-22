package HtmlCreate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class fileTest4 {
	public FileWriter fw;
	public PrintWriter pw;

	public void setfileTest4(String name,String title,String contents) {
	File newdir = new File("c:\\tmp");
	File newfile = new File("c:\\tmp\\" + name + ".html");
		if(newdir.mkdirs()) {
			System.out.println("Directory creation succeeded!");
		}else {
			System.out.println("Directory creation not succeeded!");
		}
		try {
			if(newfile.createNewFile()) {
				System.out.println("File creation succeeded!");
			}else {
				System.out.println("File creation not succeeded!");
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		 //ファイルに書き込み権限
		try {
			fw = new FileWriter("c:\\tmp\\" + name + ".html");
			pw = new PrintWriter(new BufferedWriter(fw));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("ファイルに書き込みできません");
		}
		pw.println(
				"<!DOCTYPE html><html><head><meta charset=\"utf-8\">"
						+ "<title>記事投稿 </title><link rel=\"stylesheet\" href=\"blog2.css\">"
								+ "<meta name=\"viewport\" content=\"width=device-width\"></head>"
										+ "<body><section>");
		pw.println("<h2>" + title + "</h2>");
		pw.println("<p>" + contents + "</p>");
		pw.println("<p>これはjavaを使って投稿しています</p>");
		pw.println("</section></body></html>");

    	pw.close();
	}


}