package metier.genea;

import java.io.*;
import java.util.ArrayList;




/**
 * Classe permettant de manipuler la liste des fiches généalogiques et de les charger ou enregistrer
 * dans un fichier texte. 
 * @author Claude.Duvallet@gmail.com
 *
 */

public class FichierGenealogique {

	private ArrayList<FicheGenealogique> listeFiches;

	private String nomDuFichier = "";

	public String getNomDuFichier() {
		return this.nomDuFichier;
	}
	/**
	 * Constructeur par défaut.
	 */
	public FichierGenealogique (){
		listeFiches = new ArrayList<FicheGenealogique>();
	}
	
	/** 
	 * Méthode permettant de tester si une fiche généalogique existe déjà
	 * @param fiche la fiche généalogique à recherche.
	 * @return la fiche si elle existe sinon null.
	 */
	public FicheGenealogique contains (FicheGenealogique fiche){
		int nombreDeFiches = listeFiches.size();
		int compteur=0;
		FicheGenealogique ficheCourante;
		// On parcours la liste des fiches et on compare chaque fiche de la liste avec la fiche courante.
		while (compteur<nombreDeFiches){
			ficheCourante= listeFiches.get(compteur);
			if (ficheCourante.equals(fiche)) return ficheCourante;
			compteur++;
		}
		return null;
	}
	
	/**
	 * Cette méthode permet d'ajouter une nouvelle fiche généalogique si elle n'existe pas.
	 * @param fiche la nouvelle fiche à ajouter.
	 */
	public void addFicheGenealogique(FicheGenealogique fiche){
		FicheGenealogique ficheExistante;
		if ((ficheExistante=contains(fiche))==null){
			listeFiches.add(fiche);
			ficheExistante=fiche;
		}
		else {
			if ((ficheExistante.getDateDeNaissance()==null) || (ficheExistante.getDateDeNaissance().equals("")))
				ficheExistante.setDateDeNaissance(fiche.getDateDeNaissance());
			if ((ficheExistante.getVilleDeNaissance()==null) || (ficheExistante.getVilleDeNaissance().equals("")))
				ficheExistante.setVilleDeNaissance(fiche.getVilleDeNaissance());
			if ((ficheExistante.getDeptDeNaissance()==null) || (ficheExistante.getDeptDeNaissance().equals("")))
				ficheExistante.setDeptDeNaissance(fiche.getDeptDeNaissance());
			if ((ficheExistante.getDateDeDeces()==null) || (ficheExistante.getDateDeDeces().equals("")))
				ficheExistante.setDateDeDeces(fiche.getDateDeDeces());
			if ((ficheExistante.getVilleDeDeces()==null) || (ficheExistante.getVilleDeDeces().equals("")))
				ficheExistante.setVilleDeDeces(fiche.getVilleDeDeces());
			if ((ficheExistante.getDeptDeDeces()==null) || (ficheExistante.getDeptDeDeces().equals("")))
				ficheExistante.setDeptDeDeces(fiche.getDeptDeDeces());
			if ((ficheExistante.getDateDeMariage()==null) || (ficheExistante.getDateDeMariage().equals("")))
				ficheExistante.setDateDeMariage(fiche.getDateDeMariage());
			if ((ficheExistante.getVilleDeMariage()==null) || (ficheExistante.getVilleDeMariage().equals("")))
				ficheExistante.setVilleDeMariage(fiche.getVilleDeMariage());
			if ((ficheExistante.getDeptDeMariage()==null) || (ficheExistante.getDeptDeMariage().equals("")))
				ficheExistante.setDeptDeMariage(fiche.getDeptDeMariage());
			if (ficheExistante.getPere()==null)
				ficheExistante.setPere(fiche.getPere());
			if (ficheExistante.getMere()==null)
				ficheExistante.setMere(fiche.getMere());
		}
		FicheGenealogique ficheResultat;
		if (ficheExistante.getPere()!=null){
			ficheResultat = getPere(ficheExistante);
			if (ficheResultat!=null) ficheExistante.setPere(ficheResultat);
		}
		if (ficheExistante.getMere()!=null){
			ficheResultat = getMere(ficheExistante);
			if (ficheResultat!=null) ficheExistante.setMere(ficheResultat);
		}
	}

	/**
	 * Méthode permettant de retrouver la fiche généalogique d'un père
	 * @param fiche celle dont on cherche le père.
	 * @return le résultat de la recherche soit le père soit null.
	 */
	public FicheGenealogique getPere(FicheGenealogique fiche){
		FicheGenealogique ficheResultat;
		int nombreDeFiches = listeFiches.size();
		int compteur=0;
		if (fiche.getPere()!=null)
			while (compteur<nombreDeFiches){
				ficheResultat= listeFiches.get(compteur);
				if ((ficheResultat.getNom().equals(fiche.getPere().getNom())) && 
						(ficheResultat.getPrenom().equals(fiche.getPere().getPrenom()))){
					return ficheResultat;
				}
				compteur++;
			}
		
		return null;
	}
	
	/**
	 * Méthode permettant de retrouver la fiche généalogique d'un mère
	 * @param fiche celle dont on cherche la mère.
	 * @return le résultat de la recherche soit le mère soit null.
	 */
	public FicheGenealogique getMere(FicheGenealogique fiche){
		FicheGenealogique ficheResultat;
		int nombreDeFiches = listeFiches.size();
		int compteur=0;
		if (fiche.getMere()!=null)
			while (compteur<nombreDeFiches){
				ficheResultat= listeFiches.get(compteur);
				
				if ((ficheResultat.getNom().equals(fiche.getMere().getNom())) 
						&& (ficheResultat.getPrenom().equals(fiche.getMere().getPrenom()))){
					return ficheResultat;
				}
				compteur++;
			}
		
		return null;
	}

	/**
	 * Lecture des fiches généalogiques à partir d'un fichier.
	 * @param nomDuFichier nom du fichier contenant les fiches.
	 */

	public void chargerFichier(String nomDuFichier){
		FileReader fr=null;
		String text;
		System.out.println("Chargement du fichier généalogique "+nomDuFichier);
		try {
			// ouverture du fichier en mode lecture
			fr = new FileReader(nomDuFichier);
			BufferedReader bufferReader = new BufferedReader(fr);
			// écriture de la ligne de texte
			while ((text=bufferReader.readLine())!=null)
				traiterLigne(text);
			// fermeture du fichier
			fr.close();
		} catch (IOException e) {
			System.out.println("Problème d'écriture dans le fichier "+nomDuFichier);
		}
		// On va refaire les liens au niveau des pères et des mères
		int nombreDeFiches = listeFiches.size();
		int compteur=0;
		FicheGenealogique fg;

		while (compteur<nombreDeFiches){
			fg= listeFiches.get(compteur);
			FicheGenealogique ficheResultat;
			ficheResultat = getPere(fg);
			if (ficheResultat!=null)
				fg.setPere(ficheResultat);
			else fg.setPere(null);
			
			ficheResultat = getMere(fg);
			if (ficheResultat!=null) 
				fg.setMere(ficheResultat);
			else fg.setMere(null);
			
			compteur++;

			this.nomDuFichier = nomDuFichier;
		}
		
	}
	
	/**
	 * Méthode permettant d'enregistrer toutes les fiches généalogiques dans un fichier texte.
	 * @param nomDuFichier le nom du fichier à enregistrer
	 */
	public void enregistrerFichier(String nomDuFichier){
		FileWriter fw=null;
		System.out.println("Enregistrement de toutes les fiches généalogiques.");
		try {
			// ouverture du fichier en mode écriture
			fw = new FileWriter(nomDuFichier,false);
			// écriture des lignes de texte
			int nombreDeFiches = listeFiches.size();
			int compteur=0;
			FicheGenealogique fg;
			String ligne;
			while (compteur<nombreDeFiches){
				fg= listeFiches.get(compteur);
				ligne=fg.convertToString();
				fw.write(ligne);
				compteur++;
			}
			// fermeture du fichier
			fw.close();
		} catch (IOException e) {
			System.out.println("Problème d'écriture dans le fichier "+nomDuFichier);
		}
	}
	
	/**
	 * Méthode permettant de récupérer les informations situées dans les lignes du fichier texte.
	 * Chaque ligne possède toutes les informations d'une fiche généalogique. 
	 * @param text la ligne de texte à traiter.
	 */
	private void traiterLigne(String text) {
		//System.out.println(text);
		String [] elements = text.split(";",16);
		FicheGenealogique ficheGenealogique = new FicheGenealogique (elements[0],elements[1],elements[2],elements[3],elements[4]);
		ficheGenealogique.setDateDeMariage(elements[5]);
		ficheGenealogique.setVilleDeMariage(elements[6]);
		ficheGenealogique.setDeptDeMariage(elements[7]);
		ficheGenealogique.setDateDeDeces(elements[8]);
		ficheGenealogique.setVilleDeDeces(elements[9]);
		ficheGenealogique.setDeptDeDeces(elements[10]);

		ficheGenealogique.setPere(new FicheGenealogique(elements[11],elements[12]));
		ficheGenealogique.setMere(new FicheGenealogique(elements[13],elements[14]));

		FicheGenealogique ficheResultat;
		ficheResultat = getPere(ficheGenealogique.getPere());
		if (ficheResultat!=null) 
			ficheGenealogique.setPere(ficheResultat);

		ficheResultat = getPere(ficheGenealogique.getMere());
		if (ficheResultat!=null) 
			ficheGenealogique.setMere(ficheResultat);
		//System.out.println(ficheGenealogique);
		listeFiches.add(ficheGenealogique);
	}

	/**
	 * @return la liste des fiches présentes dans l'outil.
	 */
	public ArrayList<FicheGenealogique> getListeFiches() {
		return listeFiches;
	}

	/**
	 * @param listeFiches la nouvelle liste de fiches à définir.
	 */
	public void setListeFiches(ArrayList<FicheGenealogique> listeFiches) {
		this.listeFiches = listeFiches;
	}
	
	
}
