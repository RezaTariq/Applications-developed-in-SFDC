// A controller for BookingCalendar VF page

public class CalendarApp_Class{
 public CalendarApp_Class(){
        string ReservationName='0055';
         reservation = 
                    [select Name, Reference__c, Account__c, Notes__c, Comments__c, No_of_AdultR__c, No_of_ChildR__c, No_of_InfantR__c, 
                    Length_of_Stay__c, Room_Price_total__c,  Select_Arrival_Time__c, Booking_Source__c, Status__c, Account__r.Email__c, Account__r.Name,  
                    Account__r.Phone,  Account__r.Comment__c, Account__r.Country__c, Payment_Status__c FROM Reservation__c WHERE name= :ReservationName]; // a1BN0000001CcBkMAK
                system.debug('reservation  size-->'+reservation.size()); 
    }
    
    @RemoteAction
    public static list<Room__c> getCalendarAppData(string strSelectedDate)
    {
        list<Room__c> lst = new list<Room__c>();
        list<string> lstDT = new list<string>();
        date dt;
        if(strSelectedDate != null && strSelectedDate.trim() !='')
            lstDT = strSelectedDate.split('/');
            
        if(lstDT.size() == 3)
            dt = date.newinstance(integer.valueof(lstDT[2]),  integer.valueof(lstDT[0]), integer.valueof(lstDT[1]));
        
        lst=[Select Id,Name,Reservation__r.Check_in_text__c,Reservation__r.Check_out_text__c,Reservation__r.Check_In__c,Reservation__r.Check_Out__c, Reservation__r.Payment_Status__c, 
                Room_Number__c,Reservation__r.account__c,Reservation__r.account__r.name,
                Reservation__r.account__r.Country__c, Reservation__c,Reservation__r.Name,Reservation__r.status__c from Room__c where Reservation__r.Check_In__c >=: dt OR Reservation__r.Check_Out__c >=: dt];
        return lst;
    }
    
   // public with sharing class ReservationBookedClass{ 
        public static List<Reservation__c> reservation {get; set;}
        public string ReservationName {get; set;}
        public Id     ReservationId{get; set;}
        public Reservation__c res {get; set;} 
        public string RoomNum{get;set;}
       
        public void ReservationBooked(){ 
       string strSelectedDate=ReservationName;
        // code with new logic
        date dt;
        list<Room__c> lst = new list<Room__c>();
        list<string> lstDT = new list<string>();    
        if(strSelectedDate != null && strSelectedDate.trim() !='')
            lstDT = strSelectedDate.split('-');
            system.debug('lstDT.size()-->'+lstDT.size());
        if(lstDT.size() == 3)
            dt = date.newinstance(integer.valueof(lstDT[0]),  integer.valueof(lstDT[1]+1), integer.valueof(lstDT[2]));
        system.debug('dt-->'+dt+' --RoomNum--'+RoomNum);
        RoomNum=RoomNum.trim();
        lst=[Select Id,Name,Reservation__r.Check_in_text__c,
             Reservation__r.Check_out_text__c,Reservation__r.Check_In__c,
             Reservation__r.Check_Out__c, Room_Number__c,Reservation__r.account__c,
             Reservation__r.account__r.name,Reservation__c,Reservation__r.Name, Reservation__r.Payment_Status__c,
             Reservation__r.status__c from Room__c where 
             Room_Number__c=:RoomNum AND Reservation__r.Check_In__c <= :dt AND Reservation__r.Check_Out__c >=:dt];
           system.debug('lst  size-->'+lst.size()); 
            if( lst.size()>0){ //
            ReservationName=lst.get(0).Reservation__r.name;
            }
            //system.debug('lst.get(0).Reservation__r.Reservation__r.Check_In__c-->'+lst.get(0).Reservation__r.Check_In__c);
         //  system.debug('lst.get(0).Reservation__r.Reservation__r.Check_Out__c-->'+lst.get(0).Reservation__r.Check_Out__c);
                system.debug('dt-->'+dt);
            system.debug('enter into ReservationBooked-->'+ReservationName);
          reservation = 
                    [select id,Name, Reference__c, Account__c, Notes__c, Comments__c, No_of_AdultR__c, No_of_ChildR__c, No_of_InfantR__c,  
                    Length_of_Stay__c, Room_Price_total__c , Select_Arrival_Time__c, Booking_Source__c, Status__c, Account__r.Email__c, Account__r.Name,
                    Account__r.Country__c,  Payment_Status__c,
                    Account__r.Phone,  Account__r.Comment__c, (select Id, Name, Room_Number__c from Rooms__r) FROM Reservation__c WHERE name= :ReservationName]; // a1BN0000001CcBkMAK
            if(reservation.size() > 0){
            res= reservation.get(0); 
            ReservationId=res.id;
            }           
                system.debug('reservation  size-->'+reservation.size()); 
                }
        public void cancleRes(){
          system.debug('enter into cancleRes--->'+ReservationId);
           List<Reservation__c> res=[select id,name from Reservation__c where id=:ReservationId];
           delete res;
           }     
}