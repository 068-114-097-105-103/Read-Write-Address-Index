/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personarray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Nick Tomasso
 */
public class PersonArray {

    /**
     * @param args the command line arguments
     */
    private static Person [] people = new Person[200];
    private static String[][] array = new String[200][11];
    private static int count = 0;
    private static final int ARRAY_GROWTH = 1;
    public static void main(String[] args) {
        boolean end = false;
        do{
            System.out.println("1. Read a File"
                    + "\n2. Deduplicate records"
                    + "\n3. Sort records"
                    + "\n4. Find a record"
                    + "\n5. Write mailing labels"
                    + "\n6. Exit");
            Scanner scan = new Scanner (System.in);
            switch (scan.nextInt()){
                case 1:
                    System.out.print("Enter a file name: ");             
                    read(scan.next());
                    break;
                case 2:
                    deDup();
                    compact();
                    break;
                case 3:
                    sort();
                    System.out.println("Data Sorted");
                    break;
                case 4:
                    System.out.print("Enter a new file name: ");
                    write(scan.next());
                    break;
                case 5:
                    System.out.println("Menu closing....Goodbye");
                    end = true;
                    break;
            }
            try{
                sleep(2000);
            }catch(InterruptedException e){
                
            }
            for (int i=0;i<50; i++){
                System.out.println("");
            }
        }while(end==false);
        print(5);
    }
    public static int getCount(){
        return count;
    }
    public static int getLength(){
        return people.length;
    }
    public static void read(String fileName){
        int i = 0;  // To count lines read from file
        String line;  // To contain line read from file
        Scanner inputStream = null; //Scanner to read file
        try{
            inputStream = new Scanner(new File(fileName));
        }catch(FileNotFoundException e){
            
        }
        while(inputStream.hasNextLine()){
            // Read a line from file
            line = inputStream.nextLine();
            // Split the line into fields on the commas
            array[i] = line.split(","); // *****This is where you can copy the fields to your Person
            
                
            for (int j = 0;j<array[i].length;j++){
                array[i][j]= toProperCase(array[i][j]);
            }
            if(i>=1){
                people[i]= new Person (array[i][0],array[i][1],array[i][2],array[i][3],array[i][4],array[i][5],array[i][6],array[i][7],array[i][8],array[i][9],array[i][10]);
            }
            i++;  // Increment i
            
        }
        count=i-1;
        System.out.println(i + " Lines read");
        // Close output file stream
        inputStream.close();
        
    }
    public static int write(String filename){
        int i = 0;  // To count lines written to file
        // This object is used to write data to file
        PrintWriter outputStream = null;
        // Try to open output file stream for writing
        try{
            outputStream = new PrintWriter(filename);
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found");
            return 0;
        }
        // Write data to file
       for(i=1; i<count-1; i++){
            outputStream.println(print(i));
            System.out.println("Write: " + Arrays.toString(array[i]));
        }
        // Close the file stream
        outputStream.close();
        // Return number of lines written
        return i;
    }
    public static void insert(int x){
        people[x-1]=new Person(array[x][0],array[x][1],array[x][2],array[x][3],array[x][4],array[x][5],array[x][6],array[x][7],array[x][8],array[x][9],array[x][10]);
        for(int i = 0; i<11;i++){
            System.out.println(array[x][i]);
        }
    }
    public static Person[] resize(){
        Person[] holder= people;
        Person[] people = new Person[holder.length+ARRAY_GROWTH];
        for(int i=0;i<holder.length;i++){
            people[i]= holder[i];
        }
        return people;
    }
    public static String print(int x){
        String print =
                people[x].getNameF() + " " + people[x].getNameL()+
                "\n" + people[x].getAddressLine1()+
                "\n" + people[x].getCity()+
                " " + people[x].getState()+
                ", " + people[x].getZipCode() + "\n\n";
        System.out.println("ID          " + people[x].getPersonID());
        System.out.println("Name        " + people[x].getNameF() + " " + people[x].getNameL() );
        System.out.println("Address     " + people[x].getAddressLine1());
        System.out.println("City        " + people[x].getCity());
        System.out.println("State       " + people[x].getState());
        System.out.println("Zip         " + people[x].getZipCode());
        System.out.println("Phone       " + people[x].getPhone());
        System.out.println("SSN         " + people[x].getSSN());
        System.out.println("Birth date  " + people[x]);
        System.out.println("Gender      " + people[x].getGender());
        System.out.println("Count       " + count);
        System.out.println("Length      " + people.length);
        return print;
    }
    public static void swap(int x, int y){
        Person holder = people[x];
        people[x] = people[y];
        people[y] = holder;
    }
    public static String toProperCase(String x){
        
        String[] toProper = x.toLowerCase().split("");
        String holder =  toProper[0].toUpperCase();
        if(x.length()==2){
            holder+=toProper[1].toUpperCase();
        }else{
            for(int i = 1;i<toProper.length;i++){
                if(toProper[i-1].equals(" "))
                    holder += toProper[i].toUpperCase();
                else
                    holder +=toProper[i];
            }
        }
        return holder;
    }
    public static void sort(){
        String name1 ="", name2 ="";
        int i =0, j= 0;
        for(i=1;i<count-1;i++){
            for(j= i+1;j<count;j++){
                name1 =  people[i].getNameL()+people[i].getNameF();
                name2 =  people[j].getNameL()+people[j].getNameF();
                if(name1.compareTo(name2)>0){
                    swap(j,i);
                }
            }
        }
    }
    public static boolean isSorted(){
        boolean isSorted = false;
        String name1="", name2="";
        int i =0, j= 0;
        for(i=0;i<count-1;i++){
            name1=people[i].getNameF()+people[i].getNameL();
            name2 = people[i-1].getNameF()+people[i-1].getNameL();            
            if(name1.compareTo(name2)!=0){
                isSorted=false;
            }else if(name1.compareTo(name2)==0){
                isSorted=true;
            }
        }
        return isSorted;
    }
    public static void deDup(){
        int dups = 0;
        String name1="", name2="";
        for(int i = 1;i<count-1; i++){
            for(int j=i+1; j<count;j++){
                name1 = people[i].getNameF()+people[i].getNameL();
                name2 = people[j].getNameF()+people[j].getNameL();
                if(name1.compareTo(name2)==0){
                    people[j].setDelete(true);
                    dups++;
                }
            }
        }
        System.out.println(dups+ " duplicates found");        
    }
    public static void compact(){
        for(int i= 1; i<count-1;i++){    
            if(people[i].getDelete()==true){                System.out.println(people[i]);
                people[i]= people[i+1];
            }   
        }
    }
}
class Person{
    //all my variables declared here, they r name in accordance to the value they represent
    static Scanner scan = new Scanner(System.in);
    Date[] births = new Date[200];
    private static int count=0;
    private int personID = 0;
    private String nameF, nameM, nameL, addressLine1, city, state, zipCode, phoneNum,gender,ssn;
    private boolean delete = false;
    
    Person(String id, String nameF, String nameL, String address, String city, String state, String zip, String phone, String dob, String ssn, String gender){
        this.personID = Integer.parseInt(id);
        this.nameF = nameF;
        this.nameL = nameL;
        this.addressLine1 =address;
        this.city = city;
        this.state = state;
        this.zipCode = zip;
        this.phoneNum = phone;
        births[count]= new Date(dob);
        this.ssn = ssn;
        this.gender = gender;
        this.delete = false;
        count++;
    }
    //all setters set the variable indicated in the name
    public void setPersonID(int x){
        this.personID = x;
    }
    public void setNameF(String nameF){
        this.nameF = nameF;
    }
    public void setNameM(String nameM){
        this.nameM=nameM;
    }
    public void setNameL(String nameL){
        this.nameL = nameL;
    }
    public void setAddress1(String a1){
        this.addressLine1 = a1;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setState(String state){//sets the state using Postal Abbreviations if not already used
        switch (state){
            case "Alabama":
                this.state = "AL";
                break;
            case "Alaska":
                this.state = "AK";
                break;
            case "Arizona":
                this.state = "AZ";
                break;
            case "Arkansas":
                this.state = "AR";
                break;
            case "California":
                this.state = "CA";
                break;
            case "Colorado":
                this.state = "CO";
                break;
            case "Connecticut":
                this.state = "CT";
                break;
            case "Delaware":
                this.state = "DE";
                break;
            case "Florida":
                this.state = "FL";
                break;
            case "Georgia":
                this.state = "GA";
                break;
            case "Hawaii":
                this.state = "HI";
                break;
            case "Idaho":
                this.state = "ID";
                break;
            case "Illinois":
                this.state = "IL";
                break;
            case "Indiana":
                this.state = "IN";
                break;
            case "Iowa":
                this.state = "IA";
                break;
            case "Kansas":
                this.state = "KS";
                break;
            case "Kentucky":
                this.state = "KY";
                break;
            case "Louisiana":
                this.state = "LA";
                break;
            case "Maine":
                this.state = "ME";
                break;
            case "Maryland":
                this.state = "MD";
                break;
            case "Massachusetts":
                this.state = "MA";
                break;
            case "Michigan":
                this.state = "MI";
                break;
            case "Minnesota":
                this.state = "MN";
                break;
            case "Mississippi":
                this.state = "MS";
                break;
            case "Missouri":
                this.state = "MO";
                break;
            case "Montana":
                this.state = "MT";
                break;
            case "Nebraska":
                this.state = "NE";
                break;
            case "Nevada":
                this.state = "NV";
                break;
            case "New Hampshire":
                this.state = "NH";
                break;
            case "New Jersey":
                this.state = "NJ";
                break;
            case "New Mexico":
                this.state = "NM";
                break;
            case "New York":
                this.state = "Ny";
                break;
            case "North Carolina":
                this.state = "NC";
                break;
            case "North Dakota":
                this.state = "ND";
                break;
            case "Ohio":
                this.state = "OH";
                break;
            case "Oklahoma":
                this.state = "OK";
                break;
            case "Oregon":
                this.state = "OR";
                break;
            case "Pennsylvania":
                this.state = "PA";
                break;
            case "Rhode Island":
                this.state = "RI";
                break;
            case "South Carolina":
                this.state = "SC";
                break;
            case "South Dakota":
                this.state = "SD";
                break;
            case "Tennessee":
                this.state = "TN";
                break;
            case "Texas":
                this.state = "TX";
                break;
            case "Utah":
                this.state = "UT";
                break;
            case "Vermont":
                this.state = "VT";
                break;
            case "Virginia ":
                this.state = "VA";
                break;
            case "Washington":
                this.state = "WA";
                break;
            case "West Virginia":
                this.state = "WV";
                break;
            case "Wisconsin":
                this.state = "WI";
                break;
            case "Wyoming":
                this.state = "WY";
                break;
            default:
                this.state = state;
                break;
        }
    }
    public void setZipCode(String zip){
        this.zipCode = zip;
    }
    public void setPhoneNum(String num){
        this.phoneNum = num;
    }
    public void setDelete(boolean b){
        this.delete=b;
    }
    public static boolean equals(Person x, Person y){//checks if one person is equal to another called from main
        boolean equals =false;
        if (x.nameF.equals(y.nameF) && x.nameL.equals(y.nameL) && x.addressLine1.equals(y.addressLine1) && x.city.equals(y.city) && x.state.equals(y.state) &&  x.phoneNum.equals(y.phoneNum)){
            equals=true;
        }else{
            equals=false;
        }
        return equals;
    }
    
    //All getters get the variable in the name of the method
    public String getNameF(){
        return nameF;
    }
    public String getNameM(){
        return nameM;
    }
    public String getNameL(){
        return nameL;
    }
    public String getAddressLine1(){
        return addressLine1;
    }
    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public String getZipCode(){
        return zipCode;
    }
    public String getPhone(){
        return phoneNum;
    }
    public int getPersonID(){
        return personID;
    }
    public String getGender(){
        return gender;
    }
    public String getSSN(){
        return ssn;
    }
    public boolean getDelete(){
        return delete;
    }
    public String getBirth(){
        return births[personID].getBirth();
    }
    public void clone(Person p){ //Instantiate and clone all data in a Person
            
        }
	public Person copy(Person p){ //Copy all data from a argument Person to this Person
            Person copy;
            copy = p;
            return copy;
        }
}
        
//the data for the date object
class Date{
    
    private int MoB, DoB, YoB;
    private String birth;
    Date(String dob){
        birth = dob;
        date1(dob);
    }
    public void date1(String dob){
        String [] dobArray = dob.split("/");
        try{
            MoB = Integer.parseInt(dobArray[0]);
            DoB = Integer.parseInt(dobArray[1]);
            YoB = Integer.parseInt(dobArray[2]);
        }catch(NumberFormatException e){
            System.out.println("You did not enter a valid date");
            date1(dob);
        }
    }
    public String getBirth(){
        return birth;
    }
    
}
