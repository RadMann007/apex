//fce
System.debug('FCE list :' + v.TECH_FCE_Import__c);
System.debug('le compte dans FCE :'+ v.TECH_Compte_Import__c);

if (mapFCEs.containsKey(v.TECH_FCE_Import__c))
    v.FCE__c = mapFCEs.get(v.TECH_FCE_Import__c).Id;   

else{                
    System.debug('Pas de FCE !!! on va creer un nouveau FCE');
        //test ajout nouveau fce                             
    FCE__c aNewFce = new FCE__c (
        Name = v.TECH_FCE_Import__c, 
        Compte__c='test bidon de cmpt', 
        Tech_cle_unique__c=v.TECH_FCE_Import__c
        );
    insert aNewFce; 
    v.FCE__c = aNewFce.id;
    System.debug('la valeur du nouveau FCE :'+ aNewFce);                                                   
}

//compte
System.debug('Compte : ' + v.TECH_Compte_Import__c);

if (mapComptes.containsKey( v.TECH_Compte_Import__c )){
 v.FCE__c = mapComptes.get( v.TECH_Compte_Import__c ).Id;
}     
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

 /* System.debug('FCE : ' + v.TECH_FCE_Import__c);
            List<FCE__c> fce = [SELECT Id FROM FCE__c WHERE Name = :v.TECH_FCE_Import__c];
            
            if (fce.size()>0)
                v.FCE__c = fce[0].Id;
             */

//fce et compte absent
if (mapFCEs.containsKey(v.TECH_FCE_Import__c) && (mapComptes.containsKey(v.TECH_Compte_Import__c))){
                v.FCE__c = mapFCEs.get(v.TECH_FCE_Import__c).Id;
                cmtFce = mapComptes.get(v.TECH_Compte_Import__c).Id;
                    }
            else{
                FCE__c fce_cmt = new FCE__c (
                    Name = v.TECH_FCE_Import__c, 
                    Compte__c=v.TECH_Compte_Import__c, 
                    Tech_cle_unique__c=v.TECH_FCE_Import__c
                );
                insert fce_cmt;
                
                Account Compte_fce = new Account (
                    Name = v.TECH_Compte_Import__c, 
                    Entite__c='France', 
                    Type='Autres'
                );
                insert Compte_fce;
            }


