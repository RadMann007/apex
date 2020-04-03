@isTest
public class VoyagesTriggerHandler_TEST {

    // On déclare les variables pour pouvoir les utiliser dans les methodes de tests
    public static Date dtoday;
    public static Account testCompteNom;
    public static List<FBC__c> lstFBCs;
    public static List<FCE__c> lstFCEs;
    public static List<Account> lstComptes;
    
    // Le bloc "static" est executé avant chaque method de test
    // Il sert à initialiser les données communnes à toutes les methodes de test
    // c'est notre "jeu de données"
    static {
        // Ajouter les données
        // Ajouter quelques FBC, quelques FCE et des comptes
        System.debug('START STATIC DONNEES');
        
        dtoday = Date.today();
        
        //Ajout comptes 
        testCompteNom = new Account(Name ='TEST1', Entite__c='France', Type='Autres');
        insert testCompteNom;
        
        //ajout quelques FBC
        lstFBCs = new List<FBC__c> {
            new FBC__c (Name = 'FBC0', Tech_cle_unique__c='FBC0'),
            new FBC__c (Name = 'FBC1', Tech_cle_unique__c='FBC1'),
            new FBC__c (Name = 'FBC2', Tech_cle_unique__c='FBC2'),
            new FBC__c (Name = 'FBC3', Tech_cle_unique__c='FBC3'),
            new FBC__c (Name = 'FBC4', Tech_cle_unique__c='FBC4'),
            new FBC__c (Name = 'FBC5', Tech_cle_unique__c='FBC5')
        };
        insert lstFBCs;
        
        //ajout quelques FCE
        lstFCEs = new List<FCE__c> {
            new FCE__c(Name = 'FCE0', Compte__c=testCompteNom.Id, Tech_cle_unique__c='FCE0'),
            new FCE__c(Name = 'FCE1', Compte__c=testCompteNom.Id, Tech_cle_unique__c='FCE1'),
            new FCE__c(Name = 'FCE2', Compte__c=testCompteNom.Id, Tech_cle_unique__c='FCE2')
        };
        insert lstFCEs;       
        
        System.debug('END STATIC DONNEES');
    }   
    
    static testMethod void testAllExists() {
        /* Tester un voyage avec un FBC, FCE et comptes existant */     
        System.debug('START testAllExists');
        
        
        Test.startTest();

        // Insert d'un voyage
        // Il faut s'imaginer faire l'import du voyage depuis un fichier CSV via le dataloader/workbench
        // On ne sait pas remplir les lookups, on ne dispose que des libellés des FBCs, FCEs et Comptes
        Voyage__c voyageTest = new Voyage__c(
            Date__c = dtoday, 
            TECH_FBC_Import__c='FBC1', 
            TECH_FCE_Import__c='FCE1', 
            TECH_Compte_Import__c='TEST1'
        );
        insert voyageTest;

        Test.stopTest();
        
        System.debug('VoyageTest Avant : ' + voyageTest);

        // Relire le voyage pour les champs FCE__c, FBC__c
        voyageTest = [SELECT Id, FCE__c, FBC__c FROM Voyage__c WHERE Id = :voyageTest.Id];       
        
        // Vérifier que les champs FCE__c et FBC__c sont remplis
        System.assertEquals(lstFBCs[1].Id, voyageTest.FBC__c);
        System.assertEquals(lstFCEs[1].Id, voyageTest.FCE__c);

        System.debug('END testAllExists');
        
    }
    

    static testMethod void testFBC() {
        // Tester un voyage avec un FBC absent        
       System.debug('Start test FBC absent');
        
        Test.startTest();
        
        //Insert voyage avec FBC absent.
        Voyage__c voyageTestFBC = new Voyage__c(
            Date__c = dtoday, 
            TECH_FBC_Import__c='QFG1', 
            TECH_FCE_Import__c='FCE2', 
            TECH_Compte_Import__c='TEST1'
        );
        insert voyageTestFBC;
        
        Test.stopTest();

        // Relire le voyage pour les champs FCE__c, FBC__c
        voyageTestFBC = [SELECT Id, FCE__c, FBC__c FROM Voyage__c WHERE Id = :voyageTestFBC.Id];
        
        FBC__c newFbc = [SELECT Id FROM FBC__c WHERE Tech_cle_unique__c = 'QFG1'];
        
        System.assertEquals(newFbc.Id, voyageTestFBC.FBC__c);
        System.assertEquals(lstFCEs[2].Id, voyageTestFBC.FCE__c);
        
    }

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
        
        System.assertEquals(newFce.Id, voyageTestFCE.FCE__c);
        //System.assertEquals(lstFCEs[1].Id, voyageTestFCE.FCE__c);
        
    }
	
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
    }
    /*
    static testMethod void testCompte_FBC_absent() {
        // Tester un voyage avec un FBE et le compte absent
                
    }
    
     static testMethod void testFBC_FCE_absent() {
        // Tester un voyage avec un FBE et le compte absent
        
    }

// Tester 2 voyage avec le meme FBC qui n'existe pas



*/
    /*
    static testMethod void testBulk() {
        // Tester l'ajout de 200 voyages
 
        List<Voyage__c> lstVoyages = new List<Voyage__c>();
        for(Integer ii=0; ii<200; ii++) {
	        Voyage__c v = new Voyage__c(Date__c = dtoday, TECH_FBC_Import__c='FBC'+ii, TECH_FCE_Import__c='FCE'+ii, TECH_Compte_Import__c='TEST');
            lstVoyages.add(v);
        }
        insert lstVoyages;
    }
*/

}