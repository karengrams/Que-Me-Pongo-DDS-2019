package domain.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoPrenda{
		Remera			(Categoria.Superior,
						Arrays.asList(Material.algodon,Material.lycra,Material.seda, Material.saten,Material.gamuza,Material.chiffon, Material.poliester,Material.mezclilla),
						0,
						"Remera"
						),
		RemeraMangaCorta(Categoria.Superior,
						Arrays.asList(Material.algodon,Material.lycra,Material.seda, Material.saten,Material.gamuza,Material.chiffon, Material.poliester,Material.mezclilla),
						0,
						"Remera manga corta"
						),
		RemeraMangaLarga(Categoria.Superior,
						Arrays.asList(Material.algodon,Material.lycra,Material.seda, Material.saten,Material.gamuza,Material.chiffon, Material.poliester,Material.mezclilla),
						1,
						"Remera manga larga"
						),
		CamisaMangaCorta(Categoria.Superior,
						Arrays.asList(Material.algodon,Material.lycra,Material.seda, Material.saten,Material.gamuza, Material.jean,Material.mezclilla,Material.lino),
						0,
						"Camisa manga corta"
						),
		CamisaMangaLarga(Categoria.Superior,
						Arrays.asList(Material.algodon,Material.lycra,Material.seda, Material.saten,Material.gamuza, Material.jean,Material.mezclilla,Material.lino),
						1,
						"Camisa manga larga"
						),
		Pantalon		(Categoria.Inferior,
						Arrays.asList(Material.jean, Material.spandex,Material.cuero,Material.gabardina,Material.modal,Material.lino,Material.mezclilla),
						2,
						"Pantalon"
						),
		PantalonDeAbrigo(Categoria.Inferior,
					Arrays.asList(Material.jean, Material.spandex,Material.cuero,Material.gabardina,Material.modal,Material.lino,Material.mezclilla),
					4,
					"Pantalon Abrigo"
					),
		Short			(Categoria.Inferior,
						Arrays.asList(Material.jean, Material.spandex,Material.cuero,Material.gabardina,Material.modal,Material.lino,Material.mezclilla),
						1,
						"Short"
						),
		PolleraCorta	(Categoria.Inferior,
						Arrays.asList(Material.lanilla,Material.jean, Material.cuero,Material.gabardina,Material.spandex),
						1,
						"Pollera corta"
						),
		PolleraLarga	(Categoria.Inferior,
						Arrays.asList(Material.lanilla,Material.jean, Material.cuero,Material.gabardina,Material.spandex),
						2,
						"Pollera larga"
						),
		Bermuda			(Categoria.Inferior,
						Arrays.asList(Material.jean, Material.spandex,Material.cuero,Material.gabardina,Material.modal,Material.lino,Material.mezclilla),
						1,
						"Bermuda"
						),
		Calza			(Categoria.Inferior,
						Arrays.asList(Material.algodon,Material.mezclilla,Material.poliester),
						1,
						"Calza"
						),
		Jardinero		(Categoria.Inferior,
						Arrays.asList(Material.jean),
						2,
						"Jardinero"
						),
		Zapatos			(Categoria.Calzado,
						Arrays.asList(Material.cuero),
						3,
						"Zapatos"
						),
		Ojotas			(Categoria.Calzado,
						Arrays.asList(Material.cuero,Material.poliester,Material.caucho),
						0,
						"Ojotas"
						),
		Botas			(Categoria.Calzado,
						Arrays.asList(Material.cuero,Material.poliester,Material.caucho),
						3,
						"Botas"
						),
		Zapatillas		(Categoria.Calzado,
						Arrays.asList(Material.nylon,Material.lana,Material.cuero),
						2,
						"Zapatillas"
						),
		Borcegos		(Categoria.Calzado,
						Arrays.asList(Material.cuero),
						4,
						"Borcegos"
						),
		Gorro			(Categoria.Accesorio,
						Arrays.asList(Material.mezclilla,Material.algodon, Material.lana),
						3,
						"Gorro"
						),
		Gorra			(Categoria.Accesorio,
						Arrays.asList(Material.cuero,Material.nylon,Material.poliester,Material.algodon),
						2,
						"Gorra"
						),
		Sombrero		(Categoria.Accesorio,
						Arrays.asList(Material.rayon,Material.lana),
						2,
						"Sombrero"
						),
		Tapado			(Categoria.Superior,
						Arrays.asList(Material.rayon,Material.lana),
						4,
						"Tapado"
						),
		Buzo			(Categoria.Superior,
						Arrays.asList(Material.algodon,Material.lana,Material.nylon),
						2,
						"Buzo"
						),
		Campera			(Categoria.Superior,
						Arrays.asList(Material.algodon,Material.lana,Material.nylon),
						2,
						"Campera"
						);
		//Etc.

		public Categoria categoria;
		public List <Material> materialesPermitidos;
		public int nivelDeFrio;
		public String tipo;
		TipoPrenda(Categoria categoria, List<Material> materiales,int nivel,String tipo){
			this.categoria=categoria;
			this.materialesPermitidos=materiales;
			this.nivelDeFrio=nivel;
			this.tipo=tipo;
		}
}
