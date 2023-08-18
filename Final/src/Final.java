import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Final {

	public static void main(String[] args) {
		try
		{
		BufferedWriter bw = new BufferedWriter(new FileWriter("final.csv"));
		bw.write("Results From Algorithm");
		bw.newLine();
		File csvFile = new File("/home/guruthor/Documents/output_got.csv");
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String line = "";
		while ((line = br.readLine()) != null) 
		{
			int flag=0;
			String[] arr = line.split(",");
			if(arr[1].equals("Genuine")||arr[1].equals("Fake")||arr[2].equals("Genuine")||arr[2].equals("Fake")||arr[3].equals("Genuine")||arr[3].equals("Fake")||arr[4].equals("Genuine")||arr[4].equals("Fake")||arr[5].equals("Genuine")||arr[5].equals("Fake")||arr[6].equals("Genuine")||arr[6].equals("Fake"))
				flag=1;
			int sum=check(arr[1])+check(arr[2])+check(arr[3])+check(arr[4])+check(arr[5])+check(arr[6]);
			if(sum>0)
				bw.write(String.format("%s;","Genuine"));
			else if(sum<0)
				bw.write(String.format("%s;","Fake"));
			else if(sum==0&&flag==1)
				bw.write(String.format("%s;","Ambiguous"));
			else
				bw.write(String.format("%s;","No Results Found"));  
			bw.newLine();
		}
		bw.close();
		br.close();
		System.out.println("Done");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}

	}
	public static  int check(String a)
	{
		if(a.equals("Genuine"))
			return 1;
		else if(a.equals("Fake"))
			return -1;
		else 
			return 0;
	}

}
