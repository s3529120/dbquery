package dbquery;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class dbquery {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void searchHeap(String searchword,int pagesize){
		String line="",recline;
		String filename="heapfile"+pagesize+".dat";
		String[] record;
		ArrayList<String> matches=new ArrayList<String>();
		
		int pages=1;
		//Read in heap file
		try (BufferedReader br = new BufferedReader(new FileReader(filename))){
			//Read in each line
			while((line=br.readLine())!=null) {
				//If new page marker encountered
				if(line.compareTo("<")==0){
					//Print matched record finds for page
					for(String rec:matches){
						System.out.println("Page "+pages+":");
						System.out.println(rec);
					}
					System.out.print("\n");
					
					//Increment page number
					pages++;
					
					//Reset matches
					matches=new ArrayList<String>();
					
					//Continue reading from next page
					continue;
				}
				
				//Split record line and generate print string
				record=line.split(">");
				if(record[0].compareToIgnoreCase(searchword)==0){
					recline="RECORD MATCH "+matches.size()+1+":\n";
					recline.concat("Business name: "+record[0]+"\n");
					recline.concat("Status: "+record[1]+"\n"); 
					recline.concat("Registration date: "+record[2]+"\n");
					recline.concat("Cancellation date: "+record[3]+"\n"); 
					recline.concat("Renewal date: "+record[4]+"\n"); 
					recline.concat("Former state number: "+record[5]+"\n"); 
					recline.concat("Former state of registration: "+record[6]+"\n"); 
					recline.concat("ABN: "+record[7]+"\n");
					matches.add(recline);
					
				}
				
				
			}
			}catch(FileNotFoundException e){
				
			}catch(IOException e){
				
			}
			
	}

}
