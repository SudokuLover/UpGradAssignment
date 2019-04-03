import java.util.*;

public class UpGrad {

	//keep track of no. of existing student 
	static int element =0;
	
	//keep all grades in separate list to get maxGrade
	static ArrayList<String> grade = null;
	
	// It is holding the complete data.
	static HashMap<String,HashMap<String,ArrayList<Integer>>> data = null;
	
	//creating the requirements
	public static void create(){
			
		grade = new ArrayList<>();
			
		data = new HashMap<>();
	}
	
	// it is used to serve the student
	public static void served(){
		if(element==0)
			return ;
		
		element--;
		
		//sort the gardes to get maxGrade among rest
		Collections.sort(grade);
		
		String maxGrade = grade.remove(grade.size()-1);
		HashMap<String,ArrayList<Integer>> nameToToken = data.get(maxGrade);
		
		ArrayList<String> names = new ArrayList<>();
		for(String s: nameToToken.keySet())
		{
			names.add(s);
		}
		//sorting the name associated to maxGrade to get the string which comes first in ascending order
		Collections.sort(names);
		
		
		String s = names.get(0);
		
		ArrayList<Integer> tokens = nameToToken.get(s);
		
		//sorting the token to get smallest token
		Collections.sort(tokens);
	
		tokens.remove(0);
		
		if(tokens.size()==0){
			nameToToken.remove(s);
		}
		else{
			nameToToken.put(s,tokens);	
		}
		data.put(maxGrade, nameToToken);
	}
	
	
	/*
	 * Storing the data of student as follow 
	 * 
	 * create a HashMap of all grades
	 * 
	 * Many Student may have same grade 
	 * 
	 * so create a HashMap of student name 
	 * 
	 * as there could be many student having same name and grade but token would be different
	 * 
	 * therefore at the end -> create a list of token associated with student name 
	 * 		->  association is done using hashMap
	 * 
	 * Used Above HashMap and associated with corresponding Grade  
	 * 			->  association is done using hashMap
	 * 
	 */
	public static void enter(String name,String marks, int token){
		element++ ;
		
		grade.add(marks);
		
		HashMap<String,ArrayList<Integer>> nameToToken = null;
		ArrayList<Integer> tn = null;
		if(!data.containsKey(marks))
		{
			tn = new ArrayList<>();
			tn.add(token);
			nameToToken = new HashMap<>();
			nameToToken.put(name, tn);
		}
		else{
			nameToToken = data.get(marks);
			if(!nameToToken.containsKey(name))
			{
				tn = new ArrayList<>();
				tn.add(token);
			}
			else{
				tn = nameToToken.get(name);
				tn.add(token);
			}
			nameToToken.put(name, tn);
		}
		
		data.put(marks, nameToToken);
	}
	
	public static void decision(String s){
	
		String p[] = s.split(" ");
		
		//deciding whether to enter the student data or serve the student
		
		if(p.length==1 && p[0].charAt(0)=='S')
		{
			served();
		}
		else{
			enter(p[1],p[2],Integer.parseInt(p[3]));
		}
		
	}
	public static ArrayList<String> result(){
	
		// getting student -> who has not been served 
		
		ArrayList<String> allName = new ArrayList<String>();
		
		Collections.sort(grade);
		for(int i=grade.size()-1;i>=0;i--)
		{
			HashMap<String,ArrayList<Integer>> nameToToken = data.get(grade.get(i));
			ArrayList<String> names = new ArrayList<>();
			for(String s: nameToToken.keySet())
			{
				names.add(s);
			}
			Collections.sort(names);
			
			for(String s : names)
			{
				ArrayList<Integer> p = nameToToken.get(s);
				
				for(int j=0;j<p.size();j++)
				{
					//System.out.println(s+" "+p.size());
					p.remove(0);
					allName.add(s);
				}
			}
		}
		return allName;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int k = sc.nextInt();
		create();	
		
		while(k>0)
		{
			String s = sc.nextLine();
			if(s.equals(""))
				continue;
			decision(s);
			k--;
		}
		
		ArrayList<String> allName = result();
		if(allName.size()==0)
		{
			System.out.println("EMPTY");
		}
		else{
			for(String s : allName)
			{
				System.out.println(s);
			}
		}
	}

}
