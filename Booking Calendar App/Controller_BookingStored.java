public with sharing class ReservationBookedClass{ 
    public ReservationBookedClass(ApexPages.StandardController controller) {

    }


public List<Reservation__c> reservation {get; set;} 
 public Id     ReservationId{get; set;}
       
public ReservationBookedClass(){ 
reservation = 
[select Name, Reference__c, Account__c, Notes__c, Comments__c, Number_of_Adult__c, Number_of_Child__c, Number_of_Infant__c, 
        Length_of_Stay__c, Select_Arrival_Time__c, Booking_Source__c, Status__c, Account__r.Email__c, Account__r.Name,  
        Account__r.Phone,  Account__r.Comment__c FROM Reservation__c WHERE id= :ReservationID]; // a1BN0000001CcBkMAK :ReservationID  'a1BN0000001CgJoMAK' 
/* 
   if(reservation.size() > 0){
            res= reservation.get(0); 
            ReservationId=res.id;
            }           
                system.debug('reservation  size-->'+reservation.size()); 
                }
                */
} 
}