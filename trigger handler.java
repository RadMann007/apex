public class VoyagesTriggerHandler {
    
    private Map<String, FBC__c> mapFBCs;
    private Map<String, FCE__c>mapFCEs;
    private Map<String, Account>mapComptes;
    
    
    public VoyagesTriggerHandler() {
        mapFBCs = new Map<String, FBC__c>();
        mapFCEs = new Map<String, FCE__c>();
        mapComptes = new Map<String, Account>();
        
    }
    
    public void handleIds(List<Voyage__c> lstVoyages) {
        String cmt;
        initializeMap();
        
		// ....
		//         
        for(Voyage__c v : lstVoyages) {
            System.debug('Compte : ' + v.TECH_Compte_Import__c);
            
            if (mapComptes.containsKey(v.TECH_Compte_Import__c)){
               cmt = mapComptes.get(v.TECH_Compte_Import__c).Id; }           
            else {
                System.debug('L iD du compte qui est saisie :' + cmt);
                System.debug('pas de compte!!! on va creer un nouveau compte');
                
                Account aNewCompte = new Account (Name = v.TECH_Compte_Import__c, Entite__c='France', Type='Autres');
                insert aNewCompte;
                System.debug('le nouveau compte est ajoutée avec success!');
                
                System.debug('le nouveau Compte :'+ aNewCompte);
                cmt = aNewCompte.Id;
                System.debug('le nouveau ID de Compte ' + cmt);
            } 
            
            System.debug('FBC : ' + v.TECH_FBC_Import__c);
            
            if (mapFBCs.containsKey(v.TECH_FBC_Import__c))
                v.FBC__c = mapFBCs.get(v.TECH_FBC_Import__c).Id;
            
            else {
                /*System.debug('le premier ID de FBC' + v.FBC__c);
                System.debug('pas de FBC!!! on va creer un nouveau FBC');  */              
                FBC__c aNewFbc = new FBC__c (Name = v.TECH_FBC_Import__c, Tech_cle_unique__c=v.TECH_FBC_Import__c);
                insert aNewFbc;                
                //System.debug('le nouveau FBC :'+ aNewFbc);
                v.FBC__c = aNewFbc.Id;
                System.debug('le nouveau ID de FBC ' + v.FBC__c);
            }  
            
            System.debug('FCE : ' + v.TECH_FCE_Import__c);
            List<FCE__c> fce = [SELECT Id FROM FCE__c WHERE Name = :v.TECH_FCE_Import__c];
            
            if (fce.size()>0)
            	v.FCE__c = fce[0].Id;
                    
            System.debug('FCE list :' + v.TECH_FCE_Import__c);
           	System.debug('le compte dans FCE :'+ v.TECH_Compte_Import__c);
            
            if (mapFCEs.containsKey(v.TECH_FCE_Import__c))
                v.FCE__c = mapFCEs.get(v.TECH_FCE_Import__c).Id;   
            	
            else{                
                System.debug('Pas de FCE !!! on va creer un nouveau FCE');
                //test ajout nouveau fce                             
                FCE__c aNewFce = new FCE__c (Name = v.TECH_FCE_Import__c, Compte__c=v.TECH_Compte_Import__c, Tech_cle_unique__c=v.TECH_FCE_Import__c);
                insert aNewFce;
                    
                    v.FCE__c = aNewFce.id;
                    System.debug('la valeur du nouveau FCE :'+ aNewFce);                                                   
            } 
            
        }
    }
    
    
    private void initializeMap() {
        
        for(FBC__c fbc : [SELECT Id, Name, Tech_cle_unique__c FROM FBC__c]) {
            mapFBCs.put(fbc.Tech_cle_unique__c, fbc);            
        }
        
        for(FCE__c fce: [SELECT Id, Name, compte__c, Tech_cle_unique__c FROM FCE__c]){
            mapFCEs.put(fce.Tech_cle_unique__c, fce);
            mapFCEs.put(fce.Compte__c, fce);
        }
        
        for(Account compte: [SELECT Id, Name, Entite__c,Type FROM Account]){
          mapComptes.put(compte.Name, compte);
        }
        
    }
    
}