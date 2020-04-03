//test fce
    static testMethod void testFCE() {
        // Tester un voyage avec un FCE absent
  		System.debug('Start test FCE absent');
        
        Test.startTest();
        
        //Insert voyage avec FCE absent.
        Voyage__c voyageTestFCE = new Voyage__c(
            Date__c = dtoday, 
            TECH_FBC_Import__c='FBC1',
            TECH_FCE_Import__c='QWERTY', 
            TECH_Compte_Import__c=testCompteNom.Id
        );
        insert voyageTestFCE;
        
        Test.stopTest();
        //relire
       	voyageTestFCE = [SELECT Id, FCE__c, FBC__c, TECH_Compte_Import__c FROM Voyage__c WHERE Id = :voyageTestFCE.Id];       
        FCE__c newFce = [SELECT Id FROM FCE__c WHERE Tech_cle_unique__c ='QWERTY'];
        
        System.debug('lstFCE id :'+ lstFCEs[1].Id);
        System.debug('Voyage test Fce :'+ voyageTestFCE.FCE__c);
        
        //System.assertEquals(newFce.Id, voyageTestFCE.FCE__c);
        //System.assertEquals(lstFCEs[1].Id, voyageTestFCE.FCE__c);
        
    }

// test compte

    static testMethod void testCompte() {
        // Tester un voyage avec un compte absent
       System.debug('Start test Compte absent');
        
        Test.startTest();
        
        //Insert voyage avec Compte absent.
        Voyage__c voyageTestCompte = new Voyage__c(
            Date__c = dtoday, 
            TECH_FBC_Import__c='FBC1', 
            TECH_FCE_Import__c='FCE1', 
            TECH_Compte_Import__c='AZERTY'
        );        
        insert voyageTestCompte;
        
        Test.stopTest();
        //relire voyageTestCompte
        voyageTestCompte  = [SELECT Id, FCE__c, FBC__c FROM Voyage__c WHERE Id = :voyageTestCompte.Id];
        Account newCompte = [SELECT Id FROM Account WHERE Name = 'AZERTY'];
        
        //System.assertEquals(newCompte.Id, voyageTestCompte);
    }

 //fce compte test
   static testMethod void testCompte_FCE_absent() {
        // Tester un voyage avec un FCE et le compte absent
        Test.startTest();
        
        //Insert voyage avec FCE et compte absent.         
             Voyage__c voyageTestFCE_COMPTE_ABS = new Voyage__c(
       			Date__c = dtoday, 
             	TECH_FBC_Import__c='FBC1', 
                TECH_FCE_Import__c='FCX', 		//FCE absent
                TECH_Compte_Import__c='CTRY'	//COMPTE absent
             );         
             
        	 insert voyageTestFCE_COMPTE_ABS;
        
        Test.stopTest();
         
         voyageTestFCE_COMPTE_ABS = [SELECT Id, FCE__c, FBC__c FROM Voyage__c WHERE Id = :voyageTestFCE_COMPTE_ABS.Id];
    }
 // test bulk
    /* static testMethod void testBulk() {
        // Tester l'ajout de 200 voyages
 
        List<Voyage__c> lstVoyages = new List<Voyage__c>();
        for(Integer ii=0; ii<200; ii++) {
	        Voyage__c v = new Voyage__c(Date__c = dtoday, TECH_FBC_Import__c='FBC'+ii, TECH_FCE_Import__c='FCE'+ii, TECH_Compte_Import__c='TEST');
            lstVoyages.add(v);
        }
        insert lstVoyages;
    }
*/