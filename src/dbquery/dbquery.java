package dbquery;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class dbquery {

	public static void main(String[] args) {
		searchHeap(args[0],args[1]);

	}
	
	public static void searchHeap(String searchword,String pagesize){
		String line="",recline;
		String filename="heapfile."+pagesize+".dat";
		String[] record;
		ArrayList<String> matches=new ArrayList<String>();
		
		int pages=1,records=0;
		//Read in heap file
		//try (BufferedReader br = new BufferedReader(new FileReader(filename))){
		try(DataInputStream in =  new DataInputStream(new FileInputStream(filename))){
			//Read in each line
			boolean remaining=true,sizeIndicator=true;
			while(remaining) {
				records++;
				//Read records per page
				if(sizeIndicator){
					try{
						int s=in.readInt();
						line=String.valueOf(s);
						sizeIndicator=false;
					}catch(EOFException e){
						remaining=false;
						break;
					}
				}
				try{
					line=in.readUTF();
				}catch(EOFException e){
					remaining=false;
				}
				
				//If new page marker encountered
				if(line.compareTo("<")==0){
					System.out.println("Page "+pages+" - "+records+" records:");
					records=0;
					//Print matched record finds for page
					for(String rec:matches){
						System.out.println(rec);
					}
					
					
					//Increment page number
					pages++;
					
					//Reset matches
					matches=new ArrayList<String>();
					
					//Set indicator for reading records per page
					sizeIndicator=true;
					
					//Continue reading from next page
					continue;
				}
				
				//Split record line and generate print string
				record=line.split(">",-1);
				if(record[0].toLowerCase().contains(searchword.toLowerCase())){
					recline="RECORD MATCH "+matches.size()+1+":\n";
					recline=recline.concat("    -Business name: "+record[0]+"\n");
					recline=recline.concat("    -Status: "+record[1]+"\n"); 
					recline=recline.concat("    -Registration date: "+record[2]+"\n");
					recline=recline.concat("    -Cancellation date: "+record[3]+"\n"); 
					recline=recline.concat("    -Renewal date: "+record[4]+"\n"); 
					recline=recline.concat("    -Former state number: "+record[5]+"\n"); 
					recline=recline.concat("    -Former state of registration: "+record[6]+"\n");
					recline=recline.concat("    -ABN: "+record[7]+"\n");
					matches.add(recline);
					
				}
				
				
			}
			}catch(FileNotFoundException e){
				System.err.println(filename+" not found.");
			}catch(IOException e){
				System.err.println("Heapfile read failed.");
				e.printStackTrace();
			}
		
			
	}
	

}
