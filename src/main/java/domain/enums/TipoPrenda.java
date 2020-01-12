package domain.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoPrenda{
		Remera			(Categoria.Superior,
						Arrays.asList(Material.Algodon,Material.Lycra,Material.Seda, Material.Saten,Material.Gamuza,Material.Chiffon, Material.Poliester,Material.Mezclilla)
						),
		RemeraMangaCorta(Categoria.Superior,
						Arrays.asList(Material.Algodon,Material.Lycra,Material.Seda, Material.Saten,Material.Gamuza,Material.Chiffon, Material.Poliester,Material.Mezclilla)
						),
		RemeraMangaLarga(Categoria.Superior,
						Arrays.asList(Material.Algodon,Material.Lycra,Material.Seda, Material.Saten,Material.Gamuza,Material.Chiffon, Material.Poliester,Material.Mezclilla)
						),
		CamisaMangaCorta(Categoria.Superior,
						Arrays.asList(Material.Algodon,Material.Lycra,Material.Seda, Material.Saten,Material.Gamuza, Material.Jean,Material.Mezclilla,Material.Lino)
						),
		CamisaMangaLarga(Categoria.Superior,
						Arrays.asList(Material.Algodon,Material.Lycra,Material.Seda, Material.Saten,Material.Gamuza, Material.Jean,Material.Mezclilla,Material.Lino)
						),
		Pantalon		(Categoria.Inferior,
						Arrays.asList(Material.Jean, Material.Spandex,Material.Cuero,Material.Gabardina,Material.Modal,Material.Lino,Material.Mezclilla)
						),
		Short			(Categoria.Inferior,
						Arrays.asList(Material.Jean, Material.Spandex,Material.Cuero,Material.Gabardina,Material.Modal,Material.Lino,Material.Mezclilla)
						),
		PolleraCorta	(Categoria.Inferior,
						Arrays.asList(Material.Lanilla,Material.Jean, Material.Cuero,Material.Gabardina,Material.Spandex)
						),
		PolleraLarga	(Categoria.Inferior,
						Arrays.asList(Material.Lanilla,Material.Jean, Material.Cuero,Material.Gabardina,Material.Spandex)
						),
		Bermuda			(Categoria.Inferior,
						Arrays.asList(Material.Jean, Material.Spandex,Material.Cuero,Material.Gabardina,Material.Modal,Material.Lino,Material.Mezclilla)
						),
		Calza			(Categoria.Inferior,
						Arrays.asList(Material.Algodon,Material.Mezclilla,Material.Poliester)
						),
		Jardinero		(Categoria.Inferior,
						Arrays.asList(Material.Jean)
						),
		Zapatos			(Categoria.Calzado,
						Arrays.asList(Material.Cuero)
						),
		Ojotas			(Categoria.Calzado,
						Arrays.asList(Material.Cuero,Material.Poliester,Material.Caucho)
						),
		Botas			(Categoria.Calzado,
						Arrays.asList(Material.Cuero,Material.Poliester,Material.Caucho)
						),
		Zapatillas		(Categoria.Calzado,
						Arrays.asList(Material.Nylon,Material.Lana,Material.Cuero)
						),
		Borcegos		(Categoria.Calzado,
						Arrays.asList(Material.Cuero)
						),
		Gorro			(Categoria.Accesorio,
						Arrays.asList(Material.Mezclilla,Material.Algodon, Material.Lana)
						),
		Gorra			(Categoria.Accesorio,
						Arrays.asList(Material.Cuero,Material.Nylon,Material.Poliester,Material.Algodon)
						),
		Sombrero		(Categoria.Accesorio,
						Arrays.asList(Material.Rayon,Material.Lana)
						),
		Tapado			(Categoria.Superior,
						Arrays.asList(Material.Rayon,Material.Lana)
						),
		Buzo			(Categoria.Superior,
						Arrays.asList(Material.Algodon,Material.Lana,Material.Nylon)
						),
		Campera			(Categoria.Superior,
						Arrays.asList(Material.Algodon,Material.Lana,Material.Nylon)
						);
		//Etc.

		public Categoria categoria;
		public List <Material> materialesPermitidos;
		TipoPrenda(Categoria categoria, List<Material> materiales){
			this.categoria=categoria;
			this.materialesPermitidos=materiales;
		}
}
