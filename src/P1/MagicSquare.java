package P1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

//LAB1 TARGET1

public class MagicSquare {
	public static boolean isLegalMagicSquare(String fileName) {
		try {
			Scanner sn = new Scanner(new FileInputStream(fileName));
			String str = "";
			while (sn.hasNextLine()) {
				str += sn.nextLine() + "\n";
			}

			String lines[];
			try {
				lines = str.split("\n");
			} catch (PatternSyntaxException e) {
				sn.close();
				System.out.println(fileName+"：错误：文件格式错误！行应用换行符分隔！");
				//e.printStackTrace();
				return false;
			}
			String thisline[];
			int width;
			int height = lines.length;
			int num[][] = new int[height][height];
			int i = 0, j = 0;
			for (i = 0; i < height; i++) {
				try {
					thisline = lines[i].split("\t");
				} catch (PatternSyntaxException e) {
					sn.close();
					System.out.println(fileName+"：错误：文件格式错误！数字应用tab符分隔！");
					//e.printStackTrace();
					return false;
				}

				width = thisline.length;
				if (width != height) {
					System.out.println(fileName+"：isNotMagicSquare：行列数不相等："+height+"行" + width+"列");
					sn.close();
					return false;
				}

				for (j = 0; j < width; j++) {
					try {
						num[i][j] = Integer.valueOf(thisline[j]);
						if (num[i][j] <= 0) {
							System.out.println(fileName+"：isNotMagicSquare：第" + i + "行第" + j + "列输入为非正整数：" + num[i][j]);
							sn.close();
							return false;
						}
					} catch (NumberFormatException e) {
						System.out.println(fileName+"：错误：文本到整数格式转换错误：" + thisline[j]);
						sn.close();
						//e.printStackTrace();
						return false;
					}
				}
			}
			/*
			 * for(i=0;i<height;i++) { for(j=0;j<height;j++) {
			 * System.out.print(num[i][j]+"\t"); } System.out.print("\n"); }
			 */

			// row sum
			int constnum = -1;
			int sum = 0;
			for (i = 0; i < height; i++) {
				sum = 0;
				for (j = 0; j < height; j++) {
					sum += num[i][j];
				}
				if (constnum == -1)
					constnum = sum;
				else if (constnum != sum) {
					sn.close();
					System.out.println(fileName+"：isNotMagicSquare：行和错误！第" + i + "行和为"  + sum + "，应为" + constnum);
					//System.out.println("row" + ' ' + i + ' ' + j + ' ' + sum + ' ' + constnum);
					return false;
				}
			}

			// col sum
			for (i = 0; i < height; i++) {
				sum = 0;
				for (j = 0; j < height; j++) {
					sum += num[j][i];
				}
				if (constnum != sum) {
					sn.close();
					System.out.println(fileName+"：isNotMagicSquare：列和错误！第" + i + "列和为"  + sum + "，应为" + constnum);
					return false;
				}
			}

			// dia sum
			sum = 0;
			for (i = 0; i < height; i++) {
				sum += num[i][i];
			}
			if (constnum != sum) {
				sn.close();
				System.out.println(fileName+"：isNotMagicSquare：主对角线和错误！ 和：" + sum + "，应该是：" + constnum);
				return false;
			}
			sum = 0;
			for (i = 0; i < height; i++) {
				sum += num[i][height - 1 - i];
			}
			if (constnum != sum) {
				sn.close();
				System.out.println(fileName+"：isNotMagicSquare：副对角线和错误！ 和：" + sum + "， 应该是：" + constnum);
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			System.out.println(fileName+"：错误：未找到文件：" + fileName);
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return false;
	}
	//生成一个n*n的MagicSquare并打印，如果成功则返回True，否则返回假
	public static boolean generateMagicSquare(int n) {
		int magic[][] = new int[n][n];//新建n*n数组
		int row = 0, col = n / 2, i, j, square = n * n;//初始化
		if(n%2==0||n<0) {
			System.out.println("n必须是正奇数！");
			return false;
		}
		
		
		//从第一行的中心开始
		for (i = 1; i <= square; i++) {//循环n个数
			magic[row][col] = i;
			if (i % n == 0) //沿着对角线生成
				row++; //这个对角线填满了，换下一个对角线
			else {//沿着对角线，row--，col++，触碰到边界返回
				if (row == 0)
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		try {
			FileWriter fw = new FileWriter(".\\src\\P1\\txt\\6.txt");
			for (i = 0; i < n; i++) {//逐行输出数组的值
				for (j = 0; j < n; j++){
					System.out.print(magic[i][j] + "\t");
					fw.write(magic[i][j] + "\t");
				}
				System.out.println();
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("文件写出失败！");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		boolean testRes;
		for(int i=1;i<=5;i++) {
			testRes = MagicSquare.isLegalMagicSquare(".\\src\\P1\\txt\\"+i+".txt");
			System.out.println(testRes);
		}
		MagicSquare.generateMagicSquare(9);
		testRes = MagicSquare.isLegalMagicSquare(".\\src\\P1\\txt\\6.txt");
		System.out.println(testRes);
	}
}
