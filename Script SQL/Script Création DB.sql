# Modification de la table produits pour que chaque produit ait un fournisseur
ALTER TABLE produits ADD COLUMN idFournisseur VARCHAR(255);

# Modification de la table vente pour que chaque vente ait un produit
ALTER TABLE vente ADD COLUMN idProduit INT;

# Modification de la table produits pour que chaque produit ait une quantité
ALTER TABLE produits ADD COLUMN quantite INT DEFAULT 0;

create table produits (
	idProduits int,
    nomProduit varchar(50),
    descriptionProduit varchar(50),
    prixProduit float
);

create table vente (
	idVente int,
    dateVente varchar(50),
	montantVente double
);

create table fournisseurs (
	nomFournisseur varchar(50),
    idFournisseur varchar(50)
);

create table rapport (
		idRapport int,
        dateRapport varchar(50),
        contenuRapport varchar(50)
);