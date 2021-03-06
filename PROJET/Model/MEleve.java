package PROJET.Model;

import java.io.*;
import java.io.Serializable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class MEleve implements Serializable {
	
	private ArrayList<Eleve> lesEleves; 
	
	
	public MEleve() {
		this.lesEleves = new ArrayList<Eleve>(); 
	}
	
	
	public ArrayList<Eleve> getLesEleves(){
		return this.lesEleves;
	}
	
	/* Ajout d'un eleve dans une classe sans groupe */
	public void ajoutEleve(String nomEleve, String prenomEleve, int indexClasse, MClasse lesClasses) {
		if(nomEleve.length() == 0) {
			JOptionPane.showMessageDialog(null, "Erreur : Vous devez indiquer un nom pour l'élève !");
		} else if(prenomEleve.length() == 0) {
			JOptionPane.showMessageDialog(null, "Erreur : Vous devez indiquer un prénom pour l'élève !");
		} else {
			Eleve nouvelEleve = new Eleve(nomEleve, prenomEleve, lesClasses.getLesClasses().get(indexClasse));
			JOptionPane.showMessageDialog(null, "L'élève " + nomEleve + " " + prenomEleve + " a été ajouté.");
			System.out.println("Eleve : " + nomEleve + " " + prenomEleve + " - " + lesClasses.getLesClasses().get(indexClasse).getNiveauClasse() + " " + lesClasses.getLesClasses().get(indexClasse).getNomClasse() + " - Pas de groupe");
		
			/* Ajout d'un nouvel élève dans le fichier saveEleve.txt */
			try {
				FileWriter monFichier = new FileWriter("saveEleve.txt", true);
				BufferedWriter out = new BufferedWriter(monFichier);
				out.write(nomEleve + "\n" + prenomEleve + "\n" + indexClasse + "\n" + "-\n");
				out.close();
			} catch (IOException ex) {
				System.out.println("Erreur : " + ex);
			}
		}
		
	}

	/* Ajout d'un eleve dans une classe avec un groupe */
	public void ajoutEleve(String nomEleve, String prenomEleve, int indexClasse, int indexGroupe, MClasse lesClasses) {
		if(nomEleve.length() == 0) {
			JOptionPane.showMessageDialog(null, "Erreur : Vous devez indiquer un nom pour l'élève !");
		} else if(prenomEleve.length() == 0) {
			JOptionPane.showMessageDialog(null, "Erreur : Vous devez indiquer un prénom pour l'élève !");
		} else {
			Eleve nouvelEleve = new Eleve(nomEleve, prenomEleve, lesClasses.getLesClasses().get(indexClasse), lesClasses.getLesClasses().get(indexClasse).getLesGroupesClasse().get(indexGroupe));
			JOptionPane.showMessageDialog(null, "L'élève " + nomEleve + " " + prenomEleve + " a été ajouté.");
			System.out.println("Eleve : " + nomEleve + " " + prenomEleve + " - " + lesClasses.getLesClasses().get(indexClasse).getNiveauClasse() + " " + lesClasses.getLesClasses().get(indexClasse).getNomClasse() + " - " + lesClasses.getLesClasses().get(indexClasse).getLesGroupesClasse().get(indexGroupe).getNomGroupe());
			
			/* Ajout d'un nouvel élève dans le fichier saveEleve.txt */
			try {
				FileWriter monFichier = new FileWriter("saveEleve.txt", true);
				BufferedWriter out = new BufferedWriter(monFichier);
				out.write(nomEleve + "\n" + prenomEleve + "\n" + indexClasse + "\n" + indexGroupe + "\n");
				out.close();
			} catch (IOException ex) {
				System.out.println("Erreur : " + ex);
			}
		}
		
	}
	
	public void chargerLesEleves(MClasse lesClasses) {
		try {
			Charset  charset = Charset.forName("ISO-8859-1");
			Path path = Paths.get("saveEleve.txt");
			List<String> lignes = Files.readAllLines(path, charset);
			int i = 0, indexClasse = 0, indexGroupe = 0;
			String nomEleve = "", prenomEleve = "";
			Eleve nouvelEleve;
			
			for(String ligne : lignes) {
				if(i == 0) { nomEleve = ligne; }
				if(i == 1) { prenomEleve = ligne; }
				if(i == 2) { indexClasse = Integer.parseInt(ligne); }
				if(i == 3) {  
					if(ligne == "-") {
						nouvelEleve = new Eleve(nomEleve, prenomEleve, lesClasses.getLesClasses().get(indexClasse));
					} else {
						indexGroupe = Integer.parseInt(ligne);
						nouvelEleve = new Eleve(nomEleve, prenomEleve, lesClasses.getLesClasses().get(indexClasse), lesClasses.getLesClasses().get(indexClasse).getLesGroupesClasse().get(indexGroupe));
					}
					lesEleves.add(nouvelEleve);
					i = -1;
				}
				i++;
			} 
		} catch(Exception ex) {
			System.out.println("Erreur : " + ex);
		}			
	}
				
	public void chargerComboBoxEleve(MClasse lesClasses, JComboBox<String> cmbClasse, JComboBox<String> cmbGroupe) {
		for(Classe c : lesClasses.getLesClasses()) {
			cmbClasse.addItem(c.getNiveauClasse() + " " + c.getNomClasse());
		}

		if(lesClasses.getLesClasses().size() > 0) {
			for(Groupe g : lesClasses.getLesClasses().get(0).getLesGroupesClasse()) {
				cmbGroupe.addItem(g.getNomGroupe());
			}
		}
	}

	public void majComboBoxEleve(MClasse lesClasses, JComboBox<String> cmbClasse, JComboBox<String> cmbGroupe) {
		cmbGroupe.removeAllItems();
		for(Groupe g : lesClasses.getLesClasses().get(cmbClasse.getSelectedIndex()).getLesGroupesClasse()) {
			cmbGroupe.addItem(g.getNomGroupe());
		}
	}
	
}
